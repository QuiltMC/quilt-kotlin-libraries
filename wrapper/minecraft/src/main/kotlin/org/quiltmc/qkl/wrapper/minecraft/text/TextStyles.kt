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

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.*
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.text.mixin.StyleAccessor
import java.util.UUID

/**
 * A mutable color object that can be transformed into Minecraft [color][TextColor].
 *
 * @property rgb The RGB value to convert to a Minecraft [color][TextColor].
 *
 * @author NoComment1105
 */
@TextDslMarker
public class MutableColor(public var rgb: Int) {
    public companion object {
        /**
         * Gets a color from [TextColor] and converts it to [MutableColor].
         *
         * @param color The [TextColor] to convert
         * @return A [MutableColor] from [color]
         *
         * @author NoComment1105
         */
        public fun fromTextColor(color: TextColor): MutableColor {
            return MutableColor(color.rgb)
        }

        /**
         * Gets a color from [Formatting] and converts it to [MutableColor].
         * If the color value is null, it defaults to black.
         *
         * @param formattingColor The color to convert
         * @return A [MutableColor] from [formattingColor] or black if invalid/null
         *
         * @author NoComment1105
         */
        public fun fromFormatting(formattingColor: Formatting): MutableColor {
            return MutableColor(formattingColor.colorValue ?: 0x000000)
        }
    }

    /**
     * Converts [rgb] to a [TextColor].
     *
     * @return A [TextColor] created from the [rgb] value
     *
     * @author NoComment1105
     */
    public fun toTextColor(): TextColor {
        return TextColor.fromRgb(rgb)
    }
}

/**
 * The build to create a hover event for an item.
 *
 * @author NoComment1105
 */
@TextDslMarker
public class ItemHoverEvent {
    /** The item stack to apply the event to. */
    public var itemStack: ItemStack? = null
    /** The item to apply the event to. */
    public var item: Item? = null
    /** The NBT of the item to apply the event to. */
    public var nbt: NbtCompound? = null

    /**
     * Gets the item based off the [itemStack], [item] or [nbt] variables.
     *
     * @author NoComment1105
     */
    private fun getItem(): ItemStack {
        return if (itemStack != null) {
            itemStack!!
        } else if (item != null) {
            ItemStack(item).let {
                it.nbt = nbt
                it
            }
        } else if (nbt != null) {
            ItemStack.fromNbt(nbt)
        } else {
            ItemStack.EMPTY
        }
    }

    /**
     * Creates the hover event.
     *
     * @author NoComment1105
     */
    public fun create(): HoverEvent {
        return HoverEvent(
            HoverEvent.Action.SHOW_ITEM,
            HoverEvent.ItemStackContent(
                itemStack ?: getItem()
            )
        )
    }
}

/**
 * The builder to create a hover event for an entity.
 *
 * @author NoComment1105
 */
@TextDslMarker
public class EntityHoverEvent {
    /** The entity type to apply the hover event to. */
    public var entityType: EntityType<Entity>? = null
    /** The UUID of hte entity to apply the event to. */
    public var uuid: UUID? = null
    /** The name of the entity to apply the event to. */
    public var name: Text? = null

    /**
     * Creates a hover event for an entity based off of [entityType], [uuid] and [name].
     *
     * @author NoComment1105
     */
    public fun create(): HoverEvent {
        return HoverEvent(
            HoverEvent.Action.SHOW_ENTITY,
            HoverEvent.EntityContent(
                entityType ?: EntityType.PLAYER,
                uuid ?: UUID.randomUUID(),
                name
            )
        )
    }
}

/**
 * The builder to create a hover event for text.
 *
 * @author NoComment1105
 */
@TextDslMarker
public class TextHoverEvent {
    /** The text to apply the hover event to. */
    public var text: Text? = null

    /**
     * Creates a hover event for the [text] provided.
     *
     * @author NoComment1105
     */
    public fun create(): HoverEvent {
        return HoverEvent(
            HoverEvent.Action.SHOW_TEXT,
            text ?: buildText {
                empty()
            }
        )
    }
}

/**
 * The builder to create a [ClickEvent].
 *
 * @author NoComment1105
 */
public class QklClickEvent {
    /** The action to perform on the click. */
    private var action: ClickEvent.Action? = null
    /** The value for the event. */
    private var value: String? = null

