package org.quiltmc.qsk.wrapper.qsl.client.screen

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import org.quiltmc.qsl.base.api.util.TriState
import org.quiltmc.qsl.screen.api.client.ScreenEvents
import org.quiltmc.qsl.screen.api.client.ScreenMouseEvents.*
import org.quiltmc.qsl.screen.api.client.ScreenKeyboardEvents.*

//region: Mouse events

public typealias MouseClick<T> = (screen: Screen, mouseX: Double, mouseY: Double, button: Int) -> T
public typealias MouseScroll<T> =
            (screen: Screen, mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double) -> T

public fun allowMouseClick(callback: MouseClick<TriState>) {
    ALLOW_MOUSE_CLICK.register(AllowMouseClick(callback))
}

public fun beforeMouseClick(callback: MouseClick<Unit>) {
    BEFORE_MOUSE_CLICK.register(BeforeMouseClick(callback))
}

public fun afterMouseClick(callback: MouseClick<Unit>) {
    AFTER_MOUSE_CLICK.register(AfterMouseClick(callback))
}

public fun allowMouseRelease(callback: MouseClick<TriState>) {
    ALLOW_MOUSE_RELEASE.register(AllowMouseRelease(callback))
}

public fun beforeMouseRelease(callback: MouseClick<Unit>) {
    BEFORE_MOUSE_RELEASE.register(BeforeMouseRelease(callback))
}

public fun afterMouseRelease(callback: MouseClick<Unit>) {
    AFTER_MOUSE_RELEASE.register(AfterMouseRelease(callback))
}

public fun allowMouseScroll(callback: MouseScroll<TriState>) {
    ALLOW_MOUSE_SCROLL.register(AllowMouseScroll(callback))
}

public fun beforeMouseScroll(callback: MouseScroll<Unit>) {
    BEFORE_MOUSE_SCROLL.register(BeforeMouseScroll(callback))
}

public fun afterMouseScroll(callback: MouseScroll<Unit>) {
    AFTER_MOUSE_SCROLL.register(AfterMouseScroll(callback))
}

//endregion

//region: Keyboard events

public typealias KeyboardKey<T> = (screen: Screen, key: Int, scanCode: Int, modifiers: Int) -> T

public fun allowKeyPress(callback: KeyboardKey<TriState>) {
    ALLOW_KEY_PRESS.register(AllowKeyPress(callback))
}

public fun beforeKeyPress(callback: KeyboardKey<Unit>) {
    BEFORE_KEY_PRESS.register(BeforeKeyPress(callback))
}

public fun afterKeyPress(callback: KeyboardKey<Unit>) {
    AFTER_KEY_PRESS.register(AfterKeyPress(callback))
}

public fun allowKeyRelease(callback: KeyboardKey<TriState>) {
    ALLOW_KEY_RELEASE.register(AllowKeyRelease(callback))
}

public fun beforeKeyRelease(callback: KeyboardKey<Unit>) {
    BEFORE_KEY_RELEASE.register(BeforeKeyRelease(callback))
}

public fun afterKeyRelease(callback: KeyboardKey<Unit>) {
    AFTER_KEY_RELEASE.register(AfterKeyRelease(callback))
}

//endregion

//region: General Screen events

public typealias ScreenGenericCallback = (screen: Screen) -> Unit
public typealias ScreenInitCallback = (screen: Screen, mc: MinecraftClient, scaledWidth: Int, scaledHeight: Int) -> Unit
public typealias ScreenRenderCallback =
            (screen: Screen, matrices: MatrixStack, mouseX: Int, mouseY: Int, tickDelta: Float) -> Unit

public fun beforeScreenInit(callback: ScreenInitCallback) {
    ScreenEvents.BEFORE_INIT.register(ScreenEvents.BeforeInit(callback))
}

public fun afterScreenInit(callback: ScreenInitCallback) {
    ScreenEvents.AFTER_INIT.register(ScreenEvents.AfterInit(callback))
}

public fun screenRemoved(callback: ScreenGenericCallback) {
    ScreenEvents.REMOVE.register(ScreenEvents.Remove(callback))
}

public fun beforeScreenRender(callback: ScreenRenderCallback) {
    ScreenEvents.BEFORE_RENDER.register(ScreenEvents.BeforeRender(callback))
}

public fun afterScreenRender(callback: ScreenRenderCallback) {
    ScreenEvents.AFTER_RENDER.register(ScreenEvents.AfterRender(callback))
}

public fun beforeScreenTick(callback: ScreenGenericCallback) {
    ScreenEvents.BEFORE_TICK.register(ScreenEvents.BeforeTick(callback))
}

public fun afterScreenTick(callback: ScreenGenericCallback) {
    ScreenEvents.AFTER_TICK.register(ScreenEvents.AfterTick(callback))
}

//endregion
