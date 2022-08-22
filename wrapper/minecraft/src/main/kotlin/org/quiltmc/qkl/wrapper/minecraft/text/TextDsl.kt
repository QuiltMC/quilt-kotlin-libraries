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
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.text.data.TextData
import java.util.*
import java.util.stream.Stream

/**
 * Marks objects as being part of QKL's Text Builder DSL.
 */
@DslMarker
public annotation class TextDsl

/**
 * This class contains the functions for building a [Text] object.
 * To use the DSL use [buildText].
 *
 * @property text [Text] object being built
 *
 * @author NoComment1105
 */
@TextDsl
public class TextBuilder
@PublishedApi internal constructor(
    private val text: MutableText
) : StyleBuilder() {
    /**
     * Appends a given [child] text
     * to the end of this builder's result text.
     *
     * @author Cypher121
     */
    public fun append(child: Text) {
        text.append(child)
    }

    /**
     * Builds a [Text] based on properties set on this builder.
     *
     * @author Cypher121
     */
    public fun buildText(): Text {
        return super.applyTo(text)
    }

    /**
     * This method of [StyleBuilder] is not
     * available on [TextBuilder] and will throw
     * an exception if used.
     *
     * @throws UnsupportedOperationException
     *
     * @author Cypher121
     */
    @Deprecated(
        message = "Text cannot be built as a Style",
        level = DeprecationLevel.HIDDEN
    )
    override fun buildStyle(): Style {
        throw UnsupportedOperationException(
            "Text cannot be built as a Style"
        )
    }

    /**
     * This method of [StyleBuilder] is not
     * available on [TextBuilder] and will throw
     * an exception if used.
     *
     * @throws UnsupportedOperationException
     *
     * @author Cypher121
     */
    @Deprecated(
        message = "Text cannot be built as a Style",
        level = DeprecationLevel.HIDDEN
    )
    override fun applyTo(text: MutableText): MutableText {
        throw UnsupportedOperationException(
            "Text cannot be built as a Style"
        )
    }
}

/**
 * Wrap given text in a [TextBuilder],
 * apply the given [action], then append the result
 * to the receiver [TextBuilder].
 *
 * @author Cypher121
 */
public inline fun TextBuilder.buildAndAppend(
    text: MutableText,
    action: TextBuilder.() -> Unit
) {
    append(TextBuilder(text).apply(action).buildText())
}


/**
 * Adds a translatable text.
 *
 * @param value The translation key of the text.
 * @param args Any arguments to apply to the translatable text. Can be left
 * empty for no arguments.
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.translatable(
    value: String,
    vararg args: Any,
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(Text.translatable(value, args), action)
}

/**
 * Adds a literal text.
 *
 * @param value The text.
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.literal(
    value: String,
    action: TextBuilder.() -> Unit = { }
) {

    buildAndAppend(Text.literal(value), action)
}

/**
 * Adds a mutable key bind text.
 *
 * @param key The key of the Key bind
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.keyBind(
    key: String,
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(Text.keyBind(key), action)
}


/**
 * @see Text.nbt
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.nbt(
    pathPattern: String,
    interpreting: Boolean,
    separator: Optional<Text>,
    nbt: TextData,
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(
        Text.nbt(
            pathPattern,
            interpreting,
            separator,
            nbt
        ),
        action
    )
}

/**
 * @see Text.nbt
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.nbt(
    pathPattern: String,
    interpreting: Boolean,
    separator: Optional<Text>,
    noinline nbt: ((ServerCommandSource) -> Stream<NbtCompound>),
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(
        Text.nbt(
            pathPattern,
            interpreting,
            separator,
            nbt
        ),
        action
    )
}

/**
 * TODO remove?
 * Add a plain text.
 *
 * @param value The text to add
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.text(
    value: String,
    action: TextBuilder.() -> Unit = { }
) {
    literal(value, action)
}

/**
 * Adds a scoreboard.
 *
 * @param name The name to add to the scoreboard
 * @param objective The objective of the scoreboard
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.scoreboard(
    name: String,
    objective: String,
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(Text.score(name, objective), action)
}

/**
 * Adds a resolvable entity selector.
 *
 * @param separator the optional separator if there's multiple matches issued from the selector
 * @param selector the selector
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.selector(
    selector: String,
    separator: Optional<Text>,
    action: TextBuilder.() -> Unit = { }
) {
    buildAndAppend(Text.selector(selector, separator), action)
}

/**
 * Adds a mutable empty text.
 *
 * @see StyleBuilder for action
 *
 * @author NoComment1105
 */
public inline fun TextBuilder.empty(action: TextBuilder.() -> Unit = { }) {
    buildAndAppend(Text.empty(), action)
}

/**
 * Creates a [Text] object configured by given [action].
 *
 * @see TextBuilder for available parameters
 *
 * @author NoComment1105
 */
public inline fun buildText(action: TextBuilder.() -> Unit): Text {
    return TextBuilder(Text.empty()).apply(action).buildText()
}
/*
private val example: Text = buildText {
    font = Identifier("my:font")

    literal("OWO") {
        clickEvent = copyToClipboard("abcd")
    }
}*/
