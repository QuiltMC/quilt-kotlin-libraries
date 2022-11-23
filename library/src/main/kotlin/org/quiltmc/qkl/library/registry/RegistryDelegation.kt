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

package org.quiltmc.qkl.library.registry

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item

/**
 * Provides a [lazy][Lazy] registration using [scope][this].
 *
 * User should define actual registration inside [provider].
 *
 * When getting [value][Lazy.value] for the first time, the corresponding object is registered.
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegistryScopeDelegate
 */
public fun <T : Any> RegistryScope.provide(provider: RegistryScope.() -> T): Lazy<T> {
    return lazy { provider() }
}

/**
 * Provides a [lazy][Lazy] registration using [action][this].
 *
 * When getting [value][Lazy.value] for the first time, the corresponding object is registered.
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegistryActionDelegate
 */
public fun <T : Any> RegistryAction<in T>.provide(path: String, provider: () -> T): Lazy<T> {
    return lazy { provider() withId path }
}

/**
 * Provides a [lazy][Lazy] registration using [action][this].
 *
 * When getting [value][Lazy.value] for the first time, the corresponding object is registered.
 *
 * This is a shortcut for default [BlockItem] registration.
 */
public fun RegistryAction<Item>.provideBlockItem(path: String, provider: () -> Block): Lazy<BlockItem> {
    return lazy { BlockItem(provider(), Item.Settings()) withId path }
}
