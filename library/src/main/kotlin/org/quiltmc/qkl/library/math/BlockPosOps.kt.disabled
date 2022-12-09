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
 * Adds a [BlockPos] to a [BlockPos].
 */
public operator fun BlockPos.plus(other: BlockPos): BlockPos {
    return BlockPos(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [BlockPos] from a [BlockPos].
 */
public operator fun BlockPos.minus(other: BlockPos): BlockPos {
    return BlockPos(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [BlockPos] and a [BlockPos].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun BlockPos.times(other: BlockPos): BlockPos {
    return BlockPos(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Multiplies a [BlockPos] and an Int.
 */
public operator fun BlockPos.times(other: Int): BlockPos {
    return BlockPos(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies an Int and a [BlockPos].
 */
public operator fun Int.times(other: BlockPos): BlockPos {
    return BlockPos(
        this * other.x,
        this * other.y,
        this * other.z
    )
}

/**
 * Divides a [BlockPos] and an Int.
 */
public operator fun BlockPos.div(other: Int): BlockPos {
    return BlockPos(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Negates a [BlockPos].
 */
public operator fun BlockPos.unaryMinus(): BlockPos {
    return this.times(-1)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3i] to a [BlockPos].
 */
public operator fun BlockPos.plus(other: Vec3i): BlockPos {
    return BlockPos(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3i] from a [BlockPos].
 */
public operator fun BlockPos.minus(other: Vec3i): BlockPos {
    return BlockPos(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [BlockPos] and a [Vec3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun BlockPos.times(other: Vec3i): BlockPos {
    return BlockPos(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [BlockPos] and a [Vec3i].
 */
public infix fun BlockPos.dot(other: Vec3i): Int {
    return x * other.x + y * other.y + z * other.z
}

/**
 * The cross product of a [BlockPos] and a [Vec3i].
 */
public infix fun BlockPos.cross(other: Vec3i): BlockPos {
    return this.crossProduct(other)
}

/**
 * The dot product of a [BlockPos] and a [BlockPos].
 */
public infix fun BlockPos.dot(other: BlockPos): Int {
    return x * other.x + y * other.y + z * other.z
}

/**
 * The cross product of a [BlockPos] and a [BlockPos].
 */
public infix fun BlockPos.cross(other: BlockPos): BlockPos {
    return this.crossProduct(other)
}

/**
 * The cross product of a [BlockPos] and a [Vector3d].
 */
public infix fun BlockPos.cross(other: Vector3d): BlockPos {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
}

/**
 * The [`x`][BlockPos.x] component of a [BlockPos].
 */
public operator fun BlockPos.component1(): Int {
    return this.x
}

/**
 * The [`y`][BlockPos.y] component of a [BlockPos].
 */
public operator fun BlockPos.component2(): Int {
    return this.y
}

/**
 * The [`z`][BlockPos.z] component of a [BlockPos].
 */
public operator fun BlockPos.component3(): Int {
    return this.z
}
//endregion

//region Conversion methods
/**
 * Converts a [BlockPos] to a [Vec3d].
 */
public fun BlockPos.toVec3d(): Vec3d {
    return Vec3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}

/**
 * Converts a [BlockPos] to a [Vec3f].
 */
public fun BlockPos.toVec3f(): Vec3f {
    return Vec3f(
        this.x.toFloat(),
        this.y.toFloat(),
        this.z.toFloat()
    )
}

/**
 * Converts a [BlockPos] to a [Vec3i].
 */
public fun BlockPos.toVec3i(): Vec3i {
    return Vec3i(
        this.x,
        this.y,
        this.z
    )
}

/**
 * Converts a [BlockPos] to a [Vector3d].
 */
public fun BlockPos.toVector3d(): Vector3d {
    return Vector3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}
//endregion
