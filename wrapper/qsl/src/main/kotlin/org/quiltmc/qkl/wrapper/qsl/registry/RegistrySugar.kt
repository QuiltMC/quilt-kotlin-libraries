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

package org.quiltmc.qkl.wrapper.qsl.registry

import com.mojang.serialization.Codec
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qkl.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.registry.api.event.RegistryEntryContext
import org.quiltmc.qsl.registry.api.event.RegistryEvents
import org.quiltmc.qsl.registry.attachment.api.RegistryEntryAttachment
import org.quiltmc.qsl.registry.attachment.api.RegistryExtensions
import org.quiltmc.qsl.registry.attachment.api.RegistryExtensions.BuiltinAttachmentBuilder
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
 * Register a [value] with the given [id] in this registry.
 *
 * @author sschr15
 */
public fun <V> Registry<in V>.register(
    id: Identifier,
    value: V,
    block: BuiltinAttachmentBuilder<in V>.() -> Unit
) {
    RegistryExtensions.register(this, id, value, block)
}

public fun <R, V : Any> buildRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    valueClass: KClass<V>,
    codec: Codec<V>,
    block: RegistryEntryAttachment.Builder<R, V>.() -> Unit
): RegistryEntryAttachment<R, V> {
    val builder = RegistryEntryAttachment.builder(registry, id, valueClass.java, codec)
    builder.block()
    return builder.build()
}

public fun <R> buildBooleanRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Boolean>.() -> Unit
): RegistryEntryAttachment<R, Boolean> {
    return buildRegistryEntryAttachment(registry, id, Boolean::class, Codec.BOOL, block)
}

public fun <R> buildIntRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Int>.() -> Unit
): RegistryEntryAttachment<R, Int> {
    return buildRegistryEntryAttachment(registry, id, Int::class, Codec.INT, block)
}

public fun <R> buildStringRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, String>.() -> Unit
): RegistryEntryAttachment<R, String> {
    return buildRegistryEntryAttachment(registry, id, String::class, Codec.STRING, block)
}

public fun <R> buildLongRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Long>.() -> Unit
): RegistryEntryAttachment<R, Long> {
    return buildRegistryEntryAttachment(registry, id, Long::class, Codec.LONG, block)
}

public fun <R> buildFloatRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Float>.() -> Unit
): RegistryEntryAttachment<R, Float> {
    return buildRegistryEntryAttachment(registry, id, Float::class, Codec.FLOAT, block)
}

public fun <R> buildDoubleRegistryEntryAttachment(
    registry: Registry<R>,
    id: Identifier,
    block: RegistryEntryAttachment.Builder<R, Double>.() -> Unit
): RegistryEntryAttachment<R, Double> {
    return buildRegistryEntryAttachment(registry, id, Double::class, Codec.DOUBLE, block)
}
