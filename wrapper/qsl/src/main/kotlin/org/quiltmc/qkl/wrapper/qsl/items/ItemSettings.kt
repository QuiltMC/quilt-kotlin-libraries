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

package org.quiltmc.qkl.wrapper.qsl.items

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.FoodComponent
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Rarity
import org.quiltmc.qsl.item.setting.api.CustomDamageHandler
import org.quiltmc.qsl.item.setting.api.CustomItemSetting
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings

public fun itemSettingsOf(
    maxCount: Int = 64,
    maxDamage: Int = 0,
    recipeRemainder: Item? = null,
    group: ItemGroup? = null,
    rarity: Rarity = Rarity.COMMON,
    foodComponent: FoodComponent? = null,
    fireproof: Boolean = false,
    customDamage: CustomDamageHandler? = null,
    equipmentSlot: ((ItemStack) -> EquipmentSlot)? = null,
    otherSettings: List<Pair<CustomItemSetting<*>, *>> = emptyList()
): QuiltItemSettings = buildItemSettings {
    maxCount(maxCount)
    maxDamage(maxDamage)
    if (recipeRemainder != null) recipeRemainder(recipeRemainder)
    if (group != null) group(group)
    rarity(rarity)
    if (foodComponent != null) food(foodComponent)
    if (fireproof) fireproof()
    if (customDamage != null) customDamage(customDamage)
    if (equipmentSlot != null) equipmentSlot(equipmentSlot)
    otherSettings.forEach { (setting, value) ->
        @Suppress("UNCHECKED_CAST")
        customSetting(setting as CustomItemSetting<Any>, value)
    }
}

public fun buildItemSettings(block: QuiltItemSettings.() -> Unit): QuiltItemSettings =
    QuiltItemSettings().apply(block)

public fun <T> QuiltItemSettings.customSetting(value: T, setting: CustomItemSetting<T>) {
    customSetting(setting, value)
}
