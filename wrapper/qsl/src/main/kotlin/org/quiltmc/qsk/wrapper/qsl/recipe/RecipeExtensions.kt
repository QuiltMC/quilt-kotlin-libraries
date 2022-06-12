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

package org.quiltmc.qsk.wrapper.qsl.recipe

import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.recipe.*
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qsl.recipe.api.RecipeManagerHelper
import org.quiltmc.qsl.recipe.api.builder.ShapedRecipeBuilder
import org.quiltmc.qsl.recipe.api.builder.ShapelessRecipeBuilder
import org.quiltmc.qsl.recipe.api.builder.VanillaRecipeBuilders.*

/**
 * Currently Kotlin does not have union types, so this is a workaround.
 */
public typealias IngredientLike = Any

@Suppress("UNCHECKED_CAST")
public fun coerceIngredient(ingredient: IngredientLike): Ingredient = when (ingredient) {
    is Ingredient -> ingredient
    is Array<*> -> coerceArrayToIngredient(ingredient)
    is Collection<*> -> coerceArrayToIngredient(ingredient.toTypedArray())
    is TagKey<*> -> if (ingredient.isOfRegistry(Registry.ITEM_KEY)) {
        Ingredient.ofTag(ingredient as TagKey<Item>)
    } else {
        error("Unsupported ingredient type")
    }
    else -> error("Unsupported ingredient type")
}

@Suppress("UNCHECKED_CAST")
private fun coerceArrayToIngredient(array: Array<*>): Ingredient = if (array.isEmpty()) {
    Ingredient.EMPTY // Prevent ArrayIndexOutOfBoundsException
} else {
    when (array[0]) {
        is ItemConvertible -> Ingredient.ofItems(*array as Array<ItemConvertible>)
        is ItemStack -> Ingredient.ofStacks(*array as Array<ItemStack>)
        else -> error("Unsupported ingredient type")
    }
}

public fun shapedRecipe(
    id: Identifier,
    group: String,
    pattern: String,
    vararg ingredients: Pair<Char, IngredientLike>
): ShapedRecipe {
    val builder = ShapedRecipeBuilder(*pattern.split("\n").toTypedArray())
    ingredients.forEach { (key, ingredient) ->
        builder.ingredient(key, coerceIngredient(ingredient))
    }
    return builder.build(id, group)
}

public fun shapelessRecipe(
    id: Identifier,
    group: String,
    result: ItemStack,
    vararg ingredients: IngredientLike
): ShapelessRecipe {
    val builder = ShapelessRecipeBuilder(result)
    ingredients.forEach { ingredient ->
        builder.ingredient(coerceIngredient(ingredient))
    }
    return builder.build(id, group)
}

/**
 * A more general recipe builder creating three recipes for the primary ways of cooking.
 * These three are returned in an array, in the order of:
 * - SmeltingRecipe
 * - SmokingRecipe
 * - CampfireCookingRecipe
 *
 * The cook time parameters are calculated in a special way:
 * - [cookTime] is the time in ticks it takes to cook the item in a furnace.
 *   It defaults to 200, Minecraft's default of 10 seconds.
 * - [smokerCookTime] is the time in ticks it takes to cook the item in a smoker.
 *   It defaults to half the [cookTime], rounded down.
 * - [campfireCookTime] is the time in ticks it takes to cook the item in a campfire.
 *   It defaults to triple the [cookTime].
 */
@Suppress("MagicNumber")
public fun foodCookingRecipe(
    baseId: Identifier,
    group: String,
    input: IngredientLike,
    result: ItemStack,
    experience: Float = 0.0f,
    cookTime: Int = 200,
    smokerCookTime: Int = cookTime / 2,
    campfireCookTime: Int = cookTime * 3
): Array<AbstractCookingRecipe> {
    val smeltingId = Identifier(baseId.namespace, "${baseId.path}_smelting")
    val smokingId = Identifier(baseId.namespace, "${baseId.path}_smoking")
    val campfireId = Identifier(baseId.namespace, "${baseId.path}_campfire")

    val ingredient = coerceIngredient(input)

    val smeltingRecipe = smeltingRecipe(
        smeltingId,
        group,
        ingredient,
        result,
        experience,
        cookTime
    )

    val smokingRecipe = smokingRecipe(
        smokingId,
        group,
        ingredient,
        result,
        experience,
        smokerCookTime
    )

    val campfireRecipe = campfireCookingRecipe(
        campfireId,
        group,
        ingredient,
        result,
        experience,
        campfireCookTime
    )

    return arrayOf(smeltingRecipe, smokingRecipe, campfireRecipe)
}

/**
 * @see RecipeManagerHelper.registerStaticRecipe
 */
public fun registerStaticRecipes(vararg recipes: Recipe<*>) {
    recipes.forEach(RecipeManagerHelper::registerStaticRecipe)
}

/**
 * @see RecipeManagerHelper.registerStaticRecipe
 */
@Suppress("UNCHECKED_CAST") // The implementation is defined as returning the input
public fun <C : Inventory, R : Recipe<C>> registerStaticRecipe(recipe: R): R =
    RecipeManagerHelper.registerStaticRecipe(recipe) as R
