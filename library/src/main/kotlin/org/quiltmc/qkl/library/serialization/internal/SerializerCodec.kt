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

package org.quiltmc.qkl.library.serialization.internal

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import org.quiltmc.qkl.library.serialization.internal.decoder.DynamicDecoder
import org.quiltmc.qkl.library.serialization.internal.encoder.DynamicEncoder
import org.quiltmc.qkl.library.serialization.options.CodecOptions

internal class SerializerCodec<A>(
    private val serializer: KSerializer<A>,
    private val options: CodecOptions,
    private val serializersModule: SerializersModule
) : Codec<A> {
    override fun <T : Any> encode(input: A, ops: DynamicOps<T>, prefix: T): DataResult<T> {
        val encoder = DynamicEncoder(ops, prefix, options, serializersModule)

        return try {
            encoder.encodeSerializableValue(serializer, input)
            DataResult.success(encoder.result)
        } catch (e: Exception) {
            val trace = encoder.collectTrace()

            val errorMessage = if (options.printErrorStackTraces) {
                CodecSerializationException("Encoding exception at $trace", e).fillInStackTrace().stackTraceToString()
            } else {
                "Encoding exception at $trace: ${e.message}"
            }

            DataResult.error(errorMessage)
        }
    }

    override fun <T : Any> decode(ops: DynamicOps<T>, input: T): DataResult<Pair<A, T>> {
        val decoder = DynamicDecoder(ops, input, options, serializersModule)

        return try {
            DataResult.success(Pair.of(decoder.decodeSerializableValue(serializer), ops.empty()))
        } catch (e: Exception) {
            val trace = decoder.collectTrace()

            val errorMessage = if (options.printErrorStackTraces) {
                CodecSerializationException("Decoding exception at $trace", e).fillInStackTrace().stackTraceToString()
            } else {
                "Encoding exception at $trace: ${e.message}"
            }

            DataResult.error(errorMessage)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun toString(): String {
        return "SerializerCodec[${serializer.descriptor.serialName}]"
    }
}
