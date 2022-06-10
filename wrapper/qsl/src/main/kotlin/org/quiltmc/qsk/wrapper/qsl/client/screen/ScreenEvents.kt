@file:Suppress("unused")
@file:Environment(EnvType.CLIENT)

package org.quiltmc.qsk.wrapper.qsl.client.screen

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.base.api.util.TriState
import org.quiltmc.qsl.screen.api.client.ScreenEvents
import org.quiltmc.qsl.screen.api.client.ScreenMouseEvents.*
import org.quiltmc.qsl.screen.api.client.ScreenKeyboardEvents.*

//region: Mouse events

public typealias MouseClick<T> = Screen.(
    mouseX: Double,
    mouseY: Double,
    button: Int
) -> T

public typealias MouseScroll<T> = Screen.(
    mouseX: Double,
    mouseY: Double,
    scrollX: Double,
    scrollY: Double
) -> T

public fun EventRegistration.allowMouseClick(callback: MouseClick<TriState>) {
    ALLOW_MOUSE_CLICK.register(AllowMouseClick(callback))
}

public fun EventRegistration.beforeMouseClick(callback: MouseClick<Unit>) {
    BEFORE_MOUSE_CLICK.register(BeforeMouseClick(callback))
}

public fun EventRegistration.afterMouseClick(callback: MouseClick<Unit>) {
    AFTER_MOUSE_CLICK.register(AfterMouseClick(callback))
}

public fun EventRegistration.allowMouseRelease(callback: MouseClick<TriState>) {
    ALLOW_MOUSE_RELEASE.register(AllowMouseRelease(callback))
}

public fun EventRegistration.beforeMouseRelease(callback: MouseClick<Unit>) {
    BEFORE_MOUSE_RELEASE.register(BeforeMouseRelease(callback))
}

public fun EventRegistration.afterMouseRelease(callback: MouseClick<Unit>) {
    AFTER_MOUSE_RELEASE.register(AfterMouseRelease(callback))
}

public fun EventRegistration.allowMouseScroll(callback: MouseScroll<TriState>) {
    ALLOW_MOUSE_SCROLL.register(AllowMouseScroll(callback))
}

public fun EventRegistration.beforeMouseScroll(callback: MouseScroll<Unit>) {
    BEFORE_MOUSE_SCROLL.register(BeforeMouseScroll(callback))
}

public fun EventRegistration.afterMouseScroll(callback: MouseScroll<Unit>) {
    AFTER_MOUSE_SCROLL.register(AfterMouseScroll(callback))
}

//endregion

//region: Keyboard events

public typealias KeyboardKey<T> = Screen.(
    key: Int,
    scanCode: Int,
    modifiers: Int
) -> T

public fun EventRegistration.allowKeyPress(callback: KeyboardKey<TriState>) {
    ALLOW_KEY_PRESS.register(AllowKeyPress(callback))
}

public fun EventRegistration.beforeKeyPress(callback: KeyboardKey<Unit>) {
    BEFORE_KEY_PRESS.register(BeforeKeyPress(callback))
}

public fun EventRegistration.afterKeyPress(callback: KeyboardKey<Unit>) {
    AFTER_KEY_PRESS.register(AfterKeyPress(callback))
}

public fun EventRegistration.allowKeyRelease(callback: KeyboardKey<TriState>) {
    ALLOW_KEY_RELEASE.register(AllowKeyRelease(callback))
}

public fun EventRegistration.beforeKeyRelease(callback: KeyboardKey<Unit>) {
    BEFORE_KEY_RELEASE.register(BeforeKeyRelease(callback))
}

public fun EventRegistration.afterKeyRelease(callback: KeyboardKey<Unit>) {
    AFTER_KEY_RELEASE.register(AfterKeyRelease(callback))
}

//endregion

//region: General Screen events

public typealias ScreenGenericCallback = Screen.() -> Unit
public typealias ScreenInitCallback = Screen.(
    mc: MinecraftClient,
    scaledWidth: Int,
    scaledHeight: Int
) -> Unit

public typealias ScreenRenderCallback = Screen.(
    matrices: MatrixStack,
    mouseX: Int,
    mouseY: Int,
    tickDelta: Float
) -> Unit

public fun EventRegistration.beforeScreenInit(callback: ScreenInitCallback) {
    ScreenEvents.BEFORE_INIT.register(ScreenEvents.BeforeInit(callback))
}

public fun EventRegistration.afterScreenInit(callback: ScreenInitCallback) {
    ScreenEvents.AFTER_INIT.register(ScreenEvents.AfterInit(callback))
}

public fun EventRegistration.onScreenRemoved(callback: ScreenGenericCallback) {
    ScreenEvents.REMOVE.register(ScreenEvents.Remove(callback))
}

public fun EventRegistration.beforeScreenRender(callback: ScreenRenderCallback) {
    ScreenEvents.BEFORE_RENDER.register(ScreenEvents.BeforeRender(callback))
}

public fun EventRegistration.afterScreenRender(callback: ScreenRenderCallback) {
    ScreenEvents.AFTER_RENDER.register(ScreenEvents.AfterRender(callback))
}

public fun EventRegistration.beforeScreenTick(callback: ScreenGenericCallback) {
    ScreenEvents.BEFORE_TICK.register(ScreenEvents.BeforeTick(callback))
}

public fun EventRegistration.afterScreenTick(callback: ScreenGenericCallback) {
    ScreenEvents.AFTER_TICK.register(ScreenEvents.AfterTick(callback))
}

//endregion
