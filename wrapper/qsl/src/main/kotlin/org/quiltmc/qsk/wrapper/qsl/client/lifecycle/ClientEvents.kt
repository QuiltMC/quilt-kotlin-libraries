@file:Suppress("unused")
@file:Environment(EnvType.CLIENT)

package org.quiltmc.qsk.wrapper.qsl.client.lifecycle

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.world.World
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsk.wrapper.qsl.MustRunQuick
import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents

//region: Client lifecycle events

public typealias GenericClientCallback = (MinecraftClient) -> Unit

public fun EventRegistration.onClientReady(callback: GenericClientCallback) {
    ClientLifecycleEvents.READY.register(ClientLifecycleEvents.Ready(callback))
}

public fun EventRegistration.onClientStopping(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPING.register(ClientLifecycleEvents.Stopping(callback))
}

public fun EventRegistration.onClientStopped(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPED.register(ClientLifecycleEvents.Stopped(callback))
}

//endregion

//region: Client tick events

@MustRunQuick
public fun EventRegistration.onClientTickStart(callback: GenericClientCallback) {
    ClientTickEvents.START.register(ClientTickEvents.Start(callback))
}

@MustRunQuick
public fun EventRegistration.onClientTickFinish(callback: GenericClientCallback) {
    ClientTickEvents.END.register(ClientTickEvents.End(callback))
}

//endregion

//region: Client world tick events

public typealias WorldTickCallback = (MinecraftClient, World) -> Unit

@MustRunQuick
public fun EventRegistration.onWorldTickStart(callback: WorldTickCallback) {
    ClientWorldTickEvents.START.register(ClientWorldTickEvents.Start(callback))
}

@MustRunQuick
public fun EventRegistration.onWorldTickFinish(callback: WorldTickCallback) {
    ClientWorldTickEvents.END.register(ClientWorldTickEvents.End(callback))
}

//endregion
