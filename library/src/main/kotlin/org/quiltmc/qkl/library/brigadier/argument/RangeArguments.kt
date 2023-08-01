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


import net.minecraft.command.argument.NumberRangeArgumentType
import net.minecraft.predicate.NumberRange
import org.quiltmc.qkl.library.brigadier.*

/**
 * Reads the [NumberRange.FloatRange] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see NumberRangeArgumentType.FloatRangeArgumentType.getRangeArgument
 *
 * @author Cypher121
 */
@JvmName("valueFloatRangeArg")
@BrigadierDsl
public fun DefaultArgumentReader<NumberRangeArgumentType.FloatRangeArgumentType>.value(): NumberRange.FloatRange {
    return NumberRangeArgumentType.FloatRangeArgumentType.getRangeArgument(
        context.assumeSourceNotUsed(),
        name
    )
}

/**
 * Reads the [NumberRange.IntRange] value from
 * the argument in the receiver [ArgumentReader].
 *
 * @see NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument
 *
 * @author Cypher121
 */
@JvmName("valueIntRangeArg")
@BrigadierDsl
public fun DefaultArgumentReader<NumberRangeArgumentType.IntRangeArgumentType>.value(): NumberRange.IntRange {
    return NumberRangeArgumentType.IntRangeArgumentType.getRangeArgument(
        context.assumeSourceNotUsed(),
        name
    )
}

/**
 * Creates a float range argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> floatRange(
    name: String
): DefaultArgumentConstructor<S, NumberRangeArgumentType.FloatRangeArgumentType> {
    return argument(name, NumberRangeArgumentType.floatRange())
}

/**
 * Creates an int range argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> intRange(
    name: String
): DefaultArgumentConstructor<S, NumberRangeArgumentType.IntRangeArgumentType> {
    return argument(name, NumberRangeArgumentType.intRange())
}
