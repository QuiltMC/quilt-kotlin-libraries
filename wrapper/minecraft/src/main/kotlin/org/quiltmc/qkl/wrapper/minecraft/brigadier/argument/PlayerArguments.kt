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

import com.mojang.authlib.GameProfile
import com.mojang.brigadier.builder.ArgumentBuilder
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.GameProfileArgumentType
import net.minecraft.command.argument.TeamArgumentType
import net.minecraft.scoreboard.Team
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

@JvmName("valueGameProfileArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<GameProfileArgumentType>
        >.value(): Collection<GameProfile> =
    GameProfileArgumentType.getProfileArgument(context, name)

@JvmName("valueTeamArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<TeamArgumentType>>.value(): Team =
    TeamArgumentType.getTeam(context, name)

@JvmName("valuePlayerArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, SinglePlayerArgumentDescriptor>.value(): ServerPlayerEntity =
    EntityArgumentType.getPlayer(context, name)

@JvmName("requiredPlayerArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, ListPlayerArgumentDescriptor>.required(): Collection<ServerPlayerEntity> =
    EntityArgumentType.getPlayers(context, name)

@JvmName("optionalPlayerArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, ListPlayerArgumentDescriptor>.optional(): Collection<ServerPlayerEntity> =
    EntityArgumentType.getOptionalPlayers(context, name)

/**
 * Adds a game profile argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.gameProfile(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<GameProfileArgumentType>>
) {
    argument(name, GameProfileArgumentType.gameProfile(), action)
}

/**
 * Adds a team argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.team(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<TeamArgumentType>>
) {
    argument(name, TeamArgumentType.team(), action)
}

/**
 * Adds a player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.player(
    name: String,
    action: RequiredArgumentAction<S, SinglePlayerArgumentDescriptor>
) {
    argument(name, EntityArgumentType.player(), SinglePlayerArgumentDescriptor, action)
}

/**
 * Adds a multiple player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.players(
    name: String,
    action: RequiredArgumentAction<S, ListPlayerArgumentDescriptor>
) {
    argument(name, EntityArgumentType.players(), ListPlayerArgumentDescriptor, action)
}

public object SinglePlayerArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>
public object ListPlayerArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>