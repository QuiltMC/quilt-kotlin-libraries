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

package org.quiltmc.qkl.library.lifecycle

import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldTickEvents

//region: Server lifecycle events

public typealias GenericServerCallback = MinecraftServer.() -> Unit

/**
 * @see ServerLifecycleEvents.STARTING
 * @see ServerLifecycleEvents.Starting.startingServer
 *
 * @author sschr15
 */
public fun EventRegistration.onServerStarting(callback: GenericServerCallback) {
    ServerLifecycleEvents.STARTING.register(ServerLifecycleEvents.Starting(callback))
}

/**
 * @see ServerLifecycleEvents.READY
 * @see ServerLifecycleEvents.Ready.readyServer
 *
 * @author sschr15
 */
public fun EventRegistration.onServerReady(callback: GenericServerCallback) {
    ServerLifecycleEvents.READY.register(ServerLifecycleEvents.Ready(callback))
}

/**
 * @see ServerLifecycleEvents.STOPPING
 * @see ServerLifecycleEvents.Stopping.stoppingServer
 *
 * @author sschr15
 */
public fun EventRegistration.onServerStopping(callback: GenericServerCallback) {
    ServerLifecycleEvents.STOPPING.register(ServerLifecycleEvents.Stopping(callback))
}

/**
 * @see ServerLifecycleEvents.STOPPED
 * @see ServerLifecycleEvents.Stopped.exitServer
 *
 * @author sschr15
 */
public fun EventRegistration.onServerStopped(callback: GenericServerCallback) {
    ServerLifecycleEvents.STOPPED.register(ServerLifecycleEvents.Stopped(callback))
}

//endregion

//region: Server tick events

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ServerTickEvents.START
 * @see ServerTickEvents.Start.startServerTick
 *
 * @author sschr15
 */
public fun EventRegistration.onServerTickStart(callback: GenericServerCallback) {
    ServerTickEvents.START.register(ServerTickEvents.Start(callback))
}

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ServerTickEvents.END
 * @see ServerTickEvents.End.endServerTick
 *
 * @author sschr15
 */
public fun EventRegistration.onServerTickEnd(callback: GenericServerCallback) {
    ServerTickEvents.END.register(ServerTickEvents.End(callback))
}

//endregion

//region: Server world events

public typealias ServerWorldCallback = MinecraftServer.(ServerWorld) -> Unit

/**
 * @see ServerWorldLoadEvents.LOAD
 * @see ServerWorldLoadEvents.Load.loadWorld
 *
 * @author sschr15
 */
public fun EventRegistration.onServerWorldLoad(callback: ServerWorldCallback) {
    ServerWorldLoadEvents.LOAD.register(ServerWorldLoadEvents.Load(callback))
}

/**
 * @see ServerWorldLoadEvents.UNLOAD
 * @see ServerWorldLoadEvents.Unload.unloadWorld
 *
 * @author sschr15
 */
public fun EventRegistration.onServerWorldUnload(callback: ServerWorldCallback) {
    ServerWorldLoadEvents.UNLOAD.register(ServerWorldLoadEvents.Unload(callback))
}

//endregion

//region: Server world tick events

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ServerWorldTickEvents.START
 * @see ServerWorldTickEvents.Start.startWorldTick
 *
 * @author sschr15
 */
public fun EventRegistration.onServerWorldTickStart(callback: ServerWorldCallback) {
    ServerWorldTickEvents.START.register(ServerWorldTickEvents.Start(callback))
}

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ServerWorldTickEvents.END
 * @see ServerWorldTickEvents.End.endWorldTick
 *
 * @author sschr15
 */
public fun EventRegistration.onServerWorldTickEnd(callback: ServerWorldCallback) {
    ServerWorldTickEvents.END.register(ServerWorldTickEvents.End(callback))
}

//endregion
