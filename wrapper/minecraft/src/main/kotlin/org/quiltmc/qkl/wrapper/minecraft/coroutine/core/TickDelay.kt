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
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

public interface TickDelay {
    public fun scheduleResumeAfterDelayTicks(ticks: Long, continuation: CancellableContinuation<Unit>)

    public fun invokeOnTimeoutTicks(ticks: Long, context: CoroutineContext, block: Runnable): DisposableHandle
}

public suspend fun delayTicks(ticks: Long) {
    if (ticks <= 0L) {
        return
    }
    return suspendCancellableCoroutine { continuation ->
        if (ticks < Long.MAX_VALUE) {
            continuation.context.tickDelay.scheduleResumeAfterDelayTicks(ticks, continuation)
        }
    }
}

// Internal

internal val CoroutineContext.tickDelay: TickDelay
    get() = get(ContinuationInterceptor) as? TickDelay
        ?: error("A TickDelay is not available in this CoroutineContext. " +
                "Callers should supply an appropriate CoroutineDispatcher using withContext. " +
                "May use Dispatchers.MinecraftClient or Dispatchers.MinecraftServer.")
