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
import net.minecraft.command.argument.*
import net.minecraft.scoreboard.ScoreboardCriterion
import net.minecraft.scoreboard.ScoreboardObjective
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the [OperationArgumentType.Operation] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see OperationArgumentType.getOperation
 *
 * @author Cypher121
 */
@JvmName("valueOperationArg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<OperationArgumentType>
        >.value(): OperationArgumentType.Operation =
    OperationArgumentType.getOperation(context.assumeSourceNotUsed(), name)

/**
 * Reads the [ScoreboardCriterion] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see ScoreboardCriterionArgumentType.getCriterion
 *
 * @author Cypher121
 */
@JvmName("valueScoreboardCriterionArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ScoreboardCriterionArgumentType>>.value(): ScoreboardCriterion =
    ScoreboardCriterionArgumentType.getCriterion(context.assumeSourceNotUsed(), name)

/**
 * Reads the [ScoreboardObjective] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see ScoreboardObjectiveArgumentType.getObjective
 *
 * @author Cypher121
 */
@JvmName("valueScoreboardObjectiveArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<ScoreboardObjectiveArgumentType>
        >.value(): ScoreboardObjective =
    ScoreboardObjectiveArgumentType.getObjective(context, name)

/**
 * Reads the [ScoreboardObjective] value from the argument in
 * the receiver [ArgumentReader].
 *
 * Throws an exception if the [criterion][ScoreboardObjective.getCriterion]
 * for the selected objective is [read-only][ScoreboardCriterion.isReadOnly].
 *
 * @see ScoreboardObjectiveArgumentType.getWritableObjective
 *
 * @author Cypher121
 */
@JvmName("writableScoreboardObjectiveArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<ScoreboardObjectiveArgumentType>
        >.writable(): ScoreboardObjective =
    ScoreboardObjectiveArgumentType.getWritableObjective(context, name)

/**
 * Reads the integer slot value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see ScoreboardSlotArgumentType.getScoreboardSlot
 *
 * @author Cypher121
 */
@JvmName("valueScoreboardSlotArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<ScoreboardSlotArgumentType>>.value(): Int =
    ScoreboardSlotArgumentType.getScoreboardSlot(context.assumeSourceNotUsed(), name)

/**
 * Reads the score holder name from the argument in
 * the receiver [ArgumentReader].
 *
 * @see ScoreHolderArgumentType.getScoreHolder
 *
 * @author Cypher121
 */
@JvmName("valueSingleScoreHolderArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        SingleScoreHolderArgumentDescriptor
        >.value(): String =
    ScoreHolderArgumentType.getScoreHolder(context, name)

/**
 * Reads all score holder names from the argument in
 * the receiver [ArgumentReader].
 *
 * Throws an exception if no score holders are
 * found for this argument.
 *
 * @see ScoreHolderArgumentType.getScoreHolders
 *
 * @author Cypher121
 */
@JvmName("valueListScoreHolderArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, ListScoreHolderArgumentDescriptor>.value(): Collection<String> =
    ScoreHolderArgumentType.getScoreHolders(context, name)

/**
 * Reads all score holder names from the argument in
 * the receiver [ArgumentReader].
 *
 * Returns the names of all known players on the scoreboard
 * if no score holders are found for this argument.
 *
 * @see ScoreHolderArgumentType.getScoreHolders
 *
 * @author Cypher121
 */
@JvmName("defaultToAllKnownListScoreHolderArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        ListScoreHolderArgumentDescriptor
        >.defaultToAllKnown(): Collection<String> =
    ScoreHolderArgumentType.getScoreboardScoreHolders(context, name)

/**
 * Adds an operation compound argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.operation(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<OperationArgumentType>>
) {
    argument(name, OperationArgumentType.operation(), action)
}

/**
 * Adds a scoreboard criterion argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.scoreboardCriterion(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ScoreboardCriterionArgumentType>>
) {
    argument(name, ScoreboardCriterionArgumentType.scoreboardCriterion(), action)
}

/**
 * Adds a scoreboard objective argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.scoreboardObjective(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ScoreboardObjectiveArgumentType>>
) {
    argument(name, ScoreboardObjectiveArgumentType.scoreboardObjective(), action)
}

/**
 * Adds a scoreboard slot argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.scoreboardSlot(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ScoreboardSlotArgumentType>>
) {
    argument(name, ScoreboardSlotArgumentType.scoreboardSlot(), action)
}

/**
 * Adds a score holder argument with [name] as the parameter name.
 *
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.scoreHolder(
    name: String,
    action: RequiredArgumentAction<S, SingleScoreHolderArgumentDescriptor>
) {
    argument(name, ScoreHolderArgumentType.scoreHolder(), SingleScoreHolderArgumentDescriptor, action)
}

/**
 * Adds a multiple score holders argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.scoreHolders(
    name: String,
    action: RequiredArgumentAction<S, ListScoreHolderArgumentDescriptor>
) {
    argument(name, ScoreHolderArgumentType.scoreHolder(), ListScoreHolderArgumentDescriptor, action)
}

/**
 * [ArgumentDescriptor] for an [ScoreHolderArgumentType]
 * allowing a single score holder to be selected.
 *
 * @see scoreHolder
 * @see ScoreHolderArgumentType.scoreHolder
 *
 * @author Cypher121
 */
public object SingleScoreHolderArgumentDescriptor : ArgumentDescriptor<ScoreHolderArgumentType>

/**
 * [ArgumentDescriptor] for an [ScoreHolderArgumentType]
 * allowing multiple score holders to be selected.
 *
 * @see scoreHolders
 * @see ScoreHolderArgumentType.scoreHolders
 *
 * @author Cypher121
 */
public object ListScoreHolderArgumentDescriptor : ArgumentDescriptor<ScoreHolderArgumentType>
