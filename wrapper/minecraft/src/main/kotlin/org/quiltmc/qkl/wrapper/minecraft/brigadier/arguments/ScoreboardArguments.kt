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

package org.quiltmc.qkl.wrapper.minecraft.brigadier.arguments

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import net.minecraft.command.argument.*
import net.minecraft.scoreboard.ScoreboardCriterion
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithName

/**
 * Adds an operation compound argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.operation(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, OperationArgumentType.Operation>(
        name,
        OperationArgumentType()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a scoreboard criterion argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.scoreboardCriterion(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ScoreboardCriterion>(
        name,
        ScoreboardCriterionArgumentType.scoreboardCriterion()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a scoreboard objective argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.scoreboardObjective(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        ScoreboardObjectiveArgumentType()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a scoreboard slot argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.scoreboardSlot(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        ScoreboardSlotArgumentType.scoreboardSlot()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a score holder argument with [name] as the parameter name.
 *
 * @param multiple whether there are multiple scores
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.scoreHolder(
    name: String,
    multiple: Boolean = false,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ScoreHolderArgumentType.ScoreHolder>(
        name,
        ScoreHolderArgumentType(multiple)
    )
    argument.action(name)
    then(argument)
}
