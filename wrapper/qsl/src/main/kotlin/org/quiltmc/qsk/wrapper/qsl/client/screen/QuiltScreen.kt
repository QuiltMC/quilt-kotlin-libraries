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

public val Screen.buttons: List<ClickableWidget>
    get() = (this as QuiltScreen).buttons

public val Screen.itemRenderer: ItemRenderer
    get() = (this as QuiltScreen).itemRenderer

public val Screen.textRenderer: TextRenderer
    get() = (this as QuiltScreen).textRenderer

public val Screen.client: MinecraftClient
    get() = (this as QuiltScreen).client
