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

@file:Suppress("MemberVisibilityCanBePrivate")

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
import kotlin.math.roundToInt

/**
 * TODO Kdoc.
 */
@TextDslMarker
@Suppress("MagicNumber")
public fun Color(red: Int, green: Int, blue: Int): Color = Color((red shl 16) + (green shl 8) + blue)

/**
 * TODO KDoc.
 */
@TextDslMarker
@Suppress("MagicNumber")
public fun Color(red: Float, green: Float, blue: Float): Color = Color(
    (red.coerceIn(0F, 1F) * 255).roundToInt(),
    (green.coerceIn(0F, 1F) * 255).roundToInt(),
    (blue.coerceIn(0F, 1F) * 255).roundToInt()
)

/**
 * A color class that can transform RGB values into color codes.
 *
 * @property value The RGB value to convert to a color code.
 *
 * @author NoComment1105
 */
@TextDslMarker
@JvmInline
public value class Color(public val value: Int) {
    /** A specific shade of red generated from [value]. */
    public val red: Int get() = value shl 16
    /** A specific shade of green generated from [value]. */
    public val green: Int get() = value shl 8 and 0xFF
    /** A specific shade of blue generated from [value]. */
    public val blue: Int get() = value and 0xFF

    public companion object {
        /** Minecraft's native black color. */
        public val BLACK: Color = Color(0x000000)
        /** Minecraft's native dark blue color. */
        public val DARK_BLUE: Color = Color(0x0000AA)
        /** Minecraft's native dark green color. */
        public val DARK_GREEN: Color = Color(0x00AA00)
        /** Minecraft's native dark aqua color. */
        public val DARK_AQUA: Color = Color(0x00AAAA)
        /** Minecraft's native dark red color. */
        public val DARK_RED: Color = Color(0xAA0000)
        /** Minecraft's native dark purple color. */
        public val DARK_PURPLE: Color = Color(0xAA00AA)
        /** Minecraft's native gold color. */
        public val GOLD: Color = Color(0xFFAA00)
        /** Minecraft's native grey color. */
        public val GREY: Color = Color(0xAAAAAA)
        /** Minecraft's native dark grey color. */
        public val DARK_GREY: Color = Color(0x555555)
        /** Minecraft's native blue color. */
        public val BLUE: Color = Color(0x5555FF)
        /** Minecraft's native green color. */
        public val GREEN: Color = Color(0x55FF55)
        /** Minecraft's native aqua color. */
        public val AQUA: Color = Color(0x55FFFF)
        /** Minecraft's native red color. */
        public val RED: Color = Color(0xFF5555)
        /** Minecraft's native light purple color. */
        public val LIGHT_PURPLE: Color = Color(0xFF55FF)
        /** Minecraft's native yellow color. */
        public val YELLOW: Color = Color(0xFFFF55)
        /** Minecraft's native white color. */
        public val WHITE: Color = Color(0xFFFFFF)

        /**
         * Gets a color from [TextColor] and converts it to [Color].
         *
         * @param color The [TextColor] to convert
         * @return A [Color] from [color]
         *
         * @author NoComment1105
         */
        public fun fromTextColor(color: TextColor): Color {
            return Color(color.rgb)
        }

        /**
         * Gets a color from [Formatting] and converts it to [Color].
         * If the color value is null, it defaults to black.
         *
         * @param formattingColor The color to convert
         * @return A [Color] from [formattingColor] or black if invalid/null
         *
         * @author NoComment1105
         */
        public fun fromFormatting(formattingColor: Formatting): Color {
            return Color(formattingColor.colorValue ?: 0x000000)
        }
    }

    /**
     * Converts [value] to a [TextColor].
     *
     * @return A [TextColor] created from the [value] value
     *
     * @author NoComment1105
     */
    public fun toTextColor(): TextColor {
        return TextColor.fromRgb(value)
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
    /** A [Color] to apply to the text. */
    public var color: Color? = null
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
     * Converts 3 separate RGB values to a [Color].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    @Suppress("MagicNumber")
    public fun color(red: Int, green: Int, blue: Int) {
        this.color = Color(red, green, blue)
    }

    /**
     * Converts a single RGB value to a [Color].
     *
     * @param rgb The RGB value to convert
     *
     * @author NoComment1105
     */
    public fun color(rgb: Int) {
        this.color = Color(rgb)
    }

    /**
     * Converts a string color to a [Color].
     *
     * @param colorCode The color to convert.
     *
     * @author NoComment1105
     */
    public fun color(colorCode: String) {
        this.color = Color(colorCode.toColor())
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
                (color ?: Color(0xffffff)).toTextColor(),
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
