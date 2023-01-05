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

@file:Suppress("MemberVisibilityCanBePrivate")

package org.quiltmc.qkl.library.serialization.options

import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult.PartialResult
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Contextual
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import org.quiltmc.qkl.library.serialization.CodecFactory
import org.quiltmc.qkl.library.serialization.annotation.CodecSerializable
import kotlin.reflect.KClass

/**
 * Builder used to create [CodecOptions] for use in [CodecFactory].
 *
 * @param base options to copy initial values from
 */
public class CodecOptionsBuilder(base: CodecOptions = CodecOptions.DEFAULT) {
    /**
     * Options used to decode and encode enums.
     */
    public val enum: EnumOptionsBuilder = EnumOptionsBuilder(base.enum)

    /**
     * Executes the provided [config] block and sets the configured
     * [EnumOptionsBuilder] options on the receiver [CodecOptions].
     */
    public fun enum(
        config: EnumOptionsBuilder.() -> Unit
    ) {
        enum.apply(config)
    }

    /**
     * Options used to decode and encode polymorphic values, such as sealed classes
     * or classes marked as [Polymorphic].
     */
    public val polymorphism: PolymorphismOptionsBuilder = PolymorphismOptionsBuilder(base.polymorphism)

    /**
     * Executes the provided [config] block and sets the configured
     * [PolymorphismOptionsBuilder] options on the receiver [CodecOptions].
     */
    public fun polymorphism(
        config: PolymorphismOptionsBuilder.() -> Unit
    ) {
        polymorphism.apply(config)
    }

    /**
     * [SerializersModule] used to locate serializers for [Polymorphic] and [Contextual] serializers.
     */
    public var serializersModule: SerializersModule = base.serializersModule

    /**
     * A list of codecs to be used for serialization.
     *
     * Types that lack serializers can be used when creating a codec, if the element is marked [Contextual],
     * and a [TypedCodec] for the [KClass] of the element is registered in this list.
     *
     * Note that, unlike serializers, codecs are completely opaque, and nothing can be
     * reliably inferred of their structure. As such, additional care should be taken to
     * only use codecs where appropriate, e.g. not using codecs that create lists or maps
     * as map keys without enabling [useEntryListMaps].
     */
    public val codecs: MutableList<TypedCodec<*>> = base.codecs.toMutableList()

    /**
     * Executes the provided [config] block and adds the resulting codecs
     * to the receiver [CodecOptions].
     *
     * Types that lack serializers can be used when creating a codec, if the element is marked [Contextual],
     * and a [TypedCodec] for the [KClass] of the element is registered using this function.
     *
     * Note that, unlike serializers, codecs are completely opaque, and nothing can be
     * reliably inferred of their structure. As such, additional care should be taken to
     * only use codecs where appropriate, e.g. not using codecs that create lists or maps
     * as map keys without enabling [useEntryListMaps][CodecOptions.useEntryListMaps].
     *
     * See [CodecListBuilder] for available methods.
     */
    public fun codecs(config: CodecListBuilder.() -> Unit) {
        CodecListBuilder(codecs).apply(config)
    }

    /**
     * If set to `true`, properties unchanged from their default values will be encoded.
     *
     * Can be overridden for individual classes using [CodecSerializable.encodeDefaults].
     */
    public var encodeDefaults: Boolean = base.encodeDefaults

    /**
     * If set to `true`, `null` fields in classes will be encoded as `null`,
     * and decoding will fail if a nullable value is absent.
     *
     * If set to `false`, `null` fields in classes will be skipped on encoding,
     * and decoding will assume any field that is missing, required, and nullable
     * to be a `null` value.
     */
    public var explicitNulls: Boolean = base.explicitNulls

    /**
     * If set to `true`, inline value classes will be represented as structures, e.g.
     * `value class Example(val field: Int)` would encode as `{ "field": 1234 }`.
     *
     * If set to `false`, inline value classes will be represented as their contained
     * values. The above example would encode as just the integer `1234`.
     *
     * Can be overridden for individual classes using [CodecSerializable.useInlineWrapper].
     */
    public var useInlineWrappers: Boolean = base.useInlineWrappers

    /**
     * If set to `true`, maps will be encoded as lists of objects containing "key" and "value"
     * fields. For example, a map `
     */
    public var useEntryListMaps: Boolean = base.useEntryListMaps

    /**
     * If set to `true`, unknown fields found when decoding a class
     * or other map-like state will be ignored.
     *
     * If set to `false`, decoding will fail when detecting
     * an unknown field instead.
     */
    public var ignoreUnknownKeys: Boolean = base.ignoreUnknownKeys

