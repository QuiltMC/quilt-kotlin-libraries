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
@file:JvmName("QuiltArgumentsKt")

package org.quiltmc.qkl.wrapper.minecraft.brigadier.arguments

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithAccessor
import org.quiltmc.qsl.command.api.EnumArgumentType
import kotlin.reflect.KClass

/**
 * Adds an enum argument allowing all values of the
 * specified [enum] type with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Cypher121
 */
public fun <S, T : Enum<T>> ArgumentBuilder<S, *>.enum(
    name: String,
    type: KClass<T>,
    action: RequiredArgumentActionWithAccessor<S, T>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        EnumArgumentType.enumConstant(type.java)
    )
    argument.action {
        EnumArgumentType.getEnumConstant(
            unsafeSource(),
            name,
            type.java
        )
    }
    then(argument)
}

/**
 * Adds a string argument allowing only values specified
 * in [values] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Cypher121
 */
public fun <S> ArgumentBuilder<S, *>.enum(
    name: String,
    values: List<String>,
    action: RequiredArgumentActionWithAccessor<S, String>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        EnumArgumentType(*values.toTypedArray())
    )
    argument.action {
        EnumArgumentType.getEnum(unsafeSource(), name)
    }
    then(argument)
}

/**
 * Adds an enum argument allowing only values specified
 * in [values] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * @author Cypher121
 */
@JvmName("enumAllowedSublist")
public inline fun <S, reified T : Enum<T>> ArgumentBuilder<S, *>.enum(
    name: String,
    values: List<T>,
    action: RequiredArgumentActionWithAccessor<S, T>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        EnumArgumentType(*values.map { it.name }.toTypedArray())
    )
    argument.action {
        //doesn't use the function due to inline
        @Suppress("UNCHECKED_CAST")
        val constantName = EnumArgumentType.getEnum(
            this as CommandContext<ServerCommandSource>,
            name
        )

        values.first { it.name.equals(constantName, ignoreCase = true) }
    }
    then(argument)
}

/**
 * Only safe to use as it's known that the source
 * is not used by [EnumArgumentType] methods.
 */
@Suppress("UNCHECKED_CAST")
private fun CommandContext<*>.unsafeSource() =
    this as CommandContext<ServerCommandSource>
