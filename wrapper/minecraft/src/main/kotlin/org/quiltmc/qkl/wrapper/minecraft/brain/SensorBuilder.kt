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

package org.quiltmc.qkl.wrapper.minecraft.brain

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.sensor.Sensor
import net.minecraft.entity.ai.brain.sensor.SensorType
import net.minecraft.server.world.ServerWorld
import org.quiltmc.qkl.wrapper.minecraft.mixin.brain.SensorTypeAccessor


@BrainDsl
public class SensorBuilder<E : LivingEntity> {
    private lateinit var sensor: (ServerWorld, E) -> Unit
    public val outputMemories: MutableSet<MemoryModuleType<*>> = mutableSetOf()

    @BrainDsl
    public fun outputMemories(vararg memories: MemoryModuleType<*>) {
        outputMemories += memories
    }

    @BrainDsl
    public fun sense(sensor: (world: ServerWorld, entity: E) -> Unit) {
        this.sensor = sensor
    }

    public fun build(): SensorType<Sensor<in E>> {
        val memories = outputMemories.toSet()

        if (!this::sensor.isInitialized) {
            throw IllegalStateException("Sensor has not been provided with a sense block")
        }

        return SensorTypeAccessor.create {
            object : Sensor<E>() {
                override fun sense(world: ServerWorld, entity: E) {
                    sensor(world, entity)
                }

                override fun getOutputMemoryModules(): Set<MemoryModuleType<*>> {
                    return memories
                }
            }
        }
    }
}

@BrainDsl
public fun <E : LivingEntity> sensor(config: SensorBuilder<E>.() -> Unit): SensorType<Sensor<in E>> {
    return SensorBuilder<E>().apply(config).build()
}