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
import net.minecraft.command.argument.NbtCompoundArgumentType
import net.minecraft.command.argument.NbtElementArgumentType
import net.minecraft.command.argument.NbtPathArgumentType
import net.minecraft.command.argument.NbtPathArgumentType.NbtPath
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the [NbtCompound] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see NbtCompoundArgumentType.getNbtCompound
 *
 * @author Cypher121
 */
@JvmName("valueNbtCompoundArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<NbtCompoundArgumentType>>.value(): NbtCompound =
    NbtCompoundArgumentType.getNbtCompound(context, name)

/**
 * Reads the [NbtElement] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see NbtElementArgumentType.getNbtElement
 *
 * @author Cypher121
 */
@JvmName("valueNbtElementArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<NbtElementArgumentType>>.value(): NbtElement =
    NbtElementArgumentType.getNbtElement(context, name)

/**
 * Reads the [NbtPath] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see NbtPathArgumentType.getNbtPath
 *
 * @author Cypher121
 */
@JvmName("valueNbtPathArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<NbtPathArgumentType>>.value(): NbtPath =
    NbtPathArgumentType.getNbtPath(context.assumeSourceNotUsed(), name)

/**
 * Adds a nbt compound argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.nbtCompound(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<NbtCompoundArgumentType>>
) {
    argument(name, NbtCompoundArgumentType.nbtCompound(), action)
}

/**
 * Adds an NBT element argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.nbtElement(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<NbtElementArgumentType>>
) {
    argument(name, NbtElementArgumentType.nbtElement(), action)
}

/**
 * Adds an NBT path argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.nbtPath(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<NbtPathArgumentType>>
) {
    argument(name, NbtPathArgumentType.nbtPath(), action)
}
