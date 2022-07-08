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


import net.minecraft.advancement.Advancement
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.function.LootFunction
import net.minecraft.recipe.Recipe
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see IdentifierArgumentType.getIdentifier
 *
 * @author Cypher121
 */
@JvmName("valueIdentifierArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<IdentifierArgumentType>>.value(): Identifier {
    return IdentifierArgumentType.getIdentifier(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as an [Advancement].
 *
 * @see IdentifierArgumentType.getAdvancementArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asAdvancement(): Advancement {
    return IdentifierArgumentType.getAdvancementArgument(context, name)
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as a [LootCondition].
 *
 * @see IdentifierArgumentType.getPredicateArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asPredicate(): LootCondition {
    return IdentifierArgumentType.getPredicateArgument(context, name)
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as a [LootFunction].
 *
 * @see IdentifierArgumentType.getItemModifierArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asItemModifier(): LootFunction {
    return IdentifierArgumentType.getItemModifierArgument(context, name)
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as a [Recipe].
 *
 * @see IdentifierArgumentType.getRecipeArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<IdentifierArgumentType>
        >.asRecipe(): Recipe<*> {
    return IdentifierArgumentType.getRecipeArgument(context, name)
}

/**
 * Creates an identifier argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> identifier(
    name: String
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<IdentifierArgumentType>> {
    return argument(name, IdentifierArgumentType.identifier())
}