    /** Open's a URL. */
    public var openUrl: String = ""
        set(url) = run {
            action = ClickEvent.Action.OPEN_URL
            value = url
        }
    /** Open's a file. */
    public var openFile: String = ""
        set(path) = run {
            action = ClickEvent.Action.OPEN_FILE
            value = path
        }
    /** Runs a command. */
    public var runCommand: String = ""
        set(command) = run {
            action = ClickEvent.Action.RUN_COMMAND
            value = command
        }
    /** Suggests a command. */
    public var suggestCommand: String = ""
        set(suggestedCommand) = run {
            action = ClickEvent.Action.SUGGEST_COMMAND
            value = suggestedCommand
        }
    /** Change's page. */
    public var changePage: Int = 0
        set(page) = run {
            action = ClickEvent.Action.CHANGE_PAGE
            value = page.toString()
        }
    /** Copies text to the clipboard. */
    public var copyToClipboard: String = ""
        set(toCopy) = run {
            action = ClickEvent.Action.COPY_TO_CLIPBOARD
            value = toCopy
        }

    /**
     * Creates a click event based on the [action] and [value] provided.
     *
     * @author NoComment1105
     */
    public fun create(): ClickEvent {
        return ClickEvent(
            action ?: ClickEvent.Action.SUGGEST_COMMAND,
            value
        )
    }
}

/**
 * A mutable style object that transforms to Minecraft [styles][Style].
 *
 * @author NoComment1105
 */
@TextDslMarker
public class MutableStyle {
    /** A [MutableColor] to apply to the text. */
    public var color: MutableColor? = null
    /** Whether to obfuscate the text or not. */
    public var obfuscated: Boolean = false
    /** Whether to format the text in bold or not. */
    public var bold: Boolean = false
    /** Whether to format the text in italics or not. */
    public var italic: Boolean = false
    /** Whether to format the text with a strikethrough or not. */
    public var strikethrough: Boolean = false
    /** Whether to format the text with an underline or not. */
    public var underline: Boolean = false
    /** A [HoverEvent] to apply to the text. */
    public var hoverEvent: HoverEvent? = null
    /** A [ClickEvent] to apply to the text. */
    public var clickEvent: ClickEvent? = null
    /**
     * An insertion inserted when a piece of text clicked while shift key is down in the chat HUD to apply to the text.
     */
    public var insertion: String? = null
    /** An [Identifier] for the Minecraft font that would like to be used. */
    public var font: Identifier? = null

    /**
     * Converts 3 separate RGB values to a [MutableColor].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    @Suppress("MagicNumber")
    public fun color(red: Int, green: Int, blue: Int) {
        this.color = MutableColor((red shl 16) + (green shl 8) + blue)
    }

    /**
     * Converts a single RGB value to a [MutableColor].
     *
     * @param rgb The RGB value to convert
     *
     * @author NoComment1105
     */
    public fun color(rgb: Int) {
        this.color = MutableColor(rgb)
    }

    /**
     * Converts a string color to a [MutableColor].
     *
     * @param colorCode The color to convert.
     *
     * @author NoComment1105
     */
    public fun color(colorCode: String) {
        this.color = MutableColor(colorCode.toColor())
    }

    /**
     * Converts a string color to an [Int].
     *
     * @return The [Int] converted color
     *
     * @author NoComment1105
     */
    @Suppress("MagicNumber")
    private fun String.toColor(): Int {
        var color = when {
            startsWith("#") -> substring(1)
            startsWith("0x") -> substring(2)
            matches("^\\d+$".toRegex()) -> this
            else -> error("value is not a valid color")
        }
        if (color.length == 3) {
            color = color.split("").joinToString("") {
                "$it$it"
            }
        }

        return color.toInt(16)
    }

    /**
     * Applies the [styles][MutableStyle] to the [text].
     *
     * @param text The text to apply a [style][MutableStyle] to
     *
     * @author NoComment1105
     */
    public fun applyTo(text: MutableText): MutableText {
        @Suppress("MagicNumber") // It's the colour code...
        return text.setStyle(
            StyleAccessor.create(
                (color ?: MutableColor(0xffffff)).toTextColor(),
                obfuscated,
                bold,
                italic,
                strikethrough,
                underline,
                clickEvent,
                hoverEvent,
                insertion,
                font
            )
        )
    }
}