    /**
     * If set to `true`, when numeric, boolean, and codec-based values are decoded
     * the decoder will attempt to parse strings as those types. For example, a JSON
     * object `{"a": "1"}` decoded as `class Foo(val bar: Int)` would successfully
     * produce the instance `Foo(bar = 1)`.
     *
     * If set to `false`, decoding fails when encountering a string
     * where another type is expected.
     */
    public var allowStringValues: Boolean = base.allowStringValues

    /**
     * If set to `true`, classes and inline classes will be encoded using the indices
     * of properties in the descriptor instead of property names. For example,
     * a class defined as `class Foo(val bar: Int, val baz: Boolean)` would be
     * encoded to JSON as `{ "0": 1, "1": true }`.
     *
     * If set to `false`, property names are used instead. The above example
     * would then be encoded as `{ "bar": 1, "baz": true }`.
     */
    public var useClassPropertyIndices: Boolean = base.useClassPropertyIndices

    /**
     * If set to `true`, replaces shorter error messages in the
     * [DataResult failure message][PartialResult.message] with
     * a full stacktrace of the error that occurred.
     */
    public var printErrorStackTraces: Boolean = base.printErrorStackTraces

    /**
     * Builder for encoding options that dictate how an enum is represented.
     */
    public class EnumOptionsBuilder(
        base: CodecOptions.EnumOptions
    ) {
        /**
         * Determines how enums are represented in encoded form.
         *
         * @see CodecOptions.EnumEncoding
         */
        public var encoding: CodecOptions.EnumEncoding = base.encoding

        /**
         * If set to `true`, any representation matching any of the
         * [available options][CodecOptions.EnumEncoding] will be accepted by the decoder.
         *
         * If set to `false`, only the selected [encoding] will be accepted.
         */
        public var lenientDecoding: Boolean = base.lenientDecoding
    }

    /**
     * Builder for options used to decode and encode polymorphic values.
     */
    public class PolymorphismOptionsBuilder(
        base: CodecOptions.PolymorphismOptions
    ) {
        /**
         * Field name used to store the [serial name][SerialDescriptor.serialName]
         * of the actual class when polymorphic serialization is used.
         *
         * For example, if set to `"class"`, an object may be encoded as
         * `{ "class": "SomeSerialName", "value": { ... } }`.
         *
         * Can be overridden for individual classes using [CodecSerializable.Polymorphic.classDiscriminator].
         */
        public var classDiscriminator: String = base.classDiscriminator

        /**
         * If set to `true`, fields of the polymorphic value will be encoded
         * in the same object as the [classDiscriminator]. This may cause conflicts
         * if the object in question has a field and will fail if the value is not
         * represented by a map or a class.
         *
         * If set to `false`, the value will be encoded as a nested element under the key `"value"`.
         *
         * Can be overridden for individual classes using [CodecSerializable.Polymorphic.flatten].
         */
        public var flatten: Boolean = base.flatten
    }

    /**
     * Create [CodecOptions] from the properties set on the builder.
     */
    public fun build(): CodecOptions {
        return CodecOptions(
            enum = CodecOptions.EnumOptions(
                enum.encoding,
                enum.lenientDecoding
            ),
            polymorphism = CodecOptions.PolymorphismOptions(
                polymorphism.classDiscriminator,
                polymorphism.flatten
            ),
            serializersModule = serializersModule,
            codecs = codecs.toList(),
            encodeDefaults = encodeDefaults,
            explicitNulls = explicitNulls,
            useInlineWrappers = useInlineWrappers,
            useEntryListMaps = useEntryListMaps,
            ignoreUnknownKeys = ignoreUnknownKeys,
            allowStringValues = allowStringValues,
            useClassPropertyIndices = useClassPropertyIndices,
            printErrorStackTraces = printErrorStackTraces
        )
    }
}

/**
 * A configuration utility allowing addition of [typed codecs][TypedCodec]
 * without explicitly specifying the class.
 */
public class CodecListBuilder(public val list: MutableList<TypedCodec<*>>) {
    /**
     * Registers the given [codec], using the [simple name][Class.getSimpleName]
     * of the target class as the serial name of the type.
     *
     * Not recommended for use with codecs targeting built-in Minecraft classes
     * due to returning the obfuscated name.
     */
    public inline fun <reified T : Any> unnamed(codec: Codec<T>) {
        list += TypedCodec(T::class, codec, T::class.java.simpleName)
    }

    /**
     * Registers the given [codec], using the provided [name]
     * of the target class as the serial name of the type.
     */
    public inline fun <reified T : Any> named(codec: Codec<T>, name: String) {
        list += TypedCodec(T::class, codec, name)
    }

    /**
     * Registers the given [registry]'s [codec][Registry.getCodec], using the
     * [registry name][RegistryKey.value] as the serial name of the type.
     */
    public inline fun <reified T : Any> registry(registry: Registry<T>) {
        list += TypedCodec(T::class, registry.codec, registry.key.value.toString())
    }
}
