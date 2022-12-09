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

package org.quiltmc.qkl.library.mixin.text;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Style.class)
public interface StyleAccessor {
    @Invoker("<init>")
    static Style create(
            @Nullable TextColor textColor,
            @Nullable Boolean bold,
            @Nullable Boolean italic,
            @Nullable Boolean underlined,
            @Nullable Boolean strikethrough,
            @Nullable Boolean obfuscated,
            @Nullable ClickEvent clickEvent,
            @Nullable HoverEvent hoverEvent,
            @Nullable String insertion,
            @Nullable Identifier font
    ) {
        throw new UnsupportedOperationException();
    }

    @Accessor("bold")
    @Nullable
    Boolean isBoldRaw();

    @Accessor("italic")
    @Nullable
    Boolean isItalicRaw();

    @Accessor("strikethrough")
    @Nullable
    Boolean isStrikethroughRaw();

    @Accessor("underlined")
    @Nullable
    Boolean isUnderlinedRaw();

    @Accessor("obfuscated")
    @Nullable
    Boolean isObfuscatedRaw();

    @Accessor("font")
    @Nullable
    Identifier getFontRaw();
}
