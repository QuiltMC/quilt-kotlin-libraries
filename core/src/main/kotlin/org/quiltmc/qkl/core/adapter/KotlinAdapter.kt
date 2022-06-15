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

package org.quiltmc.qkl.core.adapter

import org.quiltmc.loader.api.LanguageAdapter
import org.quiltmc.loader.api.LanguageAdapterException
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.impl.launch.common.QuiltLauncherBase
import org.quiltmc.loader.impl.util.DefaultLanguageAdapter
import java.lang.reflect.Proxy
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.jvmErasure

/**
 * Kotlin Language Adapter.
 *
 * @author Oliver-makes-code (Emma)
 * */
public open class KotlinAdapter : LanguageAdapter {
    override fun <T: Any> create(
        mod: ModContainer,
        value: String,
        type: Class<T>
    ): T {
        val splitMethod = value.split("::")
        if (splitMethod.isEmpty()) {
            throw LanguageAdapterException("Invalid handle format: $value")
        }
        val clazz = try {
            Class.forName(
                splitMethod[0],
                true,
                QuiltLauncherBase.getLauncher().targetClassLoader
            )
        } catch (error: ClassNotFoundException) {
            throw LanguageAdapterException(error)
        }

        val kotlinClass = clazz.kotlin

        when (splitMethod.size) {
            1 -> {
                if (type.isAssignableFrom(clazz)) {
                    return kotlinClass.objectInstance as? T
                            ?: try {
                                kotlinClass.createInstance() as T
                            } catch (error: Error) {
                                withDefaultAdapter(mod, value, type)
                            }
                }
                throw LanguageAdapterException(
                    "Class ${clazz.name} cannot be cast to ${type.name}!"
                )
            }
            2 -> {
                val instance = try {
                    kotlinClass.objectInstance
                    // Would throw in flk, but we try to run with the default adapter
                        ?: return withDefaultAdapter(mod, value, type)
                } catch (error: UnsupportedOperationException) {
                    // Needed here in case Kotlin reflection decides to throw a fit
                    return withDefaultAdapter(mod, value, type)
                }

                // Get all the methods with the right name
                val methods = instance::class.memberFunctions.filter {
                    it.name == splitMethod[1]
                }

                // Get the first property with the right name
                val property = kotlinClass.declaredMemberProperties.find {
                    it.name == splitMethod[1]
                }

                if (property != null) {
                    // Get the return type
                    val field: KType = property.returnType

                    // Would throw in flk, but we try to run with the default adapter
                    if (
                        methods.isNotEmpty()
                        || !type.kotlin.isSuperclassOf(field.jvmErasure)
                    ) {
                        return withDefaultAdapter(mod, value, type)
                    }

                    // Return the property which is an instance of the interface
                    return (property as KProperty1<Any, T>).get(instance)
                }

                // Would throw in flk, but we try to run with the default adapter
                if (!type.isInterface || methods.size != 1) {
                        return withDefaultAdapter(mod, value, type)
                }

                // Return a proxy of T that calls the method
                return Proxy.newProxyInstance(
                    QuiltLauncherBase.getLauncher().targetClassLoader,
                    arrayOf(type)
                ) { _, _, _ ->
                    methods[0].call(instance)
                } as T
            }
            else -> {
                throw LanguageAdapterException("Invalid handle format: $value")
            }
        }
    }
    private fun <T> withDefaultAdapter(
        mod: ModContainer,
        value: String,
        type: Class<T>
    ): T {
        return DefaultLanguageAdapter.INSTANCE.create(mod, value, type)
    }
}
