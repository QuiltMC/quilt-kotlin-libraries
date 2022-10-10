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

package samples.qkl.serialization

import com.google.gson.JsonParser
import com.mojang.serialization.JsonOps
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory
import org.quiltmc.qkl.wrapper.minecraft.serialization.annotation.CodecSerializable
import org.quiltmc.qsl.base.api.util.TriState

@Suppress("MagicNumber", "Unused")
private object PolymorphicSerializationSamples {
    @CodecSerializable.Polymorphic(
        flatten = TriState.TRUE
    )
    @Serializable
    sealed class FooFlat {
        @Serializable
        @SerialName("foobar")
        data class FoobarFlat(
            val bar: Int
        ) : FooFlat()

    }

    @Serializable
    sealed class Foo {
        @Serializable
        @SerialName("foobar")
        data class Foobar(
            val bar: Int
        ) : Foo()
    }

    fun sampleFlattenedEncoding() {
        val codec = CodecFactory {
            polymorphism {
                classDiscriminator = "test_class"
            }
        }.create<FooFlat>()

        val encodeResult = codec.encodeStart(JsonOps.INSTANCE, FooFlat.FoobarFlat(123))

        require(
            encodeResult.result().orElse(null) ==
                    JsonParser.parseString("""{ "test_class": "foobar", "bar": 123 }""")
        )
    }

    fun sampleFlattenedDecoding() {
        val codec = CodecFactory {
            polymorphism {
                classDiscriminator = "test_class"
            }
        }.create<FooFlat>()

        val decodedResult = codec.decode(
            JsonOps.INSTANCE, JsonParser.parseString("""{ "test_class": "foobar", "bar": 123 }""")
        )

        require(decodedResult.result().orElse(null)?.first == FooFlat.FoobarFlat(123))
    }

    fun sampleStructuredEncoding() {
        val codec = CodecFactory {
            polymorphism {
                flatten = false
                classDiscriminator = "type"
            }
        }.create<Foo>()

        val encodeResult = codec.encodeStart(JsonOps.INSTANCE, Foo.Foobar(123))

        require(
            encodeResult.result().orElse(null) ==
                    JsonParser.parseString("""{ "type": "foobar", "value": { "bar": 123 } }""")
        )
    }

    fun sampleStructuredDecoding() {
        val codec = CodecFactory {
            polymorphism {
                flatten = false
                classDiscriminator = "type"
            }
        }.create<Foo>()

        val decodedResult = codec.decode(
            JsonOps.INSTANCE, JsonParser.parseString("""{ "type": "foobar", "value": { "bar": 123 } }""")
        )

        require(decodedResult.result().orElse(null)?.first == Foo.Foobar(123))
    }
}
