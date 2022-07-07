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

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandException
import net.minecraft.text.Text
import kotlin.experimental.ExperimentalTypeInference

public typealias CommandActionWithResult<S> = (CommandContext<S>) -> CommandResult
public typealias CommandAction<S> = (CommandContext<S>) -> Unit

/**
 * Representation of possible results of running a command.
 *
 * @author Cypher121
 */
public sealed class CommandResult {
    /**
     * Representation of successful completion with the return value of [result].
     *
     * @author Cypher121
     */
    public class Success(
        public val result: Int = Command.SINGLE_SUCCESS
    ) : CommandResult()

    /**
     * Representation of the command failing with the specified error [message].
     *
     * @author Cypher121
     */
    public class Failure(
        public val message: Text
    ) : CommandResult()
}

/**
 * Sets the action the command will take when executed.
 *
 * If [command] returns [CommandResult.Success], the command will return with [CommandResult.Success.result].
 *
 * If [command] returns [CommandResult.Failure],
 * the command will throw a [CommandException] containing the returned [CommandResult.Failure.message].
 *
 * @see ArgumentBuilder.executes
 *
 * @author Cypher121
 */
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
public fun <S> ArgumentBuilder<S, *>.execute(command: CommandActionWithResult<S>) {
    executes {
        when (val result = command(it)) {
            is CommandResult.Success -> result.result
            is CommandResult.Failure -> throw CommandException(result.message)
        }
    }
}

/**
 * Sets the action the command will take when executed.
 *
 * Command will always succeed. To indicate possible failure
 * or specify the resulting value, return a [CommandResult] from [command].
 *
 * @see ArgumentBuilder.executes
 *
 * @author Cypher121
 */
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("executeWithoutReturnCode")
public fun <S> ArgumentBuilder<S, *>.execute(command: CommandAction<S>) {
    execute {
        command(it)
        CommandResult.Success()
    }
}
