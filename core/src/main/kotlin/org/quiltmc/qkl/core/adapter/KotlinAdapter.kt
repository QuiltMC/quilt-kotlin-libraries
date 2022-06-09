package org.quiltmc.qkl.core.adapter

import org.quiltmc.loader.api.LanguageAdapter
import org.quiltmc.loader.api.LanguageAdapterException
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.impl.launch.common.QuiltLauncherBase
import org.quiltmc.loader.impl.util.DefaultLanguageAdapter
import java.lang.reflect.Proxy
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmErasure

/**
 * Kotlin Language Adapter
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
                try {
                    val instance = kotlinClass.objectInstance
                        ?: throw LanguageAdapterException("$kotlinClass is not an object")
                    val methods = instance::class.memberFunctions.filter {
                        it.name == splitMethod[1]
                    }
                    kotlinClass.declaredMemberProperties.find {
                        it.name == splitMethod[1]
                    }?.let {
                        val field: KType = try {
                            it.returnType
                        } catch (error: NoSuchFieldException) {
                            // Ignore it as we don't need it
                            null
                        }?: return@let

                        if (methods.isNotEmpty()) {
                            throw LanguageAdapterException(
                                "Common field and function name in $value"
                            )
                        }

                        if (!type.kotlin.isSuperclassOf(field.jvmErasure)) {
                            throw LanguageAdapterException(
                                "Field $value is not a supertype of ${type.name}!"
                            )
                        }

                        return (it as KProperty1<Any, T>).get(instance)
                    }
                    if (!type.isInterface) {
                        throw LanguageAdapterException(
                            "Cannot proxy method $value to ${type.name}!"
                        )
                    }
                    if (methods.size != 1) {
                        throw LanguageAdapterException(
                            "Cannot find method, as there's not only one type"
                        )
                    }
                    return Proxy.newProxyInstance(
                        QuiltLauncherBase.getLauncher().targetClassLoader, arrayOf(type)
                    ) { _, _, _ ->
                        methods[0].call(instance)
                    } as T
                } catch (error: Exception) {
                    return withDefaultAdapter(mod, value, type)
                }
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