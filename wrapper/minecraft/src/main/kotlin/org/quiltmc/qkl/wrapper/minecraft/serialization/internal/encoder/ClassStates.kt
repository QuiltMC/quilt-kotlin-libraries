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

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.wrapper.minecraft.serialization.ExtendedDynamicOps.ElementSupport
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.*
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.getPrimitiveAsString
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.orNull
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.unwrap
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.useEntryListMapForElement

@OptIn(ExperimentalSerializationApi::class)
internal class ClassState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : StructuredEncoderState<T>(serializationConfig) {
    private val mapBuilder = ops.mapBuilder()
    private var elementKey: T? = null

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions {
        elementKey = when {
            !options.useClassPropertyIndices -> ops.createString(descriptor.getElementName(index))
            extendedOps.supportedMapKeys == ElementSupport.STRINGS -> ops.createString(index.toString())
            else -> ops.createInt(index)
        }

        return ElementOptions(
            useEntryListMap = descriptor.useEntryListMapForElement(index)
        )
    }

    override fun addNull(nullElement: T) {
        if (!options.explicitNulls) {
            elementKey = null
            return
        }

        addElement(nullElement)
    }

    override fun addElement(element: T) {
        val key = elementKey ?: throw IllegalStateException("No key set for element $element")

        mapBuilder.add(key, element)

        elementKey = null
    }

    override fun build(): T {
        return mapBuilder.build(ops.empty()).unwrap()
    }

    override fun getElementTrace(): String? {
        return elementKey?.let { ".${ops.getPrimitiveAsString(it)}" }
    }
}

internal class PolymorphicState<T : Any>(
    private val discriminator: String,
    private val isFlattened: Boolean,
    private val parentOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : StructuredEncoderState<T>(serializationConfig) {
    private lateinit var serialName: T
    private lateinit var value: T

    private var encodedIndex: Int? = null

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions {
        encodedIndex = index

        return when {
            encodedIndex != 1 -> ElementOptions()
            isFlattened -> parentOptions
            else -> parentOptions.copy(isMapKey = false)
        }
    }

    override fun addElement(element: T) {
        when (encodedIndex) {
            0 -> serialName = element
            1 -> value = element
            null -> throw IllegalArgumentException(
                "PolymorphicKind serializer must be a CompositeEncoder structure"
            )

            else -> throw IllegalArgumentException(
                "PolymorphicKind serializer must contain only two elements, type and value"
            )
        }
    }

    override fun build(): T {
        return if (isFlattened) {
            val entries = ops.getMapValues(value).orNull() ?: throw IllegalArgumentException(
                "Primitive and list polymorphic elements not supported in flattened encoding (got $value)"
            )

            ops.mapBuilder().apply {
                add(discriminator, serialName)

                entries.forEach {
                    add(it.first, it.second)
                }
            }.build(ops.empty()).unwrap()
        } else {
            ops.mapBuilder()
                .add(discriminator, serialName)
                .add("value", value)
                .build(ops.empty()).unwrap()
        }
    }

    override fun getElementTrace(): String? {
        return null
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal class InlineState<T : Any>(
    private val descriptor: SerialDescriptor,
    private val useWrapper: Boolean,
    parentOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : SingleValueState<T>(serializationConfig) {
    private var isNull = false

    override val elementOptions = if (!useWrapper) {
        parentOptions.copy(
            useEntryListMap = descriptor.useEntryListMapForElement(0)
        )
    } else {
        ElementOptions(
            useEntryListMap = descriptor.useEntryListMapForElement(0)
        )
    }

    override fun addNull(nullElement: T) {
        isNull = true

        return super.addNull(nullElement)
    }

    override fun build(): T {
        if (useWrapper) {
            val key = when {
                !options.useClassPropertyIndices -> ops.createString(descriptor.getElementName(0))
                extendedOps.supportedMapKeys == ElementSupport.STRINGS -> ops.createString("0")
                else -> ops.createInt(0)
            }

            return ops.mapBuilder()
                .apply {
                    if (!isNull || options.explicitNulls) {
                        add(key, result)
                    }
                }
                .build(ops.empty())
                .unwrap()
        }

        return result
    }

    override fun getElementTrace(): String? {
        return if (useWrapper) {
            "." + if (options.useClassPropertyIndices) {
                "0"
            } else {
                descriptor.getElementName(0)
            }
        } else {
            null
        }
    }
}
