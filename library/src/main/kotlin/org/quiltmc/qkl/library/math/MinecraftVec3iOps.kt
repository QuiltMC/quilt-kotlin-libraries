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

// DO NOT EDIT MANUALLY | THIS FILE WAS GENERATED BY https://github.com/SilverAndro/qkl_math_codegen

package org.quiltmc.qkl.library.math

import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3i
import org.joml.Vector3i
import kotlin.math.sqrt

//region Standard math operators
/**
 * Adds a [Vec3i] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vec3i] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vec3i] and a [Vec3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3i.times(other: Vec3i): Vec3i {
    return Vec3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Negates a [Vec3i].
 */
public operator fun Vec3i.unaryMinus(): Vec3i {
    return Vec3i(
        -this.x,
        -this.y,
        -this.z
    )
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vector3i] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Vector3i): Vec3i {
    return Vec3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [Vector3i] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Vector3i): Vec3i {
    return Vec3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [Vector3i] and a [Vec3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3i.times(other: Vector3i): Vec3i {
    return Vec3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

/**
 * Adds a [BlockPos] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: BlockPos): Vec3i {
    return Vec3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

/**
 * Subtracts a [BlockPos] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: BlockPos): Vec3i {
    return Vec3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

/**
 * Multiplies a [BlockPos] and a [Vec3i].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vec3i.times(other: BlockPos): Vec3i {
    return Vec3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}
//endregion

//region Vector specific operators
/**
 * Divides a [Vec3i] and an Int.
 */
public operator fun Vec3i.div(other: Int): Vec3i {
    return Vec3i(
        this.x / other,
        this.y / other,
        this.z / other
    )
}

/**
 * Returns the normalized version of this vector.
 */
public fun Vec3i.normalized(): Vec3i {
    val length = sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z).toDouble())
    return Vec3i(
        (this.x / length).toInt(),
        (this.y / length).toInt(),
        (this.z / length).toInt()
    )
}

/**
 * The [`x`][Vec3i.x] of a [Vec3i].
 */
public operator fun Vec3i.component1(): Int {
    return this.x
}

/**
 * The [`y`][Vec3i.y] of a [Vec3i].
 */
public operator fun Vec3i.component2(): Int {
    return this.y
}

/**
 * The [`z`][Vec3i.z] of a [Vec3i].
 */
public operator fun Vec3i.component3(): Int {
    return this.z
}

/**
 * Returns the dot product of a [Vec3i] and a [Vector3i].
 */
public infix fun Vec3i.dot(other: Vector3i): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the dot product of a [Vec3i] and a [BlockPos].
 */
public infix fun Vec3i.dot(other: BlockPos): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the dot product of a [Vec3i] and a [Vec3i].
 */
public infix fun Vec3i.dot(other: Vec3i): Int {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z)
}

/**
 * Returns the cross product of a [Vec3i] and a [Vector3i].
 */
public infix fun Vec3i.cross(other: Vector3i): Vec3i {
    return Vec3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}

/**
 * Returns the cross product of a [Vec3i] and a [BlockPos].
 */
public infix fun Vec3i.cross(other: BlockPos): Vec3i {
    return Vec3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}

/**
 * Returns the cross product of a [Vec3i] and a [Vec3i].
 */
public infix fun Vec3i.cross(other: Vec3i): Vec3i {
    return Vec3i(
        (this.y * other.z) - (this.z * other.y),
        (this.z * other.x) - (this.x * other.z),
        (this.x * other.y) - (this.y * other.x),
    )
}
//endregion

//region Conversion methods
/**
 * Converts a [Vec3i] to a [Vector3i].
 */
public fun Vec3i.toVector3i(): Vector3i {
    return Vector3i(
        this.x,
        this.y,
        this.z
    )
}

/**
 * Converts a [Vec3i] to a [BlockPos].
 */
public fun Vec3i.toBlockPos(): BlockPos {
    return BlockPos(
        this.x,
        this.y,
        this.z
    )
}
//endregion
