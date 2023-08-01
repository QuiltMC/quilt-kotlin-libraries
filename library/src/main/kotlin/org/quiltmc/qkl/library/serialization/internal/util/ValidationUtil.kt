/*
 * Copyright 2022 The Quilt Project
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

package org.quiltmc.qkl.library.serialization.internal.util

import com.mojang.serialization.DynamicOps
import com.mojang.serialization.MapLike
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.modules.SerializersModule
import org.quiltmc.qkl.library.serialization.ExtendedDynamicOps
import org.quiltmc.qkl.library.serialization.options.CodecOptions

internal fun <T : Any> collectInvalidKeys(
    map: MapLike<T>,
    ops: DynamicOps<T>,
    validKeys: Set<String>
): List<String> {
    val keys = map.entries().map { ops.getStringValue(it.first).result().get() }.toList()
    return keys - validKeys
}

@OptIn(ExperimentalSerializationApi::class)
internal fun validatePolymorphicFields(
    module: SerializersModule,
    descriptor: SerialDescriptor,
    classDiscriminator: String
) {
    val childDescriptors = when (descriptor.kind) {
        PolymorphicKind.OPEN -> module.getPolymorphicDescriptors(descriptor)
        PolymorphicKind.SEALED -> descriptor.getElementDescriptor(1).elementDescriptors
        else -> return
    }
    for (child in childDescriptors) {
        if (classDiscriminator in child.elementNames) {
            throw IllegalArgumentException(
                "Field '$classDiscriminator' in type '${child.serialName}' conflicts with the selected " +
                "class discriminator of its base type '${descriptor.serialName}. Change discriminator in options or " +
                "base type's @CodecSerializable, disable flattened polymorphic encoding, rename the field, " +
                "or annotate it with @SerialName with a non-conflicting name"
            )
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal fun validateNullableInline(descriptor: SerialDescriptor, options: CodecOptions) {
    if (descriptor.useInlineWrapper ?: options.useInlineWrappers) {
        return
    }

    if (descriptor.getElementDescriptor(0).isNullable) {
        throw IllegalArgumentException(
            "Encoding a nullable inline class with a nullable backing field without a wrapper is ambiguous. " +
            "Enable wrappers for type ${descriptor.serialName} using CodecSerializable#useInlineWrapper " +
            "or globally using CodecOptions#useInlineWrappers"
        )
    }
}

@OptIn(ExperimentalSerializationApi::class)
internal fun validateKeyDescriptor(
    keyDescriptor: SerialDescriptor,
    module: SerializersModule,
    acceptedElements: ExtendedDynamicOps.ElementSupport
) {
    if (keyDescriptor.isNullable) {
        throw IllegalArgumentException(
            "Regular maps do not support nullable keys"
        )
    }

    if (acceptedElements == ExtendedDynamicOps.ElementSupport.ANY) {
        return
    }

    //check that keys are primitives or codecs
    val carrierDescriptor = keyDescriptor.carrierDescriptor(module)
    val keyKind = carrierDescriptor.kind

    if (
        keyKind !is PrimitiveKind &&
        keyKind != SerialKind.ENUM &&
        !carrierDescriptor.isCodec
    ) {
        throw IllegalArgumentException(
            "Regular maps do not support keys of kind $keyKind, " +
            "enable useEntryListMaps or annotate class property with @CodecEntryListMap"
        )
    }

}

@OptIn(ExperimentalSerializationApi::class)
private fun SerialDescriptor.carrierDescriptor(module: SerializersModule): SerialDescriptor {
    return when {
        kind == SerialKind.CONTEXTUAL -> module.getContextualDescriptor(this)?.carrierDescriptor(module) ?: this
        isInline -> getElementDescriptor(0).carrierDescriptor(module)
        else -> this
    }
}
