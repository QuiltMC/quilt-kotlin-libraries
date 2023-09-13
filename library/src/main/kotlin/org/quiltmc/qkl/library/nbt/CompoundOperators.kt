/*
 * Copyright 2023 The Quilt Project
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

package org.quiltmc.qkl.library.nbt

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement

//region: Setters
/**
 * Set the [key] to the given [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: NbtElement) {
    this.put(key, value)
}

/**
 * Set the [key] to the given boolean [value]. This encodes the value as a byte.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Boolean) {
    this.putBoolean(key, value)
}

/**
 * Set the [key] to the given byte [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Byte) {
    this.putByte(key, value)
}

/**
 * Set the [key] to the given short [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Short) {
    this.putShort(key, value)
}

/**
 * Set the [key] to the given int [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Int) {
    this.putInt(key, value)
}

/**
 * Set the [key] to the given long [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Long) {
    this.putLong(key, value)
}

/**
 * Set the [key] to the given float [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Float) {
    this.putFloat(key, value)
}

/**
 * Set the [key] to the given double [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: Double) {
    this.putDouble(key, value)
}

/**
 * Set the [key] to the given string [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: String) {
    this.putString(key, value)
}

/**
 * Set the [key] to the given byte array [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: ByteArray) {
    this.putByteArray(key, value)
}

/**
 * Set the [key] to the given int array [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: IntArray) {
    this.putIntArray(key, value)
}

/**
 * Set the [key] to the given long array [value].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.set(key: String, value: LongArray) {
    this.putLongArray(key, value)
}
//endregion

//region: Concatenation
/**
 * Create a new compound with this compound's contents and the given [other] compound's contents.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.plus(other: NbtCompound): NbtCompound {
    val new = NbtCompound()
    keys.forEach { new[it] = this[it]!! }
    other.keys.forEach { new[it] = other[it]!! }
    return new
}

/**
 * Create a new compound with this compound's contents and the given [other] map's contents.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.plus(other: Map<String, NbtElement>): NbtCompound {
    val new = NbtCompound()
    keys.forEach { new[it] = this[it]!! }
    other.forEach { (key, value) -> new[key] = value }
    return new
}

/**
 * Add the given [other] compound's contents to this compound.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.plusAssign(other: NbtCompound) {
    other.keys.forEach { this[it] = other[it]!! }
}

/**
 * Add the given [other] map's contents to this compound.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public operator fun NbtCompound.plusAssign(other: Map<String, NbtElement>) {
    other.forEach { (key, value) -> this[key] = value }
}
//endregion

//region: Creation
/**
 * Create a new compound with the given [elements].
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public fun NbtCompound(vararg elements: Pair<String, NbtElement>): NbtCompound {
    val new = NbtCompound()
    elements.forEach { (key, value) -> new[key] = value }
    return new
}

/**
 * Create a new compound copying the given [other] compound.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public fun NbtCompound(other: NbtCompound): NbtCompound {
    val new = NbtCompound()
    other.keys.forEach { new[it] = other[it]!! }
    return new
}

/**
 * Create a new compound copying the given [other] map.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.compoundOperators
 */
public fun NbtCompound(other: Map<String, NbtElement>): NbtCompound {
    val new = NbtCompound()
    other.forEach { (key, value) -> new[key] = value }
    return new
}
//endregion
