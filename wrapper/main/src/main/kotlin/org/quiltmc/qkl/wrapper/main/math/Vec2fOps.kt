package org.quiltmc.qkl.wrapper.main.math

import net.minecraft.client.util.math.Vector2f
import net.minecraft.util.math.Vec2f

//region Standard math operators
public operator fun Vec2f.plus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x + other.x,
        this.y + other.y
    )
}

public operator fun Vec2f.plus(other: Float): Vec2f {
    return Vec2f(
        this.x + other,
        this.y + other
    )
}

public operator fun Vec2f.minus(other: Vec2f): Vec2f {
    return Vec2f(
        this.x - other.x,
        this.y - other.y
    )
}

public operator fun Vec2f.minus(other: Float): Vec2f {
    return Vec2f(
        this.x - other,
        this.y - other
    )
}

public operator fun Vec2f.times(other: Vec2f): Vec2f {
    return Vec2f(
        this.x * other.x,
        this.y * other.y
    )
}

public operator fun Vec2f.times(other: Float): Vec2f {
    return Vec2f(
        this.x * other,
        this.y * other
    )
}

public operator fun Vec2f.unaryMinus(): Vec2f {
    return this.times(-1f)
}
//endregion

//region xAssign math operators
public operator fun Vec2f.plusAssign(other: Float) {
    this.plus(other)
}

public operator fun Vec2f.plusAssign(other: Vec2f) {
    this.plus(other)
}

public operator fun Vec2f.plusAssign(other: Vector2f) {
    this.plus(other)
}

public operator fun Vec2f.minusAssign(other: Float) {
    this.minus(other)
}

public operator fun Vec2f.minusAssign(other: Vec2f) {
    this.minus(other)
}

public operator fun Vec2f.minusAssign(other: Vector2f) {
    this.minus(other)
}

public operator fun Vec2f.timesAssign(other: Float) {
    this.times(other)
}

public operator fun Vec2f.timesAssign(other: Vec2f) {
    this.times(other)
}

public operator fun Vec2f.timesAssign(other: Vector2f) {
    this.times(other)
}
//endregion

//region Type compatibility operator variations
public operator fun Vec2f.plus(other: Vector2f): Vec2f {
    return Vec2f(
        this.x + other.x,
        this.y + other.y
    )
}

public operator fun Vec2f.minus(other: Vector2f): Vec2f {
    return Vec2f(
        this.x - other.x,
        this.y - other.y
    )
}

public operator fun Vec2f.times(other: Vector2f): Vec2f {
    return Vec2f(
        this.x * other.x,
        this.y * other.y
    )
}
//endregion

//region Vector specific operators
public infix fun Vec2f.dot(other: Vec2f): Float {
    return this.dot(other)
}

public infix fun Vec2f.dot(other: Vector2f): Float {
    return this.dot(Vec2f(other.x, other.y))
}
//endregion
