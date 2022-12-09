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

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i

//region Standard math operators
/**
 * Adds a [Vec3d] to a [Vec3d].
 */
public operator fun Vec3d.plus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3d] from a [Vec3d].
 */
public operator fun Vec3d.minus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3d] and a [Vec3d].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3d.times(other: Vec3d): Vec3d {
    return Vec3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies a [Vec3d] and a Double.
 */
public operator fun Vec3d.times(other: Double): Vec3d {
    return Vec3d(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies a Double and a [Vec3d].
 */
public operator fun Double.times(other: Vec3d): Vec3d {
    return Vec3d(
        this * other.x,
        this * other.y,
        this * other.z
    )
}

/**
 * Divides a [Vec3d] and a Double.
 */
public operator fun Vec3d.div(other: Double): Vec3d {
    return Vec3d(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates a [Vec3d].
 */
public operator fun Vec3d.unaryMinus(): Vec3d {
    return this.times(-1.0)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vector3d] to a [Vec3d].
 */
public operator fun Vec3d.plus(other: Vector3d): Vec3d {
    return Vec3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vector3d] from a [Vec3d].
 */
public operator fun Vec3d.minus(other: Vector3d): Vec3d {
    return Vec3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3d] and a [Vector3d].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3d.times(other: Vector3d): Vec3d {
    return Vec3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vec3d] and a [Vec3d].
 */
public infix fun Vec3d.dot(other: Vec3d): Double {
    return this.dotProduct(other)
}

/**
 * The cross product of a [Vec3d] and a [Vec3d].
 */
public infix fun Vec3d.cross(other: Vec3d): Vec3d {
    return this.crossProduct(other)
}

/**
 * The dot product of a [Vec3d] and a [Vector3d].
 */
public infix fun Vec3d.dot(other: Vector3d): Double {
    return this.dotProduct(Vec3d(other.x, other.y, other.z))
}

/**
 * The cross product of a [Vec3d] and a [Vector3d].
 */
public infix fun Vec3d.cross(other: Vector3d): Vec3d {
    return this.crossProduct(Vec3d(other.x, other.y, other.z))
}

/**
 * The [`x`][Vec3d.x] component of a [Vec3d].
 */
public operator fun Vec3d.component1(): Double {
    return this.x
}

/**
 * The [`y`][Vec3d.y] component of a [Vec3d].
 */
public operator fun Vec3d.component2(): Double {
    return this.y
}

/**
 * The [`z`][Vec3d.z] component of a [Vec3d].
 */
public operator fun Vec3d.component3(): Double {
    return this.z
}
//endregion

//region Conversion methods
/**
 * Converts a [Vec3d] to a [Vec3f].
 */
public fun Vec3d.toVec3f(): Vec3f {
    return Vec3f(
        this.x.toFloat(),
        this.y.toFloat(),
        this.z.toFloat()
    )
}

/**
 * Converts a [Vec3d] to a [Vec3i].
 */
public fun Vec3d.toVec3i(): Vec3i {
    return Vec3i(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}

/**
 * Converts a [Vec3d] to a [BlockPos].
 */
public fun Vec3d.toBlockPos(): BlockPos {
    return BlockPos(
        this.x.toInt(),
        this.y.toInt(),
        this.z.toInt()
    )
}

/**
 * Converts a [Vec3d] to a [Vector3d].
 */
public fun Vec3d.toVector3d(): Vector3d {
    return Vector3d(
        this.x,
        this.y,
        this.z
    )
}
//endregion
