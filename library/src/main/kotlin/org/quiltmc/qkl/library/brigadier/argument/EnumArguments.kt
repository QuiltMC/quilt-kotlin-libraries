/*
 * Copyright 2023 The Quilt Project
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

package org.quiltmc.qkl.library.brigadier.argument

import org.quiltmc.qkl.library.brigadier.*
import org.quiltmc.qsl.command.api.EnumArgumentType
import kotlin.reflect.KClass

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
 * @property type enum class of the argument.
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
 * @property map map of strings allowed by the
 * command to values returned to the command code.
 *
 * @author Cypher121
 */
public class MappedStringEnumArgumentDescriptor<T>(public val map: Map<String, T>) :
    ArgumentDescriptor<EnumArgumentType>

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
public fun <T : Enum<T>> ArgumentReader<
        *,
        TypedEnumArgumentDescriptor<T>
        >.value(): T {
    return EnumArgumentType.getEnumConstant(
        context,
        name,
        argumentDescriptor.type
    )
}

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
public fun ArgumentReader<
        *,
        StringEnumArgumentDescriptor
        >.value(): String {
    return EnumArgumentType.getEnum(context, name)
}

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
public fun <T> ArgumentReader<
        *,
        MappedStringEnumArgumentDescriptor<T>
        >.value(): T {
    val key = EnumArgumentType.getEnum(context, name)

    return argumentDescriptor.map.getValue(key.lowercase())
}

/**
 * Creates an enum argument allowing all values of the
 * specified enum [type] [T] with [name] as the parameter name.
 *
 * Enum values of [T] must have [names][Enum.name] that
 * are distinct when case is ignored.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, T : Enum<T>> enum(
    name: String,
    type: KClass<T>
): RequiredArgumentConstructor<
        S,
        TypedEnumArgumentDescriptor<T>
        > {
    return argument(
        name,
        EnumArgumentType.enumConstant(type.java),
        TypedEnumArgumentDescriptor(type.java)
    )
}

/**
 * Creates a string argument allowing only values specified
 * in [values] with [name] as the parameter name.
 *
 * All elements of [values] must be distinct
 * when case is ignored.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> enum(
    name: String,
    values: List<String>
): RequiredArgumentConstructor<
        S,
        StringEnumArgumentDescriptor
        > {
    return argument(
        name,
        EnumArgumentType(*values.toTypedArray()),
        StringEnumArgumentDescriptor
    )
}

/**
 * Creates an enum argument allowing only values specified
 * in [values] with [name] as the parameter name.
 *
 * All elements of [values] must have [names][Enum.name]
 * that are distinct when case is ignored.
 *
 * @author Cypher121
 */
@JvmName("enumAllowedSublist")
@BrigadierDsl
public fun <S, T : Enum<T>> enum(
    name: String,
    values: List<T>
): RequiredArgumentConstructor<
        S,
        MappedStringEnumArgumentDescriptor<T>
        > {
    return enum(name, values.associateBy(Enum<*>::name))
}

/**
 * Creates an enum argument allowing values specified by
 * keys of [values] with [name] as the parameter name.
 *
 * When read, the argument will convert the provided
 * string to its respective value in [values].
 *
 * Keys of [values] must be distinct when case is ignored.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, T> enum(
    name: String,
    values: Map<String, T>
): RequiredArgumentConstructor<
        S,
        MappedStringEnumArgumentDescriptor<T>
        > {
    val lowercaseValues = values.mapKeys { it.key.lowercase() }

    return argument(
        name,
        EnumArgumentType(*values.keys.toTypedArray()),
        MappedStringEnumArgumentDescriptor(lowercaseValues)
    )
}
