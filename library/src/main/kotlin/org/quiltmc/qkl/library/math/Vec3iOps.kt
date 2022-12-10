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
import kotlin.math.sqrt

/**
 * The [`x`][Vec3i.x] component of [this].
 */
public operator fun Vec3i.component1(): Int {
    return this.x
}

/**
 * The [`y`][Vec3i.y] component of [this].
 */
public operator fun Vec3i.component2(): Int {
    return this.y
}

/**
 * The [`z`][Vec3i.z] component of [this].
 */
public operator fun Vec3i.component3(): Int {
    return this.z
}

/**
 * Adds [this] and [other], returning a new [Vec3i].
 */
public operator fun Vec3i.plus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts [other] from [this], returning a new [Vec3i].
 */
public operator fun Vec3i.minus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies [this] and [other] component-wise, returning a new [Vec3i].
 */
public operator fun Vec3i.times(other: Vec3i): Vec3i {
    return Vec3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies [this] with [other], returning a new [Vec3i].
 */
public operator fun Vec3i.times(other: Int): Vec3i {
    return Vec3i(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies [other] with [this], returning a new [Vec3i].
 */
public operator fun Int.times(other: Vec3i): Vec3i {
    return other * this
}

/**
 * Divides [this] by [other] component-wise, returning a new [Vec3i].
 */
public operator fun Vec3i.div(other: Vec3i): Vec3i {
    return Vec3i(
        this.x / other.x,
        this.y / other.y,
        this.z / other.z
    )
}

/**
 * Divides [this] by [other], returning a new [Vec3i].
 */
public operator fun Vec3i.div(other: Int): Vec3i {
    return Vec3i(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates [this], returning a new [Vec3i].
 */
public operator fun Vec3i.unaryMinus(): Vec3i {
    return -1 * this
}

/**
 * The dot product of [this] and [other].
 */
public infix fun Vec3i.dot(other: Vec3i): Int {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of [this] and [other].
 */
public infix fun Vec3i.cross(other: Vec3i): Vec3i {
    return this.crossProduct(other)
}

/**
 * The length of [this].
 */
public fun Vec3i.length(): Double {
    return sqrt(this.lengthSquared().toDouble())
}

/**
 * The squared length of [this].
 */
public fun Vec3i.lengthSquared(): Int {
    return this.x * this.x + this.y * this.y + this.z * this.z
}

/**
 * Converts [this] to a [BlockPos].
 */
public fun Vec3i.toBlockPos(): BlockPos {
    return BlockPos(
        this.x,
        this.y,
        this.z
    )
}

/**
 * Converts [this] to a [Vec3d].
 */
public fun Vec3i.toVec3d(): Vec3d {
    return Vec3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}
