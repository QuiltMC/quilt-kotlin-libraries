/*
 * Copyright 2022 The Quilt Project
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

package org.quiltmc.qkl.library.serialization.internal

import com.mojang.serialization.Codec
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.quiltmc.qkl.library.serialization.internal.decoder.DynamicDecoder
import org.quiltmc.qkl.library.serialization.internal.encoder.DynamicEncoder
import org.quiltmc.qkl.library.serialization.internal.util.unwrap

@OptIn(ExperimentalSerializationApi::class)
internal class CodecSerializerAdapter<T>(private val codec: Codec<T>, private val typeName: String) : KSerializer<T> {
    @SerialInfo
    annotation class Marker

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(typeName) {
        annotations = listOf(Marker())
    }

    override fun serialize(encoder: Encoder, value: T) {
        if (encoder !is DynamicEncoder<*>) {
            throw UnsupportedOperationException(
                "Codec serializers can only be used in Dynamic serialization"
            )
        }

        serializeToDynamic(encoder, value)
    }

    override fun deserialize(decoder: Decoder): T {
        if (decoder !is DynamicDecoder<*>) {
            throw UnsupportedOperationException(
                "Codec serializers can only be used in Dynamic serialization"
            )
        }

        return deserializeFromDynamic(decoder)
    }

    private fun <A : Any> serializeToDynamic(encoder: DynamicEncoder<A>, value: T) {
        encoder.encodeExternal(codec.encode(value, encoder.ops, encoder.ops.empty()).unwrap())
    }

    private fun <A : Any> deserializeFromDynamic(decoder: DynamicDecoder<A>): T {
        return decoder.decodeExternal(codec, typeName)
    }
}
