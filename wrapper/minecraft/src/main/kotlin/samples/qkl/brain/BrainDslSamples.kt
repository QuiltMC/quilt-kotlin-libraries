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

@file:Suppress("LocalVariableName")

package samples.qkl.brain

import com.mojang.serialization.Dynamic
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.Activity
import net.minecraft.entity.ai.brain.Brain
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.sensor.SensorType
import net.minecraft.entity.ai.brain.task.HideInHomeTask
import net.minecraft.entity.ai.brain.task.RingBellTask
import net.minecraft.entity.passive.VillagerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.quiltmc.qkl.wrapper.minecraft.brain.buildBrain
import org.quiltmc.qkl.wrapper.minecraft.brain.schedule
import org.quiltmc.qkl.wrapper.minecraft.brain.sensor

/**
 * Container for samples. Functions can be referenced
 * by their full name in the @sample KDoc tag to
 * inject the body as a documentation sample.
 *
 * @author Cypher121
 */
@Suppress("unused")
private object BrainDslSamples {
    fun <T> stub(): T {
        error("Sample utility should not be called")
    }

    fun sampleRegisterSensor() {
        val CUSTOM_SENSOR_TYPE = sensor<VillagerEntity> {
            outputMemories(MemoryModuleType.PATH, MemoryModuleType.GOLEM_DETECTED_RECENTLY)

            sense { world, entity ->
                //sensor actions go here
                if (world.isFlat) {
                    entity.brain.forget(MemoryModuleType.PATH)
                    entity.brain.remember(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true)
                }
            }
        }

        Registry.register(Registry.SENSOR_TYPE, Identifier("mod_id:custom_sensor"), CUSTOM_SENSOR_TYPE)
    }

    fun sampleRegisterSchedule() {
        val CUSTOM_SCHEDULE = schedule {
            10 - Activity.IDLE

            3000 - Activity.PLAY_DEAD

            6000 - Activity.PANIC

            10000 - Activity.HIDE

            12000 - Activity.REST
        }

        Registry.register(Registry.SCHEDULE, Identifier("mod_id:custom_activity"), CUSTOM_SCHEDULE)
    }

    fun sampleCreateBrain() {
        fun deserializeBrain(data: Dynamic<*>): Brain<LivingEntity> {
            return buildBrain(data) {
                memories(MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.HIDING_PLACE)

                sensors(SensorType.GOLEM_DETECTED, SensorType.NEAREST_PLAYERS)

                defaultActivity(Activity.CORE) {

                }

                activity(Activity.HIDE) {
                    requiresMemories {
                        MemoryModuleType.HIDING_PLACE.toBePresent()
                    }

                    forgets(MemoryModuleType.PATH)

                    tasks {
                        0 - HideInHomeTask(32, 1.25f, 2)

                        priority(1) {
                            -RingBellTask()
                        }
                    }
                }

                coreActivity(Activity.CORE)
            }
        }
    }
}