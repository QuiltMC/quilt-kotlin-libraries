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

package org.quiltmc.qkl.library.serialization

import com.google.gson.JsonNull
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.MapLike

/**
 * Interface intended to be implemented (directly or via mixin)
 * on subclasses of [DynamicOps] to provide safe access to operations
 * present on the underlying type, but not exposed
 * by [DynamicOps], such as null literals.
 */
public interface ExtendedDynamicOps<T : Any> {
    /**
     * Marks whether this instance supports numeric and boolean
     * keys for its [MapLike] structures.
     *
     * Formats like JSON do not support numeric or boolean keys,
     * requiring decoders to convert those values to strings and
     * parse them back, adding more potential for errors.
     *
     * Other formats, such as binary ones, may remove that restriction,
     * allowing map keys to be strongly typed and avoid unnecessary conversion.
     *
     * Note: any instance setting this property to `true is expected to
     * support _all numeric and boolean keys_ created by its matching [DynamicOps]
     * methods, such as [DynamicOps.createDouble] or [DynamicOps.createBoolean].
     */
    public val supportedMapKeys: ElementSupport

    /**
     * Marks whether this instance supports literal nulls.
     *
     * If set to `true`, methods [createNull], [wrapNullable],
     * [isNotNull], and [unwrapNullable] can be called.
     *
     * If set to `false`, the above methods are not used,
     * and fallback operations using only [DynamicOps]
     * methods are used to represent null values.
     */
    public val supportsNull: Boolean

    /**
     * Returns a representation of literal `null`,
     * as an instance of [T], e.g. [JsonNull] when
     * for the JSON format.
     */
    public fun createNull(): T

    /**
     * Returns the given [value], annotated with any required markers
     * to ensure it is distinguishable from a `null` value.
     *
     * In formats with literal `null` support, such as JSON, the given
     * value can be returned with no alterations. In other formats,
     * additional data may be required. For example, a hypothetical
     * binary format may return the byte `0x00` from [createNull] and
     * prepend [value] with `0x01` in wrapNullable, so that the null value
     * can be distinguished from a non-null integer zero.
     */
    public fun wrapNullable(value: T): T

    /**
     * Returns `true` if the value given does not represent
     * a literal `null`, and `false` otherwise.
     *
     * It is expected that `isNotNull(createNull())` returns `false`,
     * and `isNotNull(wrapNullable(X))` returns `true` for any `X`,
     * and same holds for any values created externally in the same format
     * as the above methods.
     */
    public fun isNotNull(value: T): Boolean

    /**
     * Returns the given [value] with all nullability markers stripped,
     * such that the underlying value can be further decoded by the
     * matching methods in [DynamicOps].
     */
    public fun unwrapNullable(value: T): T

    /**
     * Possible types of elements supported in a given position. Currently used
     * for determining best encoding for [map builder][DynamicOps.mapBuilder] keys.
     * The options are:
     *
     * * Strings: only string elements are supported, e.g. JSON.
     * * Primitives: all primitives are supported. This includes strings, any numbers, and booleans.
     * * Any: any element is supported, including structured elements like maps and lists.
     */
    public enum class ElementSupport {
        STRINGS, PRIMITIVES, ANY
    }
}
