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
import net.minecraft.util.math.MathHelper.sqrt

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
 * Adds a Float to a [Vec3f].
 */
public operator fun Vec3f.plus(other: Float): Vec3f {
    return Vec3f(
        this.x + other, 
        this.y + other, 
        this.z + other
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
 * Subtracts a Float from a [Vec3f].
 */
public operator fun Vec3f.minus(other: Float): Vec3f {
    return Vec3f(
        this.x - other, 
        this.y - other, 
        this.z - other
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3f].
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
 * Negates a [Vec3f].
 */
public operator fun Vec3f.unaryMinus(): Vec3f {
    return this.times(-1f)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3d] to a [Vec3f].
 */
public operator fun Vec3f.plus(other: Vec3d): Vec3f {
    return Vec3f(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

/**
 * Adds a [Vec3i] to a [Vec3f].
 */
public operator fun Vec3f.plus(other: Vec3i): Vec3f {
    return Vec3f(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

/**
 * Adds a [Vector3d] to a [Vec3f].
 */
public operator fun Vec3f.plus(other: Vector3d): Vec3f {
    return Vec3f(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

/**
 * Subtracts a [Vec3d] from a [Vec3f].
 */
public operator fun Vec3f.minus(other: Vec3d): Vec3f {
    return Vec3f(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
    )
}

/**
 * Subtracts a [Vec3i] from a [Vec3f].
 */
public operator fun Vec3f.minus(other: Vec3i): Vec3f {
    return Vec3f(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
    )
}

/**
 * Subtracts a [Vector3d] from a [Vec3f].
 */
public operator fun Vec3f.minus(other: Vector3d): Vec3f {
    return Vec3f(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3d].
 */
public operator fun Vec3f.times(other: Vec3d): Vec3f {
    return Vec3f(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3i].
 */
public operator fun Vec3f.times(other: Vec3i): Vec3f {
    return Vec3f(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vector3d].
 */
public operator fun Vec3f.times(other: Vector3d): Vec3f {
    return Vec3f(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}
//endregion

//region xAssign math operators
/**
 * Adds a [Vec3f] to a [Vec3f].
 */
public operator fun Vec3f.plusAssign(other: Float) {
    this.plus(other)
}

/**
 * Adds a [Vec3d] to a [Vec3f].
 */
public operator fun Vec3f.plusAssign(other: Vec3d) {
    this.set(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

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
 * Adds a [Vec3i] to a [Vec3f].
 */
public operator fun Vec3f.plusAssign(other: Vec3i) {
    this.set(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

/**
 * Adds a [Vector3d] to a [Vec3f].
 */
public operator fun Vec3f.plusAssign(other: Vector3d) {
    this.set(
        this.x + other.x.toFloat(), 
        this.y + other.y.toFloat(), 
        this.z + other.z.toFloat()
    )
}

/**
 * Subtracts a [Vec3f] from a [Vec3f].
 */
public operator fun Vec3f.minusAssign(other: Float) {
    this.minus(other)
}

/**
 * Subtracts a [Vec3d] from a [Vec3f].
 */
public operator fun Vec3f.minusAssign(other: Vec3d) {
    this.set(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
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
 * Subtracts a [Vec3i] from a [Vec3f].
 */
public operator fun Vec3f.minusAssign(other: Vec3i) {
    this.set(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
    )
}

/**
 * Subtracts a [Vector3d] from a [Vec3f].
 */
public operator fun Vec3f.minusAssign(other: Vector3d) {
    this.set(
        this.x - other.x.toFloat(), 
        this.y - other.y.toFloat(), 
        this.z - other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3f].
 */
public operator fun Vec3f.timesAssign(other: Float) {
    this.times(other)
}

/**
 * Multiplies a [Vec3f] and a [Vec3d].
 */
public operator fun Vec3f.timesAssign(other: Vec3d) {
    this.set(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3f].
 */
public operator fun Vec3f.timesAssign(other: Vec3f) {
    this.set(
        this.x * other.x, 
        this.y * other.y, 
        this.z * other.z
    )
}

/**
 * Multiplies a [Vec3f] and a [Vec3i].
 */
public operator fun Vec3f.timesAssign(other: Vec3i) {
    this.set(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}

/**
 * Multiplies a [Vec3f] and a [Vector3d].
 */
public operator fun Vec3f.timesAssign(other: Vector3d) {
    this.set(
        this.x * other.x.toFloat(), 
        this.y * other.y.toFloat(), 
        this.z * other.z.toFloat()
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vec3f] and a [Vec3d].
 */
public infix fun Vec3f.dot(other: Vec3d): Float {
    return this.dot(Vec3f(other))
}

/**
 * The cross product of a [Vec3f] and a [Vec3d].
 */
public infix fun Vec3f.cross(other: Vec3d): Vec3f {
    return Vec3f(
        (this.y * other.z - this.z * other.y).toFloat(),
        (this.z * other.x - this.x * other.z).toFloat(),
        (this.x * other.y - this.y * other.x).toFloat()
    )
}

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
 * The dot product of a [Vec3f] and a [Vec3i].
 */
public infix fun Vec3f.dot(other: Vec3i): Float {
    return this.dot(Vec3f(other.x.toFloat(), other.y.toFloat(), other.z.toFloat()))
}

/**
 * The cross product of a [Vec3f] and a [Vec3i].
 */
public infix fun Vec3f.cross(other: Vec3i): Vec3f {
    return Vec3f(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

/**
 * The dot product of a [Vec3f] and a [Vector3d].
 */
public infix fun Vec3f.dot(other: Vector3d): Float {
    return this.dot(Vec3f(other.x.toFloat(), other.y.toFloat(), other.z.toFloat()))
}

/**
 * The cross product of a [Vec3f] and a [Vector3d].
 */
public infix fun Vec3f.cross(other: Vector3d): Vec3f {
    return Vec3f(
        (this.y * other.z - this.z * other.y).toFloat(),
        (this.z * other.x - this.x * other.z).toFloat(),
        (this.x * other.y - this.y * other.x).toFloat()
    )
}

/**
 * The length of a [Vec3f].
 */
public fun Vec3f.length(): Float {
    return sqrt(x * x + y * y + z * z)
}

/**
 * The length squared of a [Vec3f].
 */
public fun Vec3f.lengthSquared(): Float {
    return x * x + y * y + z * z
}
//endregion

