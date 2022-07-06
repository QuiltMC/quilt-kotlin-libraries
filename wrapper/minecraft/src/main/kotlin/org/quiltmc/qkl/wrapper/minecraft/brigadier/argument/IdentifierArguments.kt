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

/*
 * Preserve binary compatibility when moving extensions between files
 */
@file:JvmMultifileClass
@file:JvmName("ArgumentsKt")

package org.quiltmc.qkl.wrapper.minecraft.brigadier.argument

import com.mojang.brigadier.builder.ArgumentBuilder
import net.minecraft.advancement.Advancement
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.function.LootFunction
import net.minecraft.recipe.Recipe
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

@JvmName("valueIdentifierArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<IdentifierArgumentType>>.value(): Identifier =
    IdentifierArgumentType.getIdentifier(context.assumeSourceNotUsed(), name)

@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asAdvancement(): Advancement =
    IdentifierArgumentType.getAdvancementArgument(context, name)

@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asPredicate(): LootCondition =
    IdentifierArgumentType.getPredicateArgument(context, name)

@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asItemModifier(): LootFunction =
    IdentifierArgumentType.getItemModifierArgument(context, name)

@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asRecipe(): Recipe<*> =
    IdentifierArgumentType.getRecipeArgument(context, name)

/**
 * Adds an identifier argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.identifier(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<IdentifierArgumentType>>
) {
    argument(name, IdentifierArgumentType.identifier(), action)
}