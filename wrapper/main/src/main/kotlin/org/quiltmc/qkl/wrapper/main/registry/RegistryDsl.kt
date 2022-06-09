package org.quiltmc.qkl.wrapper.main.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * Registry DSL class, used to register object more cleanly
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
     * A RegistryObject with a name parameter, registers under your modid
     * @param name The name to register under
     * @param t The object to register
     *
     * @author Oliver-makes-code (Emma)
     * */
    public data class RegistryObject<T>(val name: String, val t: T)
    /**
     * Registers a RegistryObject
     * @param registry The registry to register to
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun <T> RegistryObject<T>.toRegistry(registry: Registry<T>): T {
        return Registry.register(registry, Identifier(modid,this.name), this.t)
    }
    /**
     * Creates a RegistryObject
     * @param name The name to register under
     *
     * @author Oliver-makes-code (Emma)
     * */
    public infix fun <T> T.withName(name: String): RegistryObject<T> {
        return RegistryObject(name, this)
    }
}
/**
 * A RegistryObject with a name parameter, registers under your modid
 * @param id The Identifier to register under
 * @param t The object to register
 *
 * @author Oliver-makes-code (Emma)
 * */
public data class RegistryObject<T>(val id: Identifier, val t: T)
public operator fun <T> Registry<T>.invoke(action: Registry<T>.() -> Unit) {
    apply(action)
}
/**
 * Registers a RegistryObject
 * @param registry The registry to register to
 *
 * @author Oliver-makes-code (Emma)
 * */
public infix fun <T> RegistryObject<T>.toRegistry(registry: Registry<T>): T {
    return Registry.register(registry, this.id, this.t)
}
/**
 * Creates a RegistryObject
 * @param id The Identifier to register under
 *
 * @author Oliver-makes-code (Emma)
 * */
public infix fun <T> T.withId(id: Identifier): RegistryObject<T> {
    return RegistryObject(id, this)
}