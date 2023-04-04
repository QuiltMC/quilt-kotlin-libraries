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

package org.quiltmc.qkl.library.networking.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.minecraft.network.PacketByteBuf
import org.quiltmc.qsl.networking.api.PacketByteBufs

/**
 * An [AbstractDecoder] that is capable of writing a class to a packet byte buf
 */
@OptIn(ExperimentalSerializationApi::class)
public class PacketByteBufEncoder(
    override val serializersModule: SerializersModule = EmptySerializersModule()
) : AbstractEncoder() {
    private val byteArraySerializer = serializer<ByteArray>()

    public var packetByteBuf: PacketByteBuf = PacketByteBufs.create()

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (serializer.descriptor == byteArraySerializer.descriptor) {
            packetByteBuf.writeByteArray(value as ByteArray)
        } else {
            super.encodeSerializableValue(serializer, value)
        }
    }

    override fun encodeBoolean(value: Boolean) {
        packetByteBuf.writeBoolean(value)
    }

    override fun encodeByte(value: Byte) {
        packetByteBuf.writeByte(value.toInt())
    }

    override fun encodeChar(value: Char) {
        packetByteBuf.writeChar(value.code)
    }

    override fun encodeShort(value: Short) {
        packetByteBuf.writeShort(value.toInt())
    }

    override fun encodeInt(value: Int) {
        packetByteBuf.writeInt(value)
    }

    override fun encodeLong(value: Long) {
        packetByteBuf.writeLong(value)
    }

    override fun encodeFloat(value: Float) {
        packetByteBuf.writeFloat(value)
    }

    override fun encodeDouble(value: Double) {
        packetByteBuf.writeDouble(value)
    }

    override fun encodeString(value: String) {
        packetByteBuf.writeString(value)
    }

    @ExperimentalSerializationApi
    override fun encodeNotNullMark() {
        encodeBoolean(true)
    }

    @ExperimentalSerializationApi
    override fun encodeNull() {
        encodeBoolean(false)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        packetByteBuf.writeVarInt(index)
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        packetByteBuf.writeVarInt(collectionSize)
        return this
    }
}
