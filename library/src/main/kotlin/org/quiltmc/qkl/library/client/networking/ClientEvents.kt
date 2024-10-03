/*
 * Copyright 2024 The Quilt Project
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

@file:ClientOnly
@file:Suppress("unused")

package org.quiltmc.qkl.library.client.networking

import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientConfigurationNetworkHandler
import net.minecraft.client.network.ClientLoginNetworkHandler
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.packet.payload.CustomPayload
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.client.C2SConfigurationChannelEvents
import org.quiltmc.qsl.networking.api.client.C2SPlayChannelEvents
import org.quiltmc.qsl.networking.api.client.ClientConfigurationConnectionEvents
import org.quiltmc.qsl.networking.api.client.ClientLoginConnectionEvents
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents

//region: Client login events
public typealias ClientLoginCallback = ClientLoginNetworkHandler.(
    client: MinecraftClient
) -> Unit

/**
 * @see ClientLoginConnectionEvents.INIT
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginInit(callback: ClientLoginCallback) {
    ClientLoginConnectionEvents.INIT.register(ClientLoginConnectionEvents.Init(callback))
}

/**
 * @see ClientLoginConnectionEvents.QUERY_START
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginQueryStart(callback: ClientLoginCallback) {
    ClientLoginConnectionEvents.QUERY_START.register(
        ClientLoginConnectionEvents.QueryStart(callback)
    )
}

/**
 * @see ClientLoginConnectionEvents.DISCONNECT
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginDisconnect(callback: ClientLoginCallback) {
    ClientLoginConnectionEvents.DISCONNECT.register(
        ClientLoginConnectionEvents.Disconnect(callback)
    )
}
//endregion

//region: Client configuration events
public typealias ClientConfigurationCallback = ClientConfigurationNetworkHandler.(
    client: MinecraftClient
) -> Unit

public typealias ClientConfigurationReadyCallback = ClientConfigurationNetworkHandler.(
    sender: PacketSender<CustomPayload>,
    client: MinecraftClient
) -> Unit

/**
 * @see ClientConfigurationConnectionEvents.INIT
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onConfigurationInit(callback: ClientConfigurationCallback) {
    ClientConfigurationConnectionEvents.INIT.register(ClientConfigurationConnectionEvents.Init(callback))
}

/**
 * @see ClientConfigurationConnectionEvents.START
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onConfigurationStart(callback: ClientConfigurationReadyCallback) {
    ClientConfigurationConnectionEvents.START.register(ClientConfigurationConnectionEvents.Start(callback))
}

/**
 * @see ClientConfigurationConnectionEvents.CONFIGURED
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onConfigurationConfigured(callback: ClientConfigurationCallback) {
    ClientConfigurationConnectionEvents.CONFIGURED.register(ClientConfigurationConnectionEvents.Configured(callback))
}

/**
 * @see ClientConfigurationConnectionEvents.DISCONNECT
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onConfigurationDisconnect(callback: ClientConfigurationCallback) {
    ClientConfigurationConnectionEvents.DISCONNECT.register(ClientConfigurationConnectionEvents.Disconnect(callback))
}
//endregion

//region: C2S configuration channel events
public typealias C2SConfigurationCallback = ClientConfigurationNetworkHandler.(
    sender: PacketSender<CustomPayload>,
    client: MinecraftClient,
    channels: List<CustomPayload.Id<*>>
) -> Unit

/**
 * @see C2SConfigurationChannelEvents.REGISTER
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onClientConfigurationChannelRegister(callback: C2SConfigurationCallback) {
    C2SConfigurationChannelEvents.REGISTER.register(C2SConfigurationChannelEvents.Register(callback))
}

/**
 * @see C2SConfigurationChannelEvents.UNREGISTER
 *
 * @author Quinn Semele
 */
public fun EventRegistration.onClientConfigurationChannelUnregister(callback: C2SConfigurationCallback) {
    C2SConfigurationChannelEvents.UNREGISTER.register(C2SConfigurationChannelEvents.Unregister(callback))
}
//endregion

//region: Client play connection events
public typealias ClientPlayCallback = ClientPlayNetworkHandler.(
    client: MinecraftClient
) -> Unit

public typealias ClientPlayJoinCallback = ClientPlayNetworkHandler.(
    sender: PacketSender<CustomPayload>,
    client: MinecraftClient
) -> Unit

/**
 * @see ClientPlayConnectionEvents.INIT
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayConnectionInit(callback: ClientPlayCallback) {
    ClientPlayConnectionEvents.INIT.register(ClientPlayConnectionEvents.Init(callback))
}

/**
 * @see ClientPlayConnectionEvents.JOIN
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayConnectionReady(callback: ClientPlayJoinCallback) {
    ClientPlayConnectionEvents.JOIN.register(ClientPlayConnectionEvents.Join(callback))
}

/**
 * @see ClientPlayConnectionEvents.DISCONNECT
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayConnectionDisconnect(callback: ClientPlayCallback) {
    ClientPlayConnectionEvents.DISCONNECT.register(
        ClientPlayConnectionEvents.Disconnect(callback)
    )
}
//endregion

//region: C2S play channel events
public typealias C2SPlayCallback = ClientPlayNetworkHandler.(
    sender: PacketSender<CustomPayload>,
    client: MinecraftClient,
    channels: List<CustomPayload.Id<*>>
) -> Unit

/**
 * @see C2SPlayChannelEvents.REGISTER
 *
 * @author sschr15
 */
public fun EventRegistration.onClientPlayChannelRegister(callback: C2SPlayCallback) {
    C2SPlayChannelEvents.REGISTER.register(C2SPlayChannelEvents.Register(callback))
}

/**
 * @see C2SPlayChannelEvents.UNREGISTER
 *
 * @author sschr15
 */
public fun EventRegistration.onClientPlayChannelUnregister(callback: C2SPlayCallback) {
    C2SPlayChannelEvents.UNREGISTER.register(C2SPlayChannelEvents.Unregister(callback))
}
//endregion
