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

package org.quiltmc.qkl.library.serialization.internal.decoder

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.descriptors.elementNames
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.modules.SerializersModule
import org.quiltmc.qkl.library.serialization.ExtendedDynamicOps.ElementSupport
import org.quiltmc.qkl.library.serialization.internal.*
import org.quiltmc.qkl.library.serialization.internal.util.*
import org.quiltmc.qkl.library.serialization.options.CodecOptions

@OptIn(ExperimentalSerializationApi::class)
internal class DynamicDecoder<T : Any>(
    val ops: DynamicOps<T>,
    input: T,
    private val options: CodecOptions,
    override val serializersModule: SerializersModule
) : AbstractDecoder() {
    private val extendedOps = ops.getExtendedWithDefault()
    private val serializationConfig = SerializationConfig(
        ops,
        extendedOps,
        options
    )

    //state management

    private val stateStack = ArrayDeque<DecoderState<T>>()

    private val currentState: DecoderState<T>
        get() {
            return stateStack.lastOrNull() ?: throw IllegalStateException(
                "Attempting to use decoder state when stack is empty " +
                "(multiple decode* calls outside a structure or mismatched endStructure)"
            )
        }

    init {
        pushState(RootState(input, serializationConfig))
    }

    private fun pushState(state: DecoderState<T>) {
        stateStack.addLast(state)
    }

    private tailrec fun popState() {
        currentState.onComplete()
        stateStack.removeLast()

        if (!stateStack.isEmpty() && !currentState.isStructure) {
            popState()
        }
    }

    //decoding utilities

    private fun getElementAndPop(): Pair<T, ElementOptions> {
        val elementPair = currentState.getElement()

        if (!currentState.isStructure) {
            popState()
        }

        return elementPair
    }

    private fun stringsAllowedForElement(elementOptions: ElementOptions): Boolean {
        return options.allowStringValues ||
               (elementOptions.isMapKey && extendedOps.supportedMapKeys == ElementSupport.STRINGS)
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        return currentState.getNextIndex(descriptor)
    }

    override fun decodeSequentially(): Boolean {
        return currentState.collectionSize != null
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return currentState.collectionSize ?: -1
    }

    //structure elements

    override fun beginStructure(descriptor: SerialDescriptor): CompositeDecoder {
        val (element, elementOptions) = currentState.getElement()

        val newState = when (descriptor.kind) {
            StructureKind.LIST -> ListState(ops.getStream(element).unwrap().toList(), serializationConfig)

            StructureKind.MAP -> if (elementOptions.useEntryListMap || options.useEntryListMaps) {
                EntryListMapState(ops.getStream(element).unwrap().toList(), serializationConfig)
            } else {
                validateKeyDescriptor(
                    descriptor.getElementDescriptor(0),
                    serializersModule,
                    extendedOps.supportedMapKeys
                )

                RegularMapState(ops.getMap(element).unwrap(), serializationConfig)
            }

            StructureKind.CLASS -> ClassState(ops.getMap(element).unwrap(), serializationConfig)
            StructureKind.OBJECT -> ObjectState(ops.getMap(element).unwrap(), serializationConfig)
            is PolymorphicKind -> {
                val discriminator = if (options.useClassPropertyIndices) {
                    "-1"
                } else {
                    descriptor.classDiscriminator ?: options.polymorphism.classDiscriminator
                }
                val isFlattened = descriptor.flattenPolymorphic ?: options.polymorphism.flatten

                if (isFlattened) {
                    validatePolymorphicFields(serializersModule, descriptor, discriminator)
                }

                PolymorphicState(
                    ops.getMap(element).unwrap(),
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
        if (!currentState.isStructure) {
            throw IllegalStateException(
                "endStructure called on a non-structural state ${currentState.javaClass.simpleName}"
            )
        }

        popState()
    }

    //early descriptor validation
    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        if (deserializer.descriptor.isNullable && deserializer.descriptor.isInline) {
            validateNullableInline(deserializer.descriptor, options)
        }

        return super.decodeSerializableValue(deserializer)
    }

    //special elements: inline, nullable, enum, and external (codec elements)

    override fun decodeInline(descriptor: SerialDescriptor): Decoder {
        //backup in case decodeSerializableValue doesn't get called
        //won't catch outer nulls
        if (currentState is NullableState) {
            validateNullableInline(descriptor, options)
        }

        val (element, elementOptions) = currentState.getElement()

        val requiresPrimitives = elementOptions.isMapKey && extendedOps.supportedMapKeys != ElementSupport.ANY
        val useWrapper = !requiresPrimitives && (descriptor.useInlineWrapper ?: options.useInlineWrappers)

        pushState(
            InlineState(
                element,
                useWrapper,
                descriptor,
                elementOptions,
                serializationConfig
            )
        )

        return this
    }

    override fun decodeNotNullMark(): Boolean {
        val (element, elementOptions) = currentState.getElement()

        return if (extendedOps.isNotNull(element)) {
            pushState(NullableState(element, elementOptions, serializationConfig))
            true
        } else {
            false
        }
    }

    override fun decodeNull(): Nothing? {
        //element already discarded in decodeNotNullMark
        if (!currentState.isStructure) {
            popState()
        }

        return null
    }

    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        val (input, elementOptions) = getElementAndPop()
        val stringsAllowed = stringsAllowedForElement(elementOptions)

        if (options.enum.lenientDecoding) {
            return decodeEnumByIndex(input, enumDescriptor, stringsAllowed)
                   ?: decodeEnumByName(input, enumDescriptor, true)
                   ?: throw IllegalArgumentException(
                       "'Enum ${enumDescriptor.serialName}' cannot be decoded from $input, " +
                       "must be an index between 0 and ${enumDescriptor.elementsCount - 1} " +
                       "or one of ${enumDescriptor.elementNames.joinToString { "'$it'" }} (case-insensitive)"
                   )
        }

        return when (options.enum.encoding) {
            CodecOptions.EnumEncoding.Index ->
                decodeEnumByIndex(input, enumDescriptor, stringsAllowed)
                ?: throw IllegalArgumentException(
                    "'Enum ${enumDescriptor.serialName}' cannot be decoded from $input, " +
                    "must be an index between 0 and ${enumDescriptor.elementsCount - 1})"
                )

            is CodecOptions.EnumEncoding.SerialName ->
                decodeEnumByName(input, enumDescriptor, caseInsensitive = options.enum.encoding.caseInsensitive)
                ?: throw IllegalArgumentException(
                    "'Enum ${enumDescriptor.serialName}' cannot be decoded from $input, " +
                    "must be one of ${enumDescriptor.elementNames.joinToString { "'$it'" }} " +
                    if (options.enum.encoding.caseInsensitive) {
                        "(case-insensitive)"
                    } else {
                        "case-sensitive"
                    }
                )

        }
    }

    private fun decodeEnumByName(input: T, enumDescriptor: SerialDescriptor, caseInsensitive: Boolean): Int? {
        val strInput = ops.getStringValue(input).orNull() ?: return null

        val matches = enumDescriptor.elementNames.mapIndexedNotNull { index, name ->
            if (name.equals(strInput, ignoreCase = caseInsensitive)) {
                index to name
            } else {
                null
            }
        }

        if (matches.size > 1) {
            throw IllegalArgumentException(
                "Multiple enum values match input '$strInput': ${matches.joinToString { "'${it.second}'" }}" +
                " (case-insensitive mode is on)".takeIf { caseInsensitive }.orEmpty()
            )
        }

        return matches.firstOrNull()?.first
    }

    private fun decodeEnumByIndex(input: T, enumDescriptor: SerialDescriptor, allowStringInput: Boolean): Int? {
        val intIndex = ops.getNumberValue(input).orNull()?.toInt()

        if (intIndex != null) {
            return intIndex.takeIf { it in 0 until enumDescriptor.elementsCount }
        }

        if (!allowStringInput) {
            return null
        }

        val stringIndex = ops.getStringValue(input).orNull()?.toIntOrNull()

        return stringIndex.takeIf { it in 0 until enumDescriptor.elementsCount }

    }

    fun <A> decodeExternal(codec: Codec<A>, typeName: String): A {
        val (element, elementOptions) = getElementAndPop()
        val stringsAllowed = stringsAllowedForElement(elementOptions)

        val directResult = codec.decode(ops, element)

        if (directResult.result().isPresent || !stringsAllowed) {
            return directResult.unwrap().first
        }

        val stringElement = ops.getStringValue(element).orNull()

        if (stringElement != null) {
            //unknown what this codec expects, so have to try numbers and booleans
            val numberResult = stringElement.toDoubleOrNull()?.let {
                codec.decode(ops, ops.createNumeric(it)).orNull()
            }

            if (numberResult != null) {
                return numberResult.first
            }

            val booleanResult = stringElement.toBooleanStrictOrNull()?.let {
                codec.decode(ops, ops.createBoolean(it)).orNull()
            }

            if (booleanResult != null) {
                return booleanResult.first
            }
        }

        throw IllegalArgumentException(
            "Could not decode value of type '$typeName' with the provided codec: " +
            directResult.error().get().message()
        )
    }

    //primitives

    private inline fun <R : Any> decodeValue(fromInput: (T) -> DataResult<R>, fromString: (String) -> R?): R {
        val (element, elementOptions) = getElementAndPop()
        val stringsAllowed = stringsAllowedForElement(elementOptions)

        val decodedValue = fromInput(element)

        if (decodedValue.result().isPresent || !stringsAllowed) {
            return decodedValue.unwrap()
        }

        val stringDecoded = ops.getStringValue(element).orNull()?.let(fromString)

        return stringDecoded ?: throw IllegalArgumentException(
            "Could not decode element directly or from a string: ${decodedValue.error().get().message()}"
        )
    }

    private inline fun <R : Any> decodeNumber(noinline fromNumber: (Number) -> R, fromString: (String) -> R?): R {
        return decodeValue({ ops.getNumberValue(it).map(fromNumber) }, fromString)
    }

    override fun decodeBoolean(): Boolean = decodeValue(ops::getBooleanValue, String::toBooleanStrictOrNull)

    override fun decodeByte(): Byte = decodeNumber(Number::toByte, String::toByteOrNull)
    override fun decodeShort(): Short = decodeNumber(Number::toShort, String::toShortOrNull)
    override fun decodeInt(): Int = decodeNumber(Number::toInt, String::toIntOrNull)
    override fun decodeLong(): Long = decodeNumber(Number::toLong, String::toLongOrNull)
    override fun decodeFloat(): Float = decodeNumber(Number::toFloat, String::toFloatOrNull)
    override fun decodeDouble(): Double = decodeNumber(Number::toDouble, String::toDoubleOrNull)

    override fun decodeChar(): Char {
        val str = decodeString()

        return str.singleOrNull() ?: throw IllegalArgumentException("Value must be a single character, was '$str'")
    }

    override fun decodeString(): String {
        return ops.getStringValue(getElementAndPop().first).unwrap()
    }

    //debug

    fun collectTrace(): String {
        return stateStack.mapNotNull { it.getElementTrace() }.joinToString(
            separator = "",
            prefix = "$"
        )
    }
}
