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

import com.mojang.brigadier.arguments.*
import com.mojang.brigadier.builder.ArgumentBuilder
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

@JvmName("valueBoolArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<BoolArgumentType>>.value(): Boolean =
    BoolArgumentType.getBool(context, name)

@JvmName("valueDoubleArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<DoubleArgumentType>>.value(): Double =
    DoubleArgumentType.getDouble(context, name)

@JvmName("valueFloatArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<FloatArgumentType>>.value(): Float =
    FloatArgumentType.getFloat(context, name)

@JvmName("valueIntArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<IntegerArgumentType>>.value(): Int =
    IntegerArgumentType.getInteger(context, name)

@JvmName("valueLongArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<LongArgumentType>>.value(): Long =
    LongArgumentType.getLong(context, name)

/**
 * Adds a boolean argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.boolean(
    name: String,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<BoolArgumentType>>
) {
    argument(name, BoolArgumentType.bool(), action)
}

/**
 * Adds a double argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.double(
    name: String,
    min: Double = -Double.MAX_VALUE,
    max: Double = Double.MAX_VALUE,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<DoubleArgumentType>>
) {
    argument(name, DoubleArgumentType.doubleArg(min, max), action)
}

/**
 * Adds a float argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.float(
    name: String,
    min: Float = -Float.MAX_VALUE,
    max: Float = Float.MAX_VALUE,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<FloatArgumentType>>
) {
    argument(name, FloatArgumentType.floatArg(min, max), action)
}

/**
 * Adds an integer argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.integer(
    name: String,
    min: Int = -Int.MAX_VALUE,
    max: Int = Int.MAX_VALUE,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<IntegerArgumentType>>
) {
    argument(name, IntegerArgumentType.integer(min, max), action)
}

/**
 * Adds a long argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> ArgumentBuilder<S, *>.long(
    name: String,
    min: Long = -Long.MAX_VALUE,
    max: Long = Long.MAX_VALUE,
    action: RequiredArgumentAction<S, DefaultArgumentDescriptor<LongArgumentType>>
) {
    argument(name, LongArgumentType.longArg(min, max), action)
}
