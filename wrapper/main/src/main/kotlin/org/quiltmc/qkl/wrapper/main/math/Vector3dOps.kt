package org.quiltmc.qkl.wrapper.main.math

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i

//region Standard math operators
public operator fun Vector3d.plus(other: Vector3d): Vector3d {
    return Vector3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

public operator fun Vector3d.plus(other: Double): Vector3d {
    return Vector3d(
        this.x + other,
        this.y + other,
        this.z + other
    )
}

public operator fun Vector3d.minus(other: Vector3d): Vector3d {
    return Vector3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

public operator fun Vector3d.minus(other: Double): Vector3d {
    return Vector3d(
        this.x - other,
        this.y - other,
        this.z - other
    )
}

public operator fun Vector3d.times(other: Vector3d): Vector3d {
    return Vector3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

public operator fun Vector3d.times(other: Double): Vector3d {
    return Vector3d(
        this.x * other,
        this.y * other,
        this.z * other
    )
}

public operator fun Vector3d.unaryMinus(): Vector3d {
    return this.times(-1.0)
}
//endregion

//region xAssign math operators
public operator fun Vector3d.plusAssign(other: Double) {
    this.plus(other)
}

public operator fun Vector3d.minusAssign(other: Double) {
    this.minus(other)
}

public operator fun Vector3d.timesAssign(other: Double) {
    this.times(other)
}
//endregion

//region Type compatibility operator variations
public operator fun Vector3d.plus(other: Vec3d): Vector3d {
    return Vector3d(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z
    )
}

public operator fun Vector3d.plus(other: Vec3f): Vector3d {
    return Vector3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

public operator fun Vector3d.plus(other: Vec3i): Vector3d {
    return Vector3d(
        this.x + other.x.toDouble(),
        this.y + other.y.toDouble(),
        this.z + other.z.toDouble()
    )
}

public operator fun Vector3d.minus(other: Vec3d): Vector3d {
    return Vector3d(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z
    )
}

public operator fun Vector3d.minus(other: Vec3f): Vector3d {
    return Vector3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
    )
}

public operator fun Vector3d.minus(other: Vec3i): Vector3d {
    return Vector3d(
        this.x - other.x.toDouble(),
        this.y - other.y.toDouble(),
        this.z - other.z.toDouble()
    )
}

public operator fun Vector3d.times(other: Vec3d): Vector3d {
    return Vector3d(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z
    )
}

public operator fun Vector3d.times(other: Vec3f): Vector3d {
    return Vector3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

public operator fun Vector3d.times(other: Vec3i): Vector3d {
    return Vector3d(
        this.x * other.x.toDouble(),
        this.y * other.y.toDouble(),
        this.z * other.z.toDouble()
    )
}

//endregion

//region Vector specific operators
public infix fun Vector3d.dot(other: Vec3d): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

public infix fun Vector3d.cross(other: Vec3d): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

public infix fun Vector3d.dot(other: Vec3f): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

public infix fun Vector3d.cross(other: Vec3f): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

public infix fun Vector3d.dot(other: Vec3i): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

public infix fun Vector3d.cross(other: Vec3i): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}

public infix fun Vector3d.dot(other: Vector3d): Double {
    return this.x * other.x + this.y * other.y + this.z * other.z
}

public infix fun Vector3d.cross(other: Vector3d): Vector3d {
    return Vector3d(
        this.y * other.z - this.z * other.y,
        this.z * other.x - this.x * other.z,
        this.x * other.y - this.y * other.x
    )
}
//endregion
