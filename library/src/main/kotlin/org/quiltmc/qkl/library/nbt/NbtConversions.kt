/*
 * Copyright 2022 The Quilt Project
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

import net.minecraft.nbt.*

//region: NBT <-> Raw values
/**
 * Get this boolean as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Boolean.nbt: NbtByte get() {
    return NbtByte.of(this)
}

/**
 * Get this byte as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Byte.nbt: NbtByte get() {
    return NbtByte.of(this)
}

/**
 * Get this short as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Short.nbt: NbtShort get() {
    return NbtShort.of(this)
}

/**
 * Get this int as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Int.nbt: NbtInt get() {
    return NbtInt.of(this)
}

/**
 * Get this long as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Long.nbt: NbtLong get() {
    return NbtLong.of(this)
}

/**
 * Get this float as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Float.nbt: NbtFloat get() {
    return NbtFloat.of(this)
}

/**
 * Get this double as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val Double.nbt: NbtDouble get() {
    return NbtDouble.of(this)
}

/**
 * Get this string as an NBT element.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val String.nbt: NbtString get() {
    return NbtString.of(this)
}

/**
 * Get this element as a boolean, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.boolean: Boolean get() {
    return (this as NbtByte).byteValue() != 0.toByte()
}

/**
 * Get this element as a byte, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.byte: Byte get() {
    return (this as NbtByte).byteValue()
}

/**
 * Get this element as a short, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.short: Short get() {
    return (this as NbtShort).shortValue()
}

/**
 * Get this element as an int, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.int: Int get() {
    return (this as NbtInt).intValue()
}

/**
 * Get this element as a long, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.long: Long get() {
    return (this as NbtLong).longValue()
}

/**
 * Get this element as a float, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.float: Float get() {
    return (this as NbtFloat).floatValue()
}

/**
 * Get this element as a double, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.double: Double get() {
    return (this as NbtDouble).doubleValue()
}

/**
 * Get this element as a string, if possible.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val NbtElement.string: String get() {
    return asString()
}
//endregion

//region: NBT <-> Lists / Arrays
/**
 * Get this byte array as an NBT byte array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val ByteArray.nbt: NbtByteArray get() {
    return NbtByteArray(this)
}

/**
 * Get this int array as an NBT int array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val IntArray.nbt: NbtIntArray get() {
    return NbtIntArray(this)
}

/**
 * Get this long array as an NBT long array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public val LongArray.nbt: NbtLongArray get() {
    return NbtLongArray(this)
}

/**
 * Get these bytes as an NBT byte array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("bytesToNbt")
public fun Iterable<Byte>.toNbt(): NbtByteArray {
    return NbtByteArray(toList())
}

/**
 * Get these ints as an NBT int array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("intsToNbt")
public fun Iterable<Int>.toNbt(): NbtIntArray {
    return NbtIntArray(toList())
}

/**
 * Get these longs as an NBT long array.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("longsToNbt")
public fun Iterable<Long>.toNbt(): NbtLongArray {
    return NbtLongArray(toList())
}

/**
 * Get these shorts as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("shortsToNbt")
public fun Iterable<Short>.toNbt(): NbtList {
    return NbtList().also { it.addAll(map { s -> s.nbt }) }
}

/**
 * Get these floats as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("floatsToNbt")
public fun Iterable<Float>.toNbt(): NbtList {
    return NbtList().also { it.addAll(map { f -> f.nbt }) }
}

/**
 * Get these doubles as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("doublesToNbt")
public fun Iterable<Double>.toNbt(): NbtList {
    return NbtList().also { it.addAll(map { d -> d.nbt }) }
}

/**
 * Get these strings as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
@JvmName("stringsToNbt")
public fun Iterable<String>.toNbt(): NbtList {
    return NbtList().also { it.addAll(map { s -> s.nbt }) }
}
//endregion

//region: NBT <-> NBT
/**
 * Get this NBT byte array as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public fun NbtByteArray.toNbtList(): NbtList {
    return NbtList().also { it.addAll(this) }
}

/**
 * Get this NBT int array as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public fun NbtIntArray.toNbtList(): NbtList {
    return NbtList().also { it.addAll(this) }
}

/**
 * Get this NBT long array as an NBT list.
 * 
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.elementConversions
 */
public fun NbtLongArray.toNbtList(): NbtList {
    return NbtList().also { it.addAll(this) }
}
//endregion
