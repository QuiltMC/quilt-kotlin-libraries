package org.quiltmc.qkl.wrapper.main.math

import net.minecraft.client.util.math.Vector3d
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.Vec3f
import net.minecraft.util.math.Vec3i

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

public operator fun Vec3i.minusAssign(other: Int) {
    this.minus(other)
}

public operator fun Vec3i.timesAssign(other: Int) {
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
//endregion
