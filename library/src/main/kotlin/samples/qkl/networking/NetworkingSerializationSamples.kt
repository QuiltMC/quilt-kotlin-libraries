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

package samples.qkl.networking

import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Identifier
import org.quiltmc.qkl.library.networking.serialization.*

@Suppress("unused", "UNUSED_VARIABLE")
private object NetworkingSerializationSamples {
    val age = 19

    fun <T> stub(): T {
        TODO()
    }

    fun example() {
        // Define the data
        val person = TestPerson(
            "Silver",
            age,
            null,
            setOf(
                Pronouns("it/its"),
                Pronouns("she/her")
            )
        )

        // Encode to a packet byte buf
        val asByteBuf = PacketByteBufEncoder.encode(person)
        // or, if you need more control such as a serializers module
        val asByteBuf2 = PacketByteBufEncoder(serializersModule = stub()).let {
            it.encodeSerializableValue(serializer(), person)
            it.packetByteBuf
        }

        // Read it from a packet byte buf (in this case, the one we just created)
        val andBackAgain = PacketByteBufDecoder.decode<TestPerson>(asByteBuf)
        // or, if you need more control such as a serializers module
        val andBackAgain2 = PacketByteBufDecoder(asByteBuf, serializersModule = stub())
            .decodeSerializableValue<TestPerson>(serializer())
        assert(person == andBackAgain)

        // Register a bi-directional packet of type `TestPerson`
        // that can be send both ways on the `example:packet` channel
        registerSerializedPacket<TestPerson>(
            Identifier("example", "packet"),
            SerializedPacketRegistration.Direction.BiDirectional
        ) {
            // When we get a packet on the client
            onClientReceive { minecraftClient, clientPlayNetworkHandler, packetSender ->
                println("Got person on client! $this")
            }

            // When we get a packet on the server
            onServerReceive { minecraftServer, serverPlayerEntity, serverPlayNetworkHandler, packetSender ->
                println("Got person on server! $this")
            }
        }

        // Send the created data from the client to the server
        SerializedClientPlayNetworking.send(Identifier("example", "packet"), person)
        // or vice versa
        SerializedServerPlayNetworking.send(stub<ServerPlayerEntity>(), Identifier("example", "packet"), person)
    }

    @Serializable
    data class Pronouns(val value: String)

    @Serializable
    data class TestPerson(val name: String, val age: Int, val gender: ByteArray?, val pronouns: Set<Pronouns>)
}
