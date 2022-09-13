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

@file:Suppress("INVISIBLE_REFERENCE", "INVISIBLE_MEMBER")

package org.quiltmc.qkl.wrapper.minecraft.coroutine.core

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.internal.ScopeCoroutine
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.disposeOnCompletion
import kotlinx.coroutines.intrinsics.startUndispatchedOrReturnIgnoreTimeout
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

@OptIn(ExperimentalContracts::class)
public suspend fun <T> withTimeoutTicks(ticks: Long, block: suspend CoroutineScope.() -> T): T {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    if (ticks <= 0L) {
        throw TickTimeoutCancellationException("Timed out immediately")
    }
    return suspendCoroutineUninterceptedOrReturn { uCont ->
        setupTimeout(TickTimeoutCoroutine(ticks, uCont), block)
    }
}

public suspend fun <T> withTimeoutTicksOrNull(ticks: Long, block: suspend CoroutineScope.() -> T): T? {
    if (ticks <= 0L) {
        return null
    }
    var job: Job? = null
    try {
        return suspendCoroutineUninterceptedOrReturn { uCont ->
            val tickTimeoutCoroutine = TickTimeoutCoroutine(ticks, uCont)
            job = tickTimeoutCoroutine
            setupTimeout(tickTimeoutCoroutine, block)
        }
    } catch (e: TickTimeoutCancellationException) {
        if (e.job === job) {
            return null
        }
        throw e
    }
}

public class TickTimeoutCancellationException(
    message: String,
    @JvmField internal val job: Job? = null
) : CancellationException(message)

// Internal

private fun <U, T : U> setupTimeout(
    coroutine: TickTimeoutCoroutine<U, T>,
    block: suspend CoroutineScope.() -> T
): Any? {
    val context = coroutine.uCont.context
    coroutine.disposeOnCompletion(context.tickDelay.invokeOnTimeoutTicks(coroutine.ticks, coroutine.context, coroutine))
    return coroutine.startUndispatchedOrReturnIgnoreTimeout(coroutine, block)
}

private class TickTimeoutCoroutine<U, in T : U>(
    @JvmField val ticks: Long,
    uCont: Continuation<U>
) : ScopeCoroutine<T>(uCont.context, uCont), Runnable {
    override fun run() {
        cancelCoroutine(TickTimeoutCancellationException("Timed out waiting for $ticks ticks", this))
    }
}
