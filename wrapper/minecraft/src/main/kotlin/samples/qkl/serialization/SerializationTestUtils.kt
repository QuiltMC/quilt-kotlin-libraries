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

package samples.qkl.serialization

import com.google.gson.JsonParser
import com.mojang.serialization.Codec
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.JsonOps
import org.quiltmc.qkl.wrapper.minecraft.serialization.internal.util.orNull
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.jvm.isAccessible

internal object SerializationTestUtils {
    fun <T> encodesToJson(codec: Codec<T>, value: T, json: String): Boolean {
        return codec.encodeStart(JsonOps.INSTANCE, value).orNull() ==
                JsonParser.parseString(json)
    }

    fun <T> decodesFromJson(codec: Codec<T>, value: T, json: String): Boolean {
        return codec.decode(JsonOps.INSTANCE, JsonParser.parseString(json))
            .orNull()?.first == value
    }

    fun <T> failsToEncodeJson(codec: Codec<T>, value: T): Boolean {
        return codec.encodeStart(JsonOps.INSTANCE, value).error().isPresent
    }

    fun failsToDecodeJson(codec: Codec<*>, json: String): Boolean {
        return codec.decode(JsonOps.INSTANCE, JsonParser.parseString(json))
            .error().isPresent
    }

    fun <T> identicalAfterEncoding(
        codec: Codec<T>,
        value: T,
        ops: DynamicOps<*> = JsonOps.INSTANCE
    ): Boolean {
        fun <A> typedOpsWrapper(ops: DynamicOps<A>): Boolean {
            val encoded = codec.encodeStart(ops, value).orNull() ?: return false

            return codec.decode(ops, encoded).orNull()?.first == value
        }

        return typedOpsWrapper(ops)
    }
}

//TODO running things from IDE is broken due to circular loom deps so making this public and running it from REPL
//     is the best way to run all tests. Ideally this whole thing should be in JUnit tests or some other engine.
public fun runSerializationTests() {
    val classes = listOf("Basic", "Nullable", "Polymorphic", "Map").map {
        "samples.qkl.serialization.${it}SerializationTests"
    }

    classes.forEach {
        val kclass = try {
            Class.forName(it).kotlin
        } catch (_: Exception) {
            println("No class $it found    ")
            return@forEach
        }

        val inst = try {
            val field = kclass.java.getDeclaredField("INSTANCE")
            field.isAccessible = true

            field.get(null)
        } catch (_: Exception) {
            return@forEach
        }

        kclass.memberFunctions.forEach { fn ->
            fn.isAccessible = true

            if ("test" in fn.name) {
                println("Running $it:${fn.name}    ")
                fn.call(inst)
            }
        }
    }
}
