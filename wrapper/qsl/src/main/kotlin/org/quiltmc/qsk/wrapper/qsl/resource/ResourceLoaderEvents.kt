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

package org.quiltmc.qsk.wrapper.qsl.resource

import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents

public fun EventRegistration.onDataPackReloadStart(
    callback: (server: MinecraftServer?, manager: ResourceManager?) -> Unit
) {
    ResourceLoaderEvents.START_DATA_PACK_RELOAD.register(
        ResourceLoaderEvents.StartDataPackReload(callback)
    )
}

public fun EventRegistration.onDataPackReloadFinish(
    callback: (server: MinecraftServer?, manager: ResourceManager?, error: Throwable?) -> Unit
) {
    ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(
        ResourceLoaderEvents.EndDataPackReload(callback)
    )
}
