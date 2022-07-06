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

package org.quiltmc.qkl.wrapper.minecraft.brigadier

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandBuildContext
import net.minecraft.util.registry.DynamicRegistryManager

public typealias ArgumentValueAccessor<S, D> =
        CommandContext<S>.() -> ArgumentReader<S, D>
public typealias RequiredArgumentAction<S, D> =
        RequiredArgumentBuilder<S, *>.(getValue: ArgumentValueAccessor<S, D>) -> Unit

public typealias LiteralArgumentAction<S> = LiteralArgumentBuilder<S>.() -> Unit

@DslMarker
public annotation class BrigadierDsl

/**
 * Registers a command under [command] as the name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> CommandDispatcher<S>.register(
    command: String,
    action: LiteralArgumentAction<S>
) {
    val argument = LiteralArgumentBuilder.literal<S>(command)
    argument.apply(action)
    register(argument)
}

/**
 * Creates and returns a [CommandBuildContext]
 * with the given [dynamicRegistryManager]
 * and configured with [action].
 *
 * @see CommandBuildContext
 */
public fun commandBuildContext(
    dynamicRegistryManager: DynamicRegistryManager,
    action: CommandBuildContext.() -> Unit
): CommandBuildContext {
    val context = CommandBuildContext(dynamicRegistryManager)
    context.apply(action)
    return context
}

/**
 * Adds an argument of the specified [argumentType] with [name]
 * as the parameter name.
 *
 * The accessor passed to [action] can be used
 * on [CommandContext] during execution to create
 * an [ArgumentReader] for this argument.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, D : ArgumentDescriptor<A>, AT, A : ArgumentType<AT>> ArgumentBuilder<S, *>.argument(
    name: String,
    argumentType: A,
    argumentDescriptor: D,
    action: RequiredArgumentAction<S, D>
) {
    val builder = RequiredArgumentBuilder.argument<S, AT>(
        name,
        argumentType
    )

    builder.action {
        ArgumentReader(this, name, argumentDescriptor)
    }

    then(builder)
}

@BrigadierDsl
public fun <S, AT, A : ArgumentType<AT>> ArgumentBuilder<S, *>.argument(
    name: String,
    argumentType: A,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<A>>
) {
    argument(name, argumentType, DefaultArgumentDescriptor(), action)
}