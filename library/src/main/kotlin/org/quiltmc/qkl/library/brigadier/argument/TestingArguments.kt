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

package org.quiltmc.qkl.library.brigadier.argument

import net.minecraft.command.argument.TestClassArgumentType
import net.minecraft.command.argument.TestFunctionArgumentType
import net.minecraft.test.TestFunction
import org.quiltmc.qkl.library.brigadier.*

/**
 * Reads the test class name from the argument in
 * the receiver [ArgumentReader].
 *
 * @see TestClassArgumentType.getTestClass
 *
 * @author Cypher121
 */
@JvmName("valueTestClassArg")
@BrigadierDsl
public fun DefaultArgumentReader<TestClassArgumentType>.value(): String {
    return TestClassArgumentType.getTestClass(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [TestFunction] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see TestFunctionArgumentType.getFunction
 *
 * @author Cypher121
 */
@JvmName("valueTestFunctionArg")
@BrigadierDsl
public fun DefaultArgumentReader<TestFunctionArgumentType>.value(): TestFunction {
    return TestFunctionArgumentType.getFunction(context.assumeSourceNotUsed(), name)
}

/**
 * Creates a test class argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> testClass(
    name: String
): DefaultArgumentConstructor<S, TestClassArgumentType> {
    return argument(name, TestClassArgumentType.testClass())
}

/**
 * Creates a test function argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> testFunction(
    name: String
): DefaultArgumentConstructor<S, TestFunctionArgumentType> {
    return argument(name, TestFunctionArgumentType.testFunction())
}
