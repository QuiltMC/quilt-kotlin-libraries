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
import net.minecraft.command.argument.ItemPredicateArgumentType
import net.minecraft.command.argument.ItemSlotArgumentType
import net.minecraft.command.argument.ItemStackArgument
import net.minecraft.command.argument.ItemStackArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentAction

/**
 * Adds an item predicate argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.itemPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<
            S,
            ItemPredicateArgumentType.ItemPredicateArgument
            >(
        name,
        ItemPredicateArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.itemSlot(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        ItemSlotArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.itemStack(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ItemStackArgument>(
        name,
        ItemStackArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}
