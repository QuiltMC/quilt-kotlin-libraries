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

import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.text.TextColor
import net.minecraft.util.Formatting
import org.quiltmc.qkl.core.annotations.QklDslMarker

/**
 * A mutable color object that can be transformed into Minecraft [color][TextColor].
 *
 * @property rgb The RGB value to convert to a Minecraft [color][TextColor].
 *
 * @author NoComment1105
 */
@QklDslMarker("This is a utility class for TextDSL")
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
     * Converts [rgb] to a [TextColor]
     *
     * @return A [TextColor] created from the [rgb] value
     *
     * @author NoComment1105
     *
     */
    public fun toTextColor(): TextColor {
        return TextColor.fromRgb(rgb)
    }
}


/**
 * A mutable style object that transforms to Minecraft [styles][Style].
 *
 * @author NoComment1105
 */
@QklDslMarker("This is a utility class for TextDSL")
@Suppress("MagicNumber")
public class MutableStyle {
    public var color: MutableColor? = null
    public var obfuscated: Boolean = false
    public var bold: Boolean = false
    public var italic: Boolean = false
    public var strikethrough: Boolean = false
    public var underline: Boolean = false

    /**
     * Converts 3 separate RGB values to a [MutableColor].
     *
     * @param red The red channel of the color
     * @param green The green channel of the color
     * @param blue The blue channel of the color
     *
     * @author NoComment1105
     */
    public fun color(red: Int, green: Int, blue: Int) {
        this.color = MutableColor((red shl 16) + (green shl 8) + blue)
    }

    /**
     * Converts a single RGB value to a [MutableColor]
     *
     * @param rgb The RGB value to convert
     *
     * @author NoComment1105
     */
    public fun color(rgb: Int) {
        this.color = MutableColor(rgb)
    }

    /**
     * Converts a string color to a [MutableColor]
     *
     * @param color The color to convert
     *
     * @author NoComment1105
     */
    public fun color(color: String) {
        this.color = MutableColor(color.toColor())
    }

    /**
     * Converts a string color to an [Int]
     *
     * @return The [Int] converted color
     *
     * @author NoComment1105
     */
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
     * Applies the [styles][MutableStyle] to the [text]
     *
     * @param text The text to apply a [style][MutableStyle] to
     *
     * @author NoComment1105
     */
    public fun applyTo(text: MutableText): MutableText {
        return text.setStyle(
            Style.EMPTY.apply {
                if (color != null) {
                    withColor(color!!.rgb)
                }
                withObfuscated(obfuscated)
                withBold(bold)
                withItalic(italic)
                withStrikethrough(strikethrough)
                withUnderline(underline)
            }
        )
    }
}