package org.quiltmc.qkl.core.adapter

import org.quiltmc.loader.api.LanguageAdapter
import org.quiltmc.loader.api.LanguageAdapterException
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.loader.impl.launch.common.QuiltLauncherBase
import org.quiltmc.loader.impl.util.DefaultLanguageAdapter
import java.lang.reflect.Proxy
import kotlin.reflect.KProperty1
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberFunctions

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
                    return try {
                        // Can safely ignore these errors, as that's what we're trying against
                        kotlinClass.objectInstance as T?
                            ?: kotlinClass.createInstance() as T
                    } catch (error: Error) {
                        throw LanguageAdapterException(error)
                    }
                }
                throw LanguageAdapterException(
                    "Class ${clazz.name} cannot be cast to ${type.name}!"
                )
            }
            2 -> {
                val instance = kotlinClass.objectInstance
                    ?: withDefaultAdapter(mod, value, type)
                val methods = instance::class.memberFunctions.filter {
                    it.name == splitMethod[1]
                }
                kotlinClass.declaredMemberProperties.find {
                    it.name == splitMethod[1]
                }?.let { it: KProperty1<out Any, *> ->
                    val returnType = it.returnType
                    if (methods.isNotEmpty()) {
                        return withDefaultAdapter(mod, value, type)
                    }
                    try {
                        // Can safely ignore these errors, as that's what we're trying against
                        return it.get(returnType as Nothing) as T
                    } catch (error: NoSuchFieldException) {
                        // We just checked so we can safely ignore
                    } catch (error: Exception) {
                        return withDefaultAdapter(mod, value, type)
                    }
                }
                if (methods.size != 1) {
                    return withDefaultAdapter(mod, value, type)
                }

                if (!type.isInterface) {
                    return withDefaultAdapter(mod, value, type)
                }

                return try {
                    Proxy.newProxyInstance(
                        QuiltLauncherBase.getLauncher().targetClassLoader,
                        arrayOf(type)
                    ) { _, _, _ ->
                        methods[0].call(instance)
                    } as T
                } catch (error: Error) {
                    withDefaultAdapter(mod, value, type)
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