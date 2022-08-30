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

import net.minecraft.entity.ai.brain.Activity
import net.minecraft.entity.ai.brain.Schedule
import net.minecraft.entity.ai.brain.ScheduleBuilder

@BrainDsl
public class ScheduleBuilderScope(
    private val scheduleBuilder: ScheduleBuilder
) {
    @BrainDsl
    public operator fun Int.minus(activity: Activity) {
        scheduleBuilder.withActivity(this, activity)
    }
}

@BrainDsl
public fun schedule(config: ScheduleBuilderScope.() -> Unit): Schedule {
    val schedule = Schedule()

    val builder = ScheduleBuilder(schedule)
    ScheduleBuilderScope(builder).apply(config)

    return builder.build()
}