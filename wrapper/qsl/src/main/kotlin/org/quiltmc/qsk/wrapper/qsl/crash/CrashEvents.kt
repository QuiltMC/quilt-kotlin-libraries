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

@file:Suppress("unused")

package org.quiltmc.qsk.wrapper.qsl.crash

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.util.SystemDetails
import net.minecraft.util.crash.CrashReport
import net.minecraft.util.crash.CrashReportSection
import net.minecraft.util.math.BlockPos
import net.minecraft.world.HeightLimitView
import net.minecraft.world.World
import org.quiltmc.qsk.wrapper.qsl.EventRegistration
import org.quiltmc.qsl.crash.api.CrashReportEvents

public typealias ReportDetails<T> = T.(section: CrashReportSection) -> Unit
public typealias BlockDetails = HeightLimitView.(
    pos: BlockPos,
    state: BlockState?,
    section: CrashReportSection
) -> Unit

public fun EventRegistration.addSystemDetails(callback: SystemDetails.() -> Unit) {
    CrashReportEvents.SYSTEM_DETAILS.register(CrashReportEvents.SystemDetails(callback))
}

public fun EventRegistration.addAffectedWorldDetails(callback: ReportDetails<World>) {
    CrashReportEvents.WORLD_DETAILS.register(CrashReportEvents.WorldDetails(callback))
}

public fun EventRegistration.addBlockDetails(callback: BlockDetails) {
    CrashReportEvents.BLOCK_DETAILS.register(CrashReportEvents.BlockDetails(callback))
}

public fun EventRegistration.addEntityDetails(callback: ReportDetails<Entity>) {
    CrashReportEvents.ENTITY_DETAILS.register(CrashReportEvents.EntityDetails(callback))
}

public fun EventRegistration.addBlockEntityDetails(callback: ReportDetails<BlockEntity>) {
    CrashReportEvents.BLOCKENTITY_DETAILS.register(CrashReportEvents.BlockEntityDetails(callback))
}

public fun EventRegistration.crashReportCreation(callback: CrashReport.() -> Unit) {
    CrashReportEvents.CRASH_REPORT_CREATION
        .register(CrashReportEvents.CrashReportCreation(callback))
}
