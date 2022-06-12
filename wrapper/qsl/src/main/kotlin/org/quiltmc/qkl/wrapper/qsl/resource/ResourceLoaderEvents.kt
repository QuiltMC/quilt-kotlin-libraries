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

package org.quiltmc.qkl.wrapper.qsl.resource

import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import org.quiltmc.qkl.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents

public typealias DataPackReloadStart = (
    /**
     * The current server, or `null` if this is the first load.
     */
    server: MinecraftServer?,
    /**
     * The resource manager that is being reloaded, or `null` if this is the first load.
     */
    resourceManager: ResourceManager?,
) -> Unit

public typealias DataPackReloadFinish = (
    /**
     * The current server, or `null` if this is the first load.
     */
    server: MinecraftServer?,
    /**
     * The resource manager that is being reloaded, or `null` if this is the first load.
     */
    resourceManager: ResourceManager?,
    /**
     * An error that occurred during the reload, or `null` if no error occurred.
     */
    error: Throwable?,
) -> Unit

/**
 * Called when data pack reloading is about to begin.
 * The [callback]'s parameters will only be `null` on the first load.
 * @see ResourceLoaderEvents.START_DATA_PACK_RELOAD
 * @see ResourceLoaderEvents.StartDataPackReload.onStartDataPackReload
 */
public fun EventRegistration.onDataPackReloadStart(callback: DataPackReloadStart) {
    ResourceLoaderEvents.START_DATA_PACK_RELOAD.register(
        ResourceLoaderEvents.StartDataPackReload(callback)
    )
}

/**
 * Called when data pack reloading has finished.
 * The [callback]'s parameters will only be `null` on the first load.
 * @see ResourceLoaderEvents.END_DATA_PACK_RELOAD
 * @see ResourceLoaderEvents.EndDataPackReload.onEndDataPackReload
 */
public fun EventRegistration.onDataPackReloadFinish(callback: DataPackReloadFinish) {
    ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(
        ResourceLoaderEvents.EndDataPackReload(callback)
    )
}
