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
