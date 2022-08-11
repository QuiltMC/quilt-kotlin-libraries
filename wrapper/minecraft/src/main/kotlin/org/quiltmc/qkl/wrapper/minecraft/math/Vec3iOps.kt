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
 * Multiplies a [Vec3i] and an Int.
 */
public operator fun Vec3i.times(other: Int): Vec3i {
    return Vec3i(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

/**
 * Multiplies an Int and a [Vec3i].
 */
public operator fun Int.times(other: Vec3i): Vec3i {
    return Vec3i(
        this * other.x,
        this * other.y,
        this * other.z
    )
}

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
 * Negates a [Vec3i].
 */
public operator fun Vec3i.unaryMinus(): Vec3i {
    return this.times(-1)
}
//endregion

//region Type compatibility operator variations
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
 * Multiplies a [Vec3i] and a [BlockPos].
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
 * The dot product of a [Vec3i] and a [Vec3i].
 */
public infix fun Vec3i.dot(other: Vec3i): Int {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vec3i] and a [Vec3i].
 */
public infix fun Vec3i.cross(other: Vec3i): Vec3i {
    return this.crossProduct(other)
}

/**
 * The dot product of a [Vec3i] and a [BlockPos].
 */
public infix fun Vec3i.dot(other: BlockPos): Int {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vec3i] and a [BlockPos].
 */
public infix fun Vec3i.cross(other: BlockPos): Vec3i {
    return this.crossProduct(other)
}

/**
 * The length of a [Vec3i].
 */
public fun Vec3i.length(): Double {
    return sqrt((x * x + y * y + z * z).toDouble())
}

/**
 * The length squared of a [Vec3i].
 */
public fun Vec3i.lengthSquared(): Int {
    return x * x + y * y + z * z
}

/**
 * The [`x`][Vec3i.x] component of a [Vec3i].
 */
public operator fun Vec3i.component1(): Int {
    return this.x
}

/**
 * The [`y`][Vec3i.y] component of a [Vec3i].
 */
public operator fun Vec3i.component2(): Int {
    return this.y
}

/**
 * The [`z`][Vec3i.z] component of a [Vec3i].
 */
public operator fun Vec3i.component3(): Int {
    return this.z
}
//endregion

//region Conversion methods
/**
 * Converts a [Vec3i] to a [Vec3d].
 */
public fun Vec3i.toVec3d(): Vec3d {
    return Vec3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}

/**
 * Converts a [Vec3i] to a [Vec3f].
 */
public fun Vec3i.toVec3f(): Vec3f {
    return Vec3f(
        this.x.toFloat(),
        this.y.toFloat(),
        this.z.toFloat()
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

/**
 * Converts a [Vec3i] to a [Vector3d].
 */
public fun Vec3i.toVector3d(): Vector3d {
    return Vector3d(
        this.x.toDouble(),
        this.y.toDouble(),
        this.z.toDouble()
    )
}
//endregion
