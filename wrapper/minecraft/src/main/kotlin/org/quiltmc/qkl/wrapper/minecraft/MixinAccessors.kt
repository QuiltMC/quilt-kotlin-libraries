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

package org.quiltmc.qkl.wrapper.minecraft

import net.minecraft.text.Style
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.text.mixin.StyleAccessor

//Accessors for internal Style variables. Required as getters on Style coerce null values to defaults.

@PublishedApi
internal val Style.isBoldRaw: Boolean?
    get() = (this as StyleAccessor).isBoldRaw

@PublishedApi
internal val Style.isItalicRaw: Boolean?
    get() = (this as StyleAccessor).isItalicRaw

@PublishedApi
internal val Style.isStrikethroughRaw: Boolean?
    get() = (this as StyleAccessor).isStrikethroughRaw

@PublishedApi
internal val Style.isUnderlinedRaw: Boolean?
    get() = (this as StyleAccessor).isUnderlinedRaw

@PublishedApi
internal val Style.isObfuscatedRaw: Boolean?
    get() = (this as StyleAccessor).isObfuscatedRaw

@PublishedApi
internal val Style.fontRaw: Identifier?
    get() = (this as StyleAccessor).fontRaw
