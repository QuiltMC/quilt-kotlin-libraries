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

package org.quiltmc.qkl.wrapper.minecraft.brigadier.argument

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import org.quiltmc.qsl.command.api.EnumArgumentType
import kotlin.reflect.KClass

/**
 * Reads the enum value of type [T] from the argument in
 * the receiver [ArgumentReader].
 *
 * @see EnumArgumentType.getEnumConstant
 *
 * @author Cypher121
 */
@JvmName("valueEnumClassArg")
@BrigadierDsl
public fun <T : Enum<T>> ArgumentReader<*, TypedEnumArgumentDescriptor<T>>.value(): T =
    EnumArgumentType.getEnumConstant(
        context.assumeSourceNotUsed(),
        name,
        argumentDescriptor.type
    )

/**
 * Reads the string value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see EnumArgumentType.getEnum
 *
 * @author Cypher121
 */
@JvmName("valueEnumStringArg")
@BrigadierDsl
public fun ArgumentReader<*, StringEnumArgumentDescriptor>.value(): String =
    EnumArgumentType.getEnum(
        context.assumeSourceNotUsed(),
        name
    )

/**
 * Reads the string value from the argument in
 * the receiver [ArgumentReader] and converts it
 * to a value of type [T] according to the map
 * provided when creating the argument.
 *
 * @see EnumArgumentType.getEnum
 *
 * @author Cypher121
 */
@JvmName("valueEnumMappedArg")
@BrigadierDsl
public fun <T> ArgumentReader<*, MappedStringEnumArgumentDescriptor<T>>.value(): T {
    val key = EnumArgumentType.getEnum(
        context.assumeSourceNotUsed(),
        name
    )

    return argumentDescriptor.map.getValue(key.lowercase())
}

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
@BrigadierDsl
public fun <S, T : Enum<T>> ArgumentBuilder<S, *>.enum(
    name: String,
    type: KClass<T>,
    action: RequiredArgumentAction<S, TypedEnumArgumentDescriptor<T>>
) {
    argument(
        name,
        EnumArgumentType.enumConstant(type.java),
        TypedEnumArgumentDescriptor(type.java),
        action
    )
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
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.enum(
    name: String,
    values: List<String>,
    action: RequiredArgumentAction<S, StringEnumArgumentDescriptor>
) {
    argument(
        name,
        EnumArgumentType(*values.toTypedArray()),
        StringEnumArgumentDescriptor,
        action
    )
}

/**
 * Adds an enum argument allowing only values specified
 * in [values] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * All elements of [values] must have [names][Enum.name]
 * that are distinct when case is ignored.
 *
 * @author Cypher121
 */
@JvmName("enumAllowedSublist")
@BrigadierDsl
public fun <S, T : Enum<T>> ArgumentBuilder<S, *>.enum(
    name: String,
    values: List<T>,
    action: RequiredArgumentAction<S, MappedStringEnumArgumentDescriptor<T>>
) {
    enum(name, values.associateBy(Enum<*>::name), action)
}

/**
 * Adds an enum argument allowing values specified by
 * keys of [values] with [name] as the parameter name.
 *
 * An accessor is passed to [action] allowing type-safe
 * retrieval from [CommandContext] during execution.
 *
 * The accessor returns the value from [values] matching
 * the key provided to the command.
 *
 * Keys of [values] must be distinct when case is ignored.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, T> ArgumentBuilder<S, *>.enum(
    name: String,
    values: Map<String, T>,
    action: RequiredArgumentAction<S, MappedStringEnumArgumentDescriptor<T>>
) {
    val lowercaseValues = values.mapKeys { it.key.lowercase() }

    argument(
        name,
        EnumArgumentType(*values.keys.toTypedArray()),
        MappedStringEnumArgumentDescriptor(lowercaseValues),
        action
    )
}

/**
 * [ArgumentDescriptor] for [EnumArgumentType]
 * allowing a set of specified strings.
 *
 * @see EnumArgumentType
 *
 * @author Cypher121
 */
public object StringEnumArgumentDescriptor : ArgumentDescriptor<EnumArgumentType>

/**
 * [ArgumentDescriptor] for [EnumArgumentType]
 * allowing all enum constants of [T].
 *
 * @see EnumArgumentType.enumConstant
 *
 * @author Cypher121
 */
public class TypedEnumArgumentDescriptor<T : Enum<T>>(public val type: Class<T>) : ArgumentDescriptor<EnumArgumentType>

/**
 * [ArgumentDescriptor] for [EnumArgumentType]
 * allowing a set of specified strings, and
 * containing values of [T] matching every
 * allowed string.
 *
 * @author Cypher121
 */
public class MappedStringEnumArgumentDescriptor<T>(public val map: Map<String, T>) :
    ArgumentDescriptor<EnumArgumentType>
