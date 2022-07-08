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
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandBuildContext
import net.minecraft.util.registry.DynamicRegistryManager

public typealias ArgumentAccessor<S, D> =
        CommandContext<S>.() -> ArgumentReader<S, D>

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
    action: LiteralArgumentBuilder<S>.() -> Unit
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
 * Applies the [accessor] to the receiver [CommandContext].
 *
 * Shorthand/alternative to `context.accessor()`.
 */
@JvmName("getRequired")
public operator fun <S, D : ArgumentDescriptor<*>> CommandContext<S>.get(
    accessor: ArgumentAccessor<S, D>
): ArgumentReader<S, D> {
    return accessor()
}

/**
 * Applies the [accessor] to the receiver [CommandContext].
 *
 * If [accessor] is `null`, returns `null`.
 *
 * Shorthand/alternative to `context.accessor()`.
 */
@JvmName("getOptional")
public operator fun <S, D : ArgumentDescriptor<*>> CommandContext<S>.get(
    accessor: ArgumentAccessor<S, D>?
): ArgumentReader<S, D>? {
    return accessor?.invoke(this)
}
