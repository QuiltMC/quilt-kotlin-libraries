/*
 * Copyright 2022 The Quilt Project
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

import com.mojang.authlib.GameProfile

import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.GameProfileArgumentType
import net.minecraft.command.argument.TeamArgumentType
import net.minecraft.scoreboard.Team
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import org.quiltmc.qkl.library.brigadier.*

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing a single player to be selected.
 *
 * @see player
 * @see EntityArgumentType.player
 *
 * @author Cypher121
 */
public object SinglePlayerArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing multiple players to be selected.
 *
 * @see players
 * @see EntityArgumentType.players
 *
 * @author Cypher121
 */
public object ListPlayerArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>

/**
 * Reads the [GameProfile] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see GameProfileArgumentType.getProfileArgument
 *
 * @author Cypher121
 */
@JvmName("valueGameProfileArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                GameProfileArgumentType
                >
        >.value(): Collection<GameProfile> {
    return GameProfileArgumentType.getProfileArgument(context, name)
}

/**
 * Reads the [Team] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see TeamArgumentType.getTeam
 *
 * @author Cypher121
 */
@JvmName("valueTeamArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                TeamArgumentType
                >
        >.value(): Team {
    return TeamArgumentType.getTeam(context, name)
}

/**
 * Reads the [ServerPlayerEntity] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see EntityArgumentType.getPlayer
 *
 * @author Cypher121
 */
@JvmName("valuePlayerArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        SinglePlayerArgumentDescriptor
        >.value(): ServerPlayerEntity {
    return EntityArgumentType.getPlayer(context, name)
}

/**
 * Reads the collection of players from the argument in
 * the receiver [ArgumentReader].
 *
 * Throws an exception if no entities are matched.
 *
 * @see EntityArgumentType.getPlayers
 *
 * @author Cypher121
 */
@JvmName("requiredPlayerArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        ListPlayerArgumentDescriptor
        >.required(): Collection<ServerPlayerEntity> {
    return EntityArgumentType.getPlayers(context, name)
}

/**
 * Reads the collection of players from the argument in
 * the receiver [ArgumentReader].
 *
 * Returns an empty collection if no entities are matched.
 *
 * @see EntityArgumentType.getOptionalPlayers
 *
 * @author Cypher121
 */
@JvmName("optionalPlayerArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        ListPlayerArgumentDescriptor
        >.optional(): Collection<ServerPlayerEntity> {
    return EntityArgumentType.getOptionalPlayers(context, name)
}

/**
 * Creates a game profile argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> gameProfile(
    name: String
): DefaultArgumentConstructor<S, GameProfileArgumentType> {
    return argument(name, GameProfileArgumentType.gameProfile())
}

/**
 * Creates a team argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> team(
    name: String
): DefaultArgumentConstructor<S, TeamArgumentType> {
    return argument(name, TeamArgumentType.team())
}

/**
 * Creates a player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> player(
    name: String
): RequiredArgumentConstructor<
        S,
        SinglePlayerArgumentDescriptor
        > {
    return argument(name, EntityArgumentType.player(), SinglePlayerArgumentDescriptor)
}

/**
 * Creates a multiple player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> players(
    name: String
): RequiredArgumentConstructor<
        S,
        ListPlayerArgumentDescriptor
        > {
    return argument(name, EntityArgumentType.players(), ListPlayerArgumentDescriptor)
}
