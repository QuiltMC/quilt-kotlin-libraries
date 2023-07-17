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

public typealias NbtListPropertyProvider<T> = NbtPropertyProvider<MutableList<T>>

internal class NbtListWatcher<T, N : NbtElement>(
    val list: AbstractNbtList<N>,
    val toNbt: (T) -> N,
    val fromNbt: (N) -> T,
) : MutableList<T> {
    override val size: Int get() = list.size

    override fun clear() {
        list.clear()
    }

    override fun addAll(elements: Collection<T>) = list.addAll(elements.map(toNbt))

    override fun addAll(index: Int, elements: Collection<T>) = list.addAll(index, elements.map(toNbt))

    override fun add(index: Int, element: T) {
        list.add(index, toNbt(element))
    }

    override fun add(element: T) =
        list.add(toNbt(element))

    override fun get(index: Int) = fromNbt(list[index])

    override fun isEmpty() = list.isEmpty()

    override fun iterator() = object : MutableIterator<T> {
        private var index = 0
        override fun hasNext() = index < list.size
        override fun next() = fromNbt(list[index++])
        override fun remove() {
            list.removeAt(index--)
        }
    }

    override fun listIterator() = listIterator(0)

    override fun listIterator(index: Int) = object : MutableListIterator<T> {
        private var current = index
        override fun add(element: T) {
            list.add(current++, toNbt(element))
        }
        override fun hasNext() = current < list.size
        override fun next() = fromNbt(list[current++])
        override fun hasPrevious() = current > 0
        override fun previous() = fromNbt(list[current--])
        override fun nextIndex() = current + 1
        override fun previousIndex() = current - 1
        override fun remove() {
            list.removeAt(current--)
        }
        override fun set(element: T) {
            list[current] = toNbt(element)
        }
    }

    override fun removeAt(index: Int) = fromNbt(list.removeAt(index))

    override fun subList(fromIndex: Int, toIndex: Int) = toMutableList().subList(fromIndex, toIndex)

    override fun set(index: Int, element: T) = fromNbt(list.set(index, toNbt(element)))

    override fun retainAll(elements: Collection<T>) = list.retainAll(elements.map(toNbt).toSet())

    override fun removeAll(elements: Collection<T>) = list.removeAll(elements.map(toNbt).toSet())

    override fun remove(element: T) = list.remove(toNbt(element))

    override fun lastIndexOf(element: T) = list.lastIndexOf(toNbt(element))

    override fun indexOf(element: T) = list.indexOf(toNbt(element))

    override fun containsAll(elements: Collection<T>) = list.containsAll(elements.map(toNbt).toSet())

    override fun contains(element: T) = list.contains(toNbt(element))
}

/**
 * A byte array, represented as a mutable list of bytes.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.byteArray(
    name: String? = null,
    default: ByteArray? = null
): NbtListPropertyProvider<Byte> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            ::NbtByteArray
        ) { NbtListWatcher(it as NbtByteArray, NbtByte::of, NbtByte::byteValue) }
    }
}

/**
 * An overload of [byteArray] that allows for an iterable of bytes as the default value.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.byteArray(
    name: String? = null,
    default: Iterable<Byte>?
): NbtListPropertyProvider<Byte> {
    return byteArray(name, default?.toList()?.toByteArray())
}

/**
 * A byte list. Lists are not always guaranteed to be of the correct type.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.byteList(
    name: String? = null,
    default: Iterable<Byte>? = null
): NbtListPropertyProvider<Byte> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtByte::of)) } }
        ) { NbtListWatcher(it as NbtList, { b: Byte -> NbtByte.of(b) }) { nbt -> (nbt as NbtByte).byteValue() } }
    }
}

/**
 * A short list. Lists are not always guaranteed to be of the correct type.
 * No short array type exists in NBT.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.shortList(
    name: String? = null,
    default: Iterable<Short>? = null
): NbtListPropertyProvider<Short> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtShort::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtShort::of) { nbt -> (nbt as NbtShort).shortValue() } }
    }
}

/**
 * An int array, represented as a mutable list of integers.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.intArray(
    name: String? = null,
    default: IntArray? = null
): NbtListPropertyProvider<Int> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            ::NbtIntArray
        ) { NbtListWatcher(it as NbtIntArray, NbtInt::of, NbtInt::intValue) }
    }
}

/**
 * An overload of [intArray] that allows for an iterable of integers as the default value.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.intArray(
    name: String? = null,
    default: Iterable<Int>?
): NbtListPropertyProvider<Int> {
    return intArray(name, default?.toList()?.toIntArray())
}

/**
 * An int list. Lists are not always guaranteed to be of the correct type.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.intList(
    name: String? = null,
    default: Iterable<Int>? = null
): NbtListPropertyProvider<Int> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtInt::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtInt::of) { nbt -> (nbt as NbtInt).intValue() } }
    }
}

/**
 * A long array, represented as a mutable list of longs.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.longArray(
    name: String? = null,
    default: LongArray? = null
): NbtListPropertyProvider<Long> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            ::NbtLongArray
        ) { NbtListWatcher(it as NbtLongArray, NbtLong::of, NbtLong::longValue) }
    }
}

/**
 * An overload of [longArray] that allows for an iterable of longs as the default value.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.longArray(
    name: String? = null,
    default: Iterable<Long>?
): NbtListPropertyProvider<Long> {
    return longArray(name, default?.toList()?.toLongArray())
}

/**
 * A long list. Lists are not always guaranteed to be of the correct type.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.longList(
    name: String? = null,
    default: Iterable<Long>? = null
): NbtListPropertyProvider<Long> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtLong::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtLong::of) { nbt -> (nbt as NbtLong).longValue() } }
    }
}

/**
 * A float list. Lists are not always guaranteed to be of the correct type.
 * No float array type exists in NBT.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.floatList(
    name: String? = null,
    default: Iterable<Float>? = null
): NbtListPropertyProvider<Float> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtFloat::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtFloat::of) { nbt -> (nbt as NbtFloat).floatValue() } }
    }
}

/**
 * A double list. Lists are not always guaranteed to be of the correct type.
 * No double array type exists in NBT.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.doubleList(
    name: String? = null,
    default: Iterable<Double>? = null
): NbtListPropertyProvider<Double> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtDouble::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtDouble::of) { nbt -> (nbt as NbtDouble).doubleValue() } }
    }
}

/**
 * A string list. Lists are not always guaranteed to be of the correct type.
 * No string array type exists in NBT.
 *
 * @author sschr15
 * @sample samples.qkl.nbt.NbtSamples.Lists
 */
public fun CompoundProperty.stringList(
    name: String? = null,
    default: Iterable<String>? = null
): NbtListPropertyProvider<String> {
    return provider { propName ->
        Delegate(
            this,
            name ?: propName,
            default?.toMutableList(),
            { NbtList().also { list -> list.addAll(it.map(NbtString::of)) } }
        ) { NbtListWatcher(it as NbtList, NbtString::of, NbtElement::asString) }
    }
}
