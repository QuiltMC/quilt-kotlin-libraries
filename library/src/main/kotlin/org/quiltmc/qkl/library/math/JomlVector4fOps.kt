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

import org.joml.Vector4f
import kotlin.math.sqrt

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
 * This method is a shorthand for component wise multiplication.
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
        -this.x,
        -this.y,
        -this.z,
        -this.w
    )
}
//endregion

//region Vector specific operators
/**
 * Returns the normalized version of this vector.
 */
public fun Vector4f.normalized(): Vector4f {
    val length = sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z) + (this.w * this.w).toDouble())
    return Vector4f(
        (this.x / length).toFloat(),
        (this.y / length).toFloat(),
        (this.z / length).toFloat(),
        (this.w / length).toFloat()
    )
}

/**
 * The [`x`][Vector4f.x] of a [Vector4f].
 */
public operator fun Vector4f.component1(): Float {
    return this.x
}

/**
 * The [`y`][Vector4f.y] of a [Vector4f].
 */
public operator fun Vector4f.component2(): Float {
    return this.y
}

/**
 * The [`z`][Vector4f.z] of a [Vector4f].
 */
public operator fun Vector4f.component3(): Float {
    return this.z
}

/**
 * The [`w`][Vector4f.w] of a [Vector4f].
 */
public operator fun Vector4f.component4(): Float {
    return this.w
}

/**
 * Returns the dot product of a [Vector4f] and a [Vector4f].
 */
public infix fun Vector4f.dot(other: Vector4f): Float {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z) + (this.w * other.w)
}
//endregion
