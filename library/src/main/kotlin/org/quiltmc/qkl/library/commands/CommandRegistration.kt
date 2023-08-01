/*
 * Copyright 2023 The Quilt Project
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

@file:Suppress("unused")

package org.quiltmc.qkl.library.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandBuildContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.command.api.CommandRegistrationCallback
import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback
import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource

public typealias CommandRegisterCallback<S> = CommandDispatcher<S>.(
    buildContext: CommandBuildContext,
    environment: CommandManager.RegistrationEnvironment
) -> Unit

/**
 * @see CommandRegistrationCallback.EVENT
 *
 * @author sschr15
 */
public fun EventRegistration.onCommandRegistration(
    callback: CommandRegisterCallback<ServerCommandSource>
) {
    CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback(callback))
}

/**
 * @see ClientCommandRegistrationCallback.EVENT
 *
 * @author sschr15
 */
@ClientOnly
public fun EventRegistration.onClientCommandRegistration(
    callback: CommandRegisterCallback<QuiltClientCommandSource>
) {
    ClientCommandRegistrationCallback.EVENT.register(ClientCommandRegistrationCallback(callback))
}
