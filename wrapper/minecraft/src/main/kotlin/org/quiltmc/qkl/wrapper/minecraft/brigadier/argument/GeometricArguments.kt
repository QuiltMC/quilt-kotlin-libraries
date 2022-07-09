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


import net.minecraft.command.argument.*
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.math.*
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import java.util.*

/**
 * Reads the float value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see AngleArgumentType.getAngle
 *
 * @author Cypher121
 */
@JvmName("valueAngleArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                AngleArgumentType
                >
        >.value(): Float {
    return AngleArgumentType.getAngle(context, name)
}

/**
 * Reads the raw [PosArgument] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RotationArgumentType.getRotation
 *
 * @author Cypher121
 */
@JvmName("valueRotationArg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<
                RotationArgumentType
                >
        >.value(): PosArgument {
    return RotationArgumentType.getRotation(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [Vec2f] value from the argument
 * in the receiver [ArgumentReader], converting
 * the contained [PosArgument] to an absolute rotation.
 *
 * @see RotationArgumentType.getRotation
 * @see PosArgument.toAbsoluteRotation
 *
 * @author Cypher121
 */
@JvmName("absoluteRotationArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                RotationArgumentType
                >
        >.absolute(): Vec2f {
    return RotationArgumentType.getRotation(context, name).toAbsoluteRotation(context.source)
}

/**
 * Reads the set of [Direction.Axis] from the
 * argument in the receiver [ArgumentReader].
 *
 * @see SwizzleArgumentType.getSwizzle
 *
 * @author Cypher121
 */
@JvmName("valueSwizzleArg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<
                SwizzleArgumentType
                >
        >.value(): EnumSet<Direction.Axis> {
    return SwizzleArgumentType.getSwizzle(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [BlockPos] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see BlockPosArgumentType.getBlockPos
 *
 * @author Cypher121
 */
@JvmName("valueBlockPosArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                BlockPosArgumentType
                >
        >.value(): BlockPos {
    return BlockPosArgumentType.getBlockPos(context, name)
}

/**
 * Reads the [BlockPos] value from the
 * argument in the receiver [ArgumentReader].
 *
 * Throws an exception if the selected position is not
 * loaded on the world in the command's [ServerCommandSource].
 *
 * @see BlockPosArgumentType.getLoadedBlockPos
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                BlockPosArgumentType
                >
        >.requireLoaded(): BlockPos {
    return BlockPosArgumentType.getLoadedBlockPos(context, name)
}

/**
 * Reads the [ColumnPos] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see ColumnPosArgumentType.getColumnPos
 *
 * @author Cypher121
 */
@JvmName("valueColumnPosArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                ColumnPosArgumentType
                >
        >.value(): ColumnPos {
    return ColumnPosArgumentType.getColumnPos(context, name)
}

/**
 * Reads the [Vec2f] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see Vec2ArgumentType.getVec2
 *
 * @author Cypher121
 */
@JvmName("valueVec2Arg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                Vec2ArgumentType
                >
        >.value(): Vec2f {
    return Vec2ArgumentType.getVec2(context, name)
}

/**
 * Reads the [Vec3d] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see Vec3ArgumentType.getVec3
 *
 * @author Cypher121
 */
@JvmName("valueVec3Arg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        DefaultArgumentDescriptor<
                Vec3ArgumentType
                >
        >.value(): Vec3d {
    return Vec3ArgumentType.getVec3(context, name)
}

/**
 * Reads the raw [PosArgument] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see Vec3ArgumentType.getPosArgument
 *
 * @author Cypher121
 */
@JvmName("posVec3Arg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<
                Vec3ArgumentType
                >
        >.posArgument(): PosArgument {
    return Vec3ArgumentType.getPosArgument(context.assumeSourceNotUsed(), name)
}


/**
 * Creates an angle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> angle(
    name: String
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                AngleArgumentType
                >
        > {
    return argument(name, AngleArgumentType.angle())
}

/**
 * Creates a rotation argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> rotation(
    name: String
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                RotationArgumentType
                >
        > {
    return argument(name, RotationArgumentType.rotation())
}

/**
 * Creates a swizzle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> swizzle(
    name: String
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                SwizzleArgumentType
                >
        > {
    return argument(name, SwizzleArgumentType.swizzle())
}

/**
 * Creates a block pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> blockPos(
    name: String
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                BlockPosArgumentType
                >
        > {
    return argument(name, BlockPosArgumentType.blockPos())
}

/**
 * Creates a column pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> columnPos(
    name: String
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                ColumnPosArgumentType
                >
        > {
    return argument(name, ColumnPosArgumentType.columnPos())
}

/**
 * Creates a vec2 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> vec2(
    name: String,
    centerIntegers: Boolean = false
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                Vec2ArgumentType
                >
        > {
    return argument(name, Vec2ArgumentType.vec2(centerIntegers))
}

/**
 * Creates a vec3 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> vec3(
    name: String,
    centerIntegers: Boolean = false
): RequiredArgumentConstructor<
        S,
        DefaultArgumentDescriptor<
                Vec3ArgumentType
                >
        > {
    return argument(name, Vec3ArgumentType.vec3(centerIntegers))
}
