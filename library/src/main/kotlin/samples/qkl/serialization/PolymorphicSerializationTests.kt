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

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.quiltmc.qkl.library.serialization.CodecFactory
import org.quiltmc.qkl.library.serialization.annotation.CodecSerializable
import org.quiltmc.qsl.base.api.util.TriState
import samples.qkl.serialization.SerializationTestUtils.decodesFromJson
import samples.qkl.serialization.SerializationTestUtils.encodesToJson
import samples.qkl.serialization.SerializationTestUtils.failsToDecodeJson
import samples.qkl.serialization.SerializationTestUtils.failsToEncode

@Suppress("MagicNumber", "Unused")
private object PolymorphicSerializationTests {
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

    @CodecSerializable.Polymorphic(
        classDiscriminator = "foo",
        flatten = TriState.TRUE
    )
    sealed class Conflicting {
        @Serializable
        data class Subclass(val foo: Int): Conflicting()

        @Serializable
        @SerialName("Sibling")
        object Sibling : Conflicting()
    }

    fun testFlattenedEncoding() {
        val codec = CodecFactory {
            polymorphism {
                classDiscriminator = "test_class"
            }
        }.create<FooFlat>()

        require(
            encodesToJson(
                codec,
                FooFlat.FoobarFlat(123),
                """{ "test_class": "foobar", "bar": 123 }"""
            )
        )
    }

    fun testFlattenedDecoding() {
        val codec = CodecFactory {
            polymorphism {
                classDiscriminator = "test_class"
            }
        }.create<FooFlat>()

        require(
            decodesFromJson(
                codec,
                FooFlat.FoobarFlat(123),
                """{ "test_class": "foobar", "bar": 123 }"""
            )
        )
    }

    fun testStructuredEncoding() {
        val codec = CodecFactory {
            polymorphism {
                flatten = false
                classDiscriminator = "type"
            }
        }.create<Foo>()

        require(
            encodesToJson(
                codec,
                Foo.Foobar(123),
                """{ "type": "foobar", "value": { "bar": 123 } }"""
            )
        )
    }

    fun testStructuredDecoding() {
        val codec = CodecFactory {
            polymorphism {
                flatten = false
                classDiscriminator = "type"
            }
        }.create<Foo>()

        require(
            decodesFromJson(
                codec,
                Foo.Foobar(123),
                """{ "type": "foobar", "value": { "bar": 123 } }"""
            )
        )
    }

    fun testConflictingDiscriminator() {
        val codec = CodecFactory.create<Conflicting>()

        require(failsToEncode(codec, Conflicting.Sibling))
        require(failsToDecodeJson(codec, """{"foo": "Sibling"}"""))
    }
}
