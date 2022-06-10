@file:Suppress("unused")

package org.quiltmc.qsk.wrapper.qsl.lifecycle

import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsk.wrapper.qsl.MustRunQuick
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldTickEvents

//region: Server lifecycle events

public typealias GenericServerCallback = (MinecraftServer) -> Unit

public fun EventRegistration.onServerStarting(callback: GenericServerCallback) {
    ServerLifecycleEvents.STARTING.register(ServerLifecycleEvents.Starting(callback))
}

public fun EventRegistration.onServerReady(callback: GenericServerCallback) {
    ServerLifecycleEvents.READY.register(ServerLifecycleEvents.Ready(callback))
}

public fun EventRegistration.onServerStopping(callback: GenericServerCallback) {
    ServerLifecycleEvents.STOPPING.register(ServerLifecycleEvents.Stopping(callback))
}

public fun EventRegistration.onServerStopped(callback: GenericServerCallback) {
    ServerLifecycleEvents.STOPPED.register(ServerLifecycleEvents.Stopped(callback))
}

//endregion

//region: Server tick events

@MustRunQuick
public fun EventRegistration.onServerTickStart(callback: GenericServerCallback) {
    ServerTickEvents.START.register(ServerTickEvents.Start(callback))
}

@MustRunQuick
public fun EventRegistration.onServerTickEnd(callback: GenericServerCallback) {
    ServerTickEvents.END.register(ServerTickEvents.End(callback))
}

//endregion

//region: Server world events

public typealias ServerWorldCallback = (MinecraftServer, ServerWorld) -> Unit

public fun EventRegistration.onServerWorldLoad(callback: ServerWorldCallback) {
    ServerWorldLoadEvents.LOAD.register(ServerWorldLoadEvents.Load(callback))
}

public fun EventRegistration.onServerWorldUnload(callback: ServerWorldCallback) {
    ServerWorldLoadEvents.UNLOAD.register(ServerWorldLoadEvents.Unload(callback))
}

//endregion

//region: Server world tick events

@MustRunQuick
public fun EventRegistration.onServerWorldTickStart(callback: ServerWorldCallback) {
    ServerWorldTickEvents.START.register(ServerWorldTickEvents.Start(callback))
}

@MustRunQuick
public fun EventRegistration.onServerWorldTickEnd(callback: ServerWorldCallback) {
    ServerWorldTickEvents.END.register(ServerWorldTickEvents.End(callback))
}

//endregion
