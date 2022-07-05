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
 *
 * Extensions in this file are QSL specific.
 * TODO move to qsl wrapper module. Requires wrapper/qsl to depend on wrapper/minecraft
 */
@file:JvmMultifileClass
@file:JvmName("QuiltArgumentsKt")

package org.quiltmc.qkl.wrapper.minecraft.brigadier.arguments

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithAccessor
import org.quiltmc.qkl.wrapper.minecraft.brigadier.assumeSourceNotUsed
import org.quiltmc.qsl.command.api.EnumArgumentType
import kotlin.reflect.KClass

/**
 * Adds an enum argument allowing all values of the
 * specified enum [type] [T] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * Enum values of [T] must have [names][Enum.name] that
 * are distinct when case is ignored.
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
            assumeSourceNotUsed(),
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
 * All elements of [values] must be distinct
 * when case is ignored.
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
        EnumArgumentType.getEnum(assumeSourceNotUsed(), name)
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
 * All elements of [values] must have [names][Enum.name]
 * that are distinct when case is ignored
 *
 * @author Cypher121
 */
@JvmName("enumAllowedSublist")
public fun <S, T : Enum<T>> ArgumentBuilder<S, *>.enum(
    name: String,
    values: List<T>,
    action: RequiredArgumentActionWithAccessor<S, T>
) {
    enum(name, values.associateBy(Enum<*>::name), action)
}

/**
 * Adds an enum argument allowing values specified by
 * keys of [values] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 * The accessor returns the value from [values] matching
 * the key provided to the command
 *
 * Keys of [values] must be distinct when case is ignored.
 *
 * @author Cypher121
 */
public fun <S, T> ArgumentBuilder<S, *>.enum(
    name: String,
    values: Map<String, T>,
    action: RequiredArgumentActionWithAccessor<S, T>
) {
    val lowercaseValues = values.mapKeys { it.key.lowercase() }

    enum(name, values.keys.toList()) { getKey ->
        action {
            lowercaseValues.getValue(getKey().lowercase())
        }
    }
}
