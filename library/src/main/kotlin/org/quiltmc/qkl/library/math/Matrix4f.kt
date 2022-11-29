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

import net.minecraft.util.math.Matrix4f
import net.minecraft.util.math.Quaternion

/**
 * Adds a [Matrix4f] and a [Matrix4f].
 */
public operator fun Matrix4f.plusAssign(other: Matrix4f) {
    this.add(other)
}

/**
 * Subtracts a [Matrix4f] from a [Matrix4f].
 */
public operator fun Matrix4f.minusAssign(other: Matrix4f) {
    this.subtract(other)
}

/**
 * Multiplies a [Matrix4f] and a [Matrix4f].
 */
public operator fun Matrix4f.timesAssign(other: Matrix4f) {
    this.multiply(other)
}

/**
 * Multiplies a [Float] and a [Matrix4f].
 */
public operator fun Matrix4f.timesAssign(other: Float) {
    this.multiply(other)
}

/**
 * Multiplies a [Quaternion] and a [Matrix4f].
 */
public operator fun Matrix4f.timesAssign(other: Quaternion) {
    this.multiply(other)
}
