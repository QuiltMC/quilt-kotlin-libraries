/*
 * Copyright 2022 The Quilt Project
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

package org.quiltmc.qkl.library.text

import net.minecraft.block.MapColor
import net.minecraft.text.TextColor
import net.minecraft.util.DyeColor
import net.minecraft.util.Formatting
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
@JvmInline
public value class Color(public val value: Int) {
    @Suppress("MagicNumber")
    public constructor(red: Int, green: Int, blue: Int) : this(
        (red.coerceIn(0, 255) shl 16) +
        (green.coerceIn(0, 255) shl 8) +
        blue.coerceIn(0, 255)
    )

    /** A color of red influenced by [value]. */
    public val red: Int get() = value shr 16 and 0xFF

    /** A color of green influenced by [value]. */
    public val green: Int get() = value shr 8 and 0xFF

    /** A color of blue influenced by [value]. */
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
