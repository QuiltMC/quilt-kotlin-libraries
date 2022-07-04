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

package org.quiltmc.qkl.wrapper.minecraft.brigadier.arguments

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.BlockPredicateArgumentType
import net.minecraft.command.argument.BlockStateArgument
import net.minecraft.command.argument.BlockStateArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithName

/**
 * Adds a block predicate argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.blockPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, BlockPredicateArgumentType.BlockPredicate>(
        name,
        BlockPredicateArgumentType(context)
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a block state argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.blockState(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, BlockStateArgument>(
        name,
        BlockStateArgumentType(context)
    )
    argument.action(name)
    then(argument)
}
