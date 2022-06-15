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
import net.minecraft.client.util.math.Vector3d
import kotlin.math.sqrt

//region Standard math operators
/**
 * Adds a [Vector3d] to a [Vector3d].
 */
public operator fun Vector3d.plus(other: Vector3d): Vector3d {
    return Vector3d(
        this.x + other.x, 
        this.y + other.y, 
        this.z + other.z
    )
}

/**
 * Adds a Double to a [Vector3d].
 */
public operator fun Vector3d.plus(other: Double): Vector3d {
    return Vector3d(
        this.x + other, 
        this.y + other, 
        this.z + other
    )
}

/**
 * Subtracts a [Vector3d] from a [Vector3d].
 */
public operator fun Vector3d.minus(other: Vector3d): Vector3d {
    return Vector3d(
        this.x - other.x, 
        this.y - other.y, 
        this.z - other.z
    )
}

/**
 * Subtracts a Double from a [Vector3d].
 */
public operator fun Vector3d.minus(other: Double): Vector3d {
    return Vector3d(
        this.x - other, 
        this.y - other, 
        this.z - other
    )
}

/**
 * Multiplies a [Vector3d] and a [Vector3d].
 */
public operator fun Vector3d.times(other: Vector3d): Vector3d {
    return Vector3d(
        this.x * other.x, 
        this.y * other.y, 
        this.z * other.z
    )
}

/**
 * Multiplies a [Vector3d] and a Double.
 */
public operator fun Vector3d.times(other: Double): Vector3d {
    return Vector3d(
        this.x * other, 
        this.y * other, 
        this.z * other
    )
}

/**
 * Negates a [Vector3d].
 */
public operator fun Vector3d.unaryMinus(): Vector3d {
    return this.times(-1.0)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3d] to a [Vector3d].
 */
public operator fun Vector3d.plus(other: Vec3d): Vector3d {
    return Vector3d(
        this.x + other.x, 
        this.y + other.y, 
        this.z + other.z
    )
}

/**
 * Adds a [Vec3f] to a [Vector3d].
 */
public operator fun Vector3d.plus(other: Vec3f): Vector3d {
    return Vector3d(
        this.x + other.x.toDouble(), 
        this.y + other.y.toDouble(), 
        this.z + other.z.toDouble()
    )
}

/**
 * Adds a [Vec3i] to a [Vector3d].
 */
public operator fun Vector3d.plus(other: Vec3i): Vector3d {
    return Vector3d(
        this.x + other.x.toDouble(), 
        this.y + other.y.toDouble(), 
        this.z + other.z.toDouble()
    )
}

/**
 * Subtracts a [Vec3d] from a [Vector3d].
 */
public operator fun Vector3d.minus(other: Vec3d): Vector3d {
    return Vector3d(
        this.x - other.x, 
        this.y - other.y, 
        this.z - other.z
    )
}

/**
 * Subtracts a [Vec3f] from a [Vector3d].
 */
public operator fun Vector3d.minus(other: Vec3f): Vector3d {
    return Vector3d(
        this.x - other.x.toDouble(), 
        this.y - other.y.toDouble(), 
        this.z - other.z.toDouble()
    )
}

/**
 * Subtracts a [Vec3i] from a [Vector3d].
 */
public operator fun Vector3d.minus(other: Vec3i): Vector3d {
    return Vector3d(
        this.x - other.x.toDouble(), 
        this.y - other.y.toDouble(), 
        this.z - other.z.toDouble()
    )
}

/**
 * Multiplies a [Vector3d] and a [Vec3d].
 */
public operator fun Vector3d.times(other: Vec3d): Vector3d {
    return Vector3d(
        this.x * other.x, 
        this.y * other.y, 
        this.z * other.z
    )
}

/**
 * Multiplies a [Vector3d] and a [Vec3f].
 */
public operator fun Vector3d.times(other: Vec3f): Vector3d {
    return Vector3d(
        this.x * other.x.toDouble(), 
        this.y * other.y.toDouble(), 
        this.z * other.z.toDouble()
    )
}

/**
 * Multiplies a [Vector3d] and a [Vec3i].
 */
public operator fun Vector3d.times(other: Vec3i): Vector3d {
    return Vector3d(
        this.x * other.x.toDouble(), 
        this.y * other.y.toDouble(), 
        this.z * other.z.toDouble()
    )
}
//endregion

//region xAssign math operators
/**
 * Adds a [Vector3d] to a [Vector3d].
 */
public operator fun Vector3d.plusAssign(other: Double) {
    this.x = this.x + other
    this.y = this.y + other
    this.z = this.z + other
}

/**
 * Adds a [Vec3d] to a [Vector3d].
 */
public operator fun Vector3d.plusAssign(other: Vec3d) {
    this.x = this.x + other.x
    this.y = this.y + other.y
    this.z = this.z + other.z
}

/**
 * Adds a [Vec3f] to a [Vector3d].
 */
