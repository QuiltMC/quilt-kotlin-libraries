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

import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.BlockPredicateArgumentType
import net.minecraft.command.argument.BlockStateArgument
import net.minecraft.command.argument.BlockStateArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.function.Predicate

/**
 * Reads the block predicate value of the argument in
 * the receiver [ArgumentReader].
 *
 * @see BlockPredicateArgumentType.getBlockPredicate
 *
 * @author Cypher121
 */
@JvmName("valueBlockPredicateArg")
@BrigadierDsl
public fun DefaultArgumentReader<BlockPredicateArgumentType>.value(): Predicate<CachedBlockPosition> {
    return BlockPredicateArgumentType.getBlockPredicate(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [BlockStateArgument] value of the argument in
 * the receiver [ArgumentReader].
 *
 * @see BlockStateArgumentType.getBlockState
 *
 * @author Cypher121
 */
@JvmName("valueBlockStateArg")
@BrigadierDsl
public fun DefaultArgumentReader<BlockStateArgumentType>.value(): BlockStateArgument {
    return BlockStateArgumentType.getBlockState(context.assumeSourceNotUsed(), name)
}

/**
 * Creates a block predicate argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> blockPredicate(
    name: String,
    context: CommandBuildContext
): DefaultArgumentConstructor<S, BlockPredicateArgumentType> {
    return argument(name, BlockPredicateArgumentType.blockPredicate(context))
}

/**
 * Creates a block state argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> blockState(
    name: String,
    context: CommandBuildContext
): DefaultArgumentConstructor<S, BlockStateArgumentType> {
    return argument(name, BlockStateArgumentType.blockState(context))
}
