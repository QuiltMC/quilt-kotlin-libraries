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

import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vector4f

//region Standard math operators
/**
 * Adds a [Vector4f] to a [Vector4f].
 */
public operator fun Vector4f.plus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z,
        this.w + other.w
    )
}

/**
 * Subtracts a [Vector4f] from a [Vector4f].
 */
public operator fun Vector4f.minus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z,
        this.w - other.w
    )
}

/**
 * Multiplies a [Vector4f] and a [Vector4f].
 */
public operator fun Vector4f.times(other: Vector4f): Vector4f {
    return Vector4f(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z,
        this.w * other.w
    )
}

/**
 * Negates a [Vector4f].
 */
public operator fun Vector4f.unaryMinus(): Vector4f {
    return Vector4f(
        this.x * -1f,
        this.y * -1f,
        this.z * -1f,
        this.w * -1f
    )
}
//endregion

//region Type compatibility operator variations//endregion

//region xAssign math operators
/**
 * Adds a [Vector4f] to a [Vector4f].
 */
public operator fun Vector4f.plusAssign(other: Vector4f) {
    this.set(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z,
        this.w + other.w
    )
}

/**
 * Subtracts a [Vector4f] from a [Vector4f].
 */
public operator fun Vector4f.minusAssign(other: Vector4f) {
    this.set(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z,
        this.w - other.w
    )
}

/**
 * Multiplies a [Vector4f] and a [Vector4f].
 */
public operator fun Vector4f.timesAssign(other: Vector4f) {
    this.set(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z,
        this.w * other.w
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vector4f] and a [Vector4f].
 */
public infix fun Vector4f.dot(other: Vector4f): Float {
    return this.dotProduct(other)
}

/**
 * Returns the normalized form of a [Vector4f].
 *
 * Must break convention here and name this `normalized` instead of `normalize`
 * because Vector4f.normalize() is an existing, mutating function.
 *
 * @author SilverAndro
 */
public fun Vector4f.normalized(): Vector4f {
    val len = length()
    return if (len < EPSILON) {
        Vector4f(0f, 0f, 0f, 0f)
    } else {
        Vector4f(this.x / len, this.y / len, this.z / len, this.w / len)
    }
}

/**
 * The length of a [Vector4f].
 */
public fun Vector4f.length(): Float {
    return MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)
}

/**
 * The length squared  of a [Vector4f].
 */
public fun Vector4f.lengthSquared(): Float {
    return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w
}
//endregion
