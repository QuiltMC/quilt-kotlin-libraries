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

@file:Environment(EnvType.CLIENT)
@file:Suppress("unused")

package org.quiltmc.qkl.library.client.networking

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientLoginNetworkHandler
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.client.C2SPlayChannelEvents
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

//region: Client play connection events
public typealias ClientPlayCallback = ClientPlayNetworkHandler.(
    client: MinecraftClient
) -> Unit

public typealias ClientPlayJoinCallback = ClientPlayNetworkHandler.(
    sender: PacketSender,
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
    sender: PacketSender,
    client: MinecraftClient,
    channels: List<Identifier>
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
