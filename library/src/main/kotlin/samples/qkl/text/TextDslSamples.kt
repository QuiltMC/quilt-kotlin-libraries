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

package samples.qkl.text

import net.minecraft.item.Items
import net.minecraft.text.Style
import net.minecraft.text.Text
import org.quiltmc.qkl.library.text.*


/**
 * Container for samples. Functions can be referenced
 * by their full name in the @sample KDoc tag to
 * inject the body as a documentation sample.
 *
 * @author Cypher121
 */
@Suppress("unused", "UNUSED_VARIABLE")
private object TextDslSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    fun sampleBuildText() {
        val hello: Text = buildText {
            italic {
                translatable("sample_mod.hello")
            }

            color(Color.GREEN) {
                bold {
                    translatable("sample_mod.world")
                }
            }
        }
    }

    fun sampleStyledText() {
        val preExistingStyle: Style = stub()

        val styledText: Text = buildText {
            styled(preExistingStyle) {
                literal("some text")
            }

            styled(bold = true, italic = true, color = Color.GOLD) {
                literal("more text")
            }
        }
    }

    fun sampleTextEvents() {
        val textWithEvents: Text = buildText {
            hoverEvent(HoverEvents.showItem(Items.DIAMOND)) {
                literal("hoverable")
            }

            clickEvent(ClickEvents.copyToClipboard("hello")) {
                literal("clickable")
            }
        }
    }

    fun sampleBuildStyle() {
        val style: Style = buildStyle {
            clickEvent = ClickEvents.openUrl("https://quiltmc.org")
            obfuscated = true

            underlined = false
        }
    }
}
