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

package org.quiltmc.qkl.library.registry

import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

/**
 * Marks functions as part of QKL Registry DSL.
 *
 * @author Peanuuutz
 */
@DslMarker
public annotation class RegistryDsl

/**
 * A RegistryObject with a path, which will be registered under a modid.
 *
 * @param modid The modid to register under.
 * @param path The path to register under.
 * @param t The object to register.
 *
 * @author Oliver-makes-code (Emma)
 * */
public data class RegistryObject<T>(val modid: String, val path: String, val t: T) {
    /**
     * Registers a RegistryObject.
     *
     * @param registry The registry to register to.
     *
     * @author Oliver-makes-code (Emma)
     * */
    @RegistryDsl
    public infix fun toRegistry(registry: Registry<in T>): T {
        return Registry.register(registry, Identifier(modid, path), t)
    }
}

/**
 * Represents an action with a registry.
 *
 * @param modid The modid to register under.
 * @param registry The registry to register to.
 *
 * @author Oliver-makes-code (Emma)
 * */
public data class RegistryAction<T>(val modid: String?, val registry: Registry<T>) {
    /**
     * Registers an object with a given Identifier.
     *
     * @param id The Identifier to register under.
     *
     * @author Oliver-makes-code (Emma)
     * */
    @RegistryDsl
    public infix fun <R : T> R.withId(id: Identifier): R {
        return Registry.register(registry, id, this)
    }

    /**
     * Registers an object with a given stringified id.
     *
     * * If passed `namespace:path`, then this object will be registered under `namespace:path`.
     * * If passed `path` and [modid] is not `null`, then this will be under `modid:path`.
     * * Otherwise, this will be under `minecraft:path`.
     *
     * @param id The id to register under.
     *
     * @author Oliver-makes-code (Emma)
     * */
    @RegistryDsl
    public infix fun <R : T> R.withId(id: String): R {
        val identifier = if (':' in id || modid == null) {
            Identifier(id)
        } else {
            Identifier(modid, id)
        }
        return Registry.register(registry, identifier, this)
    }
}

/**
 * Creates a RegistryScope to register objects under a modid.
 *
 * @param modid The modid to register under.
 * @param action The action.
 *
 * @author Peanuuutz
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegisterWithScope
 * */
@RegistryDsl
public inline fun registryScope(
    modid: String,
    action: RegistryScope.() -> Unit
) {
    RegistryScope(modid).apply(action)
}

/**
 * Registry DSL class, used to register objects more cleanly.
 *
 * @param modid The modid to register under.
 *
 * @author Oliver-makes-code (Emma)
 * */
public class RegistryScope(public val modid: String) {
    /**
     * Creates a RegistryObject.
     *
     * @param path The path to register under.
     *
     * @author Oliver-makes-code (Emma)
     * */
    @RegistryDsl
    public infix fun <T> T.withPath(path: String): RegistryObject<T> {
        return RegistryObject(modid, path, this)
    }

    /**
     * Applies a RegistryAction.
     *
     * @param action The action.
     *
     * @author Oliver-makes-code (Emma)
     * */
    @RegistryDsl
    public inline operator fun <T> Registry<T>.invoke(action: RegistryAction<T>.() -> Unit) {
        action(this).apply(action)
    }

    /**
     * Creates a RegistryAction
     *
     * @param registry The registry to register into
     *
     * @author Peanuuutz
     */
    public fun <T> action(registry: Registry<T>): RegistryAction<T> = RegistryAction(modid, registry)
}

/**
 * Applies a RegistryAction.
 *
 * @param modid The modid to register under.
 * @param action The action.
 *
 * @author Oliver-makes-code (Emma)
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegisterWithRegistry
 * */
@RegistryDsl
public inline operator fun <T> Registry<T>.invoke(modid: String, action: RegistryAction<T>.() -> Unit) {
    RegistryAction(modid, this).apply(action)
}

/**
 * Applies a RegistryAction with no default modid.
 *
 * @param action The action.
 *
 * @author Oliver-makes-code (Emma)
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegisterWithRegistry
 * */
@RegistryDsl
public inline operator fun <T> Registry<T>.invoke(action: RegistryAction<T>.() -> Unit) {
    RegistryAction(null, this).apply(action)
}

/**
 * Creates a RegistryObject.
 *
 * @param id The Identifier to register under.
 *
 * @author Oliver-makes-code (Emma)
 *
 * @sample samples.qkl.registry.RegistryDslSamples.sampleRegisterGlobally
 * */
@RegistryDsl
public infix fun <T> T.withId(id: Identifier): RegistryObject<T> {
    return RegistryObject(id.namespace, id.path, this)
}
