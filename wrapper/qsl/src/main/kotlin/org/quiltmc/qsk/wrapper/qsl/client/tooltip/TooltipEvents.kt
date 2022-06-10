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

@file:Suppress("unused")
@file:Environment(EnvType.CLIENT)

package org.quiltmc.qsk.wrapper.qsl.client.tooltip

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.item.TooltipContext
import net.minecraft.client.item.TooltipData
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback
import org.quiltmc.qsl.tooltip.api.client.TooltipComponentCallback

public typealias TooltipCallback = (
    stack: ItemStack,
    player: PlayerEntity?,
    ctx: TooltipContext,
    lines: List<Text>
) -> Unit

public typealias TooltipComponentGenerator = (data: TooltipData) -> TooltipComponent?

public fun EventRegistration.onItemTooltip(callback: TooltipCallback) {
    ItemTooltipCallback.EVENT.register(ItemTooltipCallback(callback))
}

public fun EventRegistration.onTooltipComponent(callback: TooltipComponentGenerator) {
    TooltipComponentCallback.EVENT.register(TooltipComponentCallback(callback))
}
