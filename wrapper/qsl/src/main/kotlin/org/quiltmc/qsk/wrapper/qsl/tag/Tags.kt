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

package org.quiltmc.qsk.wrapper.qsl.tag

import net.minecraft.tag.TagKey
import net.minecraft.util.Holder
import net.minecraft.util.registry.Registry
import org.quiltmc.qsl.tag.api.TagRegistry
import org.quiltmc.qsl.tag.api.TagRegistry.TagValues
import org.quiltmc.qsl.tag.api.TagType

public fun <T> Registry<T>.getTags(type: TagType = TagType.NORMAL): List<TagValues<T>> =
    TagRegistry.stream(key, type).toList()

public fun <T> getTag(key: TagKey<T>): Collection<Holder<T>> = TagRegistry.getTag(key)

public operator fun <T> TagValues<T>.get(index: Int): Holder<T> = values.toList()[index]
