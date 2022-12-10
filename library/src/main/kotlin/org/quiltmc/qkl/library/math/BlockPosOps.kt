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

package org.quiltmc.qkl.library.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i

/**
 * Adds [this] and [other], returning a new [BlockPos].
 */
public operator fun BlockPos.plus(other: Vec3i): BlockPos {
    return BlockPos(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts [other] from [this], returning a new [BlockPos].
 */
public operator fun BlockPos.minus(other: Vec3i): BlockPos {
    return BlockPos(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies [this] and [other] component-wise, returning a new [BlockPos].
 */
public operator fun BlockPos.times(other: Vec3i): BlockPos {
    return BlockPos(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies [this] with [other], returning a new [BlockPos].
 */
public operator fun BlockPos.times(other: Int): BlockPos {
    return BlockPos(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies [other] with [this], returning a new [BlockPos].
 */
public operator fun Int.times(other: BlockPos): BlockPos {
    return other * this
}

/**
 * Divides [this] by [other] component-wise, returning a new [BlockPos].
 */
public operator fun BlockPos.div(other: Vec3i): BlockPos {
    return BlockPos(
        this.x / other.x,
        this.y / other.y,
        this.z / other.z
    )
}

/**
 * Divides [this] by [other], returning a new [BlockPos].
 */
public operator fun BlockPos.div(other: Int): BlockPos {
    return BlockPos(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates [this], returning a new [BlockPos].
 */
public operator fun BlockPos.unaryMinus(): BlockPos {
    return -1 * this
}

/**
 * The cross product of [this] and [other].
 */
public infix fun BlockPos.cross(other: Vec3i): BlockPos {
    return this.crossProduct(other)
}
