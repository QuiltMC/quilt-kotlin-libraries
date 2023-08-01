/*
 * Copyright 2023 The Quilt Project
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
@file:ClientOnly

package org.quiltmc.qkl.library.client.lifecycle

import net.minecraft.client.MinecraftClient
import net.minecraft.world.World
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.lifecycle.api.client.event.ClientLifecycleEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents
import org.quiltmc.qsl.lifecycle.api.client.event.ClientWorldTickEvents

//region: Client lifecycle events

public typealias GenericClientCallback = MinecraftClient.() -> Unit

/**
 * @see ClientLifecycleEvents.READY
 * @see ClientLifecycleEvents.Ready.readyClient
 *
 * @author sschr15
 */
public fun EventRegistration.onClientReady(callback: GenericClientCallback) {
    ClientLifecycleEvents.READY.register(ClientLifecycleEvents.Ready(callback))
}

/**
 * @see ClientLifecycleEvents.STOPPING
 * @see ClientLifecycleEvents.Stopping.stoppingClient
 *
 * @author sschr15
 */
public fun EventRegistration.onClientStopping(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPING.register(ClientLifecycleEvents.Stopping(callback))
}

/**
 * @see ClientLifecycleEvents.STOPPED
 * @see ClientLifecycleEvents.Stopped.stoppedClient
 *
 * @author sschr15
 */
public fun EventRegistration.onClientStopped(callback: GenericClientCallback) {
    ClientLifecycleEvents.STOPPED.register(ClientLifecycleEvents.Stopped(callback))
}

//endregion

//region: Client tick events

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ClientTickEvents.START
 * @see ClientTickEvents.Start.startClientTick
 *
 * @author sschr15
 */
public fun EventRegistration.onClientTickStart(callback: GenericClientCallback) {
    ClientTickEvents.START.register(ClientTickEvents.Start(callback))
}

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ClientTickEvents.END
 * @see ClientTickEvents.End.endClientTick
 *
 * @author sschr15
 */
public fun EventRegistration.onClientTickFinish(callback: GenericClientCallback) {
    ClientTickEvents.END.register(ClientTickEvents.End(callback))
}

//endregion

//region: Client world tick events

public typealias WorldTickCallback = MinecraftClient.(World) -> Unit

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ClientWorldTickEvents.START
 * @see ClientWorldTickEvents.Start.startWorldTick
 *
 * @author sschr15
 */
public fun EventRegistration.onWorldTickStart(callback: WorldTickCallback) {
    ClientWorldTickEvents.START.register(ClientWorldTickEvents.Start(callback))
}

/**
 * This tends to be a code hotspot, so any code should be sure to run quickly.
 * @see ClientWorldTickEvents.END
 * @see ClientWorldTickEvents.End.endWorldTick
 *
 * @author sschr15
 */
public fun EventRegistration.onWorldTickFinish(callback: WorldTickCallback) {
    ClientWorldTickEvents.END.register(ClientWorldTickEvents.End(callback))
}

//endregion
