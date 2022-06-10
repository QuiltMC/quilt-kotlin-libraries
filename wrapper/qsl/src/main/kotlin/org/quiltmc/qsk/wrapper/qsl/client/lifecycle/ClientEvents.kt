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

public typealias GenericClientCallback = MinecraftClient.() -> Unit

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

public typealias WorldTickCallback = MinecraftClient.(World) -> Unit

@MustRunQuick
public fun EventRegistration.onWorldTickStart(callback: WorldTickCallback) {
    ClientWorldTickEvents.START.register(ClientWorldTickEvents.Start(callback))
}

@MustRunQuick
public fun EventRegistration.onWorldTickFinish(callback: WorldTickCallback) {
    ClientWorldTickEvents.END.register(ClientWorldTickEvents.End(callback))
}

//endregion
