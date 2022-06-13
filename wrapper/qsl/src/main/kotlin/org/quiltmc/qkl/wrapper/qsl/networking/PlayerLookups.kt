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

public val MinecraftServer.allPlayers: Collection<ServerPlayerEntity>
    get() = PlayerLookup.all(this)

public val ServerWorld.playersInWorld: Collection<ServerPlayerEntity>
    get() = PlayerLookup.world(this)

public fun ServerWorld.getPlayersTrackingChunk(pos: ChunkPos): Collection<ServerPlayerEntity> =
    PlayerLookup.tracking(this, pos)

public fun Entity.getPlayersTracking(): Collection<ServerPlayerEntity> =
    PlayerLookup.tracking(this)

public fun BlockEntity.getPlayersTracking(): Collection<ServerPlayerEntity> =
    PlayerLookup.tracking(this)

public fun ServerWorld.getPlayersNear(pos: Vec3d, radius: Double): Collection<ServerPlayerEntity> =
    PlayerLookup.around(this, pos, radius)

public fun ServerWorld.getPlayersNear(pos: Vec3i, radius: Double): Collection<ServerPlayerEntity> =
    PlayerLookup.around(this, pos, radius)
