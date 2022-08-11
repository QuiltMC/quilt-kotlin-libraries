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

package org.quiltmc.qkl.wrapper.minecraft.math

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i

//region Standard math operators
/**
 * Adds a [Vec3f] to a [Vec3f].
 */
public operator fun Vec3f.plus(other: Vec3f): Vec3f {
    return Vec3f(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3f] from a [Vec3f].
 */
public operator fun Vec3f.minus(other: Vec3f): Vec3f {
    return Vec3f(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3f.times(other: Vec3f): Vec3f {
    return Vec3f(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies a [Vec3f] and a Float.
 */
public operator fun Vec3f.times(other: Float): Vec3f {
    return Vec3f(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies a Float and a [Vec3f].
 */
public operator fun Float.times(other: Vec3f): Vec3f {
    return Vec3f(
        this * other.x,
        this * other.y,
        this * other.z
    )
}

/**
 * Divides a [Vec3f] and a Float.
 */
public operator fun Vec3f.div(other: Float): Vec3f {
    return Vec3f(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates a [Vec3f].
 */
public operator fun Vec3f.unaryMinus(): Vec3f {
    return this.times(-1f)
}
//endregion

//region xAssign math operators
/**
 * Adds a [Vec3f] to a [Vec3f].
 */
public operator fun Vec3f.plusAssign(other: Vec3f) {
    this.set(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3f] from a [Vec3f].
 */
public operator fun Vec3f.minusAssign(other: Vec3f) {
    this.set(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3f.timesAssign(other: Vec3f) {
    this.set(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies a [Vec3f] and a Float.
 */
public operator fun Vec3f.timesAssign(other: Float) {
    this.set(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Divides a [Vec3f] and a Float.
 */
public operator fun Vec3f.divAssign(other: Float) {
    this.set(
        this.x / other,
        this.y / other,
        this.z / other
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vec3f] and a [Vec3f].
 */
public infix fun Vec3f.dot(other: Vec3f): Float {
    return this.dot(other)
}

/**
 * The cross product of a [Vec3f] and a [Vec3f].
 */
public infix fun Vec3f.cross(other: Vec3f): Vec3f {
    return Vec3f(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * The [`x`][Vec3f.x] component of a [Vec3f].
 */
public operator fun Vec3f.component1(): Float {
    return this.x
}

/**
 * The [`y`][Vec3f.y] component of a [Vec3f].
 */
public operator fun Vec3f.component2(): Float {
    return this.y
}

/**
 * The [`z`][Vec3f.z] component of a [Vec3f].
 */
public operator fun Vec3f.component3(): Float {
    return this.z
}
//endregion

//region Conversion methods
/**
 * Converts a [Vec3f] to a [Vec3d].
 */
public fun Vec3f.toVec3d(): Vec3d {
    return Vec3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}

/**
 * Converts a [Vec3f] to a [Vec3i].
 */
public fun Vec3f.toVec3i(): Vec3i {
    return Vec3i(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}

/**
 * Converts a [Vec3f] to a [BlockPos].
 */
public fun Vec3f.toBlockPos(): BlockPos {
    return BlockPos(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}

/**
 * Converts a [Vec3f] to a [Vector3d].
 */
public fun Vec3f.toVector3d(): Vector3d {
    return Vector3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}
//endregion
