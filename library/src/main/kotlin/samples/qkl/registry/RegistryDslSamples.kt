/*
 * Copyright 2023 QuiltMC
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
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.registry.*

/**
 * Container for samples. Functions can be referenced
 * by their full name in the @sample KDoc tag to
 * inject the body as a documentation sample.
 *
 * @author Peanuuutz
 */
@Suppress("unused", "UNUSED_VARIABLE")
private object RegistryDslSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    class TestItem(settings: Settings) : Item(settings)
    class TestBlock(settings: Settings) : Block(settings)

    fun sampleRegisterGlobally() {
        val item: TestItem = stub()
        val identifier: Identifier = stub()

        item withId identifier toRegistry Registries.ITEM
    }

    fun sampleRegisterWithRegistry() {
        val item: TestItem = stub()
        val identifier = Identifier("my", "item1")

        Registries.ITEM("my") {
            item withId identifier // Goes under `my:item1`
            item withId "my:item2" // Goes under `my:item2`
            item withId "item3" // Goes under `my:item3`
        }

        Registries.ITEM {
            item withId identifier // Goes under `my:item1`
            item withId "my:item2" // Goes under `my:item2`
            item withId "item3" // Goes under `minecraft:item3`
        }
    }

    fun sampleRegisterWithScope() {
        val item: TestItem = stub()
        val identifier = Identifier("my", "item1")

        registryScope("my") {
            Registries.ITEM {
                item withId identifier // Goes under `my:item1`
                item withId "my:item2" // Goes under `my:item2`
                item withId "item3" // Goes under `my:item3`
            }

            item withPath "item3" toRegistry Registries.ITEM
        }
    }

    fun sampleRegistryScopeDelegate() {
        val scope: RegistryScope = stub()

        val item: TestItem by scope.provide {
            val instance: TestItem = stub()

            instance withPath "item" toRegistry Registries.ITEM
        }
    }

    fun sampleRegistryActionDelegate() {
        // GlobalRegistrar.kt
        val scope: RegistryScope = stub()

        // Blocks.kt
        val blocks = scope.action(Registries.BLOCK)

        val myBlock by blocks.provide("block") { TestBlock(stub()) }

        // Items.kt
        val items = scope.action(Registries.ITEM)

        val myItem: TestItem by items.provide("item") { TestItem(stub()) }
        val myBlockItem by items.provideBlockItem("block") { myBlock } // Shortcut
    }

    fun sampleScopeWithResult() {
        val item: TestItem = with(RegistryAction("id", Registries.ITEM)) {
            TestItem(stub()) withId "test"
        }
    }
}
