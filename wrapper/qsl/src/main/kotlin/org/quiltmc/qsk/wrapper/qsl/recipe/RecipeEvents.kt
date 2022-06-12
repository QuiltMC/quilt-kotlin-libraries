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

@file:Suppress("unused")

package org.quiltmc.qsk.wrapper.qsl.recipe

import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.recipe.api.RecipeLoadingEvents.*

public fun EventRegistration.onAddRecipes(
    consumer: (handler: AddRecipesCallback.RecipeHandler) -> Unit
) {
    ADD.register(AddRecipesCallback(consumer))
}

public fun EventRegistration.onModifyRecipes(
    consumer: (handler: ModifyRecipesCallback.RecipeHandler) -> Unit
) {
    MODIFY.register(ModifyRecipesCallback(consumer))
}

public fun EventRegistration.onRemoveRecipes(
    consumer: (handler: RemoveRecipesCallback.RecipeHandler) -> Unit
) {
    REMOVE.register(RemoveRecipesCallback(consumer))
}
