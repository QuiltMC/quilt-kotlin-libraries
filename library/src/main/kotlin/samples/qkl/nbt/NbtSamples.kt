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

package samples.qkl.nbt

import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import org.quiltmc.qkl.library.nbt.*

@Suppress("unused", "MagicNumber", "UNUSED_VARIABLE")
private object NbtSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    @JvmName("getCompoundFunction")
    fun getCompound(): NbtCompound {
        error("Sample utility should not be called")
    }

    val compound: NbtCompound = stub()

    object BasicTypes {
        val compound: NbtCompound = stub()
        val secondCompound = getCompound()

        var count by ::compound.byte(default = 1)
        var maxDamage by ::compound.int()
        var currentDamage by ::compound.int(name = "damage")

        var name by ::getCompound.string()
        var height by ::getCompound.float()

        fun modify() {
            count = 2
            maxDamage = 100
            currentDamage = 50

            name = "test"
            height = 1.0f
        }
    }

    object Lists {
        val compound: NbtCompound = stub()

        var ints by ::compound.intArray()
        var strings by ::compound.stringList(name = "someStrings")
        var numbers by ::compound.doubleList(default = listOf(1.0, 2.0, 3.0))

        fun modify() {
            ints[2] = 4
            strings.add("hello")
            numbers[1] = 2.5
        }
    }

    object Nesting {
        val compound: NbtCompound = stub()

        val objects by ::compound.compound()
        val objectsInObjects by ::objects.compound(name = "objects")
        var isNested by ::objectsInObjects.boolean(name = "isNested")

        fun modify() {
            isNested = true
            objectsInObjects["objects"] = NbtCompound()
        }
    }

    abstract class Properties {
        abstract fun getCompound(): NbtCompound

        val constantReference = ::getCompound.constant()

        fun actions() {
            val compound = getCompound()
            val byte by compound.property().byte(default = 5)
        }
    }

    fun compoundOperators() {
        val otherCompound: NbtCompound = stub()
        val customData: Map<String, NbtElement> = stub()

        val combined = compound + otherCompound
        val copied = NbtCompound(compound)

        val new = NbtCompound(customData)

        compound["someKey"] = new
        compound["aNumber"] = 2.718
        compound["aString"] = "hello"

        val element = compound["someKey"]
    }

    fun elementConversions() {
        val someInteger = 174.nbt
        val someString = "hello".nbt
        val someBoolean = true.nbt

        val anInt = compound["number"]?.int
        val aString = compound["string"]?.string
        val aBoolean = compound["boolean"]?.boolean

        val someStrings: List<String> = stub()
        val strings = someStrings.toNbt()
        compound["strings"] = strings
    }
}
