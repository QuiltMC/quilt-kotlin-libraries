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

package org.quiltmc.qkl.library.worlds

import net.minecraft.entity.Entity
import net.minecraft.world.DimensionTransition
import org.quiltmc.qsl.worldgen.dimension.api.QuiltDimensions

/**
 * Teleport this entity to the given [location].
 * If [location] is null, the entity will not be teleported.
 * If the entity in the teleported location is not the same type
 * and is not null, an exception will be thrown. Otherwise,
 * the teleported entity (or null) will be returned.
 *
 * If the entity is unable to be teleported, this returns null.
 *
 * Every entity put into the world is a new copy of the entity,
 * unless the given entity is already in the world or is the player.
 *
 * @author sschr15
 */
public inline fun <reified E : Entity> E.teleport(
    location: DimensionTransition
): E? = when (val teleported = QuiltDimensions.teleport<Entity>(this, location)) {
    null -> null
    is E -> teleported
    else -> error("Unexpected teleported entity type: " +
            "${teleported::class.java.name} instead of ${E::class.java.name}")
}

/**
 * A class-safe version of [teleport], where the type of the teleported entity
 * is not specified and therefore may have changed when teleported.
 *
 * @author sschr15
 */
public fun Entity.teleportGeneral(
    location: DimensionTransition
): Entity? = QuiltDimensions.teleport(this, location)
