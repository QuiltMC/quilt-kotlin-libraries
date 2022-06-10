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
