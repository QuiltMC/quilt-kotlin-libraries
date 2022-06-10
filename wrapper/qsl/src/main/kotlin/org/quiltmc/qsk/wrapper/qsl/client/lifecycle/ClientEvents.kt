@file:Environment(EnvType.CLIENT)

package org.quiltmc.qsk.wrapper.qsl.client.lifecycle

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.world.World
import org.quiltmc.qsk.wrapper.qsl.lifecycle.MustRunQuick
import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents

//region: Client lifecycle events

public typealias GenericClientCallback = (MinecraftClient) -> Unit

public fun onClientReady(callback: GenericClientCallback) {
    ClientLifecycleEvents.READY.register(ClientLifecycleEvents.Ready(callback))
}

public fun onClientStopping(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPING.register(ClientLifecycleEvents.Stopping(callback))
}

public fun onClientStopped(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPED.register(ClientLifecycleEvents.Stopped(callback))
}

//endregion

//region: Client tick events

@MustRunQuick
public fun onClientTickStart(callback: GenericClientCallback) {
    ClientTickEvents.START.register(ClientTickEvents.Start(callback))
}

@MustRunQuick
public fun onClientTickFinish(callback: GenericClientCallback) {
    ClientTickEvents.END.register(ClientTickEvents.End(callback))
}

//endregion

//region: Client world tick events

public typealias WorldTickCallback = (MinecraftClient, World) -> Unit

@MustRunQuick
public fun onWorldTickStart(callback: WorldTickCallback) {
    ClientWorldTickEvents.START.register(ClientWorldTickEvents.Start(callback))
}

@MustRunQuick
public fun onWorldTickFinish(callback: WorldTickCallback) {
    ClientWorldTickEvents.END.register(ClientWorldTickEvents.End(callback))
}

//endregion
