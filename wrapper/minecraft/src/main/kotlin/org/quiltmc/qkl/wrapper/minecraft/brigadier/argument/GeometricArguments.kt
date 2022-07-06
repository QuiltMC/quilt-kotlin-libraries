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
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.math.*
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.*

@JvmName("valueAngleArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<AngleArgumentType>>.value(): Float =
    AngleArgumentType.getAngle(context, name)

@JvmName("valueRotationArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<RotationArgumentType>>.value(): PosArgument =
    RotationArgumentType.getRotation(context.assumeSourceNotUsed(), name)

@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<RotationArgumentType>>.absolute(): Vec2f =
    RotationArgumentType.getRotation(context, name).toAbsoluteRotation(context.source)

@JvmName("valueSwizzleArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<SwizzleArgumentType>>.value(): EnumSet<Direction.Axis> =
    SwizzleArgumentType.getSwizzle(context.assumeSourceNotUsed(), name)

@JvmName("valueBlockPosArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<BlockPosArgumentType>>.value(): BlockPos =
    BlockPosArgumentType.getBlockPos(context, name)

@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<BlockPosArgumentType>>.requireLoaded(): BlockPos =
    BlockPosArgumentType.getLoadedBlockPos(context, name)

@JvmName("valueColumnPosArg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<ColumnPosArgumentType>>.value(): ColumnPos =
    ColumnPosArgumentType.getColumnPos(context, name)

@JvmName("valueVec2Arg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<Vec2ArgumentType>>.value(): Vec2f =
    Vec2ArgumentType.getVec2(context, name)

@JvmName("valueVec3Arg")
@BrigadierDsl
public fun ArgumentReader<ServerCommandSource, DefaultArgumentDescriptor<Vec3ArgumentType>>.value(): Vec3d =
    Vec3ArgumentType.getVec3(context, name)

@JvmName("posVec3Arg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<Vec3ArgumentType>>.posArgument(): PosArgument =
    Vec3ArgumentType.getPosArgument(context.assumeSourceNotUsed(), name)


/**
 * Adds an angle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.angle(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<AngleArgumentType>>
) {
    argument(name, AngleArgumentType.angle(), action)
}

/**
 * Adds a rotation argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.rotation(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<RotationArgumentType>>
) {
    argument(name, RotationArgumentType.rotation(), action)
}

/**
 * Adds a swizzle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.swizzle(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<SwizzleArgumentType>>
) {
    argument(name, SwizzleArgumentType.swizzle(), action)
}

/**
 * Adds a block pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.blockPos(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<BlockPosArgumentType>>
) {
    argument(name, BlockPosArgumentType.blockPos(), action)
}

/**
 * Adds a column pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.columnPos(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<ColumnPosArgumentType>>
) {
    argument(name, ColumnPosArgumentType.columnPos(), action)
}

/**
 * Adds a vec2 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.vec2(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<Vec2ArgumentType>>
) {
    argument(name, Vec2ArgumentType.vec2(centerIntegers), action)
}

/**
 * Adds a vec3 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.vec3(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<Vec3ArgumentType>>
) {
    argument(name, Vec3ArgumentType.vec3(centerIntegers), action)
}
