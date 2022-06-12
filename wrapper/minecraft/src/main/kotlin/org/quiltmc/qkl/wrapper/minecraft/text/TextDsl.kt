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

package org.quiltmc.qkl.wrapper.minecraft.text

import net.minecraft.nbt.NbtCompound
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.data.TextData
import net.minecraft.util.Formatting
import java.util.*
import java.util.stream.Stream

public class TextDsl(action: TextDsl.() -> Unit) {
    init {
        apply(action)
    }

    public fun createTranslatable(text: String, vararg args: Any): MutableText {
        return Text.translatable(text, args)
    }

    public fun createTranslatable(text: String): MutableText {
        return Text.translatable(text)
    }

    public fun createLiteral(text: String): MutableText {
        return Text.literal(text)
    }

    public fun createKeyBind(key: String): MutableText {
        return Text.keyBind(key)
    }

    public fun createNbt(
        pathPattern: String,
        interpreting: Boolean,
        separator: Optional<Text>,
        nbt: TextData
    ): MutableText {
        return Text.nbt(pathPattern, interpreting, separator, nbt)
    }

    public fun createNbt(
        pathPattern: String,
        interpreting: Boolean,
        seperator: Optional<Text>,
        nbt: ((ServerCommandSource) -> Stream<NbtCompound>)
    ): MutableText {
        return Text.nbt(pathPattern, interpreting, seperator, nbt)
    }

    public fun createText(text: String): Text {
        return Text.of(text)
    }

    public fun createScoreboardText(name: String, objective: String): MutableText {
        return Text.score(name, objective)
    }

    public fun createSelectorText(selector: String, separator: Optional<Text>): MutableText {
        return Text.selector(selector, separator)
    }


    public fun createEmpty(): MutableText {
        return Text.empty()
    }


    public fun createFormattedTranslatable(text: String, formatting: Formatting): MutableText {
        return createTranslatable(text).formatted(formatting)
    }

    public fun createFormattedTranslatable(
        text: String,
        formatting: Formatting,
        vararg args: Any
    ): MutableText {
        return createTranslatable(text, args).formatted(formatting)
    }

    public fun createFormattedLiteral(text: String, formatting: Formatting): MutableText {
        return createLiteral(text).formatted(formatting)
    }

    public fun createFormattedKeyBind(key: String, formatting: Formatting): MutableText {
        return createKeyBind(key).formatted(formatting)
    }

    public fun createFormattedNbt(
        pathPattern: String,
        interpreting: Boolean,
        separator: Optional<Text>,
        nbt: TextData,
        formatting: Formatting
    ): MutableText {
        return createNbt(pathPattern, interpreting, separator, nbt).formatted(formatting)
    }

    public fun createFormattedNbt(
        pathPattern: String,
        interpreting: Boolean,
        seperator: Optional<Text>,
        nbt: ((ServerCommandSource) -> Stream<NbtCompound>),
        formatting: Formatting
    ): MutableText {
        return createNbt(pathPattern, interpreting, seperator, nbt).formatted(formatting)
    }
}
