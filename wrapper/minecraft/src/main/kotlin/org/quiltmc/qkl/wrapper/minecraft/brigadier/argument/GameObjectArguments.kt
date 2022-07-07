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
import com.mojang.datafixers.util.Either
import com.mojang.datafixers.util.Pair
import net.minecraft.command.argument.*
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.particle.ParticleEffect
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.function.CommandFunction
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the collection of [CommandFunction]s from the
 * argument in the receiver [ArgumentReader].
 *
 * @see CommandFunctionArgumentType.getFunctions
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<CommandFunctionArgumentType>
        >.functions(): Collection<CommandFunction> =
    CommandFunctionArgumentType.getFunctions(context, name)

/**
 * Reads the matched [CommandFunction]s from the
 * argument in the receiver [ArgumentReader].
 *
 * Return pair contains the selected [Identifier] and
 * either the matched [CommandFunction] if the function was
 * matched, or a collection of [CommandFunction]s if
 * a tag containing those functions was matched.
 *
 * @see CommandFunctionArgumentType.getFunctionOrTag
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<CommandFunctionArgumentType>
        >.functionOrTag(): Pair<Identifier, Either<CommandFunction, Collection<CommandFunction>>> =
    CommandFunctionArgumentType.getFunctionOrTag(context, name)

/**
 * Reads the [ServerWorld] value for the selected dimension
 * from the argument in the receiver [ArgumentReader].
 *
 * @see DimensionArgumentType.getDimensionArgument
 *
 * @author Cypher121
 */
@JvmName("valueDimensionArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource,
        DefaultArgumentDescriptor<DimensionArgumentType>>.value(): ServerWorld =
    DimensionArgumentType.getDimensionArgument(context, name)

/**
 * Reads the [Enchantment] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see EnchantmentArgumentType.getEnchantment
 *
 * @author Cypher121
 */
@JvmName("valueEnchantmentArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<EnchantmentArgumentType>>.value(): Enchantment =
    EnchantmentArgumentType.getEnchantment(context.assumeSourceNotUsed(), name)

/**
 * Reads the [ParticleEffect] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ParticleEffectArgumentType.getParticle
 *
 * @author Cypher121
 */
@JvmName("valueParticleEffectArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ParticleEffectArgumentType>>.value(): ParticleEffect =
    ParticleEffectArgumentType.getParticle(context.assumeSourceNotUsed(), name)

/**
 * Reads the [StatusEffect] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see StatusEffectArgumentType.getStatusEffect
 *
 * @author Cypher121
 */
@JvmName("valueStatusEffectArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<StatusEffectArgumentType>>.value(): StatusEffect =
    StatusEffectArgumentType.getStatusEffect(context.assumeSourceNotUsed(), name)

/**
 * Adds a command function argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.commandFunction(
    name: String,
    action: RequiredArgumentAction<
            S,
            DefaultArgumentDescriptor<CommandFunctionArgumentType>
            >
) {
    argument(name, CommandFunctionArgumentType.commandFunction(), action)
}

/**
 * Adds a dimension argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.dimension(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<DimensionArgumentType>>
) {
    argument(name, DimensionArgumentType.dimension(), action)
}

/**
 * Adds an enchantment argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 * @author
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.enchantment(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<EnchantmentArgumentType>>
) {
    argument(name, EnchantmentArgumentType.enchantment(), action)
}


/**
 * Adds a particle effect argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.particleEffect(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ParticleEffectArgumentType>>
) {
    argument(name, ParticleEffectArgumentType(), action)
}

/**
 * Adds a status effect argument with [name] as the parameter name.
 *
 * Accessor passed to [action] can be used on a [CommandContext]
 * with an [execute] block to obtain an [ArgumentReader] for this argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.statusEffect(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<StatusEffectArgumentType>>
) {
    argument(name, StatusEffectArgumentType(), action)
}
