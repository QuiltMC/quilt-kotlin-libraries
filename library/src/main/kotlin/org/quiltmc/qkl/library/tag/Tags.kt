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

package org.quiltmc.qkl.library.tag

import net.minecraft.tag.TagKey
import net.minecraft.util.Holder
import net.minecraft.util.registry.Registry
import org.quiltmc.qsl.tag.api.TagRegistry
import org.quiltmc.qsl.tag.api.TagRegistry.TagValues
import org.quiltmc.qsl.tag.api.TagType

/**
 * Get all tags for a given registry.
 */
public fun <T> Registry<T>.getTags(type: TagType = TagType.NORMAL): List<TagValues<T>> =
    TagRegistry.stream(key, type).toList()

/**
 * Get all instances of objects registered with the given tag.
 */
public fun <T> getTag(key: TagKey<T>): Collection<Holder<T>> = TagRegistry.getTag(key)

/**
 * Get a particular tag value for a given tag.
 */
public operator fun <T> TagValues<T>.get(index: Int): Holder<T> = values.toList()[index]
