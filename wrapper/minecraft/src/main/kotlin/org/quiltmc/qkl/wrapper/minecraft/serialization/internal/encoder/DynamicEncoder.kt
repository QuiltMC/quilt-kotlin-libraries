/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal.encoder

import com.mojang.serialization.DynamicOps
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import org.quiltmc.qkl.wrapper.minecraft.serialization.ExtendedDynamicOps.ElementSupport
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.*
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.*
import org.quiltmc.qkl.wrapper.minecraft.serialization.options.CodecOptions

@OptIn(ExperimentalSerializationApi::class)
internal class DynamicEncoder<T : Any>(
    val ops: DynamicOps<T>,
    private val prefix: T,
    private val options: CodecOptions,
    override val serializersModule: SerializersModule
) : AbstractEncoder() {
    lateinit var result: T
    private val extendedOps = ops.getExtendedWithDefault()
    private val serializationConfig = SerializationConfig(
        ops,
        extendedOps,
        options
    )

    //state management

    private val stateStack = ArrayDeque<EncoderState<T>>()

    private val currentState: EncoderState<T>
        get() {
            return stateStack.lastOrNull() ?: throw IllegalStateException(
                "Attempting to use encoder state when stack is empty " +
                "(multiple encode* calls outside a structure or mismatched endStructure)"
            )
        }

    init {
        pushState(RootState(serializationConfig))
    }

    private fun pushState(state: EncoderState<T>) {
        stateStack.addLast(state)
    }

    private tailrec fun popResult() {
        val stateResult = stateStack.removeLast().build()

        if (stateStack.isEmpty()) {
            result = ops.mergeToPrimitive(prefix, stateResult).unwrap()
            return
        }

        addElementOrNull(checkStringify(stateResult))

        if (currentState is SingleValueState) {
            popResult()
        }
    }

    private fun addElementOrNull(element: T) {
        if (extendedOps.isNotNull(element)) {
            currentState.addElement(element)
        } else {
            currentState.addNull(element)
        }
    }

    private fun addElement(element: T) {
        addElementOrNull(element)

        if (currentState is SingleValueState) {
            popResult()
        }
    }

    //encoding utilities

    private var elementOptions: ElementOptions? = null

    private fun getOptionsOrThrow(): ElementOptions {
        return when (val state = currentState) {
            is SingleValueState -> state.elementOptions
            is StructuredEncoderState -> elementOptions ?: throw IllegalStateException(
                "Null element options but element was not skipped"
            )
        }
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        val state = currentState

        if (state !is StructuredEncoderState) {
            throw IllegalStateException("encodeElement called on a non-structured state ${state.javaClass.simpleName}")
        }

        elementOptions = state.beforeStructureElement(descriptor, index)

        return elementOptions != null
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean {
        return descriptor.encodeDefaults ?: options.encodeDefaults
    }

    private fun checkStringify(value: T): T {
        if (!getOptionsOrThrow().isMapKey || extendedOps.supportedMapKeys != ElementSupport.STRINGS) {
            return value
        }

        ops.getPrimitiveAsString(value)?.let {
            return ops.createString(it)
        }

        //only codec-created values will likely get here
        //everything else is validated in beginStructure
        throw IllegalArgumentException("Value $value is not a primitive and cannot be turned into a string")
    }

    //structures

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        val elementOptions = getOptionsOrThrow()

        val newState = when (descriptor.kind) {
            StructureKind.CLASS -> ClassState(serializationConfig)
            StructureKind.LIST -> ListState(serializationConfig)
            StructureKind.MAP -> {
                if (elementOptions.useEntryListMap || options.useEntryListMaps) {
                    EntryListMapState(serializationConfig)
                } else {
                    validateKeyDescriptor(
                        descriptor.getElementDescriptor(0),
                        serializersModule,
                        extendedOps.supportedMapKeys
                    )

                    RegularMapState(serializationConfig)
                }
            }

            StructureKind.OBJECT -> ObjectState(serializationConfig)
            is PolymorphicKind -> {
                val discriminator = if (options.useClassPropertyIndices) {
                    "-1"
                } else {
                    descriptor.classDiscriminator ?: options.polymorphism.classDiscriminator
                }
                val isFlattened = descriptor.flattenPolymorphic ?: options.polymorphism.flatten

                if (isFlattened && !options.useClassPropertyIndices) {
                    validatePolymorphicFields(serializersModule, descriptor, discriminator)
                }

                PolymorphicState(
                    discriminator,
                    isFlattened,
                    elementOptions,
                    serializationConfig
                )
            }

            else -> throw IllegalArgumentException(
                "Serializer of kind ${descriptor.kind} cannot be a structure"
            )
        }

        pushState(newState)

        return this
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        if (currentState is SingleValueState) {
            throw IllegalStateException(
                "endStructure called on a non-structural state ${currentState.javaClass.simpleName}"
            )
        }

        popResult()
    }

    //special elements: inline, nullable, enum, and external (codec elements)

    override fun encodeInline(descriptor: SerialDescriptor): Encoder {
        val elementOptions = getOptionsOrThrow()
        val requiresPrimitives = elementOptions.isMapKey && extendedOps.supportedMapKeys != ElementSupport.ANY

        val useWrapper = !requiresPrimitives && (descriptor.useInlineWrapper ?: options.useInlineWrappers)

        pushState(InlineState(descriptor, useWrapper, elementOptions, serializationConfig))

        return this
    }

    override fun encodeNotNullMark() {
        pushState(NullableState(getOptionsOrThrow(), serializationConfig))
    }

    override fun encodeNull() {
        addElement(extendedOps.createNull())
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        when (options.enum.encoding) {
            CodecOptions.EnumEncoding.Index -> encodePrimitive(index, ops::createInt)
            is CodecOptions.EnumEncoding.SerialName -> {
                val encodedName = if (options.enum.encoding.caseInsensitive) {
                    enumDescriptor.getElementName(index).lowercase()
                } else {
                    enumDescriptor.getElementName(index)
                }

                encodePrimitive(encodedName, ops::createString)
            }
        }
    }

    fun encodeExternal(value: T) {
        addElement(checkStringify(value))
    }

    //primitives

    private inline fun <V> encodePrimitive(value: V, encode: (V) -> T) {
        val encoded = if (getOptionsOrThrow().isMapKey) {
            ops.createString(value.toString())
        } else {
            encode(value)
        }

        addElement(encoded)
    }

    override fun encodeBoolean(value: Boolean) = encodePrimitive(value, ops::createBoolean)
    override fun encodeByte(value: Byte) = encodePrimitive(value, ops::createByte)
    override fun encodeShort(value: Short) = encodePrimitive(value, ops::createShort)
    override fun encodeInt(value: Int) = encodePrimitive(value, ops::createInt)
    override fun encodeLong(value: Long) = encodePrimitive(value, ops::createLong)
    override fun encodeFloat(value: Float) = encodePrimitive(value, ops::createFloat)
    override fun encodeDouble(value: Double) = encodePrimitive(value, ops::createDouble)

    override fun encodeChar(value: Char) = encodePrimitive(value.toString(), ops::createString)
    override fun encodeString(value: String) = encodePrimitive(value, ops::createString)

    //debug

    fun collectTrace(): String {
        return stateStack.mapNotNull { it.getElementTrace() }.joinToString(
            separator = "",
            prefix = "$"
        )
    }
}
