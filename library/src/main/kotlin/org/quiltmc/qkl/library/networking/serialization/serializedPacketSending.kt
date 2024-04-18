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

package org.quiltmc.qkl.library.networking.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.networking.api.ServerPlayNetworking
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking

/**
 * A utility object for sending serialized objects from the client to the server.
 *
 * @see ClientPlayNetworking
 */
@ClientOnly
public object SerializedClientPlayNetworking {
    /**
     * Serializes and sends a packet object from the client to the server.
     */
    public inline fun <reified T> send(
        id: Identifier,
        obj: @Serializable T,
        serializersModule: SerializersModule = EmptySerializersModule()
    ) {
        ClientPlayNetworking.send(id, PacketByteBufEncoder.encode(obj, serializersModule))
    }
}

/**
 * A utility object for sending serialized objects from the server to players.
 *
 * @see ServerPlayNetworking
 */
public object SerializedServerPlayNetworking {
    /**
     * Serializes and sends a packet object from the server to a player.
     */
    public inline fun <reified T> send(
        player: ServerPlayerEntity,
        id: Identifier,
        obj: @Serializable T,
        serializersModule: SerializersModule = EmptySerializersModule()
    ) {
        ServerPlayNetworking.send(player, id, PacketByteBufEncoder.encode(obj, serializersModule))
    }

    /**
     * Serializes and sends a packet object from the server to the provided players.
     */
    public inline fun <reified T> send(
        players: Collection<ServerPlayerEntity>,
        id: Identifier,
        obj: @Serializable T,
        serializersModule: SerializersModule = EmptySerializersModule()
    ) {
        ServerPlayNetworking.send(players, id, PacketByteBufEncoder.encode(obj, serializersModule))
    }
}
