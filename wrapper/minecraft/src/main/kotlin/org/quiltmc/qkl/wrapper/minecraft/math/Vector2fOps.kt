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

import net.minecraft.util.math.Vec2f
import net.minecraft.client.util.math.Vector2f
import net.minecraft.util.math.MathHelper.sqrt

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
 * Adds a Float to a [Vector2f].
 */
public operator fun Vector2f.plus(other: Float): Vector2f {
    return Vector2f(
        this.x + other, 
        this.y + other
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
 * Subtracts a Float from a [Vector2f].
 */
public operator fun Vector2f.minus(other: Float): Vector2f {
    return Vector2f(
        this.x - other, 
        this.y - other
    )
}

/**
 * Multiplies a [Vector2f] and a [Vector2f].
 */
public operator fun Vector2f.times(other: Vector2f): Vector2f {
    return Vector2f(
        this.x * other.x, 
        this.y * other.y
    )
}

/**
 * Multiplies a [Vector2f] and a Float.
 */
public operator fun Vector2f.times(other: Float): Vector2f {
    return Vector2f(
        this.x * other, 
        this.y * other
    )
}

/**
 * Negates a [Vector2f].
 */
public operator fun Vector2f.unaryMinus(): Vector2f {
    return this.times(-1f)
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
 * Multiplies a [Vector2f] and a [Vec2f].
 */
public operator fun Vector2f.times(other: Vec2f): Vector2f {
    return Vector2f(
        this.x * other.x, 
        this.y * other.y
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vector2f] and a [Vec2f].
 */
public infix fun Vector2f.dot(other: Vec2f): Float {
    return this.x * other.x + this.y * other.y
}

/**
 * The dot product of a [Vector2f] and a [Vector2f].
 */
public infix fun Vector2f.dot(other: Vector2f): Float {
    return this.x * other.x + this.y * other.y
}

/**
 * Returns the normalized form of a [Vector2f].
 */
public fun Vector2f.normalize(): Vector2f {
    val len = length()
    return if (len < EPSILON){
        Vector2f(0f, 0f)
    } else {
        Vector2f(this.x / len, this.y / len)
    }
}

/**
 * The length of a [Vector2f].
 */
public fun Vector2f.length(): Float {
    return sqrt(this.x * this.x + this.y * this.y)
}

/**
 * The length squared of a [Vector2f].
 */
public fun Vector2f.lengthSquared(): Float {
    return this.x * this.x + this.y * this.y
}
//endregion

