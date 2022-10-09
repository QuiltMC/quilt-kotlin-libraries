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
import kotlinx.serialization.Serializable
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory

//TODO anything that doesn't use registries can eventually be a unit test
@Suppress("MagicNumber")
private object SerializationSamples {
    fun basicEncoding() {
        @Serializable
        data class Foo(
            val bar: Int
        )

        val codec = CodecFactory.create<Foo>()

        val encodeResult = codec.encodeStart(JsonOps.INSTANCE, Foo(123))

        assert(encodeResult.result().isPresent)
        val encoded = encodeResult.result().get()

        assert(encoded.isJsonObject)
        assert(encoded.asJsonObject["bar"].isJsonPrimitive)
        assert(encoded.asJsonObject["bar"].asNumber == 123)
    }

    fun basicDecoding() {
        @Serializable
        data class Foo(
            val bar: Int
        )

        val codec = CodecFactory.create<Foo>()
        val json = JsonParser.parseString("""{ "bar": 123 }""")

        val decodedResult = codec.decode(JsonOps.INSTANCE, json)

        assert(decodedResult.result().isPresent)
        assert(decodedResult.result().get().first == Foo(123))
    }
}

internal fun runTests() {
    with(SerializationSamples) {
        basicEncoding()
        basicDecoding()
    }
}
