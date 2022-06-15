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

package org.quiltmc.qkl.wrapper.minecraft.nbt

import net.minecraft.nbt.*

//region Getting nullable values from compound tags

// Implementor's note (leocth):
//
// For checking the types of the tags, the method we use below is theoretically a bit slower
// than the way Minecraft uses—instead of checking a single number, we use the `as?` operator
// which is a bit like an `instanceof` + a cast, which could be a *lot* slower.
//
// However, in absence of performance benchmarks, I think the convenience of using `as?`
// far outweighs any performance benefits comparing numeric NBT types might have.
// I might change our implementation in the future if a substantial performance increase
// can be identified.

/**
 * Gets a boolean from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getBooleanOrNull(key: String): Boolean? {
    return get(key)?.let {
            it as? NbtByte
        }?.let {
            it.byteValue() == 1.toByte()
        }
}
/**
 * Gets an 8-bit integer from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getByteOrNull(key: String): Byte? {
    return get(key)?.let { it as? NbtByte }?.byteValue()
}
/**
 * Gets a 16-bit integer from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getShortOrNull(key: String): Short? {
    return get(key)?.let { it as? NbtShort }?.shortValue()
}
/**
 * Gets a 32-bit integer from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getIntOrNull(key: String): Int? {
    return get(key)?.let { it as? NbtInt }?.intValue()
}
/**
 * Gets a 64-bit integer from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getLongOrNull(key: String): Long? {
    return get(key)?.let { it as? NbtLong }?.longValue()
}
/**
 * Gets a 32-bit floating-point number from the NBT compound with the given key,
 * returning `null` if the key is not present in the compound, or if the found tag
 * is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getFloatOrNull(key: String): Float? {
    return get(key)?.let { it as? NbtFloat }?.floatValue()
}
/**
 * Gets a 64-bit floating-point number from the NBT compound with the given key,
 * returning `null` if the key is not present in the compound, or if the found tag
 * is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getDoubleOrNull(key: String): Double? {
    return get(key)?.let { it as? NbtDouble }?.doubleValue()
}
/**
 * Gets a string from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getStringOrNull(key: String): String? {
    return get(key)?.let { it as? NbtString }?.asString()
}
/**
 * Gets an NBT compound from the NBT compound with the given key, returning `null` if
 * the key is not present in the compound, or if the found tag is not of the expected type.
 *
 * @author leocth
 */
public fun NbtCompound.getCompoundOrNull(key: String): NbtCompound? {
    return get(key)?.let { it as? NbtCompound }
}
//endregion Getting nullable values from compound tags
