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

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i
import kotlin.math.sqrt

//region Standard math operators
public operator fun Vec3i.plus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

public operator fun Vec3i.plus(other: Int): Vec3i {
    return Vec3i(
        this.x + other,
        this.y + other,
        this.z + other
    )
}

public operator fun Vec3i.minus(other: Vec3i): Vec3i {
    return Vec3i(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

public operator fun Vec3i.minus(other: Int): Vec3i {
    return Vec3i(
        this.x - other,
        this.y - other,
        this.z - other
    )
}

public operator fun Vec3i.times(other: Vec3i): Vec3i {
    return Vec3i(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

public operator fun Vec3i.times(other: Int): Vec3i {
    return Vec3i(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

public operator fun Vec3i.unaryMinus(): Vec3i {
    return this.times(-1)
}
//endregion

//region xAssign math operators
public operator fun Vec3i.plusAssign(other: Int) {
    this.plus(other)
}

public operator fun Vec3i.plusAssign(other: Vec3d) {
    this.plus(other)
}

public operator fun Vec3i.plusAssign(other: Vec3f) {
    this.plus(other)
}

public operator fun Vec3i.plusAssign(other: Vec3i) {
    this.plus(other)
}

public operator fun Vec3i.plusAssign(other: Vector3d) {
    this.plus(other)
}

public operator fun Vec3i.minusAssign(other: Int) {
    this.minus(other)
}

public operator fun Vec3i.minusAssign(other: Vec3d) {
    this.minus(other)
}

public operator fun Vec3i.minusAssign(other: Vec3f) {
    this.minus(other)
}

public operator fun Vec3i.minusAssign(other: Vec3i) {
    this.minus(other)
}

public operator fun Vec3i.minusAssign(other: Vector3d) {
    this.minus(other)
}

public operator fun Vec3i.timesAssign(other: Int) {
    this.times(other)
}

public operator fun Vec3i.timesAssign(other: Vec3d) {
    this.times(other)
}

public operator fun Vec3i.timesAssign(other: Vec3f) {
    this.times(other)
}

public operator fun Vec3i.timesAssign(other: Vec3i) {
    this.times(other)
}

public operator fun Vec3i.timesAssign(other: Vector3d) {
    this.times(other)
}
//endregion

//region Type compatibility operator variations
public operator fun Vec3i.plus(other: Vec3d): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

public operator fun Vec3i.plus(other: Vec3f): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

public operator fun Vec3i.plus(other: Vector3d): Vec3i {
    return Vec3i(
        this.x + other.x.toInt(),
        this.y + other.y.toInt(),
        this.z + other.z.toInt()
    )
}

public operator fun Vec3i.minus(other: Vec3d): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
    )
}

public operator fun Vec3i.minus(other: Vec3f): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
    )
}

public operator fun Vec3i.minus(other: Vector3d): Vec3i {
    return Vec3i(
        this.x - other.x.toInt(),
        this.y - other.y.toInt(),
        this.z - other.z.toInt()
    )
}

public operator fun Vec3i.times(other: Vec3d): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
    )
}

public operator fun Vec3i.times(other: Vec3f): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
    )
}

public operator fun Vec3i.times(other: Vector3d): Vec3i {
    return Vec3i(
        this.x * other.x.toInt(),
        this.y * other.y.toInt(),
        this.z * other.z.toInt()
    )
}
//endregion

//region Vector specific operators
public infix fun Vec3i.dot(other: Vec3d): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

public infix fun Vec3i.cross(other: Vec3d): Vec3i {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
}

public infix fun Vec3i.dot(other: Vec3f): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

public infix fun Vec3i.cross(other: Vec3f): Vec3i {
    return this.crossProduct(Vec3i(other.x.toDouble(), other.y.toDouble(), other.z.toDouble()))
}

public infix fun Vec3i.dot(other: Vec3i): Int {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

public infix fun Vec3i.cross(other: Vec3i): Vec3i {
    return this.crossProduct(other)
}

public infix fun Vec3i.dot(other: Vector3d): Int {
    return this.x * other.x.toInt() + this.y * other.y.toInt() + this.z * other.z.toInt()
}

public infix fun Vec3i.cross(other: Vector3d): Vec3i {
    return this.crossProduct(Vec3i(other.x, other.y, other.z))
}

public fun Vec3i.normalize(): Vec3i {
    val len = length()
    return if (len < EPSILON) {
        Vec3i.ZERO
    } else {
        Vec3i(x / len, y / len, z / len)
    }
}

public fun Vec3i.length(): Double {
    return sqrt((x * x + y * y + z * z).toDouble())
}

public fun Vec3i.lengthSquared(): Int {
    return x * x + y * y + z * z
}
//endregion
