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

import com.mojang.serialization.Dynamic
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.Activity
import net.minecraft.entity.ai.brain.Brain
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.sensor.Sensor
import net.minecraft.entity.ai.brain.sensor.SensorType

@DslMarker
public annotation class BrainDsl

@BrainDsl
public class BrainBuilder<E : LivingEntity> {
    private val memories: MutableSet<MemoryModuleType<*>> = mutableSetOf()
    private val sensors: MutableList<SensorType<out Sensor<in E>>> = mutableListOf()
    private val activities: MutableMap<Activity, ActivityBuilder<E>> = mutableMapOf()
    private val coreActivities: MutableSet<Activity> = mutableSetOf()
    private var defaultActivity: Activity? = null

    @BrainDsl
    public fun memories(vararg addedMemories: MemoryModuleType<*>) {
        memories += addedMemories
    }

    @BrainDsl
    public fun sensors(vararg types: SensorType<out Sensor<in E>>) {
        sensors += types
        memories += types.flatMap { it.create().outputMemoryModules }
    }

    @BrainDsl
    public fun activity(
        activity: Activity,
        config: ActivityBuilder<E>.() -> Unit
    ) {
        activities += activity to ActivityBuilder<E>().apply(config)
    }

    @BrainDsl
    public fun defaultActivity(
        activity: Activity,
        config: (ActivityBuilder<E>.() -> Unit)? = null
    ) {
        if (config != null) {
            activity(activity, config)
        }

        defaultActivity = activity
    }

    @BrainDsl
    public fun coreActivity(
        activity: Activity,
        config: (ActivityBuilder<E>.() -> Unit)? = null
    ) {
        if (config != null) {
            activity(activity, config)
        }

        coreActivities += activity
    }

    public fun deserializeAndConfigure(data: Dynamic<*>): Brain<E> {
        val brain = Brain.createProfile(memories, sensors).deserialize(data)

        activities.forEach {
            it.value.assignToBrainActivity(brain, it.key)
        }

        if (defaultActivity != null) {
            brain.setDefaultActivity(defaultActivity)
        }

        brain.setCoreActivities(coreActivities)

        //generics on sensors appear to break? might be doing something wrong
        @Suppress("UNCHECKED_CAST")
        return brain as Brain<E>
    }
}

@BrainDsl
public fun <E : LivingEntity> buildBrain(
    data: Dynamic<*>,
    config: BrainBuilder<E>.() -> Unit
): Brain<E> {
    return BrainBuilder<E>().apply(config).deserializeAndConfigure(data)
}