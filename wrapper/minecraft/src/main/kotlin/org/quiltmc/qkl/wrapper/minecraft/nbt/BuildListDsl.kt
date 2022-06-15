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

import net.minecraft.nbt.NbtByte
import net.minecraft.nbt.NbtByteArray
import net.minecraft.nbt.NbtDouble
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtFloat
import net.minecraft.nbt.NbtInt
import net.minecraft.nbt.NbtIntArray
import net.minecraft.nbt.NbtList
import net.minecraft.nbt.NbtLong
import net.minecraft.nbt.NbtLongArray
import net.minecraft.nbt.NbtShort
import net.minecraft.nbt.NbtString

/**
 * DSL for creating [NbtList]s.
 *
 * @author leocth
 */
public class NbtListDsl {
    /**
     * The underlying [NbtList].
     *
     * @author leocth
     */
    public val list: NbtList = NbtList()


    /**
     * Inserts a new [NbtElement] with the given key.
     *
     * @throws UnsupportedOperationException
     *      if the type of this list does not match the type of the value
     * @author leocth
     */
    public fun add(value: NbtElement) {
        list.add(value)
    }
    /**
     * Inserts a new [Boolean] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created, and its value
     * will be 1 if the boolean is true, and 0 if it is false.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Byte]s
     * @author leocth
     */
    public fun add(value: Boolean) {
        list.add(NbtByte.of(value))
    }
    /**
     * Inserts a new [Byte] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Byte]s
     * @author leocth
     */
    public fun add(value: Byte) {
        list.add(NbtByte.of(value))
    }
    /**
     * Inserts a new [Short] tag with the given key and value.
     * A new [NbtShort][net.minecraft.nbt.NbtShort] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Short]s
     * @author leocth
     */
    public fun add(value: Short) {
        list.add(NbtShort.of(value))
    }
    /**
     * Inserts a new [Int] tag with the given key and value.
     * A new [NbtInt][net.minecraft.nbt.NbtInt] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Int]s
     * @author leocth
     */
    public fun add(value: Int) {
        list.add(NbtInt.of(value))
    }
    /**
     * Inserts a new [Long] tag with the given key and value.
     * A new [NbtLong][net.minecraft.nbt.NbtLong] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Long]s
     * @author leocth
     */
    public fun add(value: Long) {
        list.add(NbtLong.of(value))
    }
    /**
     * Inserts a new [Float] tag with the given key and value.
     * A new [NbtFloat][net.minecraft.nbt.NbtFloat] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Float]s
     * @author leocth
     */
    public fun add(value: Float) {
        list.add(NbtFloat.of(value))
    }
    /**
     * Inserts a new [Double] tag with the given key and value.
     * A new [NbtDouble][net.minecraft.nbt.NbtDouble] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [Double]s
     * @author leocth
     */
    public fun add(value: Double) {
        list.add(NbtDouble.of(value))
    }
    /**
     * Inserts a new [String] tag with the given key and value.
     * A new [NbtString][net.minecraft.nbt.NbtString] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [String]s
     * @author leocth
     */
    public fun add(value: String) {
        list.add(NbtString.of(value))
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [ByteArray]s
     * @author leocth
     */
    public fun add(value: ByteArray) {
        list.add(NbtByteArray(value))
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [ByteArray]s
     * @author leocth
     */
    @JvmName("addByteList")
    public fun add(value: List<Byte>) {
        list.add(NbtByteArray(value))
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [IntArray]s
     * @author leocth
     */
    public fun add(value: IntArray) {
        list.add(NbtIntArray(value))
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [IntArray]s
     * @author leocth
     */
    @JvmName("addIntList")
    public fun add(value: List<Int>) {
        list.add(NbtIntArray(value))
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [LongArray]s
     * @author leocth
     */
    public fun add(value: LongArray) {
        list.add(NbtLongArray(value))
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @throws UnsupportedOperationException if this list has anything other than [LongArray]s
     * @author leocth
     */
    @JvmName("addLongList")
    public fun add(value: List<Long>) {
        list.add(NbtLongArray(value))
    }
    /**
     * Builds and inserts a new list tag with the given key.
     *
     * @throws UnsupportedOperationException if this list has anything other than lists
     * @author leocth
     */
    public inline fun putList(action: NbtListDsl.() -> Unit) {
        list.add(buildNbtList(action))
    }
    /**
     * Builds and inserts a new list tag with the given key.
     *
     * @throws UnsupportedOperationException if this list has anything other than compounds
     * @author leocth
     */
    public inline fun putCompound(action: NbtCompoundDsl.() -> Unit) {
        list.add(buildNbtCompound(action))
    }
}

/**
 * Builds a new [NbtList].
 *
 * @see NbtListDsl
 * @author leocth
 */
public inline fun buildNbtList(action: NbtListDsl.() -> Unit): NbtList {
    return NbtListDsl().apply(action).list
}
