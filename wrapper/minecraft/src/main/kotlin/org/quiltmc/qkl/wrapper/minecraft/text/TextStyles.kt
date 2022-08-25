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

import net.minecraft.block.MapColor
import net.minecraft.text.*
import net.minecraft.util.DyeColor
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.text.mixin.StyleAccessor
import kotlin.math.roundToInt

/**
 * Converts RGB values from floats to a [Color] for use in text.
 * Each argument should be within 0 and 1. If a number is outside the range, the function will automatically default it
 * to the maximum or minimum value, depending on which is nearer to the provided value.
 *
 * @param red The red channel of the color
 * @param green The green channel of the color
 * @param blue The blue channel of the color
 *
 * @return A [Color] created from the provided RGB Channels
 *
 * @author NoComment1105
 */
@TextDsl
@Suppress("MagicNumber")
public fun Color(red: Float, green: Float, blue: Float): Color = Color(
    (red.coerceIn(0F, 1F) * 255).roundToInt(),
    (green.coerceIn(0F, 1F) * 255).roundToInt(),
    (blue.coerceIn(0F, 1F) * 255).roundToInt()
)

/**
 * Converts RGB values from doubles to a [Color] for use in text.
 * Each argument should be within 0 and 1. If a number is outside the range, the function will automatically default it
 * to the maximum or minimum value, depending on which is nearer to the provided value.
 *
 * @param red The red channel of the color
 * @param green The green channel of the color
 * @param blue The blue channel of the color
 *
 * @return A [Color] created from the provided RGB Channels
 *
 * @author NoComment1105
 */
@TextDsl
@Suppress("MagicNumber")
public fun Color(red: Double, green: Double, blue: Double): Color = Color(
    (red.coerceIn(0.0, 1.0) * 255).roundToInt(),
    (green.coerceIn(0.0, 1.0) * 255).roundToInt(),
    (blue.coerceIn(0.0, 1.0) * 255).roundToInt(),
)

/**
 * A color class that can transform RGB values into color codes.
 *
 * @property value The RGB value to convert to a color code.
 *
 * @author NoComment1105
 */
@TextDsl
@JvmInline
public value class Color(public val value: Int) {
    @Suppress("MagicNumber")
    public constructor(red: Int, green: Int, blue: Int) : this(
        (red.coerceIn(0, 255) shl 16) +
                (green.coerceIn(0, 255) shl 8) +
                blue.coerceIn(0, 255)
    )

    /** A color of red influenced by [value]. */
    public
    val red: Int get() = value shr 16 and 0xFF

    /** A color of green influenced by [value]. */
    public
    val green: Int get() = value shr 8 and 0xFF

    /** A color of blue influenced by [value]. */
    public
    val blue: Int get() = value and 0xFF

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
         * @return A [Color] from [TextColor]
         *
         * @author NoComment1105
         */
        public fun from(color: TextColor): Color {
            return Color(color.rgb)
        }

        /**
         * Gets a color from [Formatting] and converts it to [Color].
         * If the color value is null, it defaults to black.
         *
         * @param color The [Formatting] to convert
         * @return A [Color] from [Formatting] or black if invalid/null
         *
         * @author NoComment1105
         */
        public fun from(color: Formatting): Color {
            return Color(color.colorValue?.let(::Color)?.value ?: BLACK.value)
        }

        /**
         * Gets a color from [MapColor] and converts it to [Color].
         *
         * @param color The [MapColor] to convert
         * @return A [Color] from [MapColor]
         *
         * @author NoComment1105
         */
        public fun from(color: MapColor): Color {
            return Color(color.color)
        }

        /**
         * Gets a color from [DyeColor] and converts it to [Color].
         *
         * @param color The [DyeColor] to convert
         * @return A [Color] from [DyeColor]
         */
        public fun from(color: DyeColor): Color {
            return Color(color.signColor)
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
 * A mutable style object that transforms to Minecraft [styles][Style].
 *
 * @author NoComment1105
 */
@TextDsl
public class StyleBuilder {
    /** A [Color] to apply to the text. */
    public var color: Color? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** Whether to obfuscate the text or not. */
    public var obfuscated: Boolean? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** Whether to format the text in bold or not. */
    public var bold: Boolean? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** Whether to format the text in italics or not. */
    public var italic: Boolean? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** Whether to format the text with a strikethrough or not. */
    public var strikethrough: Boolean? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** Whether to format the text with an underline or not. */
    public var underlined: Boolean? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** A [HoverEvent] to apply to the text. */
    public var hoverEvent: HoverEvent? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** A [ClickEvent] to apply to the text. */
    public var clickEvent: ClickEvent? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /**
     * An insertion inserted when a piece of text clicked while shift key is down in the chat HUD to apply to the text.
     */
    public var insertion: String? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    /** An [Identifier] for the Minecraft font that would like to be used. */
    public var font: Identifier? = null
        set(value) {
            if (field != value) {
                cachedStyle = null
            }
            field = value
        }

    private var cachedStyle: Style? = Style.EMPTY

    /**
     * Converts 3 RGB int values to a [Color].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    public fun color(red: Int, green: Int, blue: Int) {
        this.color = Color(red, green, blue)
    }

    /**
     * Converts 3 RGB float values to a [Color].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    public fun color(red: Float, green: Float, blue: Float) {
        this.color = Color(red, green, blue)
    }

    /**
     * Converts 3 RGB double values to a [Color].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    public fun color(red: Double, green: Double, blue: Double) {
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
     * Creates a new style while avoiding creating a new instance of the empty style.
     *
     * Both helpful to avoid multiple instances of the style being created,
     * and to enable optimizations in Minecraft code that use reference equality to [Style.EMPTY].
     */
    private fun createNewStyle(): Style {
        if (color == null && bold == null && italic == null && underlined == null && strikethrough == null &&
                obfuscated == null && clickEvent == null && hoverEvent == null && insertion == null && font == null) {
            return Style.EMPTY
        }

        return StyleAccessor.create(
            color?.toTextColor(),
            bold,
            italic,
            underlined,
            strikethrough,
            obfuscated,
            clickEvent,
            hoverEvent,
            insertion,
            font
        )
    }

    /**
     * Builds a [Style] based on properties set on the builder.
     *
     * If called repeatedly with no property changes, will return
     * a cached value.
     *
     * @author Cypher121
     */
    public fun buildStyle(): Style {
        return cachedStyle ?: createNewStyle().also { cachedStyle = it }
    }

    /**
     * Applies the [styles][StyleBuilder] to the [text].
     *
     * @param text The text to apply a [style][StyleBuilder] to
     *
     * @author NoComment1105
     */
    public fun applyTo(text: MutableText): MutableText {
        return text.setStyle(buildStyle())
    }
}

/**
 * Build a [Style] using the given [action] to set [StyleBuilder] properties.
 *
 * @author Cypher121
 */
public inline fun buildStyle(action: StyleBuilder.() -> Unit): Style {
    return StyleBuilder().apply(action).buildStyle()
}
