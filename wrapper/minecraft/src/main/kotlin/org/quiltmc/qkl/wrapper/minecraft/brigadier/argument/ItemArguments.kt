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
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.ItemPredicateArgumentType
import net.minecraft.command.argument.ItemSlotArgumentType
import net.minecraft.command.argument.ItemStackArgument
import net.minecraft.command.argument.ItemStackArgumentType
import net.minecraft.item.ItemStack
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.function.Predicate

/**
 * Reads the [ItemStack] predicate value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ItemPredicateArgumentType.getItemPredicate
 *
 * @author Cypher121
 */
@JvmName("valueItemPredicateArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ItemPredicateArgumentType>>.value(): Predicate<ItemStack> =
    ItemPredicateArgumentType.getItemPredicate(context.assumeSourceNotUsed(), name)

/**
 * Reads the integer value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ItemSlotArgumentType.getItemSlot
 *
 * @author Cypher121
 */
@JvmName("valueItemSlotArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ItemSlotArgumentType>>.value(): Int =
    ItemSlotArgumentType.getItemSlot(context.assumeSourceNotUsed(), name)

/**
 * Reads the [ItemStackArgument] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ItemStackArgumentType.getItemStackArgument
 *
 * @author Cypher121
 */
@JvmName("valueItemStackArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ItemStackArgumentType>>.value(): ItemStackArgument =
    ItemStackArgumentType.getItemStackArgument(context, name)

/**
 * Adds an item predicate argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.itemPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ItemPredicateArgumentType>>
) {
    argument(name, ItemPredicateArgumentType.itemPredicate(context), action)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.itemSlot(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ItemSlotArgumentType>>
) {
    argument(name, ItemSlotArgumentType.itemSlot(), action)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.itemStack(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ItemStackArgumentType>>
) {
    argument(name, ItemStackArgumentType.itemStack(context), action)
}
