/*
 * Copyright 2023 The Quilt Project
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

@file:Suppress("unused")

package org.quiltmc.qkl.library.recipe

import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.recipe.api.RecipeLoadingEvents.*

/**
 * Called when recipes are loaded. This is the time to register your own recipes.
 * It is called before [onModifyRecipes] and [onRemoveRecipes].
 */
public fun EventRegistration.onAddRecipes(
    consumer: (handler: AddRecipesCallback.RecipeHandler) -> Unit
) {
    ADD.register(AddRecipesCallback(consumer))
}

/**
 * Called when recipes are modified. This is the time to modify any recipes.
 * It is called after [onAddRecipes] but before [onRemoveRecipes].
 */
public fun EventRegistration.onModifyRecipes(
    consumer: (handler: ModifyRecipesCallback.RecipeHandler) -> Unit
) {
    MODIFY.register(ModifyRecipesCallback(consumer))
}

/**
 * Called when recipes are removed. This is the time to remove any recipes.
 * It is called after [onAddRecipes] and [onModifyRecipes].
 */
public fun EventRegistration.onRemoveRecipes(
    consumer: (handler: RemoveRecipesCallback.RecipeHandler) -> Unit
) {
    REMOVE.register(RemoveRecipesCallback(consumer))
}
