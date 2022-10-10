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

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal.decoder

import com.mojang.serialization.MapLike
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import org.quiltmc.qkl.wrapper.minecraft.serialization.ExtendedDynamicOps.ElementSupport
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.*
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.collectInvalidKeys
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.getPrimitiveAsString
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.orNull
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.useEntryListMapForElement

@OptIn(ExperimentalSerializationApi::class)
internal class ClassState<T : Any>(
    mapLike: MapLike<T>,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    private val unknownKeys = mutableListOf<String>()
    private val decodedIndices = mutableSetOf<Int>()

    private lateinit var implicitNullIndices: ArrayDeque<Int>

    private val entryQueue = ArrayDeque<Pair<T, T>>(
        mapLike.entries().map { it.first to it.second }.toList()
    )

    private var currentEntry: Triple<String, T, ElementOptions>? = null

    override val isStructure = true

    override tailrec fun getNextIndex(descriptor: SerialDescriptor): Int {
        if (entryQueue.isEmpty()) {
            if (!options.explicitNulls) {
                return getImplicitNullIndex(descriptor)
            }

            return CompositeDecoder.DECODE_DONE
        }

        val entry = entryQueue.removeFirst()

        val index =
            when {
                !options.useClassPropertyIndices -> {
                    val key = ops.getStringValue(entry.first).orNull()

                    key?.let { descriptor.getElementIndex(it) }
                }

                extendedOps.supportedMapKeys == ElementSupport.STRINGS -> {
                    ops.getStringValue(entry.first).orNull()?.toIntOrNull()
                }

                else -> ops.getNumberValue(entry.first, CompositeDecoder.UNKNOWN_NAME).toInt()
            } ?: CompositeDecoder.UNKNOWN_NAME

        if (index == CompositeDecoder.UNKNOWN_NAME) {
            unknownKeys += ops.getPrimitiveAsString(entry.first) ?: entry.first.toString()
            return getNextIndex(descriptor)
        }

        val options = ElementOptions(
            useEntryListMap = descriptor.useEntryListMapForElement(index)
        )

        currentEntry = Triple(
            ops.getPrimitiveAsString(entry.first) ?: entry.first.toString(),
            entry.second,
            options
        )

        decodedIndices += index

        return index
    }

    //if explicitNulls are disabled, for every missing required element, emit a null
    //if any of the elements are not nullable, throw instead
    private fun getImplicitNullIndex(descriptor: SerialDescriptor): Int {
        if (!this::implicitNullIndices.isInitialized) {
            val missingIndices = ((0 until descriptor.elementsCount) - decodedIndices).asSequence()

            implicitNullIndices = ArrayDeque(
                missingIndices.filterNot {
                    descriptor.isElementOptional(it)
                }.toList()
            )

            val nonNullFields = implicitNullIndices.filterNot {
                descriptor.getElementDescriptor(it).isNullable
            }.map {
                descriptor.getElementName(it)
            }

            if (nonNullFields.isNotEmpty()) {
                throw MissingFieldException(
                    nonNullFields,
                    "Non-null required fields for type '${descriptor.serialName}' were not found: " +
                    nonNullFields.joinToString { "'$it'" }
                )
            }
        }

        val nextIndex = implicitNullIndices.removeFirstOrNull() ?: return CompositeDecoder.DECODE_DONE

        //element options don't matter because it's always null
        currentEntry = Triple(descriptor.getElementName(nextIndex), extendedOps.createNull(), ElementOptions())

        return nextIndex
    }

    override fun getElement(): Pair<T, ElementOptions> {
        val (_, element, elementOptions) = currentEntry ?: throw IllegalStateException(
            "Attempted to decode class element, but no element selected with getNextIndex"
        )

        return element to elementOptions
    }

    override fun onComplete() {
        if (unknownKeys.isNotEmpty() && !options.ignoreUnknownKeys) {
            throw IllegalArgumentException("Unknown fields found: ${unknownKeys.joinToString { "'$it'" }}")
        }
    }

    override fun getElementTrace(): String? {
        return currentEntry?.let { ".${it.first}" }
    }
}

internal class PolymorphicState<T : Any>(
    private val mapLike: MapLike<T>,
    private val classDiscriminator: String,
    private val isFlattened: Boolean,
    private val parentOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    private var currentIndex = -1

    init {
        if (!isFlattened && mapLike.entries().count() > 2) {
            val invalidKeys = collectInvalidKeys(mapLike, ops, setOf(classDiscriminator, "value"))

            throw IllegalArgumentException(
                "Unknown fields found in polymorphic state: ${invalidKeys.joinToString { "'$it'" }}"
            )
        }
    }

    override fun getNextIndex(descriptor: SerialDescriptor): Int {
        return if (++currentIndex < 2) {
            currentIndex
        } else {
            CompositeDecoder.DECODE_DONE
        }
    }

    override fun getElement(): Pair<T, ElementOptions> {
        return when (currentIndex) {
            0 -> {
                val element = mapLike[classDiscriminator] ?: throw IllegalArgumentException(
                    "Required type discriminator field '$classDiscriminator' is missing"
                )

                element to ElementOptions()
            }

            1 -> if (isFlattened) {
                ops.createMap(
                    mapLike.entries().filter {
                        ops.getStringValue(it.first).orNull() != classDiscriminator
                    }
                ) to parentOptions
            } else {
                val element = mapLike["value"] ?: throw IllegalArgumentException(
                    "Required field 'value' is missing"
                )

                element to parentOptions.copy(isMapKey = false)
            }

            else -> throw IllegalStateException("Polymorphic structure is at invalid index $currentIndex")
        }
    }

    override val isStructure = true

    override fun getElementTrace(): String? {
        return null
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal class InlineState<T : Any>(
    private val input: T,
    private val useWrapper: Boolean,
    private val descriptor: SerialDescriptor,
    private val elementOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    override fun getNextIndex(descriptor: SerialDescriptor): Int {
        throw UnsupportedOperationException("Inline state does not support structure elements")
    }

    override fun getElement(): Pair<T, ElementOptions> {
        if (!useWrapper) {
            return input to elementOptions.copy(
                useEntryListMap = descriptor.useEntryListMapForElement(0)
            )
        }

        val key = when {
            !options.useClassPropertyIndices -> ops.createString(descriptor.getElementName(0))
            extendedOps.supportedMapKeys == ElementSupport.STRINGS -> ops.createString("0")
            else -> ops.createInt(0)
        }

        val map = ops.getMap(input).orNull()

        val element = map?.get(key) ?: throw IllegalArgumentException(
            "Wrapped inline class '${descriptor.serialName}' must be a map containing a field '$key' " +
            "with value of type '${descriptor.getElementDescriptor(0).serialName}', was $input"
        )

        if (!options.ignoreUnknownKeys && map.entries().count() > 1) {
            throw IllegalArgumentException()
        }

        val wrappedOptions = ElementOptions(
            useEntryListMap = descriptor.useEntryListMapForElement(0)
        )

        return element to wrappedOptions
    }

    override val isStructure = false

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
