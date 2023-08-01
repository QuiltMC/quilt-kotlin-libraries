/*
 * Copyright 2023 The Quilt Project
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

package org.quiltmc.qkl.library.resource

import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents.EndDataPackReload
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents.StartDataPackReload

public typealias DataPackReloadStart = StartDataPackReload.Context.() -> Unit

public typealias DataPackReloadFinish = EndDataPackReload.Context.() -> Unit

/**
 * Called when data pack reloading is about to begin.
 * @see ResourceLoaderEvents.START_DATA_PACK_RELOAD
 * @see ResourceLoaderEvents.StartDataPackReload.onStartDataPackReload
 *
 * @author sschr15
 */
public fun EventRegistration.onDataPackReloadStart(callback: DataPackReloadStart) {
    ResourceLoaderEvents.START_DATA_PACK_RELOAD.register(ResourceLoaderEvents.StartDataPackReload(callback))
}

/**
 * Called when data pack reloading has finished.
 * @see ResourceLoaderEvents.END_DATA_PACK_RELOAD
 * @see ResourceLoaderEvents.EndDataPackReload.onEndDataPackReload
 *
 * @author sschr15
 */
public fun EventRegistration.onDataPackReloadFinish(callback: DataPackReloadFinish) {
    ResourceLoaderEvents.END_DATA_PACK_RELOAD.register(ResourceLoaderEvents.EndDataPackReload(callback))
}
