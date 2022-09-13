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

package org.quiltmc.qkl.wrapper.minecraft.coroutine.core

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.InternalCoroutinesApi
import net.fabricmc.api.EnvType
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader
import java.util.PriorityQueue
import kotlin.collections.ArrayDeque
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

@OptIn(InternalCoroutinesApi::class)
internal abstract class BaseMinecraftDispatcher : MinecraftDispatcher(), Delay {
    private val tasks: ArrayDeque<Runnable> = ArrayDeque(64)

    private val delayedTasks: PriorityQueue<DelayedTask> = PriorityQueue(64, compareBy { it.executionTimeMillis })

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        tasks += block
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        delayedTasks += DelayedTask(System.currentTimeMillis() + timeMillis, continuation)
    }

    fun resumeTasks() {
        while (tasks.isNotEmpty()) {
            tasks.removeFirst().run()
        }
        while (delayedTasks.isNotEmpty()) {
            val currentTimeMillis = System.currentTimeMillis()
            if (delayedTasks.peek().executionTimeMillis <= currentTimeMillis) {
                delayedTasks.poll().continuation.resume(Unit)
            } else {
                break
            }
        }
    }
}

internal abstract class TickableBaseMinecraftDispatcher : BaseMinecraftDispatcher(), TickDelay {
    private val tickDelayedTasks: PriorityQueue<TickDelayedTask> = PriorityQueue(64, compareBy { it.executionTick })

    private var currentTick: Long = 0L

    override fun scheduleResumeAfterDelayTicks(ticks: Long, continuation: CancellableContinuation<Unit>) {
        tickDelayedTasks += TickDelayedResumeTask(currentTick + ticks, continuation)
    }

    override fun invokeOnTimeoutTicks(ticks: Long, context: CoroutineContext, block: Runnable): DisposableHandle {
        val task = TickDelayedRunnableTask(currentTick + ticks, block)
        tickDelayedTasks += task
        return DisposableHandle {
            tickDelayedTasks -= task
        }
    }

    fun resumeTickableTasks() {
        while (tickDelayedTasks.isNotEmpty()) { // Should resume before delayed tasks that is based on real time
            if (tickDelayedTasks.peek().executionTick <= currentTick) {
                tickDelayedTasks.poll().run()
            } else {
                break
            }
        }
        currentTick++
    }
}

internal object ClientDispatcher : TickableBaseMinecraftDispatcher() {
    override val environmentType: EnvType get() = EnvType.CLIENT
}

internal object ServerDispatcher : TickableBaseMinecraftDispatcher() {
    override val environmentType: EnvType get() = MinecraftQuiltLoader.getEnvironmentType()
}
