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

package org.quiltmc.qkl.library.serialization.internal.encoder

import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.library.serialization.internal.ElementOptions
import org.quiltmc.qkl.library.serialization.internal.SerializationConfig
import org.quiltmc.qkl.library.serialization.internal.util.unwrap

internal class ListState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : StructuredEncoderState<T>(serializationConfig) {
    private val listBuilder = ops.listBuilder()
    private var currentIndex: Int? = null

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions {
        currentIndex = index

        return ElementOptions()
    }

    override fun addElement(element: T) {
        listBuilder.add(element)
    }

    override fun build(): T {
        return listBuilder.build(ops.empty()).unwrap()
    }

    override fun getElementTrace(): String? = currentIndex?.let { "[$it]" }
}

internal abstract class MapState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : StructuredEncoderState<T>(serializationConfig) {
    protected var key: T? = null

    protected abstract fun addKeyValuePair(key: T, value: T)

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions {
        val isKey = index % 2 == 0

        //sanity check
        if (isKey != (key == null)) {
            throw IllegalStateException(
                "Map encoder receiving elements in the wrong order ($index while key element already present)"
            )
        }

        return ElementOptions()
    }

    override fun addElement(element: T) {
        key = if (key == null) {
            element
        } else {
            addKeyValuePair(key!!, element)
            null
        }
    }

    override fun getElementTrace(): String? {
        return key?.let {
            ops.getStringValue(it).result().orElse(it.toString())
        }?.let {
            "[$it]"
        }
    }

    protected fun validateBuild() {
        if (key != null) {
            throw IllegalStateException("Odd number of elements added to map, final key has no value: $key")
        }
    }
}

internal class RegularMapState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : MapState<T>(serializationConfig) {
    private val mapBuilder = ops.mapBuilder()

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions {
        return super.beforeStructureElement(descriptor, index).copy(
            isMapKey = key == null
        )
    }

    override fun addKeyValuePair(key: T, value: T) {
        mapBuilder.add(key, value)
    }

    override fun build(): T {
        return mapBuilder.build(ops.empty()).unwrap()
    }
}

internal class EntryListMapState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : MapState<T>(serializationConfig) {
    private val listBuilder = ops.listBuilder()

    override fun addKeyValuePair(key: T, value: T) {
        listBuilder.add(
            ops.mapBuilder()
                .add("key", key)
                .add("value", value)
                .build(ops.empty())
        )
    }

    override fun build(): T {
        return listBuilder.build(ops.empty()).unwrap()
    }
}
