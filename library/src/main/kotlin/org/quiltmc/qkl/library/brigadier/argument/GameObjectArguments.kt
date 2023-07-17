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
 */
@file:JvmMultifileClass
@file:JvmName("ArgumentsKt")

package org.quiltmc.qkl.library.brigadier.argument


import com.mojang.datafixers.util.Either
import com.mojang.datafixers.util.Pair
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.*
import net.minecraft.particle.ParticleEffect
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.function.CommandFunction
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.brigadier.*

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
        DefaultArgumentDescriptor<
                CommandFunctionArgumentType
                >
        >.functions(): Collection<CommandFunction> {
    return CommandFunctionArgumentType.getFunctions(context, name)
}

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
        DefaultArgumentDescriptor<
                CommandFunctionArgumentType
                >
        >.functionOrTag(): Pair<
        Identifier,
        Either<
                CommandFunction,
                Collection<CommandFunction>
                >
        > {
    return CommandFunctionArgumentType.getFunctionOrTag(context, name)
}

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
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                DimensionArgumentType
                >
        >.value(): ServerWorld {
    return DimensionArgumentType.getDimensionArgument(context, name)
}

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
public fun DefaultArgumentReader<ParticleEffectArgumentType>.value(): ParticleEffect {
    return ParticleEffectArgumentType.getParticle(context.assumeSourceNotUsed(), name)
}

/**
 * Creates a command function argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> commandFunction(
    name: String
): DefaultArgumentConstructor<S, CommandFunctionArgumentType> {
    return argument(name, CommandFunctionArgumentType.commandFunction())
}

/**
 * Creates a dimension argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> dimension(
    name: String
): DefaultArgumentConstructor<S, DimensionArgumentType> {
    return argument(name, DimensionArgumentType.dimension())
}

/**
 * Creates a particle effect argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> particleEffect(
    name: String,
    context: CommandBuildContext
): DefaultArgumentConstructor<S, ParticleEffectArgumentType> {
    return argument(name, ParticleEffectArgumentType(context))
}
