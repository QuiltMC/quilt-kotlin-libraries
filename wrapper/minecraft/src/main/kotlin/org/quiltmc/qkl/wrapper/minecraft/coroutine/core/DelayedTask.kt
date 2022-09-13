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
import kotlinx.coroutines.Runnable
import kotlin.coroutines.resume

internal class DelayedTask(
    val executionTimeMillis: Long,
    val continuation: CancellableContinuation<Unit>
)

internal abstract class TickDelayedTask(
    val executionTick: Long,
) : Runnable

internal class TickDelayedResumeTask(
    executionTick: Long,
    private val continuation: CancellableContinuation<Unit>
) : TickDelayedTask(executionTick) {
    override fun run() {
        continuation.resume(Unit)
    }
}

internal class TickDelayedRunnableTask(
    executionTick: Long,
    private val block: Runnable
) : TickDelayedTask(executionTick) {
    override fun run() {
        block.run()
    }
}
