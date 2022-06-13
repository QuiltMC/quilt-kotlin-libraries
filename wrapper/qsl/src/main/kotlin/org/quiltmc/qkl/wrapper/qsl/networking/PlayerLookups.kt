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

package org.quiltmc.qkl.wrapper.qsl.networking

import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.ChunkPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i
import org.quiltmc.qsl.networking.api.PlayerLookup

/**
 * All players currently on the server.
 */
public val MinecraftServer.allPlayers: Collection<ServerPlayerEntity>
    get() = PlayerLookup.all(this)

/**
 * All players currently in the world or dimension.
 */
public val ServerWorld.playersInWorld: Collection<ServerPlayerEntity>
    get() = PlayerLookup.world(this)

/**
 * Returns the players who are currently "tracking" the chunk, i.e. who have the chunk loaded.
 */
public fun ServerWorld.getPlayersTrackingChunk(pos: ChunkPos): Collection<ServerPlayerEntity> =
    PlayerLookup.tracking(this, pos)

/**
 * The players who are currently "tracking" the entity, i.e. who have the entity loaded.
 */
public val Entity.playersTracking: Collection<ServerPlayerEntity>
    get() = PlayerLookup.tracking(this)

/**
 * The players who are currently "tracking" the block entity,
 * i.e. who have the block entity loaded.
 */
public val BlockEntity.playersTracking: Collection<ServerPlayerEntity>
    get() = PlayerLookup.tracking(this)

/**
 * Returns the players who are a max of [radius] blocks away from the [pos].
 */
public fun ServerWorld.getPlayersNear(pos: Vec3d, radius: Double): Collection<ServerPlayerEntity> =
    PlayerLookup.around(this, pos, radius)

/**
 * Returns the players who are a max of [radius] blocks away from the [pos].
 */
public fun ServerWorld.getPlayersNear(pos: Vec3i, radius: Double): Collection<ServerPlayerEntity> =
    PlayerLookup.around(this, pos, radius)
