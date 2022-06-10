package org.quiltmc.qkl.wrapper.main.math

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i

//region Standard math operators
public operator fun Vec3d.plus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

public operator fun Vec3d.plus(other: Double): Vec3d {
    return Vec3d(
        this.x + other,
        this.y + other,
        this.z + other
    )
}

public operator fun Vec3d.minus(other: Vec3d): Vec3d {
    return Vec3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

public operator fun Vec3d.minus(other: Double): Vec3d {
    return Vec3d(
        this.x - other,
        this.y - other,
        this.z - other
    )
}

public operator fun Vec3d.times(other: Vec3d): Vec3d {
    return Vec3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

public operator fun Vec3d.times(other: Double): Vec3d {
    return Vec3d(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

public operator fun Vec3d.unaryMinus(): Vec3d {
    return this.times(-1.0)
}
//endregion

//region xAssign math operators
public operator fun Vec3d.plusAssign(other: Double) {
    this.plus(other)
}

public operator fun Vec3d.minusAssign(other: Double) {
    this.minus(other)
}

public operator fun Vec3d.timesAssign(other: Double) {
    this.times(other)
}
//endregion

//region Type compatibility operator variations
public operator fun Vec3d.plus(other: Vec3f): Vec3d {
    return Vec3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

public operator fun Vec3d.plus(other: Vec3i): Vec3d {
    return Vec3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

public operator fun Vec3d.plus(other: Vector3d): Vec3d {
    return Vec3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

public operator fun Vec3d.minus(other: Vec3f): Vec3d {
    return Vec3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
    )
}

public operator fun Vec3d.minus(other: Vec3i): Vec3d {
    return Vec3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
    )
}

public operator fun Vec3d.minus(other: Vector3d): Vec3d {
    return Vec3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

public operator fun Vec3d.times(other: Vec3f): Vec3d {
    return Vec3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

public operator fun Vec3d.times(other: Vec3i): Vec3d {
    return Vec3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

public operator fun Vec3d.times(other: Vector3d): Vec3d {
    return Vec3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

//endregion

//region Vector specific operators
public infix fun Vec3d.dot(other: Vec3d): Double {
    return this.dotProduct(other)
}

public infix fun Vec3d.cross(other: Vec3d): Vec3d {
    return this.crossProduct(other)
}

public infix fun Vec3d.dot(other: Vec3f): Double {
    return this.dotProduct(Vec3d(other))
}

public infix fun Vec3d.cross(other: Vec3f): Vec3d {
    return this.crossProduct(Vec3d(other))
}

public infix fun Vec3d.dot(other: Vec3i): Double {
    return this.dotProduct(Vec3d.of(other))
}

public infix fun Vec3d.cross(other: Vec3i): Vec3d {
    return this.crossProduct(Vec3d.of(other))
}

public infix fun Vec3d.dot(other: Vector3d): Double {
    return this.dotProduct(Vec3d(other.x, other.y, other.z))
}

public infix fun Vec3d.cross(other: Vector3d): Vec3d {
    return this.crossProduct(Vec3d(other.x, other.y, other.z))
}
//endregion
