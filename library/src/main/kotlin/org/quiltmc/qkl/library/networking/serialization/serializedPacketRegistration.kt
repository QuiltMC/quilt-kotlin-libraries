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

import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.quiltmc.qsl.networking.api.PacketSender
import org.quiltmc.qsl.networking.api.ServerPlayNetworking
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking

public typealias OnSerializedPacketClient<T> =
        T.(MinecraftClient, ClientPlayNetworkHandler, PacketSender) -> Unit
public typealias OnSerializedPacketServer<T> =
        T.(MinecraftServer, ServerPlayerEntity, ServerPlayNetworkHandler, PacketSender) -> Unit

/**
 * A utility class for serialized packet registration.
 * @param id The packet channel to expect this packet on.
 * @param direction The directions that this packet can be sent in.
 * @param serializersModule The serializers module to be used for decoding
 */
public class SerializedPacketRegistration<P>(
    public val id: Identifier,
    public val direction: Direction,
    public val serializersModule: SerializersModule
) {
    /**
     * Action to be executed when this packet is received on the client.
     */
    public var onClientReceiveAction: OnSerializedPacketClient<P>? = null
    /**
     * Action to be executed when this packet is received on the server.
     */
    public var onServerReceiveAction: OnSerializedPacketServer<P>? = null

    /**
     * Registers a method to use for receiving this packet on the client side.
     */
    public fun onClientReceive(action: OnSerializedPacketClient<P>) {
        onClientReceiveAction = action
    }

    /**
     * Registers a method to use for receiving this packet on the server side.
     */
    public fun onServerReceive(action: OnSerializedPacketServer<P>) {
        onServerReceiveAction = action
    }

    /**
     * Finishes building and registers the relevant listeners.
     * Usually should not be invoked manually, prefer use of [registerSerializedPacket].
     */
    public inline fun <reified T> finalize() {
        if (direction == Direction.ClientToServer || direction == Direction.BiDirectional) {
            check(onServerReceiveAction != null) {
                "No action set for receiving on the server with packet direction ${direction.name}"
            }

            ServerPlayNetworking
                .registerGlobalReceiver(id) { server, serverPlayer, playNetworking, packetByteBuf, sender ->
                val decoded = PacketByteBufDecoder.decode<T>(packetByteBuf, serializersModule)
                server.execute {
                    @Suppress("UNCHECKED_CAST")
                    onServerReceiveAction!!.invoke(decoded as P, server, serverPlayer, playNetworking, sender)
                }
            }
        }

        if (direction == Direction.ServerToClient || direction == Direction.BiDirectional) {
            check(onClientReceiveAction == null) {
                "No action set for receiving on the client with packet direction ${direction.name}"
            }

            ClientPlayNetworking.registerGlobalReceiver(id) { client, playNetworking, packetByteBuf, sender ->
                val decoded = PacketByteBufDecoder.decode<T>(packetByteBuf, serializersModule)
                client.execute {
                    @Suppress("UNCHECKED_CAST")
                    onClientReceiveAction!!.invoke(decoded as P, client, playNetworking, sender)
                }
            }
        }
    }

    /**
     * An enum used to give the direction that a packet may be received from during registration.
     */
    public enum class Direction {
        ClientToServer,
        ServerToClient,
        BiDirectional
    }
}

/**
 * Allows registering a serialized packet that automatically decodes and executes on the correct thread.
 */
public inline fun <reified T> registerSerializedPacket(
    id: Identifier,
    direction: SerializedPacketRegistration.Direction,
    serializersModule: SerializersModule = EmptySerializersModule(),
    action: SerializedPacketRegistration<T>.()->Unit
) {
    SerializedPacketRegistration<T>(id, direction, serializersModule).apply(action).finalize<T>()
}
