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

import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i
import net.minecraft.util.math.BlockPos
import net.minecraft.client.util.math.Vector3d

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
 * Adds a [Vec3d] to a [BlockPos].
 */
public operator fun BlockPos.plus(other: Vec3d): BlockPos {
    return BlockPos(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

/**
 * Adds a [Vec3f] to a [BlockPos].
 */
public operator fun BlockPos.plus(other: Vec3f): BlockPos {
    return BlockPos(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

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
 * Adds a [Vector3d] to a [BlockPos].
 */
public operator fun BlockPos.plus(other: Vector3d): BlockPos {
    return BlockPos(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

/**
 * Subtracts a [Vec3d] from a [BlockPos].
 */
public operator fun BlockPos.minus(other: Vec3d): BlockPos {
    return BlockPos(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
    )
}

/**
 * Subtracts a [Vec3f] from a [BlockPos].
 */
public operator fun BlockPos.minus(other: Vec3f): BlockPos {
    return BlockPos(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
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
 * Subtracts a [Vector3d] from a [BlockPos].
 */
public operator fun BlockPos.minus(other: Vector3d): BlockPos {
    return BlockPos(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
    )
}

/**
 * Multiplies a [BlockPos] and a [Vec3d].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun BlockPos.times(other: Vec3d): BlockPos {
    return BlockPos(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
    )
}

/**
 * Multiplies a [BlockPos] and a [Vec3f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun BlockPos.times(other: Vec3f): BlockPos {
    return BlockPos(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
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

/**
 * Multiplies a [BlockPos] and a [Vector3d].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun BlockPos.times(other: Vector3d): BlockPos {
    return BlockPos(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
    )
}
//endregion

//region Vector specific operators
/**
 * The cross product of a [BlockPos] and a [Vec3d].
 */
public infix fun BlockPos.cross(other: Vec3d): BlockPos {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
}

/**
 * The cross product of a [BlockPos] and a [Vec3f].
 */
public infix fun BlockPos.cross(other: Vec3f): BlockPos {
    return this.crossProduct(Vec3i(other.x.toDouble(), other.y.toDouble(), other.z.toDouble()))
}

/**
 * The cross product of a [BlockPos] and a [Vec3i].
 */
public infix fun BlockPos.cross(other: Vec3i): BlockPos {
    return this.crossProduct(other)
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
