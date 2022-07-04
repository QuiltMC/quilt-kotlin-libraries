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
import net.minecraft.util.math.Direction
import org.quiltmc.qkl.wrapper.minecraft.brigadier.RequiredArgumentAction
import java.util.*

/**
 * Adds an angle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.angle(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, AngleArgumentType.Angle>(
        name,
        AngleArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a rotation argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.rotation(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        RotationArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a swizzle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.swizzle(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EnumSet<Direction.Axis>>(
        name,
        SwizzleArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a block pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.blockPos(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        BlockPosArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a column pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.columnPos(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        ColumnPosArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a vec2 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.vec2(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        Vec2ArgumentType(centerIntegers)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a vec3 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> ArgumentBuilder<S, *>.vec3(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        Vec3ArgumentType(centerIntegers)
    )
    argument.apply(action)
    then(argument)
}
