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

package org.quiltmc.qsk.wrapper.qsl.worlds

import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.TeleportTarget
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions

/**
 * Teleport this entity to the given [location] in the [destination] dimension.
 * If [location] is null, the entity will not be teleported.
 */
public fun <E : Entity> Entity.teleport(destination: ServerWorld, location: TeleportTarget?): E? =
    QuiltDimensions.teleport(this, destination, location)

/**
 * Convenience function for [teleport] which returns an instance of the same type.
 */
public fun <E : Entity> E.teleportSelf(destination: ServerWorld, location: TeleportTarget?): E? =
    QuiltDimensions.teleport(this, destination, location)
