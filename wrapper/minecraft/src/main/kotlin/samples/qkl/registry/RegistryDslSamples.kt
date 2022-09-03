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

package samples.qkl.registry

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qkl.wrapper.minecraft.registry.RegistryScope
import org.quiltmc.qkl.wrapper.minecraft.registry.invoke
import org.quiltmc.qkl.wrapper.minecraft.registry.provide
import org.quiltmc.qkl.wrapper.minecraft.registry.provideBlockItem
import org.quiltmc.qkl.wrapper.minecraft.registry.withId
import org.quiltmc.qkl.wrapper.minecraft.registry.registryScope

/**
 * Container for samples. Functions can be referenced
 * by their full name in the @sample KDoc tag to
 * inject the body as a documentation sample.
 *
 * @author Peanuuutz
 */
@Suppress("unused")
private object RegistryDslSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    fun sampleRegisterGlobally() {
        val item: Item = stub()
        val identifier: Identifier = stub()

        item withId identifier toRegistry Registry.ITEM
    }

    fun sampleRegisterWithRegistry() {
        val item: Item = stub()
        val identifier = Identifier("my", "item1")

        Registry.ITEM("my") {
            item withId identifier // Goes under `my:item1`
            item withId "my:item2" // Goes under `my:item2`
            item withId "item3" // Goes under `my:item3`
        }

        Registry.ITEM {
            item withId identifier // Goes under `my:item1`
            item withId "my:item2" // Goes under `my:item2`
            item withId "item3" // Goes under `minecraft:item3`
        }
    }

    fun sampleRegisterWithScope() {
        val item: Item = stub()
        val identifier = Identifier("my", "item1")

        registryScope("my") {
            Registry.ITEM {
                item withId identifier // Goes under `my:item1`
                item withId "my:item2" // Goes under `my:item2`
                item withId "item3" // Goes under `my:item3`
            }

            item withPath "item3" toRegistry Registry.ITEM
        }
    }

    fun sampleRegistryScopeDelegate() {
        val scope: RegistryScope = stub()

        val item: Item by scope.provide {
            val instance: Item = stub()

            instance withPath "item" toRegistry Registry.ITEM
        }
    }

    fun sampleRegistryActionDelegate() {
        // GlobalRegistrar.kt
        val scope: RegistryScope = stub()

        // Blocks.kt
        val blocks = scope.action(Registry.BLOCK)

        val myBlock by blocks.provide("block") { Block(stub()) }

        // Items.kt
        val items = scope.action(Registry.ITEM)

        val myItem by items.provide("item") { Item(stub()) }
        val myBlockItem by items.provideBlockItem("block") { myBlock } // Shortcut
    }
}
