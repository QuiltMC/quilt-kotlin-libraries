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

package org.quiltmc.qkl.wrapper.minecraft.serialization.internal.encoder

import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.ElementOptions
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.SerializationConfig
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.SerializationState

internal sealed class EncoderState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : SerializationState<T>(serializationConfig) {

    //whether encoding is done and build should be called
    abstract fun addElement(element: T)

    open fun addNull(nullElement: T) {
        return addElement(nullElement)
    }

    abstract fun build(): T
}

internal sealed class StructuredEncoderState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : EncoderState<T>(serializationConfig) {

    //null == skip element
    abstract fun beforeStructureElement(descriptor: SerialDescriptor, index: Int): ElementOptions?
}

internal sealed class SingleValueState<T : Any>(
    serializationConfig: SerializationConfig<T>
) : EncoderState<T>(serializationConfig) {
    protected lateinit var result: T

    open val elementOptions: ElementOptions = ElementOptions()

    override fun addElement(element: T) {
        result = element
    }

    override fun getElementTrace(): String? {
        return null
    }
}
