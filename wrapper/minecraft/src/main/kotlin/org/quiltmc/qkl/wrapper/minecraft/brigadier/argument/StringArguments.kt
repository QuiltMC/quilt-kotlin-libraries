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

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

@JvmName("valueStringArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<StringArgumentType>>.value(): String =
    StringArgumentType.getString(context, name)

/**
 * Adds a string argument with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.string(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<StringArgumentType>>
) {
    argument(name, StringArgumentType.string(), action)
}

/**
 * Adds a greedy string argument with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.greedyString(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<StringArgumentType>>
) {
    argument(name, StringArgumentType.greedyString(), action)
}

/**
 * Adds a word argument with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.word(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<StringArgumentType>>
) {
    argument(name, StringArgumentType.word(), action)
}
