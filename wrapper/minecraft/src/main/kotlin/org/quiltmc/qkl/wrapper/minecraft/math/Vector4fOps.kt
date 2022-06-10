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

import net.minecraft.util.math.MathHelper.sqrt
import net.minecraft.util.math.Vector4f

//region Standard math operators
public operator fun Vector4f.plus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z,
        this.w + other.w
    )
}

public operator fun Vector4f.plus(other: Float): Vector4f {
    return Vector4f(
        this.x + other,
        this.y + other,
        this.z + other,
        this.w + other
    )
}

public operator fun Vector4f.minus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z,
        this.w - other.w
    )
}

public operator fun Vector4f.minus(other: Float): Vector4f {
    return Vector4f(
        this.x - other,
        this.y - other,
        this.z - other,
        this.w - other
    )
}

public operator fun Vector4f.times(other: Vector4f): Vector4f {
    return Vector4f(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z,
        this.w * other.w
    )
}

public operator fun Vector4f.times(other: Float): Vector4f {
    return Vector4f(
        this.x * other,
        this.y * other,
        this.z * other,
        this.w * other
    )
}

public operator fun Vector4f.unaryMinus(): Vector4f {
    return this.times(-1f)
}
//endregion

//region xAssign math operators
public operator fun Vector4f.plusAssign(other: Float) {
    this.plus(other)
}

public operator fun Vector4f.plusAssign(other: Vector4f) {
    this.plus(other)
}

public operator fun Vector4f.minusAssign(other: Float) {
    this.minus(other)
}

public operator fun Vector4f.minusAssign(other: Vector4f) {
    this.minus(other)
}

public operator fun Vector4f.timesAssign(other: Float) {
    this.times(other)
}

public operator fun Vector4f.timesAssign(other: Vector4f) {
    this.times(other)
}
//endregion

//region Vector specific operators
public infix fun Vector4f.dot(other: Vector4f): Float {
    return this.dotProduct(other)
}

/**
 * Must break convention here and name this `normalized` instead of `normalize`
 * Because Vector4f.normalize() is an existing, mutating function
 *
 * @author SilverAndro
 */
public fun Vector4f.normalized(): Vector4f {
    val len = length()
    if (len < EPSILON) {
        return Vector4f(0f, 0f, 0f, 0f)
    } else {
        return Vector4f(this.x / len, this.y / len, this.z / len, this.w / len)
    }
}

public fun Vector4f.length(): Float {
    return sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w)
}

public fun Vector4f.lengthSquared(): Float {
    return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w
}
//endregion
