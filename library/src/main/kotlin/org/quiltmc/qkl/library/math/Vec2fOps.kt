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

package org.quiltmc.qkl.library.math

import net.minecraft.util.math.Vec2f

/**
 * The [`x`][Vec2f.x] component of this [Vec2f].
 */
public operator fun Vec2f.component1(): Float {
    return this.x
}

/**
 * The [`y`][Vec2f.y] component of this [Vec2f].
 */
public operator fun Vec2f.component2(): Float {
    return this.y
}

/**
 * Adds [this] and [other], returning a new [Vec2f].
 */
public operator fun Vec2f.plus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x + other.x,
        this.y + other.y
    )
}

/**
 * Subtracts [other] from [this], returning a new [Vec2f].
 */
public operator fun Vec2f.minus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x - other.x,
        this.y - other.y
    )
}

/**
 * Multiplies [this] and [other] component-wise, returning a new [Vec2f].
 */
public operator fun Vec2f.times(other: Vec2f): Vec2f {
    return Vec2f(
        this.x * other.x,
        this.y * other.y
    )
}

/**
 * Multiplies [this] with [other], returning a new [Vec2f].
 */
public operator fun Vec2f.times(other: Float): Vec2f {
    return Vec2f(
        this.x * other,
        this.y * other
    )
}

/**
 * Multiplies [other] with [this], returning a new [Vec2f].
 */
public operator fun Float.times(other: Vec2f): Vec2f {
    return other * this
}

/**
 * Divides [this] by [other] component-wise, returning a new [Vec2f].
 */
public operator fun Vec2f.div(other: Vec2f): Vec2f {
    return Vec2f(
        this.x / other.x,
        this.y / other.y
    )
}

/**
 * Divides [this] by [other], returning a new [Vec2f].
 */
public operator fun Vec2f.div(other: Float): Vec2f {
    return Vec2f(
        this.x / other,
        this.y / other
    )
}

/**
 * Negates [this], returning a new [Vec2f].
 */
public operator fun Vec2f.unaryMinus(): Vec2f {
    return -1f * this
}

/**
 * The dot product of [this] and [other].
 */
public infix fun Vec2f.dot(other: Vec2f): Float {
    return this.dot(other)
}
