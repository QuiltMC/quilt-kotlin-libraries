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

public typealias ArgumentAccessor<S, D> =
        CommandContext<S>.() -> ArgumentReader<S, D>

public typealias RequiredArgumentAction<S, D> =
        RequiredArgumentBuilder<S, *>.(getValue: ArgumentAccessor<S, D>) -> Unit

public typealias LiteralArgumentAction<S> = LiteralArgumentBuilder<S>.() -> Unit

public typealias OptionalLiteralAction<S> = ArgumentBuilder<S, *>.(isPresent: Boolean) -> Unit

/**
 * Marks functions as part of QKL's Brigadier DSL.
 *
 * @author Cypher121
 */
@DslMarker
public annotation class BrigadierDsl

/**
 * Registers a command under [command] as the name.
 *
 * @sample samples.qkl.brigadier.BrigadierDslSamples.sampleRegisterCommand
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
 * an [ArgumentReader] for this argument. This argument reader
 * will contain the specified [argumentDescriptor] that will be
 * used to determine the correct extensions for that reader instance.
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

/**
 * Adds an argument of the specified [argumentType] with [name]
 * as the parameter name.
 *
 * The accessor passed to [action] can be used
 * on [CommandContext] during execution to create
 * an [ArgumentReader] for this argument. This argument reader
 * will contain an instance of [DefaultArgumentDescriptor] for the
 * given argument type that will be used to determine the correct
 * extensions for that reader instance.
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, AT, A : ArgumentType<AT>> ArgumentBuilder<S, *>.argument(
    name: String,
    argumentType: A,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<A>>
) {
    argument(name, argumentType, DefaultArgumentDescriptor(), action)
}

/**
 * Applies the [accessor] to the receiver [CommandContext].
 *
 * Shorthand/alternative to `context.accessor()`.
 */
@JvmName("getRequired")
public operator fun <S, D : ArgumentDescriptor<*>> CommandContext<S>.get(
    accessor: ArgumentAccessor<S, D>
): ArgumentReader<S, D> = accessor()

/**
 * Adds a literal argument with [name] as the literal.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.literal(
    name: String,
    action: LiteralArgumentAction<S>
) {
    val argument = LiteralArgumentBuilder.literal<S>(name)
    argument.action()
    then(argument)
}

/**
 * Adds an optional literal argument with [name] as the literal.
 *
 * The value passed to action is `true` if the literal
 * was used in the executed branch, or `false` otherwise.
 *
 * @sample samples.qkl.brigadier.BrigadierDslSamples.sampleCommandWithOptionals
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.optionalLiteral(
    name: String,
    action: OptionalLiteralAction<S>
) {
    literal(name) {
        action(true)
    }

    action(false)
}
