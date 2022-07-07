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
import com.mojang.brigadier.context.CommandContext
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
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<BlockPredicateArgumentType>
        >.value(): Predicate<CachedBlockPosition> =
    BlockPredicateArgumentType.getBlockPredicate(context.assumeSourceNotUsed(), name)

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
public fun ArgumentReader<*, DefaultArgumentDescriptor<BlockStateArgumentType>>.value(): BlockStateArgument =
    BlockStateArgumentType.getBlockState(context.assumeSourceNotUsed(), name)

/**
 * Adds a block predicate argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.blockPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<BlockPredicateArgumentType>>
) {
    argument(name, BlockPredicateArgumentType.blockPredicate(context), action)
}

/**
 * Adds a block state argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.blockState(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<BlockStateArgumentType>>
) {
    argument(name, BlockStateArgumentType.blockState(context), action)
}
