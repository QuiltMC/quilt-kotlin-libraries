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

@file:Environment(EnvType.CLIENT)

package org.quiltmc.qsk.wrapper.qsl.client.screen

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ClickableWidget
import net.minecraft.client.render.item.ItemRenderer
import org.quiltmc.qsl.screen.api.client.QuiltScreen

/**
 * All buttons on the screen. Adding and removing buttons can be done by
 * modifying this list, and doing so in a different way is not recommended.
 */
public val Screen.buttons: MutableList<ClickableWidget>
    get() = (this as QuiltScreen).buttons

/**
 * The current item renderer.
 */
public val Screen.itemRenderer: ItemRenderer
    get() = (this as QuiltScreen).itemRenderer

/**
 * The current text renderer.
 */
public val Screen.textRenderer: TextRenderer
    get() = (this as QuiltScreen).textRenderer

/**
 * The current Minecraft client instance.
 */
public val Screen.client: MinecraftClient
    get() = (this as QuiltScreen).client
