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

/*
 * Preserve binary compatibility when moving extensions between files
 */
@file:JvmMultifileClass
@file:JvmName("ArgumentsKt")

package org.quiltmc.qkl.library.brigadier.argument


import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.ColorArgumentType
import net.minecraft.command.argument.MessageArgumentType
import net.minecraft.command.argument.TextArgumentType
import net.minecraft.network.message.SignedChatMessage
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import org.quiltmc.qkl.library.brigadier.*


/**
 * Reads the message's [Text] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see MessageArgumentType.getMessage
 *
 * @author Cypher121
 */
@JvmName("valueMessageArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                MessageArgumentType
                >
        >.value(): Text {
    return MessageArgumentType.getMessage(context, name)
}

/**
 * Reads the [SignedChatMessage] value from
 * the argument in the receiver [ArgumentReader]
 * and calls [consumer] with it as an argument.
 *
 * The value may not be available immediately.
 *
 * @see MessageArgumentType.resolveSignedMessage
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                MessageArgumentType
                >
        >.resolveSigned(consumer: (SignedChatMessage) -> Unit) {
    MessageArgumentType.resolveChatMessage(context, name, consumer)
}

/**
 * Reads the selected color's [Formatting] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see ColorArgumentType.getColor
 *
 * @author Cypher121
 */
@JvmName("valueColorArg")
@BrigadierDsl
public fun DefaultArgumentReader<ColorArgumentType>.value(): Formatting {
    return ColorArgumentType.getColor(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [Text] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see TextArgumentType.getTextArgument
 *
 * @author Cypher121
 */
@JvmName("valueTextArg")
@BrigadierDsl
public fun DefaultArgumentReader<TextArgumentType>.value(): Text {
    return TextArgumentType.getTextArgument(context.assumeSourceNotUsed(), name)
}

/**
 * Creates a message argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> message(
    name: String
): DefaultArgumentConstructor<S, MessageArgumentType> {
    return argument(name, MessageArgumentType.message())
}

/**
 * Creates a color argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> color(
    name: String
): DefaultArgumentConstructor<S, ColorArgumentType> {
    return argument(name, ColorArgumentType.color())
}

/**
 * Creates a text argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> text(
    context: CommandBuildContext,
    name: String
): DefaultArgumentConstructor<S, TextArgumentType> {
    return argument(name, TextArgumentType.text(context))
}
