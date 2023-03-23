/*
 * Copyright 2023 QuiltMC
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

package org.quiltmc.qkl.library.networking

import net.minecraft.entity.Entity
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerLoginNetworkHandler
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.networking.api.*

//region: Server login events
public typealias GenericLoginCallback = ServerLoginNetworkHandler.(
    server: MinecraftServer
) -> Unit

public typealias LoginQueryStartCallback = ServerLoginNetworkHandler.(
    server: MinecraftServer,
    packetSender: PacketSender,
    synchronizer: ServerLoginNetworking.LoginSynchronizer,
) -> Unit

/**
 * @see ServerLoginConnectionEvents.INIT
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginInit(callback: GenericLoginCallback) {
    ServerLoginConnectionEvents.INIT.register(ServerLoginConnectionEvents.Init(callback))
}

/**
 * @see ServerLoginConnectionEvents.QUERY_START
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginQueryStart(callback: LoginQueryStartCallback) {
    ServerLoginConnectionEvents.QUERY_START.register(
        ServerLoginConnectionEvents.QueryStart(callback)
    )
}

/**
 * @see ServerLoginConnectionEvents.DISCONNECT
 *
 * @author sschr15
 */
public fun EventRegistration.onLoginDisconnect(callback: GenericLoginCallback) {
    ServerLoginConnectionEvents.DISCONNECT.register(
        ServerLoginConnectionEvents.Disconnect(callback)
    )
}
//endregion

//region: Server play connection events
public typealias GenericPlayCallback = ServerPlayNetworkHandler.(
    server: MinecraftServer
) -> Unit

public typealias S2CPlayReadyCallback = ServerPlayNetworkHandler.(
    packetSender: PacketSender,
    server: MinecraftServer,
) -> Unit

/**
 * @see ServerPlayConnectionEvents.INIT
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayInit(callback: GenericPlayCallback) {
    ServerPlayConnectionEvents.INIT.register(ServerPlayConnectionEvents.Init(callback))
}

/**
 * @see ServerPlayConnectionEvents.JOIN
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayReady(callback: S2CPlayReadyCallback) {
    ServerPlayConnectionEvents.JOIN.register(ServerPlayConnectionEvents.Join(callback))
}

/**
 * @see ServerPlayConnectionEvents.DISCONNECT
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayDisconnect(callback: GenericPlayCallback) {
    ServerPlayConnectionEvents.DISCONNECT.register(
        ServerPlayConnectionEvents.Disconnect(callback)
    )
}
//endregion

//region: S2C play channel events
public typealias S2CPlayChannelCallback = ServerPlayNetworkHandler.(
    packetSender: PacketSender,
    server: MinecraftServer,
    channels: List<Identifier>
) -> Unit

/**
 * @see S2CPlayChannelEvents.REGISTER
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayChannelRegister(callback: S2CPlayChannelCallback) {
    S2CPlayChannelEvents.REGISTER.register(S2CPlayChannelEvents.Register(callback))
}

/**
 * @see S2CPlayChannelEvents.UNREGISTER
 *
 * @author sschr15
 */
public fun EventRegistration.onPlayChannelUnregister(callback: S2CPlayChannelCallback) {
    S2CPlayChannelEvents.UNREGISTER.register(S2CPlayChannelEvents.Unregister(callback))
}
//endregion

//region: Entity tracking events
public typealias EntityTrackingCallback = (
    trackedEntity: Entity,
    player: ServerPlayerEntity,
) -> Unit

/**
 * Invoked before a player on the server starts tracking an entity.
 * This usually happens when the entity gets close enough
 * to the player to be seen.
 *
 * @see EntityTrackingEvents.BEFORE_START_TRACKING
 *
 * @author SilverAndro
 */
public fun EventRegistration.onBeforeEntityTrackingStart(callback: EntityTrackingCallback) {
    EntityTrackingEvents.BEFORE_START_TRACKING.register(EntityTrackingEvents.BeforeStartTracking(callback))
}

/**
 * Invoked after a player on the server starts tracking an entity.
 * This usually happens when the entity gets close enough
 * to the player to be seen.
 *
 * @see EntityTrackingEvents.AFTER_START_TRACKING
 *
 * @author sschr15
 */
public fun EventRegistration.onAfterEntityTrackingStart(callback: EntityTrackingCallback) {
    EntityTrackingEvents.AFTER_START_TRACKING.register(EntityTrackingEvents.AfterStartTracking(callback))
}

/**
 * Invoked when a player on the server stops tracking an entity.
 * By the time this event runs, the player has already been told
 * to destroy the entity instance, but the entity is still on the server.
 *
 * @see EntityTrackingEvents.STOP_TRACKING
 *
 * @author sschr15
 */
public fun EventRegistration.onEntityTrackingStop(callback: EntityTrackingCallback) {
    EntityTrackingEvents.STOP_TRACKING.register(EntityTrackingEvents.StopTracking(callback))
}
//endregion
