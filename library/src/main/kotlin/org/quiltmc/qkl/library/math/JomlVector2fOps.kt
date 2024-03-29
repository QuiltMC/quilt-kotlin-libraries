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

import net.minecraft.util.math.Vec2f
import org.joml.Vector2f
import kotlin.math.sqrt

//region Standard math operators
/**
 * Adds a [Vector2f] to a [Vector2f].
 */
public operator fun Vector2f.plus(other: Vector2f): Vector2f {
    return Vector2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts a [Vector2f] from a [Vector2f].
 */
public operator fun Vector2f.minus(other: Vector2f): Vector2f {
    return Vector2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies a [Vector2f] and a [Vector2f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector2f.times(other: Vector2f): Vector2f {
    return Vector2f(
        this.x * other.x,
        this.y * other.y
    )
}

/**
 * Negates a [Vector2f].
 */
public operator fun Vector2f.unaryMinus(): Vector2f {
    return Vector2f(
        -this.x,
        -this.y
    )
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vec2f] to a [Vector2f].
 */
public operator fun Vector2f.plus(other: Vec2f): Vector2f {
    return Vector2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts a [Vec2f] from a [Vector2f].
 */
public operator fun Vector2f.minus(other: Vec2f): Vector2f {
    return Vector2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies a [Vec2f] and a [Vector2f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector2f.times(other: Vec2f): Vector2f {
    return Vector2f(
        this.x * other.x,
        this.y * other.y
    )
}

/**
 * Adds a [net.minecraft.client.util.math.Vector2f] to a [Vector2f].
 */
public operator fun Vector2f.plus(other: net.minecraft.client.util.math.Vector2f): Vector2f {
    return Vector2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts a [net.minecraft.client.util.math.Vector2f] from a [Vector2f].
 */
public operator fun Vector2f.minus(other: net.minecraft.client.util.math.Vector2f): Vector2f {
    return Vector2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies a [net.minecraft.client.util.math.Vector2f] and a [Vector2f].
 * This method is a shorthand for component wise multiplication.
 */
public operator fun Vector2f.times(other: net.minecraft.client.util.math.Vector2f): Vector2f {
    return Vector2f(
        this.x * other.x,
        this.y * other.y
    )
}
//endregion

//region Vector specific operators
/**
 * Returns the normalized version of this vector.
 */
public fun Vector2f.normalized(): Vector2f {
    val length = sqrt((this.x * this.x) + (this.y * this.y).toDouble())
    return Vector2f(
        (this.x / length).toFloat(),
        (this.y / length).toFloat()
    )
}

/**
 * The [`x`][Vector2f.x] of a [Vector2f].
 */
public operator fun Vector2f.component1(): Float {
    return this.x
}

/**
 * The [`y`][Vector2f.y] of a [Vector2f].
 */
public operator fun Vector2f.component2(): Float {
    return this.y
}

/**
 * Returns the dot product of a [Vector2f] and a [Vec2f].
 */
public infix fun Vector2f.dot(other: Vec2f): Float {
    return (this.x * other.x) + (this.y * other.y)
}

/**
 * Returns the dot product of a [Vector2f] and a [net.minecraft.client.util.math.Vector2f].
 */
public infix fun Vector2f.dot(other: net.minecraft.client.util.math.Vector2f): Float {
    return (this.x * other.x) + (this.y * other.y)
}

/**
 * Returns the dot product of a [Vector2f] and a [Vector2f].
 */
public infix fun Vector2f.dot(other: Vector2f): Float {
    return (this.x * other.x) + (this.y * other.y)
}
//endregion

//region Conversion methods
/**
 * Converts a [Vector2f] to a [Vec2f].
 */
public fun Vector2f.toVec2f(): Vec2f {
    return Vec2f(
        this.x,
        this.y
    )
}

/**
 * Converts a [Vector2f] to a [net.minecraft.client.util.math.Vector2f].
 */
public fun Vector2f.toMinecraftVector2f(): net.minecraft.client.util.math.Vector2f {
    return net.minecraft.client.util.math.Vector2f(
        this.x,
        this.y
    )
}
//endregion
