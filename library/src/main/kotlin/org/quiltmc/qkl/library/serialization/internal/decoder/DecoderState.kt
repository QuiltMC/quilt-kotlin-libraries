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

package org.quiltmc.qkl.library.serialization.internal.decoder

import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.library.serialization.internal.ElementOptions
import org.quiltmc.qkl.library.serialization.internal.SerializationConfig
import org.quiltmc.qkl.library.serialization.internal.SerializationState

internal sealed class DecoderState<T : Any>(serializationConfig: SerializationConfig<T>) :
    SerializationState<T>(serializationConfig) {
    open val collectionSize: Int? = null

    abstract val isStructure: Boolean

    abstract fun getNextIndex(descriptor: SerialDescriptor): Int

    abstract fun getElement(): Pair<T, ElementOptions>

    open fun onComplete() {}
}
