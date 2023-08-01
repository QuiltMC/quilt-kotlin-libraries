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

package org.quiltmc.qkl.library.serialization.options

import com.mojang.serialization.Codec
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import org.quiltmc.qkl.library.serialization.CodecFactory
import org.quiltmc.qkl.library.serialization.internal.CodecSerializerAdapter
import kotlin.reflect.KClass

/**
 * Configuration options used by [codecs][Codec] created by [CodecFactory] to encode and decode objects.
 *
 * See [CodecOptionsBuilder] for detailed descriptions of each property.
 */
public data class CodecOptions(
    val enum: EnumOptions,
    val polymorphism: PolymorphismOptions,
    val serializersModule: SerializersModule,
    val codecs: List<TypedCodec<*>>,
    val encodeDefaults: Boolean,
    val explicitNulls: Boolean,
    val useInlineWrappers: Boolean,
    val useEntryListMaps: Boolean,
    val ignoreUnknownKeys: Boolean,
    val allowStringValues: Boolean,
    val useClassPropertyIndices: Boolean,
    val printErrorStackTraces: Boolean
) {
    /**
     * Options used to decode and encode enums.
     */
    public data class EnumOptions(
        val encoding: EnumEncoding,
        val lenientDecoding: Boolean
    )

    /**
     * Encoding options that dictate how an enum is represented.
     */
    public sealed class EnumEncoding {
        /**
         * Encodes the enum value's index. For example encoding `Example.B` defined as
         * `enum class Example { A, B }` writes the integer `1`.
         */
        public object Index : EnumEncoding()

        /**
         * Encodes the enum value's name. For example encoding `Example.B` defined as
         * `enum class Example { A, B }` writes the string `"B"`.
         *
         * If [caseInsensitive] is set to true, decoding accepts strings regardless of case.
         * In the above example, `"b"` would also be successfully decoded as `Example.B`.
         *
         * If case-insensitive mode is enabled and an input string matches more than one
         * value, decoding will fail.
         */
        public class SerialName(public val caseInsensitive: Boolean) : EnumEncoding()
    }

    /**
     * Options used to decode and encode polymorphic values.
     */
    public data class PolymorphismOptions(
        val classDiscriminator: String,
        val flatten: Boolean
    )

    public companion object {
        /**
         * Default options used by [CodecFactory].
         */
        public val DEFAULT: CodecOptions = CodecOptions(
            EnumOptions(
                EnumEncoding.SerialName(false),
                lenientDecoding = true
            ),
            PolymorphismOptions(
                classDiscriminator = "type",
                flatten = false
            ),
            EmptySerializersModule(),
            codecs = emptyList(),
            encodeDefaults = false,
            explicitNulls = false,
            useInlineWrappers = false,
            useEntryListMaps = false,
            ignoreUnknownKeys = false,
            allowStringValues = false,
            useClassPropertyIndices = false,
            printErrorStackTraces = false
        )
    }
}

/**
 * A [Codec] instance bundled with its [name] and the [type] it encodes.
 *
 * Used to register codecs for use in serializers.
 */
public data class TypedCodec<T : Any>(
    val type: KClass<T>,
    val codec: Codec<T>,
    val name: String
) {
    internal fun apply(serializersModuleBuilder: SerializersModuleBuilder) {
        serializersModuleBuilder.contextual(
            type,
            CodecSerializerAdapter(codec, name)
        )
    }
}
