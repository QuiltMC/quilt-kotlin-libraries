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
