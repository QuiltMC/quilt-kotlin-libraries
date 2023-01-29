/*
 * Copyright 2023 QuiltMC
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
import org.joml.Vector3i

//region Standard math operators
/**
 * Adds a [Vector3i] to a [Vector3i].
 */
public operator fun Vector3i.plus(other: Vector3i): Vector3i {
    return Vector3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vector3i] from a [Vector3i].
 */
public operator fun Vector3i.minus(other: Vector3i): Vector3i {
    return Vector3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vector3i] and a [Vector3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector3i.times(other: Vector3i): Vector3i {
    return Vector3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Negates a [Vector3i].
 */
public operator fun Vector3i.unaryMinus(): Vector3i {
    return Vector3i(
        -this.x,
        -this.y,
        -this.z
    )
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3i] to a [Vector3i].
 */
public operator fun Vector3i.plus(other: Vec3i): Vector3i {
    return Vector3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3i] from a [Vector3i].
 */
public operator fun Vector3i.minus(other: Vec3i): Vector3i {
    return Vector3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3i] and a [Vector3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector3i.times(other: Vec3i): Vector3i {
    return Vector3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Adds a [BlockPos] to a [Vector3i].
 */
public operator fun Vector3i.plus(other: BlockPos): Vector3i {
    return Vector3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [BlockPos] from a [Vector3i].
 */
public operator fun Vector3i.minus(other: BlockPos): Vector3i {
    return Vector3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [BlockPos] and a [Vector3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector3i.times(other: BlockPos): Vector3i {
    return Vector3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}
//endregion

//region Vector specific operators
/**
 * Divides a [Vector3i] and an Int.
 */
public operator fun Vector3i.div(other: Int): Vector3i {
    return Vector3i(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * The [`x`][Vector3i.x] of a [Vector3i].
 */
public operator fun Vector3i.component1(): Int {
    return this.x
}

/**
 * The [`y`][Vector3i.y] of a [Vector3i].
 */
public operator fun Vector3i.component2(): Int {
    return this.y
}

/**
 * The [`z`][Vector3i.z] of a [Vector3i].
 */
public operator fun Vector3i.component3(): Int {
    return this.z
}

/**
 * Returns the dot product of a [Vector3i] and a [Vec3i].
 */
public infix fun Vector3i.dot(other: Vec3i): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the dot product of a [Vector3i] and a [BlockPos].
 */
public infix fun Vector3i.dot(other: BlockPos): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the dot product of a [Vector3i] and a [Vector3i].
 */
public infix fun Vector3i.dot(other: Vector3i): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the cross product of a [Vector3i] and a [Vec3i].
 */
public infix fun Vector3i.cross(other: Vec3i): Vector3i {
    return Vector3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}

/**
 * Returns the cross product of a [Vector3i] and a [BlockPos].
 */
public infix fun Vector3i.cross(other: BlockPos): Vector3i {
    return Vector3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}

/**
 * Returns the cross product of a [Vector3i] and a [Vector3i].
 */
public infix fun Vector3i.cross(other: Vector3i): Vector3i {
    return Vector3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}
//endregion

//region Conversion methods
/**
 * Converts a [Vector3i] to a [Vec3i].
 */
public fun Vector3i.toVec3i(): Vec3i {
    return Vec3i(
        this.x,
        this.y,
        this.z
    )
}

/**
 * Converts a [Vector3i] to a [BlockPos].
 */
public fun Vector3i.toBlockPos(): BlockPos {
    return BlockPos(
        this.x,
        this.y,
        this.z
    )
}
//endregion
