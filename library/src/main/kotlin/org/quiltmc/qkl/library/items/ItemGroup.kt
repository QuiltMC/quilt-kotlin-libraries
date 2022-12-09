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

package org.quiltmc.qkl.library.items

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.quiltmc.qsl.item.group.api.QuiltItemGroup

/**
 * Create an [ItemGroup] with the given [id] and optional other information.
 * - If [displayText] is not null, it will be used as the display name for the group.
 * - If [iconSupplier] is not null, it will be used to create the icon for the group.
 * - If [itemListModifier] is not null, it will be used to modify the items
 *   shown in the group.
 *
 * @author sschr15
 */
public fun itemGroupOf(
    id: Identifier,
    displayText: Text? = null,
    iconSupplier: (() -> ItemStack)? = null,
    itemListModifier: ((MutableList<ItemStack>) -> Unit)? = null,
): ItemGroup = buildItemGroup(id) {
    if (displayText != null) {
        displayText(displayText)
    }
    if (iconSupplier != null) {
        icon(iconSupplier)
    }
    if (itemListModifier != null) {
        appendItems(itemListModifier)
    }
}

/**
 * [Build][QuiltItemGroup.Builder] an [ItemGroup] with the given [id].
 */
public fun buildItemGroup(id: Identifier, block: QuiltItemGroup.Builder.() -> Unit): ItemGroup {
    val builder = QuiltItemGroup.builder(id)
    builder.block()
    return builder.build()
}
