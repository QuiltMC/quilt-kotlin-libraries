package org.quiltmc.qkl.wrapper.main.math

import net.minecraft.util.math.Vector4f

//region Standard math operators
public operator fun Vector4f.plus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x + other.x,
        this.y + other.y,
        this.z + other.z,
        this.w + other.w
    )
}

public operator fun Vector4f.plus(other: Float): Vector4f {
    return Vector4f(
        this.x + other,
        this.y + other,
        this.z + other,
        this.w + other
    )
}

public operator fun Vector4f.minus(other: Vector4f): Vector4f {
    return Vector4f(
        this.x - other.x,
        this.y - other.y,
        this.z - other.z,
        this.w - other.w
    )
}

public operator fun Vector4f.minus(other: Float): Vector4f {
    return Vector4f(
        this.x - other,
        this.y - other,
        this.z - other,
        this.w - other
    )
}

public operator fun Vector4f.times(other: Vector4f): Vector4f {
    return Vector4f(
        this.x * other.x,
        this.y * other.y,
        this.z * other.z,
        this.w * other.w
    )
}

public operator fun Vector4f.times(other: Float): Vector4f {
    return Vector4f(
        this.x * other,
        this.y * other,
        this.z * other,
        this.w * other
    )
}

public operator fun Vector4f.unaryMinus(): Vector4f {
    return this.times(-1f)
}
//endregion

//region xAssign math operators
public operator fun Vector4f.plusAssign(other: Float) {
    this.plus(other)
}

public operator fun Vector4f.minusAssign(other: Float) {
    this.minus(other)
}

public operator fun Vector4f.timesAssign(other: Float) {
    this.times(other)
}
//endregion

//region Vector specific operators
public infix fun Vector4f.dot(other: Vector4f): Float {
    return this.dotProduct(other)
}
//endregion
