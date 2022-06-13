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
@file:Environment(EnvType.CLIENT)

package org.quiltmc.qkl.wrapper.qsl.client.resource

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.MinecraftClient
import net.minecraft.resource.ResourceManager
import org.quiltmc.qsl.resource.loader.api.client.ClientResourceLoaderEvents

public typealias ReloadStart = MinecraftClient.(
    manager: ResourceManager,
    isFirstLoad: Boolean
) -> Unit

public typealias ReloadFinish = MinecraftClient.(
    manager: ResourceManager,
    isFirstLoad: Boolean,
    /**
     * The error if the reload failed, or `null` if it succeeded.
     */
    error: Throwable?
) -> Unit

/**
 * @see ClientResourceLoaderEvents.START_RESOURCE_PACK_RELOAD
 * @see ClientResourceLoaderEvents.StartResourcePackReload.onStartResourcePackReload
 *
 * @author sschr15
 */
public fun onResourcePackReloadStart(callback: ReloadStart) {
    ClientResourceLoaderEvents.START_RESOURCE_PACK_RELOAD.register(
        ClientResourceLoaderEvents.StartResourcePackReload(callback)
    )
}

/**
 * @see ClientResourceLoaderEvents.END_RESOURCE_PACK_RELOAD
 * @see ClientResourceLoaderEvents.EndResourcePackReload.onEndResourcePackReload
 *
 * @author sschr15
 */
public fun onResourcePackReloadFinish(callback: ReloadFinish) {
    ClientResourceLoaderEvents.END_RESOURCE_PACK_RELOAD.register(
        ClientResourceLoaderEvents.EndResourcePackReload(callback)
    )
}
