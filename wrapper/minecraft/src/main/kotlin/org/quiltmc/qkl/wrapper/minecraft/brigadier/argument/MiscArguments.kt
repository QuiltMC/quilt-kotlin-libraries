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

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.IntegerArgumentType

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.command.argument.ItemPredicateArgumentType
import net.minecraft.command.argument.TimeArgumentType
import net.minecraft.command.argument.UuidArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.*

/**
 * Reads the integer value in ticks from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ItemPredicateArgumentType.getItemPredicate
 *
 * @author Cypher121
 */
@JvmName("valueTimeArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<TimeArgumentType>>.value(): Int =
    IntegerArgumentType.getInteger(context, name) // TimeArgumentType does not provide an accessor, defaulting to int

/**
 * Reads the [UUID] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see UuidArgumentType.getUuid
 *
 * @author Cypher121
 */
@JvmName("valueUuidArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<UuidArgumentType>>.value(): UUID =
    UuidArgumentType.getUuid(context.assumeSourceNotUsed(), name)

/**
 * Creates a time argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> time(
    name: String
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<TimeArgumentType>> {
    return argument(name, TimeArgumentType.time())
}

/**
 * Creates a UUID argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> uuid(
    name: String
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<UuidArgumentType>> {
    return argument(name, UuidArgumentType.uuid())
}

/**
 * Creates a literal argument with [name] as the literal.
 *
 * Like other arguments, literal arguments create accessors,
 * which can be checked for presence of [optional] literals.
 * However, [ArgumentReader]s produced from those accessors
 * serve no purpose due to the argument not having a value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> literal(
    name: String
): ArgumentConstructor<S, LiteralArgumentBuilder<S>, LiteralDescriptor> {
    return ArgumentConstructor(LiteralArgumentBuilder.literal(name), name, LiteralDescriptor)
}

/**
 * Descriptor for a literal argument.
 *
 * Separate from [DefaultArgumentDescriptor]
 * to stand out more in type hints.
 *
 * @author Cypher121
 */
public object LiteralDescriptor : ArgumentDescriptor<ArgumentType<*>>
