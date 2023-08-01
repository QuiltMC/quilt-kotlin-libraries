/*
 * Copyright 2022 The Quilt Project
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

package org.quiltmc.qkl.library.brigadier.util

import com.mojang.brigadier.builder.ArgumentBuilder
import org.quiltmc.qkl.library.brigadier.ArgumentAccessor
import org.quiltmc.qkl.library.brigadier.ArgumentConstructor
import org.quiltmc.qkl.library.brigadier.ArgumentDescriptor
import org.quiltmc.qkl.library.brigadier.BrigadierDsl

/**
 * Shorthand allowing registering multiple required
 * arguments at once, with no branches between them.
 *
 * @sample samples.qkl.brigadier.BrigadierDslSamples.sampleCommandWithResult
 *
 * @author Cypher121
 */
@BrigadierDsl
public inline fun <
        S,
        B1 : ArgumentBuilder<S, *>, B2 : ArgumentBuilder<S, *>,
        D1 : ArgumentDescriptor<*>, D2 : ArgumentDescriptor<*>
        > ArgumentBuilder<S, *>.required(
    first: ArgumentConstructor<S, B1, D1>,
    second: ArgumentConstructor<S, B2, D2>,
    action: B2.(
        ArgumentAccessor<S, D1>,
        ArgumentAccessor<S, D2>
    ) -> Unit
) {
    first.required().register(this) { firstAccessor ->
        second.required().register(this) { secondAccessor ->
            action(firstAccessor, secondAccessor)
        }
    }
}

/**
 * Shorthand allowing registering multiple required
 * arguments at once, with no branches between them.
 *
 * @sample samples.qkl.brigadier.BrigadierDslSamples.sampleCommandWithResult
 *
 * @author Cypher121
 */
@BrigadierDsl
public inline fun <
        S,
        B1 : ArgumentBuilder<S, *>, B2 : ArgumentBuilder<S, *>, B3 : ArgumentBuilder<S, *>,
        D1 : ArgumentDescriptor<*>, D2 : ArgumentDescriptor<*>, D3 : ArgumentDescriptor<*>
        > ArgumentBuilder<S, *>.required(
    first: ArgumentConstructor<S, B1, D1>,
    second: ArgumentConstructor<S, B2, D2>,
    third: ArgumentConstructor<S, B3, D3>,
    action: B3.(
        ArgumentAccessor<S, D1>,
        ArgumentAccessor<S, D2>,
        ArgumentAccessor<S, D3>
    ) -> Unit
) {
    first.required().register(this) { firstAccessor ->
        second.required().register(this) { secondAccessor ->
            third.required().register(this) { thirdAccessor ->
                action(firstAccessor, secondAccessor, thirdAccessor)
            }
        }
    }
}
