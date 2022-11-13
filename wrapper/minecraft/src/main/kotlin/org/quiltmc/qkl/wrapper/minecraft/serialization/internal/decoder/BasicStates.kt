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
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.ElementOptions
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.SerializationConfig

internal class RootState<T : Any>(
    private val input: T,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    override val isStructure = false

    override fun getNextIndex(descriptor: SerialDescriptor): Int {
        throw UnsupportedOperationException("Root state does not support structure elements")
    }

    override fun getElement(): Pair<T, ElementOptions> {
        return input to ElementOptions()
    }

    override fun getElementTrace(): String? {
        return null
    }
}

internal class NullableState<T : Any>(
    private val input: T,
    private val parentOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    override val isStructure = false

    override fun getNextIndex(descriptor: SerialDescriptor): Int {
        throw UnsupportedOperationException("Nullable state does not support structure elements")
    }

    override fun getElement(): Pair<T, ElementOptions> {
        return extendedOps.unwrapNullable(input) to parentOptions
    }

    override fun getElementTrace(): Nothing? {
        return null
    }
}

internal class ObjectState<T : Any>(
    inputMap: MapLike<T>,
    serializationConfig: SerializationConfig<T>
) : DecoderState<T>(serializationConfig) {
    override val isStructure = true

    init {
        if (!options.ignoreUnknownKeys && inputMap.entries().count() > 0) {
            throw IllegalArgumentException("Objects cannot contain elements")
        }
    }

    override fun getNextIndex(descriptor: SerialDescriptor): Int {
        return CompositeDecoder.DECODE_DONE
    }

    override fun getElement(): Pair<T, ElementOptions> {
        throw IllegalStateException("Object decoders must not read elements")
    }

    override fun getElementTrace(): String? {
        return null
    }
}
