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

import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * Reads the boolean value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see BoolArgumentType.getBool
 *
 * @author Cypher121
 */
@JvmName("valueBoolArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<BoolArgumentType>>.value(): Boolean =
    BoolArgumentType.getBool(context, name)

/**
 * Reads the boolean value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see BoolArgumentType.getBool
 *
 * @author Cypher121
 */
@JvmName("valueDoubleArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<DoubleArgumentType>>.value(): Double =
    DoubleArgumentType.getDouble(context, name)

/**
 * Reads the float value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see FloatArgumentType.getFloat
 *
 * @author Cypher121
 */
@JvmName("valueFloatArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<FloatArgumentType>>.value(): Float =
    FloatArgumentType.getFloat(context, name)

/**
 * Reads the integer value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see IntegerArgumentType.getInteger
 *
 * @author Cypher121
 */
@JvmName("valueIntArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<IntegerArgumentType>>.value(): Int =
    IntegerArgumentType.getInteger(context, name)

/**
 * Reads the long value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see LongArgumentType.getLong
 *
 * @author Cypher121
 */
@JvmName("valueLongArg")
@BrigadierDsl
public fun ArgumentReader<*, DefaultArgumentDescriptor<LongArgumentType>>.value(): Long =
    LongArgumentType.getLong(context, name)

/**
 * Creates a boolean argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> boolean(
    name: String
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<BoolArgumentType>> {
    return argument(name, BoolArgumentType.bool())
}

/**
 * Creates a double argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> double(
    name: String,
    min: Double = -Double.MAX_VALUE,
    max: Double = Double.MAX_VALUE
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<DoubleArgumentType>> {
    return argument(name, DoubleArgumentType.doubleArg(min, max))
}

/**
 * Creates a float argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> float(
    name: String,
    min: Float = -Float.MAX_VALUE,
    max: Float = Float.MAX_VALUE
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<FloatArgumentType>> {
    return argument(name, FloatArgumentType.floatArg(min, max))
}

/**
 * Creates an integer argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> integer(
    name: String,
    min: Int = -Int.MAX_VALUE,
    max: Int = Int.MAX_VALUE
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<IntegerArgumentType>> {
    return argument(name, IntegerArgumentType.integer(min, max))
}

/**
 * Creates a long argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> long(
    name: String,
    min: Long = -Long.MAX_VALUE,
    max: Long = Long.MAX_VALUE
): RequiredArgumentConstructor<S, DefaultArgumentDescriptor<LongArgumentType>> {
    return argument(name, LongArgumentType.longArg(min, max))
}
