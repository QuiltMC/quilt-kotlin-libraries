package org.quiltmc.qkl.wrapper.main.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * A RegistryObject with a name parameter, registers under your modid
 * @param modid The modid to register under
 * @param name The name to register under
 * @param t The object to register
 *
 * @author Oliver-makes-code (Emma)
 * */
public data class RegistryObject<T>(val modid: String, val name: String, val t: T) {
    /**
     * Registers a RegistryObject
     * @param registry The registry to register to
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun toRegistry(registry: Registry<T>): T {
        return Registry.register(registry, Identifier(modid, name), this.t)
    }
}

/**
 * Represents an action with a registry
 * @param modid The modid to register under
 * @param registry The registry to register to
 *
 * @author Oliver-makes-code (Emma)
 * */
public data class RegistryAction<T>(val modid: String?, val registry: Registry<T>) {
    /**
     * Registers an object with a given Identifier
     * @param id The Identifier to register under
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun T.withId(id: Identifier) {
        Registry.register(registry, id, this)
    }

    /**
     * Registers an object with a given name
     *
     * If no modid is present, it uses `"modid:name"` format
     * @param name The name to register under
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun T.withName(name: String) {
        if (modid == null) {
            Registry.register(registry, Identifier(name), this)
            return
        }
        Registry.register(registry, Identifier(modid, name), this)
    }
}

/**
 * Registry DSL class, used to register objects more cleanly
 * @param modid The modid you want to register under
 * @param action The DSL action
 *
 * @author Oliver-makes-code (Emma)
 * */
public class RegistryDsl(private val modid: String, action: RegistryDsl.() -> Unit) {
    init {
        apply(action)
    }

    /**
     * Creates a RegistryObject
     * @param name The name to register under
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun <T> T.withName(name: String): RegistryObject<T> {
        return RegistryObject(modid, name, this)
    }

    /**
     * Applies a RegistryAction
     * @param action The action
     *
     * @author Oliver-makes-code (Emma)
     * */
    public operator fun <T> Registry<T>.invoke(action: RegistryAction<T>.() -> Unit) {
        RegistryAction(modid, this).apply(action)
    }
}

/**
 * Applies a RegistryAction
 * @param modid The modid to register under
 * @param action The action
 *
 * @author Oliver-makes-code (Emma)
 * */
public operator fun <T> Registry<T>.invoke(modid: String, action: RegistryAction<T>.() -> Unit) {
    RegistryAction(modid, this).apply(action)
}

/**
 * Applies a RegistryAction
 * @param action The action
 *
 * @author Oliver-makes-code (Emma)
 * */
public operator fun <T> Registry<T>.invoke(action: RegistryAction<T>.() -> Unit) {
    RegistryAction(null, this).apply(action)
}

/**
 * Creates a RegistryObject
 * @param id The Identifier to register under
 *
 * @author Oliver-makes-code (Emma)
 * */
public infix fun <T> T.withId(id: Identifier): RegistryObject<T> {
    return RegistryObject(id.namespace, id.path, this)
}