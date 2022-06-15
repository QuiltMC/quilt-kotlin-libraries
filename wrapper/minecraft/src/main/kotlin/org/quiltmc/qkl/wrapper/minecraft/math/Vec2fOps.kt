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

//region Standard math operators
/**
 * Adds a [Vec2f] to a [Vec2f].
 */
public operator fun Vec2f.plus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts a [Vec2f] from a [Vec2f].
 */
public operator fun Vec2f.minus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies a [Vec2f] and a [Vec2f].
 */
public operator fun Vec2f.times(other: Vec2f): Vec2f {
    return Vec2f(
        this.x * other.x,
        this.y * other.y
    )
}

/**
 * Multiplies a [Vec2f] and a Float.
 */
public operator fun Vec2f.times(other: Float): Vec2f {
    return Vec2f(
        this.x * other,
        this.y * other
    )
}

/**
 * Negates a [Vec2f].
 */
public operator fun Vec2f.unaryMinus(): Vec2f {
    return this.times(-1f)
}
//endregion

//region Type compatibility operator variations
/**
 * Adds a [Vector2f] to a [Vec2f].
 */
public operator fun Vec2f.plus(other: Vector2f): Vec2f {
    return Vec2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts a [Vector2f] from a [Vec2f].
 */
public operator fun Vec2f.minus(other: Vector2f): Vec2f {
    return Vec2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies a [Vec2f] and a [Vector2f].
 */
public operator fun Vec2f.times(other: Vector2f): Vec2f {
    return Vec2f(
        this.x * other.x,
        this.y * other.y
    )
}
//endregion

//region Vector specific operators
/**
 * The dot product of a [Vec2f] and a [Vec2f].
 */
public infix fun Vec2f.dot(other: Vec2f): Float {
    return this.dot(other)
}

/**
 * The dot product of a [Vec2f] and a [Vector2f].
 */
public infix fun Vec2f.dot(other: Vector2f): Float {
    return this.dot(Vec2f(other.x, other.y))
}

/**
 * The first component of a [Vec2f].
 */
public operator fun Vec2f.component1(): Float {
    return this.x
}

/**
 * The second component of a [Vec2f].
 */
public operator fun Vec2f.component2(): Float {
    return this.y
}
//endregion
