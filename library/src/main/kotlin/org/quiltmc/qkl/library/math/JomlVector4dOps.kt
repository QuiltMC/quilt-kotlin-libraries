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

// DO NOT EDIT MANUALLY | THIS FILE WAS GENERATED BY https://github.com/SilverAndro/qkl_math_codegen

package org.quiltmc.qkl.library.math

import org.joml.Vector4d
import kotlin.math.sqrt

//region Standard math operators
/**
 * Adds a [Vector4d] to a [Vector4d].
 */
public operator fun Vector4d.plus(other: Vector4d): Vector4d {
    return Vector4d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z,
        this.w + other.w
    )
}

/**
 * Subtracts a [Vector4d] from a [Vector4d].
 */
public operator fun Vector4d.minus(other: Vector4d): Vector4d {
    return Vector4d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z,
        this.w - other.w
    )
}

/**
 * Multiplies a [Vector4d] and a [Vector4d].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector4d.times(other: Vector4d): Vector4d {
    return Vector4d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z,
        this.w * other.w
    )
}

/**
 * Negates a [Vector4d].
 */
public operator fun Vector4d.unaryMinus(): Vector4d {
    return Vector4d(
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
public fun Vector4d.normalized(): Vector4d {
    val length = sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z) + (this.w * this.w))
    return Vector4d(
        this.x / length,
        this.y / length,
        this.z / length,
        this.w / length
    )
}

/**
 * The [`x`][Vector4d.x] of a [Vector4d].
 */
public operator fun Vector4d.component1(): Double {
    return this.x
}

/**
 * The [`y`][Vector4d.y] of a [Vector4d].
 */
public operator fun Vector4d.component2(): Double {
    return this.y
}

/**
 * The [`z`][Vector4d.z] of a [Vector4d].
 */
public operator fun Vector4d.component3(): Double {
    return this.z
}

/**
 * The [`w`][Vector4d.w] of a [Vector4d].
 */
public operator fun Vector4d.component4(): Double {
    return this.w
}

/**
 * Returns the dot product of a [Vector4d] and a [Vector4d].
 */
public infix fun Vector4d.dot(other: Vector4d): Double {
    return (this.x * other.x) + (this.y * other.y) + (this.z * other.z) + (this.w * other.w)
}
//endregion