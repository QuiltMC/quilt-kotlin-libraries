/*
 * Copyright 2024 The Quilt Project
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

package org.quiltmc.qkl.library.brigadier.argument


import net.minecraft.advancement.AdvancementHolder
import net.minecraft.command.argument.IdentifierArgumentType
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.function.LootFunction
import net.minecraft.recipe.RecipeHolder
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.unmapped.C_jjjbxeeq
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.brigadier.*

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
public fun DefaultArgumentReader<IdentifierArgumentType>.value(): Identifier {
    return IdentifierArgumentType.getIdentifier(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as an [AdvancementHolder].
 *
 * @see IdentifierArgumentType.getAdvancementArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                IdentifierArgumentType
                >
        >.asAdvancement(): AdvancementHolder {
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
        DefaultArgumentDescriptor<
                IdentifierArgumentType
                >
        >.asPredicate(): LootCondition {
    return C_jjjbxeeq.method_58488(context, name).value()
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
        DefaultArgumentDescriptor<
                IdentifierArgumentType
                >
        >.asItemModifier(): LootFunction {
    return C_jjjbxeeq.method_58485(context, name).value()
}

/**
 * Reads the [Identifier] value from the
 * argument in the receiver [ArgumentReader]
 * as a [RecipeHolder].
 *
 * @see IdentifierArgumentType.getRecipeArgument
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                IdentifierArgumentType
                >
        >.asRecipe(): RecipeHolder<*> {
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
): DefaultArgumentConstructor<S, IdentifierArgumentType> {
    return argument(name, IdentifierArgumentType.identifier())
}
