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

package org.quiltmc.qkl.library.serialization.annotation

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InheritableSerialInfo
import kotlinx.serialization.MetaSerializable
import org.quiltmc.qkl.library.serialization.options.CodecOptions
import org.quiltmc.qsl.base.api.util.TriState

/**
 * Marks the target class as [Serializable] and allows overriding [CodecOptions]
 * properties with annotation values.
 *
 * @param encodeDefaults overrides [CodecOptions.encodeDefaults] for target class
 * @param useInlineWrapper overrides [CodecOptions.useInlineWrappers] for target class
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.CLASS)
@MetaSerializable
public annotation class CodecSerializable(
    //common options
    val encodeDefaults: TriState = TriState.DEFAULT,

    //inline options
    val useInlineWrapper: TriState = TriState.DEFAULT
) {

    /**
     * Marks the target class as [Serializable] and allows overriding [CodecOptions.PolymorphismOptions]
     * properties with annotation values.
     *
     * Note that unlike regular [CodecSerializable], the polymorphic version is considered
     * [InheritableSerialInfo] and, if present on subclasses, must have same values as the parent annotation,
     * as described in documentation for [InheritableSerialInfo].
     *
     * @param classDiscriminator overrides
     * [CodecOptions.polymorphism.classDiscriminator][CodecOptions.PolymorphismOptions.classDiscriminator]
     * for target class and its subclasses
     *
     * @param flatten overrides [CodecOptions.polymorphism.flatten][CodecOptions.PolymorphismOptions.flatten]
     * for target class and its subclasses
     */
    @OptIn(ExperimentalSerializationApi::class)
    @Target(AnnotationTarget.CLASS)
    @MetaSerializable
    @InheritableSerialInfo
    public annotation class Polymorphic(
        val classDiscriminator: String = "",
        val flatten: TriState = TriState.DEFAULT
    )
}
