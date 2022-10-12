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

import com.mojang.serialization.JsonOps
import kotlinx.serialization.Serializable
import net.minecraft.nbt.NbtOps
import org.quiltmc.qkl.wrapper.minecraft.serialization.CodecFactory
import org.quiltmc.qkl.wrapper.minecraft.serialization.annotation.CodecSerializable
import org.quiltmc.qsl.base.api.util.TriState
import samples.qkl.serialization.SerializationTestUtils.decodesFromJson
import samples.qkl.serialization.SerializationTestUtils.encodesToJson
import samples.qkl.serialization.SerializationTestUtils.failsToDecodeJson
import samples.qkl.serialization.SerializationTestUtils.identicalAfterEncoding

@Suppress("MagicNumber", "Unused")
private object NullableSerializationTests {
    fun testBasicNullableValue() {
        val codec = CodecFactory.create<Int?>()

        require(identicalAfterEncoding(codec, 1, JsonOps.INSTANCE))

        println(codec.encodeStart(NbtOps.INSTANCE, 1)) //todo no null checks on not wrapped states
        require(identicalAfterEncoding(codec, 1, NbtOps.INSTANCE))

        require(identicalAfterEncoding(codec, null, JsonOps.INSTANCE))
        require(identicalAfterEncoding(codec, null, NbtOps.INSTANCE))

        require(encodesToJson(codec, null, "null"))
    }

    @Serializable
    data class NullableTest(
        val foo: String?,
        val bar: Int
    )

    fun testImplicitNullFields() {
        val codec = CodecFactory {
            explicitNulls = false
        }.create<NullableTest>()

        println(codec.encodeStart(JsonOps.INSTANCE, NullableTest(null, 10)))

        require(
            encodesToJson(
                codec,
                NullableTest(null, 10),
                """{"bar": 10}"""
            )
        )

        require(
            decodesFromJson(
                codec,
                NullableTest(null, 10),
                """{"bar": 10}"""
            )
        )
    }

    fun testExplicitNullFields() {
        val codec = CodecFactory {
            explicitNulls = true
        }.create<NullableTest>()

        require(
            encodesToJson(
                codec,
                NullableTest(null, 10),
                """{"foo": null, "bar": 10}"""
            )
        )

        require(
            decodesFromJson(
                codec,
                NullableTest(null, 10),
                """{"foo": null, "bar": 10}"""
            )
        )

        require(failsToDecodeJson(codec, """{"bar": 10}"""))
    }

    @CodecSerializable(
        useInlineWrapper = TriState.FALSE
    )
    @JvmInline
    value class UnwrappedNull(val foo: Int?)

    fun testUnwrappedInlineNullAmbiguity() {
        val codec = CodecFactory.create<UnwrappedNull?>()

        require(identicalAfterEncoding(codec, null, JsonOps.INSTANCE))
        require(identicalAfterEncoding(codec, null, NbtOps.INSTANCE))

        require(
            identicalAfterEncoding(
                codec,
                UnwrappedNull(null),
                JsonOps.INSTANCE
            )
        )
        require(
            identicalAfterEncoding(
                codec,
                UnwrappedNull(null),
                NbtOps.INSTANCE
            )
        )
    }
}
