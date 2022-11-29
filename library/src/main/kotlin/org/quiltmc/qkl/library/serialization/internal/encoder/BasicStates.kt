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

@file:OptIn(ExperimentalSerializationApi::class)

package org.quiltmc.qkl.library.serialization.internal.encoder

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.library.serialization.internal.ElementOptions
import org.quiltmc.qkl.library.serialization.internal.SerializationConfig

//TODO another pass over all states to see if everything is forwarded right
//     also do a test on inline and nullable map keys and non-primitive maps!
internal class RootState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : SingleValueState<T>(serializationConfig) {
    override fun build(): T {
        return result
    }
}

internal class NullableState<T : Any>(
    parentOptions: ElementOptions,
    serializationConfig: SerializationConfig<T>
) : SingleValueState<T>(serializationConfig) {
    override val elementOptions = parentOptions

    override fun build(): T {
        return extendedOps.wrapNullable(result)
    }
}

internal class ObjectState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : StructuredEncoderState<T>(serializationConfig) {
    override fun addElement(element: T) {
        throw IllegalStateException("Object encoders must not contain elements")
    }

    override fun build(): T {
        return ops.emptyMap()
    }

    override fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions? {
        throw IllegalStateException("Object encoders must not contain elements")
    }

    override fun getElementTrace(): String? {
        return null
    }
}
