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

package org.quiltmc.qkl.library.crash

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.Entity
import net.minecraft.util.SystemDetails
import net.minecraft.util.crash.CrashReport
import net.minecraft.util.crash.CrashReportSection
import net.minecraft.util.math.BlockPos
import net.minecraft.world.HeightLimitView
import net.minecraft.world.World
import org.quiltmc.qkl.library.EventRegistration
import org.quiltmc.qsl.crash.api.CrashReportEvents

public typealias CrashDetailCallback<T> = T.(section: CrashReportSection) -> Unit
public typealias CrashBlockDetailCallback = HeightLimitView.(
    pos: BlockPos,
    state: BlockState?,
    section: CrashReportSection
) -> Unit

/**
 * @see CrashReportEvents.SYSTEM_DETAILS
 * @see CrashReportEvents.SystemDetails.addDetails
 *
 * @author sschr15
 */
public fun EventRegistration.onAddSystemDetails(callback: SystemDetails.() -> Unit) {
    CrashReportEvents.SYSTEM_DETAILS.register(CrashReportEvents.SystemDetails(callback))
}

/**
 * @see CrashReportEvents.WORLD_DETAILS
 * @see CrashReportEvents.WorldDetails.addDetails
 *
 * @author sschr15
 */
public fun EventRegistration.onAddAffectedWorldDetails(callback: CrashDetailCallback<World>) {
    CrashReportEvents.WORLD_DETAILS.register(CrashReportEvents.WorldDetails(callback))
}

/**
 * @see CrashReportEvents.BLOCK_DETAILS
 * @see CrashReportEvents.BlockDetails.addDetails
 *
 * @author sschr15
 */
public fun EventRegistration.onAddBlockDetails(callback: CrashBlockDetailCallback) {
    CrashReportEvents.BLOCK_DETAILS.register(CrashReportEvents.BlockDetails(callback))
}

/**
 * @see CrashReportEvents.ENTITY_DETAILS
 * @see CrashReportEvents.EntityDetails.addDetails
 *
 * @author sschr15
 */
public fun EventRegistration.onAddEntityDetails(callback: CrashDetailCallback<Entity>) {
    CrashReportEvents.ENTITY_DETAILS.register(CrashReportEvents.EntityDetails(callback))
}

/**
 * @see CrashReportEvents.BLOCKENTITY_DETAILS
 * @see CrashReportEvents.BlockEntityDetails.addDetails
 *
 * @author sschr15
 */
public fun EventRegistration.onAddBlockEntityDetails(callback: CrashDetailCallback<BlockEntity>) {
    CrashReportEvents.BLOCKENTITY_DETAILS.register(CrashReportEvents.BlockEntityDetails(callback))
}

/**
 * @see CrashReportEvents.CRASH_REPORT_CREATION
 * @see CrashReportEvents.CrashReportCreation.onCreate
 *
 * @author sschr15
 */
public fun EventRegistration.onCrashReportCreation(callback: CrashReport.() -> Unit) {
    CrashReportEvents.CRASH_REPORT_CREATION
        .register(CrashReportEvents.CrashReportCreation(callback))
}
