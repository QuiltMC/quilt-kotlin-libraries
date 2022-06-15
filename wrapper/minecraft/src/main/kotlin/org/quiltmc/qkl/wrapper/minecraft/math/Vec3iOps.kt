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
 * Adds an Int to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Int): Vec3i {
    return Vec3i(
        this.x + other, 
        this.y + other, 
        this.z + other
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
 * Subtracts an Int from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Int): Vec3i {
    return Vec3i(
        this.x - other, 
        this.y - other, 
        this.z - other
    )
}

/**
 * Multiplies a [Vec3i] and a [Vec3i].
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
 * Negates a [Vec3i].
 */
public operator fun Vec3i.unaryMinus(): Vec3i {
    return this.times(-1)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3d] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Vec3d): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(), 
        this.y + other.y.toInt(), 
        this.z + other.z.toInt()
    )
}

/**
 * Adds a [Vec3f] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Vec3f): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(), 
        this.y + other.y.toInt(), 
        this.z + other.z.toInt()
    )
}

/**
 * Adds a [Vector3d] to a [Vec3i].
 */
public operator fun Vec3i.plus(other: Vector3d): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(), 
        this.y + other.y.toInt(), 
        this.z + other.z.toInt()
    )
}

/**
 * Subtracts a [Vec3d] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Vec3d): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(), 
        this.y - other.y.toInt(), 
        this.z - other.z.toInt()
    )
}

/**
 * Subtracts a [Vec3f] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Vec3f): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(), 
        this.y - other.y.toInt(), 
        this.z - other.z.toInt()
    )
}

/**
 * Subtracts a [Vector3d] from a [Vec3i].
 */
public operator fun Vec3i.minus(other: Vector3d): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(), 
        this.y - other.y.toInt(), 
        this.z - other.z.toInt()
    )
}

/**
 * Multiplies a [Vec3i] and a [Vec3d].
 */
public operator fun Vec3i.times(other: Vec3d): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(), 
        this.y * other.y.toInt(), 
        this.z * other.z.toInt()
    )
}

/**
 * Multiplies a [Vec3i] and a [Vec3f].
 */
public operator fun Vec3i.times(other: Vec3f): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(), 
        this.y * other.y.toInt(), 
        this.z * other.z.toInt()
    )
}

/**
 * Multiplies a [Vec3i] and a [Vector3d].
 */
public operator fun Vec3i.times(other: Vector3d): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(), 
        this.y * other.y.toInt(), 
        this.z * other.z.toInt()
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vec3i] and a [Vec3d].
 */
public infix fun Vec3i.dot(other: Vec3d): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

/**
 * The cross product of a [Vec3i] and a [Vec3d].
 */
public infix fun Vec3i.cross(other: Vec3d): Vec3i {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
}

/**
 * The dot product of a [Vec3i] and a [Vec3f].
 */
public infix fun Vec3i.dot(other: Vec3f): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

/**
 * The cross product of a [Vec3i] and a [Vec3f].
 */
public infix fun Vec3i.cross(other: Vec3f): Vec3i {
    return this.crossProduct(Vec3i(other.x.toDouble(), other.y.toDouble(), other.z.toDouble()))
}

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
 * The dot product of a [Vec3i] and a [Vector3d].
 */
public infix fun Vec3i.dot(other: Vector3d): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

/**
 * The cross product of a [Vec3i] and a [Vector3d].
 */
public infix fun Vec3i.cross(other: Vector3d): Vec3i {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
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
//endregion

