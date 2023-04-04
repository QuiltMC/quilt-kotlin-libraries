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

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractDecoder
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import net.minecraft.network.PacketByteBuf

/**
 * An [AbstractDecoder] that is capable of reading a class from a packet byte buf
 */
@OptIn(ExperimentalSerializationApi::class)
public class PacketByteBufDecoder(
    public val input: PacketByteBuf,
    override val serializersModule: SerializersModule = EmptySerializersModule()
) : AbstractDecoder() {
    private val byteArraySerializer = serializer<ByteArray>()
    private var elementIndex = 0
    override fun decodeSequentially(): Boolean = true

    override fun <T> decodeSerializableValue(deserializer: DeserializationStrategy<T>): T {
        if (deserializer.descriptor == byteArraySerializer.descriptor) {
            @Suppress("UNCHECKED_CAST")
            return input.readByteArray() as T
        } else {
            return super.decodeSerializableValue(deserializer)
        }
    }

    override fun decodeElementIndex(descriptor: SerialDescriptor): Int {
        if (input.readerIndex() >= input.readableBytes()) return CompositeDecoder.DECODE_DONE
        return elementIndex++
    }

    override fun decodeBoolean(): Boolean {
        return input.readBoolean()
    }
    override fun decodeByte(): Byte {
        return input.readByte()
    }
    override fun decodeChar(): Char {
        return input.readChar()
    }
    override fun decodeShort(): Short {
        return input.readShort()
    }
    override fun decodeInt(): Int {
        return input.readInt()
    }
    override fun decodeLong(): Long {
        return input.readLong()
    }
    override fun decodeFloat(): Float {
        return input.readFloat()
    }
    override fun decodeDouble(): Double {
        return input.readDouble()
    }
    override fun decodeString(): String {
        return input.readString()
    }
    override fun decodeEnum(enumDescriptor: SerialDescriptor): Int {
        return input.readVarInt()
    }

    override fun decodeNotNullMark(): Boolean {
        return input.readBoolean()
    }

    override fun decodeCollectionSize(descriptor: SerialDescriptor): Int {
        return input.readVarInt()
    }

    public companion object {
        /**
         * Decodes a [PacketByteBuf] to an instance of [T]
         */
        public inline fun <reified T> decode(
            packetByteBuf: PacketByteBuf,
            serializersModule: SerializersModule = EmptySerializersModule()
        ): T {
            return PacketByteBufDecoder(packetByteBuf, serializersModule).decodeSerializableValue(serializer())
        }
    }
}
