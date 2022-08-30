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

import com.google.common.collect.ImmutableList
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.brain.Activity
import net.minecraft.entity.ai.brain.Brain
import net.minecraft.entity.ai.brain.MemoryModuleState
import net.minecraft.entity.ai.brain.MemoryModuleType
import net.minecraft.entity.ai.brain.task.Task
import kotlin.Pair
import com.mojang.datafixers.util.Pair as DFUPair

@BrainDsl
public class ActivityBuilder<E : LivingEntity> {
    private val forgottenMemories = mutableSetOf<MemoryModuleType<*>>()
    private val requiredMemories =
        mutableMapOf<MemoryModuleType<*>, MemoryModuleState>()
    private val prioritizedTasks = mutableListOf<Pair<Int, Task<in E>>>()

    @BrainDsl
    public fun requiresMemories(config: MemoryRequirements.() -> Unit) {
        MemoryRequirements().apply(config)
    }

    @BrainDsl
    public fun forgets(vararg memories: MemoryModuleType<*>) {
        forgottenMemories += memories
    }

    @BrainDsl
    public fun tasks(config: ActivityTasks.() -> Unit) {
        ActivityTasks().apply(config)
    }

    public fun assignToBrainActivity(brain: Brain<out E>, activity: Activity) {
        brain.setTaskList(
            activity,
            ImmutableList.copyOf(
                prioritizedTasks.map { DFUPair(it.first, it.second) }
            ),
            requiredMemories.map { DFUPair(it.key, it.value) }.toSet(),
            forgottenMemories
        )
    }

    public inner class MemoryRequirements {
        @BrainDsl
        public infix fun MemoryModuleType<*>.toHaveState(state: MemoryModuleState) {
            requiredMemories += this to state
        }

        @BrainDsl
        public fun MemoryModuleType<*>.toExist() {
            this toHaveState MemoryModuleState.REGISTERED
        }

        @BrainDsl
        public fun MemoryModuleType<*>.toBePresent() {
            this toHaveState MemoryModuleState.VALUE_PRESENT
        }

        @BrainDsl
        public fun MemoryModuleType<*>.toBeAbsent() {
            this toHaveState MemoryModuleState.VALUE_ABSENT
        }
    }

    public inner class ActivityTasks {
        @BrainDsl
        public fun task(priority: Int, task: Task<in E>) {
            prioritizedTasks += priority to task
        }

        @BrainDsl
        public operator fun Int.minus(task: Task<in E>) {
            task(this, task)
        }

        @BrainDsl
        public fun priority(
            priority: Int,
            config: PrioritizedTasks.() -> Unit
        ) {
            PrioritizedTasks(priority).apply(config)
        }

        @BrainDsl
        public operator fun Int.invoke(config: PrioritizedTasks.() -> Unit) {
            priority(this, config)
        }

        public inner class PrioritizedTasks(private val priority: Int) {
            @BrainDsl
            public fun task(task: Task<in E>) {
                task(priority, task)
            }

            @BrainDsl
            public operator fun Task<in E>.unaryMinus() {
                task(this)
            }
        }
    }
}

@BrainDsl
public fun <E : LivingEntity> Brain<E>.activity(
    activity: Activity,
    config: ActivityBuilder<E>.() -> Unit
) {
    ActivityBuilder<E>().apply(config).assignToBrainActivity(this, activity)
}