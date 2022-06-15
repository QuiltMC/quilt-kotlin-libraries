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

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import kotlin.reflect.KProperty

/**
 * The base class for structural NBT reading and writing.
 *
 * @author leocth
 */
public abstract class NbtStruct(protected val nbt: NbtCompound) {
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a boolean to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun boolean(key: String? = null): NbtDelegate<Boolean> {
        return object : NbtDelegate<Boolean> {
            override fun getValue(self: Any?, property: KProperty<*>): Boolean {
                return nbt.getBoolean(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Boolean) {
                nbt.putBoolean(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write an 8-bit integer to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun byte(key: String? = null): NbtDelegate<Byte> {
        return object : NbtDelegate<Byte> {
            override fun getValue(self: Any?, property: KProperty<*>): Byte {
                return nbt.getByte(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Byte) {
                nbt.putByte(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a 16-bit integer to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun short(key: String? = null): NbtDelegate<Short> {
        return object : NbtDelegate<Short> {
            override fun getValue(self: Any?, property: KProperty<*>): Short {
                return nbt.getShort(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Short) {
                nbt.putShort(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a 32-bit integer to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun int(key: String? = null): NbtDelegate<Int> {
        return object : NbtDelegate<Int> {
            override fun getValue(self: Any?, property: KProperty<*>): Int {
                return nbt.getInt(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Int) {
                nbt.putInt(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a 64-bit integer to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun long(key: String? = null): NbtDelegate<Long> {
        return object : NbtDelegate<Long> {
            override fun getValue(self: Any?, property: KProperty<*>): Long {
                return nbt.getLong(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Long) {
                nbt.putLong(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a 32-bit floating-point number to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun float(key: String? = null): NbtDelegate<Float> {
        return object : NbtDelegate<Float> {
            override fun getValue(self: Any?, property: KProperty<*>): Float {
                return nbt.getFloat(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Float) {
                nbt.putFloat(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a 64-bit floating-point number to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun double(key: String? = null): NbtDelegate<Double> {
        return object : NbtDelegate<Double> {
            override fun getValue(self: Any?, property: KProperty<*>): Double {
                return nbt.getDouble(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: Double) {
                nbt.putDouble(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a string to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun string(key: String? = null): NbtDelegate<String> {
        return object : NbtDelegate<String> {
            override fun getValue(self: Any?, property: KProperty<*>): String {
                return nbt.getString(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: String) {
                nbt.putString(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write an array of 8-bit integers to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun byteArray(key: String? = null): NbtDelegate<ByteArray> {
        return object : NbtDelegate<ByteArray> {
            override fun getValue(self: Any?, property: KProperty<*>): ByteArray {
                return nbt.getByteArray(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: ByteArray) {
                nbt.putByteArray(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write an array of 32-bit integers to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun intArray(key: String? = null): NbtDelegate<IntArray> {
        return object : NbtDelegate<IntArray> {
            override fun getValue(self: Any?, property: KProperty<*>): IntArray {
                return nbt.getIntArray(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: IntArray) {
                nbt.putIntArray(key ?: property.name, value)
            }
        }
    }

    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write an array of 64-bit integers to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun longArray(key: String? = null): NbtDelegate<LongArray> {
        return object : NbtDelegate<LongArray> {
            override fun getValue(self: Any?, property: KProperty<*>): LongArray {
                return nbt.getLongArray(key ?: property.name)
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: LongArray) {
                nbt.putLongArray(key ?: property.name, value)
            }
        }
    }
    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write another [structural NBT data type][NbtStruct] to the NBT compound
     * with the given key.
     *
     * @author leocth
     */
    public fun <T: NbtStruct> struct(
        mapper: (NbtCompound) -> T,
        key: String? = null
    ): NbtDelegate<T> {
        return object : NbtDelegate<T> {
            override fun getValue(self: Any?, property: KProperty<*>): T {
                return mapper(nbt.getCompound(key ?: property.name))
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: T) {
                nbt.put(key ?: property.name, value.nbt)
            }
        }
    }
    // TODO(leocth): listOfFloats, listOfDoubles, etc?

    /**
     * Creates a [delegate](https://kotlinlang.org/docs/delegated-properties.html) that can
     * read and write a list of objects of another [structural NBT data type][NbtStruct]
     * to the NBT compound with the given key.
     *
     * @author leocth
     */
    public fun <T: NbtStruct> listOfStructs(
        mapper: (NbtCompound) -> T,
        key: String? = null
    ): NbtDelegate<List<T>> {
        return object : NbtDelegate<List<T>> {
            override fun getValue(self: Any?, property: KProperty<*>): List<T> {
                return nbt.getList(key ?: property.name, COMPOUND.toInt()).map {
                    if (it.type != COMPOUND) {
                        throw IllegalArgumentException("NBT element must be a compound element")
                    }
                    mapper(it as NbtCompound)
                }
            }
            override fun setValue(self: Any?, property: KProperty<*>, value: List<T>) {
                val list = NbtList()
                value.forEach {
                    list.add(it.nbt)
                }
                nbt.put(key, list)
            }
        }
    }
}

/**
 * Interface containing the two operators for getting and setting via
 * [delegation](https://kotlinlang.org/docs/delegated-properties.html).
 *
 * You shouldn't need to implement this interface—its sole purpose is to
 * aid the implementation of [NbtStruct].
 *
 * @author leocth
 */
public interface NbtDelegate<T> {
    public operator fun getValue(self: Any?, property: KProperty<*>): T
    public operator fun setValue(self: Any?, property: KProperty<*>, value: T)
}