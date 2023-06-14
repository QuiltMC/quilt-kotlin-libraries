/*
 * Copyright 2023 QuiltMC
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
@file:ClientOnly

package org.quiltmc.qkl.library.client.resource

import org.quiltmc.loader.api.minecraft.ClientOnly
import org.quiltmc.qsl.resource.loader.api.client.ClientResourceLoaderEvents
import org.quiltmc.qsl.resource.loader.api.client.ClientResourceLoaderEvents.EndResourcePackReload
import org.quiltmc.qsl.resource.loader.api.client.ClientResourceLoaderEvents.StartResourcePackReload

public typealias ReloadStart = StartResourcePackReload.Context.() -> Unit

public typealias ReloadFinish = EndResourcePackReload.Context.() -> Unit

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
