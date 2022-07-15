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

import com.mojang.brigadier.arguments.StringArgumentType

import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the string value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see StringArgumentType.getString
 *
 * @author Cypher121
 */
@JvmName("valueStringArg")
@BrigadierDsl
public fun DefaultArgumentReader<StringArgumentType>.value(): String {
    return StringArgumentType.getString(context, name)
}

/**
 * Creates a string argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> string(
    name: String
): DefaultArgumentConstructor<S, StringArgumentType> {
    return argument(name, StringArgumentType.string())
}

/**
 * Creates a greedy string argument with [name] as the parameter name.
 *
 * Note that no further arguments can be added after
 * a greedy string, as any command text will be treated
 * as part of the greedy string argument.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> greedyString(
    name: String
): DefaultArgumentConstructor<S, StringArgumentType> {
    return argument(name, StringArgumentType.greedyString())
}

/**
 * Creates a word argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> word(
    name: String
): DefaultArgumentConstructor<S, StringArgumentType> {
    return argument(name, StringArgumentType.word())
}
