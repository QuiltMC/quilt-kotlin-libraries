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

package org.quiltmc.qkl.library.serialization.internal.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import org.quiltmc.qkl.library.serialization.annotation.CodecEntryListMap
import org.quiltmc.qkl.library.serialization.annotation.CodecSerializable
import org.quiltmc.qkl.library.serialization.internal.CodecSerializerAdapter

@OptIn(ExperimentalSerializationApi::class)
internal val SerialDescriptor.codecAnnotation: CodecSerializable?
    get() = annotations.filterIsInstance<CodecSerializable>().firstOrNull()


@OptIn(ExperimentalSerializationApi::class)
internal val SerialDescriptor.codecPolymorphicAnnotation: CodecSerializable.Polymorphic?
    get() = annotations.filterIsInstance<CodecSerializable.Polymorphic>().firstOrNull()


@OptIn(ExperimentalSerializationApi::class)
internal val SerialDescriptor.isCodec: Boolean
    get() = annotations.any { it is CodecSerializerAdapter.Marker }

@OptIn(ExperimentalSerializationApi::class)
internal fun SerialDescriptor.useEntryListMapForElement(index: Int): Boolean {
    return getElementAnnotations(index).any { it is CodecEntryListMap }
}


internal val SerialDescriptor.classDiscriminator: String?
    get() = codecPolymorphicAnnotation?.classDiscriminator?.takeIf { it.isNotEmpty() }

internal val SerialDescriptor.useInlineWrapper: Boolean?
    get() = codecAnnotation?.useInlineWrapper?.toBoolean()

internal val SerialDescriptor.encodeDefaults: Boolean?
    get() = codecAnnotation?.encodeDefaults?.toBoolean()

internal val SerialDescriptor.flattenPolymorphic: Boolean?
    get() = codecPolymorphicAnnotation?.flatten?.toBoolean()
