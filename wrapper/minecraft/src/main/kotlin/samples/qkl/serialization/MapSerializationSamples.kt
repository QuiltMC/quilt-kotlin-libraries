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

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory
import java.util.function.BiFunction

@Suppress("MagicNumber", "Unused")
private object MapSerializationSamples {
    @Serializable
    @JvmInline
    value class BasicInline(val field: String)

    @Serializable
    @JvmInline
    value class AllowedCodecInline(@Contextual val field: Identifier)

    @Serializable
    @JvmInline
    value class DoubleInline(val field: AllowedCodecInline)

    data class TestRecord(val foo: Int, val bar: Double)

    @Serializable
    @JvmInline
    value class ForbiddenCodecInline(@Contextual val field: TestRecord)

    val testRecordCodec: Codec<TestRecord> = RecordCodecBuilder.create {
        it.group(
            Codec.INT.fieldOf("foo").forGetter(TestRecord::foo),
            Codec.DOUBLE.fieldOf("bar").forGetter(TestRecord::bar)
        ).apply(it, it.stable(BiFunction(::TestRecord)))
    }

    val baseFactory = CodecFactory {
        codecs {
            named(Identifier.CODEC, "Identifier")
            named(testRecordCodec, "TestRecord")
        }
    }

    fun allowedMapEncoding() {
        val inlineCodec = baseFactory.create<Map<BasicInline, String>>()
        val codecCodec = baseFactory.create<Map<@Contextual Identifier, String>>()
        val inlineCodecCodec = baseFactory.create<Map<AllowedCodecInline, String>>()

        fun <T> testEncode(codec: Codec<T>, value: T): JsonElement? {
            return codec.encodeStart(JsonOps.INSTANCE, value).result().orElse(null)
        }

        assert(
            testEncode(inlineCodec, mapOf(BasicInline("foo") to "bar")) ==
                    JsonParser.parseString("""{"foo": "bar"}""")
        )

        assert(
            testEncode(codecCodec, mapOf(Identifier("foo:bar") to "baz")) ==
                    JsonParser.parseString("""{"foo:bar": "baz"}""")
        )

        assert(
            testEncode(inlineCodecCodec, mapOf(AllowedCodecInline(Identifier("foo:bar")) to "baz")) ==
                    JsonParser.parseString("""{"foo:bar": "baz"}""")
        )
    }

    fun allowedMapDecoding() {
        val inlineCodec = baseFactory.create<Map<BasicInline, String>>()
        val codecCodec = baseFactory.create<Map<@Contextual Identifier, String>>()
        val inlineCodecCodec = baseFactory.create<Map<AllowedCodecInline, String>>()

        fun <T> testDecode(codec: Codec<T>, input: String): T? {
            return codec.decode(JsonOps.INSTANCE, JsonParser.parseString(input)).result().orElse(null)?.first
        }

        assert(
            testDecode(inlineCodec, """{"foo": "bar"}""") == mapOf(BasicInline("foo") to "bar")
        )

        assert(
            testDecode(codecCodec, """{"foo:bar": "baz"}""") == mapOf(Identifier("foo:bar") to "baz")
        )

        assert(
            testDecode(inlineCodecCodec, """{"foo:bar": "baz"}""") ==
                    mapOf(AllowedCodecInline(Identifier("foo:bar")) to "baz")
        )
    }

    fun invalidMapEncoding() {
        val codecCodec = baseFactory.create<Map<@Contextual TestRecord, String>>()
    }
}
