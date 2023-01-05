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

@file:Suppress("unused")

package org.quiltmc.qkl.library.registry

import com.mojang.serialization.Codec
import net.minecraft.util.Identifier
import net.minecraft.registry.Registry
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.registry.api.event.RegistryEntryContext
import org.quiltmc.qsl.registry.api.event.RegistryEvents
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment
import kotlin.reflect.KClass

/**
 * Get a callback when a given [registry] gets a new entry.
 *
 * @author sschr15
 */
public fun <V> EventRegistration.onRegistryEntryAdded(
    registry: Registry<V>,
    callback: (RegistryEntryContext<V>) -> Unit
) {
    RegistryEvents.getEntryAddEvent(registry).register(callback)
}

/**
 * Create a new [RegistryEntryAttachment] for the given [registry] with the given information.
 * Shorthands are available for primitive types and [String]s.
 */
public fun <R, V : Any> buildRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    valueClass: KClass<V>,
    codec: Codec<V>,
    block: RegistryEntryAttachment.Builder<R, V>.() -> Unit = {}
): RegistryEntryAttachment<R, V> {
    val builder = RegistryEntryAttachment.builder(registry, id, valueClass.java, codec)
    builder.block()
    return builder.build()
}

/**
 * Create a new boolean [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildBooleanRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Boolean>.() -> Unit = {}
): RegistryEntryAttachment<R, Boolean> {
    return buildRegistryEntryAttachment(registry, id, Boolean::class, Codec.BOOL, block)
}

/**
 * Create a new int [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildIntRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Int>.() -> Unit = {}
): RegistryEntryAttachment<R, Int> {
    return buildRegistryEntryAttachment(registry, id, Int::class, Codec.INT, block)
}

/**
 * Create a new String [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildStringRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, String>.() -> Unit = {}
): RegistryEntryAttachment<R, String> {
    return buildRegistryEntryAttachment(registry, id, String::class, Codec.STRING, block)
}

/**
 * Create a new long [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildLongRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Long>.() -> Unit = {}
): RegistryEntryAttachment<R, Long> {
    return buildRegistryEntryAttachment(registry, id, Long::class, Codec.LONG, block)
}

/**
 * Create a new float [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildFloatRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Float>.() -> Unit = {}
): RegistryEntryAttachment<R, Float> {
    return buildRegistryEntryAttachment(registry, id, Float::class, Codec.FLOAT, block)
}

/**
 * Create a new double [RegistryEntryAttachment] for the given [registry] with the given information.
 */
public fun <R> buildDoubleRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Double>.() -> Unit = {}
): RegistryEntryAttachment<R, Double> {
    return buildRegistryEntryAttachment(registry, id, Double::class, Codec.DOUBLE, block)
}
