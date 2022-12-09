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

package org.quiltmc.qkl.library.recipe

import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemStack
import net.minecraft.recipe.*
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.registry.RegistryKeys
import net.minecraft.unmapped.C_gtxamkec
import org.quiltmc.qsl.recipe.api.RecipeManagerHelper
import org.quiltmc.qsl.recipe.api.builder.ShapedRecipeBuilder
import org.quiltmc.qsl.recipe.api.builder.ShapelessRecipeBuilder
import org.quiltmc.qsl.recipe.api.builder.VanillaRecipeBuilders.*

/**
 * Currently Kotlin does not have union types, so this is a workaround.
 * An IngredientLike is one of the following:
 * - [Ingredient]
 * - [ItemStack], [Array&lt;ItemStack&gt;][ItemStack], or [Collection&lt;ItemStack&gt;][ItemStack]
 * - [ItemConvertible], [Array&lt;ItemConvertible&gt;][ItemConvertible],
 *   or [Collection&lt;ItemConvertible&gt;][ItemConvertible]
 * - [TagKey&lt;Item&gt;][TagKey]
 *
 * TODO: Change into a union type if Kotlin (in the future) supports them.
 *
 * @author sschr15
 */
public typealias IngredientLike = Any

/**
 * Coerce the given [ingredient] to an [Ingredient] object.
 *
 * @author sschr15
 */
@Suppress("UNCHECKED_CAST")
public fun coerceIngredient(ingredient: IngredientLike): Ingredient = when (ingredient) {
    is Ingredient -> ingredient
    is ItemStack -> Ingredient.ofStacks(ingredient)
    is ItemConvertible -> Ingredient.ofItems(ingredient)
    is Array<*> -> coerceArrayToIngredient(ingredient)
    is Collection<*> -> coerceArrayToIngredient(ingredient.toTypedArray())
    is TagKey<*> -> if (ingredient.isOfRegistry(RegistryKeys.ITEM)) {
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

/**
 * Create a new [ShapedRecipe] with the given information.
 * The [group] string is used for finding the recipe in the recipe book.
 * The [pattern] is a newline-separated string of rows.
 * The [result] is the resulting [ItemStack] of the recipe.
 * Any [ingredients] can be listed in the form `char to ingredient`
 *
 * Example representing the hopper recipe:
 * ```kt
 * shapedRecipe(
 *     Identifier("minecraft", "hopper"),
 *     "", // group is defaulted to empty string
 *     """
 *         I I
 *         ICI
 *          IZ
 *     """.trimIndent(),
 *     ItemStack(Item.HOPPER, 1),
 *     'I' to Items.IRON_INGOT,
 *     'C' to Items.CHEST,
 *     'Z' to Items.EMPTY // Prevent trailing whitespace
 * )
 * ```
 */
public fun shapedRecipe(
    id: Identifier,
    group: String,
    pattern: String,
    result: ItemStack,
    vararg ingredients: Pair<Char, IngredientLike>
): ShapedRecipe {
    val builder = ShapedRecipeBuilder(*pattern.split("\n").toTypedArray())
    ingredients.forEach { (key, ingredient) ->
        builder.ingredient(key, coerceIngredient(ingredient))
    }
    builder.output(result)
    return builder.build(id, group)
}

/**
 * Create a new [ShapelessRecipe] with the given information.
 * The [group] string is used for finding the recipe in the recipe book.
 * The [result] is the resulting [ItemStack] of the recipe.
 * The [ingredients] are any number of [IngredientLike] objects.
 *
 * Example representing flint and steel:
 * ```kt
 * shapelessRecipe(
 *     Identifier("minecraft", "flint_and_steel"),
 *     "", // group is defaulted to empty string
 *     ItemStack(Item.FLINT_AND_STEEL, 1),
 *     Items.IRON_INGOT,
 *     Items.FLINT
 * )
 */
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
 * The [baseId] is a base identifier for the recipe, with `smelting`, `smoking`, and `campfire`
 * appended to it to create the three recipe identifiers.
 * The [group] string is used for finding the recipe in the recipe book.
 * The [input] is the input [ingredient][IngredientLike] of the recipe.
 * The [result] is the resulting [ItemStack] of the recipe.
 * The [experience] is the amount of experience gained when cooking the recipe.
 *
 * The cook time parameters are calculated in a special way:
 * - [cookTime] is the time in ticks it takes to cook the item in a furnace.
 *   It defaults to 200, Minecraft's default of 10 seconds.
 * - [smokerCookTime] is the time in ticks it takes to cook the item in a smoker.
 *   It defaults to half the [cookTime], rounded down.
 * - [campfireCookTime] is the time in ticks it takes to cook the item in a campfire.
 *   It defaults to triple the [cookTime].
 *
 * @author sschr15
 */
@Suppress("MagicNumber")
public fun foodCookingRecipe(
    baseId: Identifier,
    group: String,
    input: IngredientLike,
    result: ItemStack,
    category: C_gtxamkec,
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
        category,
        result,
        experience,
        cookTime
    )

    val smokingRecipe = smokingRecipe(
        smokingId,
        group,
        category,
        ingredient,
        result,
        experience,
        smokerCookTime
    )

    val campfireRecipe = campfireCookingRecipe(
        campfireId,
        group,
        category,
        ingredient,
        result,
        experience,
        campfireCookTime
    )

    return arrayOf(smeltingRecipe, smokingRecipe, campfireRecipe)
}

/**
 * @see RecipeManagerHelper.registerStaticRecipe
 *
 * @author sschr15
 */
public fun registerStaticRecipes(vararg recipes: Recipe<*>) {
    recipes.forEach(RecipeManagerHelper::registerStaticRecipe)
}

/**
 * @see RecipeManagerHelper.registerStaticRecipe
 *
 * @author sschr15
 */
@Suppress("UNCHECKED_CAST") // The implementation is defined as returning the input
public fun <C : Inventory, R : Recipe<C>> registerStaticRecipe(recipe: R): R =
    RecipeManagerHelper.registerStaticRecipe(recipe) as R
