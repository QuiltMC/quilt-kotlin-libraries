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
 * Negates a [Vec3d].
 */
public operator fun Vec3d.unaryMinus(): Vec3d {
    return this.times(-1.0)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec3f] to a [Vec3d].
 */
public operator fun Vec3d.plus(other: Vec3f): Vec3d {
    return Vec3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

/**
 * Adds a [Vec3i] to a [Vec3d].
 */
public operator fun Vec3d.plus(other: Vec3i): Vec3d {
    return Vec3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

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
 * Subtracts a [Vec3f] from a [Vec3d].
 */
public operator fun Vec3d.minus(other: Vec3f): Vec3d {
    return Vec3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
    )
}

/**
 * Subtracts a [Vec3i] from a [Vec3d].
 */
public operator fun Vec3d.minus(other: Vec3i): Vec3d {
    return Vec3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
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
 * Multiplies a [Vec3d] and a [Vec3f].
 */
public operator fun Vec3d.times(other: Vec3f): Vec3d {
    return Vec3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

/**
 * Multiplies a [Vec3d] and a [Vec3i].
 */
public operator fun Vec3d.times(other: Vec3i): Vec3d {
    return Vec3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

/**
 * Multiplies a [Vec3d] and a [Vector3d].
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
 * The dot product of a [Vec3d] and a [Vec3f].
 */
public infix fun Vec3d.dot(other: Vec3f): Double {
    return this.dotProduct(Vec3d(other))
}

/**
 * The cross product of a [Vec3d] and a [Vec3f].
 */
public infix fun Vec3d.cross(other: Vec3f): Vec3d {
    return this.crossProduct(Vec3d(other))
}

/**
 * The dot product of a [Vec3d] and a [Vec3i].
 */
public infix fun Vec3d.dot(other: Vec3i): Double {
    return this.dotProduct(Vec3d.of(other))
}

/**
 * The cross product of a [Vec3d] and a [Vec3i].
 */
public infix fun Vec3d.cross(other: Vec3i): Vec3d {
    return this.crossProduct(Vec3d.of(other))
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
 * The first component of a [Vec3d].
 */
public operator fun Vec3d.component1(): Double {
    return this.x
}

/**
 * The second component of a [Vec3d].
 */
public operator fun Vec3d.component2(): Double {
    return this.y
}

/**
 * The third component of a [Vec3d].
 */
public operator fun Vec3d.component3(): Double {
    return this.z
}
//endregion
