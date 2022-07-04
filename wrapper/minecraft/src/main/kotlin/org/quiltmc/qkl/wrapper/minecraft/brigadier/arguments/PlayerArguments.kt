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
import net.minecraft.command.EntitySelector
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.GameProfileArgumentType
import net.minecraft.command.argument.TeamArgumentType
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentActionWithName

/**
 * Adds a game profile argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.gameProfile(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, GameProfileArgumentType.GameProfileArgument>(
        name,
        GameProfileArgumentType()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a multiple player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.players(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.players()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.player(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.player()
    )
    argument.action(name)
    then(argument)
}

/**
 * Adds a team argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.team(
    name: String,
    action: RequiredArgumentActionWithName<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        TeamArgumentType()
    )
    argument.action(name)
    then(argument)
}
