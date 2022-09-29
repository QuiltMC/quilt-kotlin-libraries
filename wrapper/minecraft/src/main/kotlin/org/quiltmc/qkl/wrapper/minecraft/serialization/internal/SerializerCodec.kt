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

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal

import com.mojang.datafixers.util.Pair
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.SerializersModule
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.decoder.DynamicDecoder
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.encoder.DynamicEncoder
import org.quiltmc.qkl.wrapper.minecraft.serialization.options.CodecOptions

internal class SerializerCodec<A>(
    private val serializer: KSerializer<A>,
    private val options: CodecOptions,
    private val serializersModule: SerializersModule
) : Codec<A> {
    override fun <T : Any> encode(input: A, ops: DynamicOps<T>, prefix: T): DataResult<T> {
        val encoder = DynamicEncoder(ops, prefix, options, serializersModule)

        return try {
            serializer.serialize(encoder, input)
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
            DataResult.success(Pair.of(serializer.deserialize(decoder), ops.empty()))
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
}
/**
@Serializable
internal data class TestA(
    val foo: List<Direction>,
    @Contextual val bar: Identifier,
    @CodecEntryListMap val baz: Map<Int, String>,
    val foobar: List<TestInline>,
    val foobaz: TestPoly
)

@CodecSerializable(useInlineWrapper = TriState.TRUE)
@JvmInline
internal value class TestInline(val smth: String?)

@CodecSerializable(
    flattenPolymorphic = TriState.TRUE,
    classDiscriminator = "custom"
)
public sealed class TestPoly {
    @SerialName("A")
    @Serializable
    public object A : TestPoly()

    @SerialName("B")
    @Serializable
    public data class B(public val m: Map<@Contextual Identifier, Int>) : TestPoly()
}

// TODO sample
public fun testSer() {
    val json = JsonParser.parseString(
        """
        {
            "foo": ["uP", 2, "south"],
            "bar": "minecraft:stone",
            "baz": [
                {
                    "key": "1",
                    "value": "one"
                },
                {
                    "key": 2,
                    "value": "two"
                }
            ],
            "foobar": [
                {
                    "smth": "def"
                },
                {
                    "smth": "abc"
                }
            ],
            "uwu": [1234],
            "foobaz": {
                "custom": "B",
                "m": {
                    "some:id": 1
                }
            }
        }
        """.trimIndent()
    )
    val factory = CodecFactory {
        enum {
            lenientDecoding = true
        }

        polymorphism {
            flatten = false
        }

        codecs {
            named(Identifier.CODEC, "Identifier")
        }

        ignoreUnknownKeys = true
        useEntryListMaps = false
        allowStringValues = true
    }

    println(factory.create<TestA>().decode(JsonOps.INSTANCE, json).unwrap().first)
}
*/