public operator fun Vector3d.plusAssign(other: Vec3f) {
    this.x = this.x + other.x.toDouble()
    this.y = this.y + other.y.toDouble()
    this.z = this.z + other.z.toDouble()
}

/**
 * Adds a [Vec3i] to a [Vector3d].
 */
public operator fun Vector3d.plusAssign(other: Vec3i) {
    this.x = this.x + other.x.toDouble()
    this.y = this.y + other.y.toDouble()
    this.z = this.z + other.z.toDouble()
}

/**
 * Adds a [Vector3d] to a [Vector3d].
 */
public operator fun Vector3d.plusAssign(other: Vector3d) {
    this.x = this.x + other.x
    this.y = this.y + other.y
    this.z = this.z + other.z
}

/**
 * Subtracts a [Vector3d] from a [Vector3d].
 */
public operator fun Vector3d.minusAssign(other: Double) {
    this.x = this.x - other
    this.y = this.y - other
    this.z = this.z - other
}

/**
 * Subtracts a [Vec3d] from a [Vector3d].
 */
public operator fun Vector3d.minusAssign(other: Vec3d) {
    this.x = this.x - other.x
    this.y = this.y - other.y
    this.z = this.z - other.z
}

/**
 * Subtracts a [Vec3f] from a [Vector3d].
 */
public operator fun Vector3d.minusAssign(other: Vec3f) {
    this.x = this.x - other.x.toDouble()
    this.y = this.y - other.y.toDouble()
    this.z = this.z - other.z.toDouble()
}

/**
 * Subtracts a [Vec3i] from a [Vector3d].
 */
public operator fun Vector3d.minusAssign(other: Vec3i) {
    this.x = this.x - other.x.toDouble()
    this.y = this.y - other.y.toDouble()
    this.z = this.z - other.z.toDouble()
}

/**
 * Subtracts a [Vector3d] from a [Vector3d].
 */
public operator fun Vector3d.minusAssign(other: Vector3d) {
    this.x = this.x - other.x
    this.y = this.y - other.y
    this.z = this.z - other.z
}

/**
 * Multiplies a [Vector3d] and a [Vector3d].
 */
public operator fun Vector3d.timesAssign(other: Double) {
    this.x = this.x * other
    this.y = this.y * other
    this.z = this.z * other
}

/**
 * Multiplies a [Vector3d] and a [Vec3d].
 */
public operator fun Vector3d.timesAssign(other: Vec3d) {
    this.x = this.x * other.x
    this.y = this.y * other.y
    this.z = this.z * other.z
}

/**
 * Multiplies a [Vector3d] and a [Vec3f].
 */
public operator fun Vector3d.timesAssign(other: Vec3f) {
    this.x = this.x * other.x.toDouble()
    this.y = this.y * other.y.toDouble()
    this.z = this.z * other.z.toDouble()
}

/**
 * Multiplies a [Vector3d] and a [Vec3i].
 */
public operator fun Vector3d.timesAssign(other: Vec3i) {
    this.x = this.x * other.x.toDouble()
    this.y = this.y * other.y.toDouble()
    this.z = this.z * other.z.toDouble()
}

/**
 * Multiplies a [Vector3d] and a [Vector3d].
 */
public operator fun Vector3d.timesAssign(other: Vector3d) {
    this.x = this.x * other.x
    this.y = this.y * other.y
    this.z = this.z * other.z
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vector3d] and a [Vec3d].
 */
public infix fun Vector3d.dot(other: Vec3d): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vector3d] and a [Vec3d].
 */
public infix fun Vector3d.cross(other: Vec3d): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * The dot product of a [Vector3d] and a [Vec3f].
 */
public infix fun Vector3d.dot(other: Vec3f): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vector3d] and a [Vec3f].
 */
public infix fun Vector3d.cross(other: Vec3f): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * The dot product of a [Vector3d] and a [Vec3i].
 */
public infix fun Vector3d.dot(other: Vec3i): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vector3d] and a [Vec3i].
 */
public infix fun Vector3d.cross(other: Vec3i): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * The dot product of a [Vector3d] and a [Vector3d].
 */
public infix fun Vector3d.dot(other: Vector3d): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

/**
 * The cross product of a [Vector3d] and a [Vector3d].
 */
public infix fun Vector3d.cross(other: Vector3d): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * Returns the normalized form of a [Vector3d].
 */
public fun Vector3d.normalize(): Vector3d {
    val len = length()
    return if (len < EPSILON) {
        Vector3d(0.0, 0.0, 0.0)
    } else {
        Vector3d(this.x / len, this.y / len, this.z / len)
    }
}

/**
 * The length of a [Vector3d].
 */
public fun Vector3d.length(): Double {
    return sqrt(this.x * this.x + this.y * this.y + this.z * this.z)
}

/**
 * The length squared of a [Vector3d].
 */
public fun Vector3d.lengthSquared(): Double {
    return this.x * this.x + this.y * this.y + this.z * this.z
}
//endregion
