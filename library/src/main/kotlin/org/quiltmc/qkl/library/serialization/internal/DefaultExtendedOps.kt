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

package org.quiltmc.qkl.library.serialization.internal

import com.mojang.serialization.DynamicOps
import org.quiltmc.qkl.library.serialization.ExtendedDynamicOps
import org.quiltmc.qkl.library.serialization.internal.util.orNull
import org.quiltmc.qkl.library.serialization.internal.util.unwrap

/**
 * Implementation of [ExtendedDynamicOps] without support for anything.
 */
private class EmptyExtendedOps<T : Any> : ExtendedDynamicOps<T> {
    override val supportedMapKeys = ExtendedDynamicOps.ElementSupport.STRINGS

    override val supportsNull: Boolean = false

    override fun createNull(): T {
        throw UnsupportedOperationException("Empty ops")
    }

    override fun wrapNullable(value: T): T {
        throw UnsupportedOperationException("Empty ops")
    }

    override fun isNotNull(value: T): Boolean {
        throw UnsupportedOperationException("Empty ops")
    }

    override fun unwrapNullable(value: T): T {
        throw UnsupportedOperationException("Empty ops")
    }
}

internal class DefaultingExtendedOps<T : Any>(
    private val ops: DynamicOps<T>,
    private val base: ExtendedDynamicOps<T> = EmptyExtendedOps()
) : ExtendedDynamicOps<T> by base {

    override val supportsNull = true

    override fun createNull(): T {
        if (base.supportsNull) {
            return base.createNull()
        }

        return ops.emptyMap()
    }

    override fun wrapNullable(value: T): T {
        if (base.supportsNull) {
            return base.wrapNullable(value)
        }

        return ops.mapBuilder().apply {
            add("value", value)
        }.build(ops.empty()).unwrap()
    }

    override fun isNotNull(value: T): Boolean {
        if (base.supportsNull) {
            return base.isNotNull(value)
        }

        val map = ops.getMap(value).orNull()

        return if (map != null) {
            map["value"] != null
        } else {
            throw IllegalArgumentException(
                "Nullable value with default encoding must be an empty map, or a map containing the key 'value'"
            )
        }
    }

    override fun unwrapNullable(value: T): T {
        if (base.supportsNull) {
            return base.unwrapNullable(value)
        }

        val map = ops.getMap(value).result().orElse(null)

        return if (map != null) {
            map["value"]!!
        } else {
            throw IllegalArgumentException(
                "Nullable value with default encoding must be an empty map, or a map containing the key 'value'"
            )
        }
    }
}
