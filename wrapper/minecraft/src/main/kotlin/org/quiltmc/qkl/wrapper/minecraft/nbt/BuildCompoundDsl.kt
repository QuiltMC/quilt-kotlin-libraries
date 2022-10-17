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

import net.minecraft.block.BlockState
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtHelper
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import java.util.UUID

/**
 * This class contains the functions for building a [NbtCompound] object.
 * To use the DSL use [buildNbtCompound].
 *
 * @author leocth
 */
@Suppress("MemberVisibilityCanBePrivate")
public class NbtCompoundDsl {
    public val compound: NbtCompound = NbtCompound()

    //region Primitive put methods

    /**
     * Inserts a new [NbtElement] with the given key.
     *
     * @author leocth
     */
    public fun put(key: String, value: NbtElement) {
        compound.put(key, value)
    }
    /**
     * Inserts a new [Boolean] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created, and its value
     * will be 1 if the boolean is true, and 0 if it is false.
     *
     * @author leocth
     */
    public fun put(key: String, value: Boolean) {
        compound.putBoolean(key, value)
    }
    /**
     * Inserts a new [Byte] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Byte) {
        compound.putByte(key, value)
    }
    /**
     * Inserts a new [Short] tag with the given key and value.
     * A new [NbtShort][net.minecraft.nbt.NbtShort] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Short) {
        compound.putShort(key, value)
    }
    /**
     * Inserts a new [Int] tag with the given key and value.
     * A new [NbtInt][net.minecraft.nbt.NbtInt] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Int) {
        compound.putInt(key, value)
    }
    /**
     * Inserts a new [Long] tag with the given key and value.
     * A new [NbtLong][net.minecraft.nbt.NbtLong] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Long) {
        compound.putLong(key, value)
    }
    /**
     * Inserts a new [Float] tag with the given key and value.
     * A new [NbtFloat][net.minecraft.nbt.NbtFloat] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Float) {
        compound.putFloat(key, value)
    }
    /**
     * Inserts a new [Double] tag with the given key and value.
     * A new [NbtDouble][net.minecraft.nbt.NbtDouble] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: Double) {
        compound.putDouble(key, value)
    }
    /**
     * Inserts a new [String] tag with the given key and value.
     * A new [NbtString][net.minecraft.nbt.NbtString] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: String) {
        compound.putString(key, value)
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: ByteArray) {
        compound.putByteArray(key, value)
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *
     * @author leocth
     */
    @JvmName("putByteList")
    public fun put(key: String, value: List<Byte>) {
        compound.putByteArray(key, value)
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: IntArray) {
        compound.putIntArray(key, value)
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *
     * @author leocth
     */
    @JvmName("putIntList")
    public fun put(key: String, value: List<Int>) {
        compound.putIntArray(key, value)
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @author leocth
     */
    public fun put(key: String, value: LongArray) {
        compound.putLongArray(key, value)
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @author leocth
     */
    @JvmName("putLongList")
    public fun put(key: String, value: List<Long>) {
        compound.putLongArray(key, value)
    }
    /**
     * Builds and inserts a new list tag with the given key.
     *
     * @author leocth
     */
    public inline fun putList(key: String, action: NbtListDsl.() -> Unit) {
        put(key, buildNbtList(action))
    }
    /**
     * Builds and inserts a new compound tag with the given key.
     *
     * @author leocth
     */
    public inline fun putCompound(key: String, action: NbtCompoundDsl.() -> Unit) {
        put(key, buildNbtCompound(action))
    }
    //endregion Primitive put methods

    //region Abbreviated syntax for primitive put methods

    /**
     * Inserts a new [NbtElement] with the given key.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: NbtElement) {
        put(this, value)
    }
    /**
     * Inserts a new [Boolean] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created, and its value
     * will be 1 if the boolean is true, and 0 if it is false.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Boolean) {
        put(this, value)
    }
    /**
     * Inserts a new [Byte] tag with the given key and value.
     * A new [NbtByte][net.minecraft.nbt.NbtByte] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Byte) {
        put(this, value)
    }
    /**
     * Inserts a new [Short] tag with the given key and value.
     * A new [NbtShort][net.minecraft.nbt.NbtShort] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Short) {
        put(this, value)
    }
    /**
     * Inserts a new [Int] tag with the given key and value.
     * A new [NbtInt][net.minecraft.nbt.NbtInt] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Int) {
        put(this, value)
    }
    /**
     * Inserts a new [Long] tag with the given key and value.
     * A new [NbtLong][net.minecraft.nbt.NbtLong] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Long) {
        put(this, value)
    }
    /**
     * Inserts a new [Float] tag with the given key and value.
     * A new [NbtFloat][net.minecraft.nbt.NbtFloat] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Float) {
        put(this, value)
    }
    /**
     * Inserts a new [Double] tag with the given key and value.
     * A new [NbtDouble][net.minecraft.nbt.NbtDouble] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Double) {
        put(this, value)
    }
    /**
     * Inserts a new [String] tag with the given key and value.
     * A new [NbtString][net.minecraft.nbt.NbtString] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: String) {
        put(this, value)
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: ByteArray) {
        put(this, value)
    }
    /**
     * Inserts a new [ByteArray] tag with the given key and value.
     * A new [NbtByteArray][net.minecraft.nbt.NbtByteArray] will be created.
     *List
     * @author leocth
     */
    @JvmName("putByteListShortcut")
    public operator fun String.invoke(value: List<Byte>) {
        put(this, value)
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: IntArray) {
        put(this, value)
    }
    /**
     * Inserts a new [IntArray] tag with the given key and value.
     * A new [NbtIntArray][net.minecraft.nbt.NbtIntArray] will be created.
     *List
     * @author leocth
     */
    @JvmName("putIntListShortcut")
    public operator fun String.invoke(value: List<Int>) {
        put(this, value)
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: LongArray) {
        put(this, value)
    }
    /**
     * Inserts a new [LongArray] tag with the given key and value.
     * A new [NbtLongArray][net.minecraft.nbt.NbtLongArray] will be created.
     *
     * @author leocth
     */
    @JvmName("putLongListShortcut")
    public operator fun String.invoke(value: List<Long>) {
        put(this, value)
    }
    /**
     * Builds and inserts a new list tag with the given key.
     *
     * @author leocth
     */
    public inline infix fun String.list(action: NbtListDsl.() -> Unit) {
        putList(this, action)
    }
    /**
     * Builds and inserts a new compound tag with the given key.
     *
     * @author leocth
     */
    public inline infix fun String.compound(action: NbtCompoundDsl.() -> Unit) {
        putCompound(this, action)
    }
    //endregion Abbreviated syntax for primitive put methods

    //region Extended put methods

    /**
     * Inserts a new tag encoding the UUID with the given key.
     *
     * Calls [NbtHelper.fromUuid] internally.
     *
     * @author leocth
     */
    public fun put(key: String, value: UUID) {
        compound.put(key, NbtHelper.fromUuid(value))
    }
    /**
     * Inserts a new tag encoding the [Identifier] with the given key.
     *
     * Currently, Minecraft represents [Identifier]s as [NbtString][net.minecraft.nbt.NbtString]s.
     *
     * @author leocth
     */
    public fun put(key: String, value: Identifier) {
        compound.putString(key, value.toString())
    }
    /**
     * Inserts a new tag encoding the [BlockPos] with the given key.
     *
     * Calls [NbtHelper.fromBlockPos] internally.
     *
     * @author leocth
     */
    public fun put(key: String, value: BlockPos) {
        compound.put(key, NbtHelper.fromBlockPos(value))
    }
    /**
     * Inserts a new tag encoding the [BlockState] with the given key.
     *
     * Calls [NbtHelper.fromBlockState] internally.
     *
     * @author leocth
     */
    public fun put(key: String, value: BlockState) {
        compound.put(key, NbtHelper.fromBlockState(value))
    }
    //endregion Extended put methods

    //region Abbreviated syntax for extended put methods

    /**
     * Inserts a new tag encoding the UUID with the given key.
     *
     * Calls [NbtHelper.fromUuid] internally.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: UUID) {
        put(this, value)
    }
    /**
     * Inserts a new tag encoding the [Identifier] with the given key.
     *
     * Currently, Minecraft represents [Identifier]s as [NbtString][net.minecraft.nbt.NbtString]s.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: Identifier) {
        put(this, value)
    }
    /**
     * Inserts a new tag encoding the [BlockPos] with the given key.
     *
     * Calls [NbtHelper.fromBlockPos] internally.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: BlockPos) {
        put(this, value)
    }
    /**
     * Inserts a new tag encoding the [BlockState] with the given key.
     *
     * Calls [NbtHelper.fromBlockState] internally.
     *
     * @author leocth
     */
    public operator fun String.invoke(value: BlockState) {
        put(this, value)
    }
    //endregion
}
/**
 * Builds a new [NbtCompound].
 *
 * @see NbtCompoundDsl
 * @author leocth
 */
public inline fun buildNbtCompound(action: NbtCompoundDsl.() -> Unit): NbtCompound {
    return NbtCompoundDsl().apply(action).compound
}
