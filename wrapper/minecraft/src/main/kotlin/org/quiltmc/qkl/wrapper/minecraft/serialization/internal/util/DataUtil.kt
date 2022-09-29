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

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util

import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import net.minecraft.util.dynamic.ForwardingDynamicOps
import org.quiltmc.qkl.wrapper.minecraft.mixin.serialization.ForwardingDynamicOpsAccessor
import org.quiltmc.qkl.wrapper.minecraft.serialization.ExtendedDynamicOps
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.DefaultingExtendedOps
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.ExtendedJsonOps

@Suppress("UNCHECKED_CAST")
internal tailrec fun <T : Any> DynamicOps<T>.getExtendedWithDefault(): ExtendedDynamicOps<T> {
    return when (this) {
        is ExtendedDynamicOps<*> -> DefaultingExtendedOps(this, this as ExtendedDynamicOps<T>)

        is JsonOps -> ExtendedJsonOps as ExtendedDynamicOps<T>

        is ForwardingDynamicOps<*> -> (this as ForwardingDynamicOpsAccessor<T>).delegate.getExtendedWithDefault()

        else -> DefaultingExtendedOps(this)
    }
}

internal fun <R> DataResult<R>.unwrap(): R {
    return getOrThrow(false) {}
}

internal fun <R> DataResult<R>.orNull(): R? {
    return result().orElse(null)
}

internal fun <T> DynamicOps<T>.getPrimitiveAsString(value: T): String? {
    getStringValue(value).orNull()?.let {
        return it
    }

    getNumberValue(value).orNull()?.let {
        return it.toString()
    }

    getBooleanValue(value).orNull()?.let {
        return it.toString()
    }

    return null
}
