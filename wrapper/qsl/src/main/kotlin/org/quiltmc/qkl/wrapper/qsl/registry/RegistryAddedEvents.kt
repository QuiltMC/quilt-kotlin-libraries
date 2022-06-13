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

import net.minecraft.util.registry.Registry
import org.quiltmc.qkl.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.registry.api.event.RegistryEntryContext
import org.quiltmc.qsl.registry.api.event.RegistryEvents

public fun <V> EventRegistration.onRegistryEntryAdded(
    registry: Registry<V>,
    callback: (RegistryEntryContext<V>) -> Unit
) {
    RegistryEvents.getEntryAddEvent(registry).register(callback)
}
