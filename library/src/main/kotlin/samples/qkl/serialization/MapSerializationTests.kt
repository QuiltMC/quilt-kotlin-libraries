/*
 * Copyright 2023 The Quilt Project
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
import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.serialization.CodecFactory
import samples.qkl.serialization.SerializationTestUtils.decodesFromJson
import samples.qkl.serialization.SerializationTestUtils.encodesToJson
import samples.qkl.serialization.SerializationTestUtils.failsToEncode
import java.util.function.BiFunction

@Suppress("MagicNumber", "Unused")
private object MapSerializationTests {
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
    value class InvalidCodecInline(@Contextual val field: TestRecord)

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

    fun testAllowedMapEncoding() {
        val inlineCodec = baseFactory.create<Map<BasicInline, String>>()
        val codecCodec =
            baseFactory.create<Map<@Contextual Identifier, String>>()
        val inlineCodecCodec =
            baseFactory.create<Map<AllowedCodecInline, String>>()

        require(
            encodesToJson(
                inlineCodec,
                mapOf(BasicInline("foo") to "bar"),
                """{"foo": "bar"}"""
            )
        )

        require(
            encodesToJson(
                codecCodec,
                mapOf(Identifier("foo:bar") to "baz"),
                """{"foo:bar": "baz"}"""
            )
        )

        require(
            encodesToJson(
                inlineCodecCodec,
                mapOf(AllowedCodecInline(Identifier("foo:bar")) to "baz"),
                """{"foo:bar": "baz"}"""
            )
        )
    }

    fun testAllowedMapDecoding() {
        val inlineCodec = baseFactory.create<Map<BasicInline, String>>()
        val codecCodec =
            baseFactory.create<Map<@Contextual Identifier, String>>()
        val inlineCodecCodec =
            baseFactory.create<Map<AllowedCodecInline, String>>()

        require(
            decodesFromJson(
                inlineCodec,
                mapOf(BasicInline("foo") to "bar"),
                """{"foo": "bar"}"""
            )
        )

        require(
            decodesFromJson(
                codecCodec,
                mapOf(Identifier("foo:bar") to "baz"),
                """{"foo:bar": "baz"}"""
            )
        )

        println(inlineCodecCodec.decode(JsonOps.INSTANCE, JsonParser.parseString("""{"foo:bar": "baz"}""")))

        require(
            decodesFromJson(
                inlineCodecCodec,
                mapOf(AllowedCodecInline(Identifier("foo:bar")) to "baz"),
                """{"foo:bar": "baz"}"""
            )
        )
    }

    fun testInvalidMapEncoding() {
        val codecCodec =
            baseFactory.create<Map<@Contextual TestRecord, String>>()
        val inlineCodecCodec =
            baseFactory.create<Map<InvalidCodecInline, String>>()

        require(
            failsToEncode(
                codecCodec,
                mapOf(TestRecord(123, 123.0) to "foo")
            )
        )

        require(
            failsToEncode(
                inlineCodecCodec,
                mapOf(
                    InvalidCodecInline(TestRecord(123, 123.0)) to "foo"
                )
            )
        )
    }
}
