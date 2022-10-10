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

import kotlinx.serialization.Serializable
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory
import samples.qkl.serialization.SerializationTestUtils.decodesFromJson
import samples.qkl.serialization.SerializationTestUtils.encodesToJson

@Suppress("MagicNumber", "Unused")
private object BasicSerializationSamples {
    fun basicEncoding() {
        @Serializable
        data class Foo(
            val bar: Int
        )

        val codec = CodecFactory.create<Foo>()

        require(
            encodesToJson(
                codec,
                Foo(123),
                """{ "bar": 123 }"""
            )
        )
    }

    fun basicDecoding() {
        @Serializable
        data class Foo(
            val bar: Int
        )

        val codec = CodecFactory.create<Foo>()

        require(
            decodesFromJson(
                codec,
                Foo(123),
                """{ "bar": 123 }"""
            )
        )
    }
}
