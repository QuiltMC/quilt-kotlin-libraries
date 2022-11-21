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

package org.quiltmc.qkl.library.brigadier.util

import com.mojang.brigadier.context.CommandContext
import net.minecraft.entity.Entity
import net.minecraft.server.MinecraftServer
import net.minecraft.server.PlayerManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text

/**
 * Player executing the command or
 * `null` if not executed by a player.
 *
 * @see ServerCommandSource.m_ozupobtn
 *
 * @author Cypher121
 */
//TODO wait for mappings
public val CommandContext<ServerCommandSource>.player: ServerPlayerEntity?
    get() = source.m_ozupobtn()

/**
 * Entity executing the command or
 * `null` if not executed by an entity.
 *
 * @see ServerCommandSource.entity
 *
 * @author Cypher121
 */
public val CommandContext<ServerCommandSource>.entity: Entity?
    get() = source.entity

/**
 * World from which the command originates.
 *
 * @see ServerCommandSource.world
 *
 * @author Cypher121
 */
public val CommandContext<ServerCommandSource>.world: ServerWorld
    get() = source.world

/**
 * Server the command is being executed on.
 *
 * @see ServerCommandSource.server
 *
 * @author Cypher121
 */
public val CommandContext<ServerCommandSource>.server: MinecraftServer
    get() = source.server

/**
 * Sends [message] as a system message to the player.
 * Also sends to operators, if [broadcastToOps] is `true`.
 *
 * @see ServerCommandSource.sendFeedback
 *
 * @author Cypher121
 */
public fun CommandContext<ServerCommandSource>.sendFeedback(
    message: Text,
    broadcastToOps: Boolean = false
) {
    source.sendFeedback(message, broadcastToOps)
}

/**
 * Sends [message] to every player on the server,
 * to chat or the toolbar overlay controlled by [overlay]
 *
 * @see PlayerManager.m_bgctehjy
 *
 * @author Cypher121
 */
public fun CommandContext<ServerCommandSource>.broadcastSystemMessage(
    message: Text,
    overlay: Boolean
) {
    server.playerManager.m_bgctehjy(message, overlay)
}
