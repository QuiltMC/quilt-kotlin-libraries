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

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal

import com.google.gson.JsonElement
import com.google.gson.JsonNull
import org.quiltmc.qkl.wrapper.minecraft.serialization.ExtendedDynamicOps

internal object ExtendedJsonOps : ExtendedDynamicOps<JsonElement> {
    override val supportedMapKeys = ExtendedDynamicOps.ElementSupport.STRINGS

    override val supportsNull = true

    override fun createNull(): JsonElement {
        return JsonNull.INSTANCE
    }

    override fun wrapNullable(value: JsonElement): JsonElement {
        return value
    }

    override fun isNotNull(value: JsonElement): Boolean {
        return !value.isJsonNull
    }

    override fun unwrapNullable(value: JsonElement): JsonElement {
        return value
    }
}
