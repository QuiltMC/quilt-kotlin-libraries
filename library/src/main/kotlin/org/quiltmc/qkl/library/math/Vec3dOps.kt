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
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3i

/**
 * The [`x`][Vec3d.x] component of [this].
 */
public operator fun Vec3d.component1(): Double {
    return this.x
}

/**
 * The [`y`][Vec3d.y] component of [this].
 */
public operator fun Vec3d.component2(): Double {
    return this.y
}

/**
 * The [`z`][Vec3d.z] component of [this].
 */
public operator fun Vec3d.component3(): Double {
    return this.z
}

/**
 * Adds [this] and [other], returning a new [Vec3d].
 */
public operator fun Vec3d.plus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts [other] from [this], returning a new [Vec3d].
 */
public operator fun Vec3d.minus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies [this] and [other] component-wise, returning a new [Vec3d].
 */
public operator fun Vec3d.times(other: Vec3d): Vec3d {
    return Vec3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies [this] with [other], returning a new [Vec3d].
 */
public operator fun Vec3d.times(other: Double): Vec3d {
    return Vec3d(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies [other] with [this], returning a new [Vec3d].
 */
public operator fun Double.times(other: Vec3d): Vec3d {
    return other * this
}

/**
 * Divides [this] by [other] component-wise, returning a new [Vec3d].
 */
public operator fun Vec3d.div(other: Vec3d): Vec3d {
    return Vec3d(
        this.x / other.x,
        this.y / other.y,
        this.z / other.z
    )
}

/**
 * Divides [this] by [other], returning a new [Vec3d].
 */
public operator fun Vec3d.div(other: Double): Vec3d {
    return Vec3d(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates [this], returning a new [Vec3d].
 */
public operator fun Vec3d.unaryMinus(): Vec3d {
    return -1.0 * this
}

/**
 * The dot product of [this] and [other].
 */
public infix fun Vec3d.dot(other: Vec3d): Double {
    return this.dotProduct(other)
}

/**
 * The cross product of [this] and [other].
 */
public infix fun Vec3d.cross(other: Vec3d): Vec3d {
    return this.crossProduct(other)
}

/**
 * Converts [this] to a [Vec3i].
 */
public fun Vec3d.toVec3i(): Vec3i {
    return Vec3i(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}

/**
 * Converts [this] to a [BlockPos].
 */
public fun Vec3d.toBlockPos(): BlockPos {
    return BlockPos(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}
