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

package samples.qkl.brigadier

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.argument.PosArgument
import net.minecraft.command.argument.Vec3ArgumentType
import net.minecraft.network.MessageType
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.math.Vec3d
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*
import org.quiltmc.qkl.wrapper.minecraft.brigadier.argument.*
import kotlin.random.Random

/**
 * Container for samples. Functions can be referenced
 * by their full name in the @sample KDoc tag to
 * inject the body as a documentation sample.
 *
 * @author Cypher121
 */
@Suppress("unused")
private object BrigadierDslSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    fun sampleRegisterCommand() {
        val dispatcher: CommandDispatcher<ServerCommandSource> = stub()

        dispatcher.register("echo") {
            //register arguments with extension methods
            required(string("message")) { message -> // treat the argument as a key
                required(boolean("toCaps")) { getToCaps -> // or as an accessor
                    //standard builder methods can be called as normal
                    requires {
                        it.player.experienceLevel > Random.nextInt()
                    }

                    //execute the command with an extension method
                    execute { context ->
                        val response = if (context.getToCaps().value()) {
                            context[message].value().uppercase()
                        } else {
                            context[message].value()
                        }

                        context.source.player.sendMessage(Text.literal(response), MessageType.SYSTEM)
                    }
                }
            }
        }
    }

    fun sampleCustomCommandSource() {
        //some special command source your code needs
        //e.g. commands coming from external integrations
        abstract class CustomSource {
            abstract fun calculateVec3(pos: PosArgument): Vec3d

            abstract fun useVec3ForSomething(vec: Vec3d)
        }

        //can use any name including `value`
        //`custom` added for clarity
        fun ArgumentReader<CustomSource, DefaultArgumentDescriptor<Vec3ArgumentType>>.customValue(): Vec3d =
            context.source.calculateVec3(posArgument())

        val dispatcher: CommandDispatcher<CustomSource> = stub()

        dispatcher.register("customSourceCommand") {
            required(vec3("pos")) { getPos ->
                execute {
                    val vec = it.getPos().customValue()

                    it.source.useVec3ForSomething(vec)
                }
            }
        }
    }

    fun sampleCommandWithResult(dispatcher: CommandDispatcher<Any>) {
        dispatcher.register("repeat") {
            required(string("string")) { getString ->
                required(integer("times")) { getTimes ->
                    execute {
                        val times = it[getTimes].value()

                        if (times % 2 == 1) {
                            CommandResult.Failure(Text.literal("times must be even"))
                        } else {
                            repeat(times) { _ ->
                                println(it.getString().value())
                            }

                            CommandResult.Success(times)
                        }
                    }
                }
            }
        }
    }

    fun sampleCommandWithOptionals(dispatcher: CommandDispatcher<ServerCommandSource>) {
        dispatcher.register("slap") {
            optional(player("target")) { target ->
                optional(enum(
                    "weapon",
                    mapOf(
                        "fish" to "a fish",
                        "book" to "an entire book"
                    )
                )) { weapon ->
                    optional(literal("repeatedly")) { repeatedly ->
                        execute {
                            //operator access is nullable only for optionals
                            val targetName = it[target]?.value()?.displayName ?:
                                Text.literal("themselves")

                            it.source.server.playerManager.broadcastSystemMessage(
                                Text.empty().apply {
                                    append(it.source.player.displayName)
                                    append(Text.literal(" slaps "))
                                    append(targetName)

                                    //checking the accessor directly works as well
                                    if (weapon != null) {
                                        append(Text.literal(" with ${it[weapon].value()}"))
                                    }

                                    if (repeatedly != null) {
                                        append(Text.literal(" repeatedly"))
                                    }
                                },
                                MessageType.SAY_COMMAND
                            )
                        }
                    }
                }
            }
        }
    }
}
