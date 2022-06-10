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

package org.quiltmc.qsk.wrapper.qsl.blocks

import net.minecraft.block.*
import net.minecraft.block.piston.PistonBehavior
import net.minecraft.entity.EntityType
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings
import org.quiltmc.qsl.block.extensions.api.QuiltMaterialBuilder

/**
 * Creates a [Material] with the given settings.
 */
public fun materialOf(
    color: MapColor,
    pistonBehavior: PistonBehavior = PistonBehavior.NORMAL,
    blocksMovement: Boolean = true,
    isBurnable: Boolean = false,
    isLiquid: Boolean = false,
    isReplaceable: Boolean = false,
    isSolid: Boolean = true,
    isOpaque: Boolean = true,
): Material = QuiltMaterialBuilder(color).also {
    it.pistonBehavior(pistonBehavior)
    if (!blocksMovement) it.allowsMovement()
    if (isBurnable) it.burnable()
    if (isLiquid) it.liquid()
    if (isReplaceable) it.replaceable()
    if (!isSolid) it.notSolid()
    if (!isOpaque) it.lightPassesThrough()
}.build()

public typealias Test = (
    state: BlockState,
    view: BlockView,
    pos: BlockPos
) -> Boolean

public typealias EntityTest = (
    state: BlockState,
    world: BlockView,
    pos: BlockPos,
    entity: EntityType<*>
) -> Boolean

/**
 * Creates a new [AbstractBlock.Settings] with the given settings.
 * - If [luminanceFunction] is null, [luminance] is used.
 * - If [hardness] is null, [resistance] is used.
 * - If any of the predicates are null, their default values are used.
 */
public fun blockSettingsOf(
    material: Material,
    color: MapColor = material.color,
    isCollidable: Boolean = true,
    soundGroup: BlockSoundGroup = BlockSoundGroup.STONE,
    luminance: Int = 0,
    luminanceFunction: ((BlockState) -> Int)? = null,
    resistance: Float = 0.0f,
    hardness: Float? = null,
    requiresTool: Boolean = false,
    ticksRandomly: Boolean = false,
    slipperiness: Float = 0.6f,
    velocityMultiplier: Float = 1.0f,
    jumpVelocityMultiplier: Float = 1.0f,
    lootTableId: Any? = null,
    isOpaque: Boolean = true,
    isAir: Boolean = false,
    spawnCheck: EntityTest? = null,
    isSolid: Test? = null,
    causesSuffocation: Test? = null,
    blocksVision: Test? = null,
    shouldPostProcess: Test? = null,
    isEmissive: Test? = null,
): AbstractBlock.Settings = QuiltBlockSettings.of(material, color).also {
    it.collidable(isCollidable)
    it.sounds(soundGroup)
    if (luminanceFunction == null) {
        it.luminance(luminance)
    } else {
        it.luminance(luminanceFunction)
    }
    it.resistance(resistance)
    it.hardness(hardness ?: resistance)
    if (requiresTool) it.requiresTool()
    if (ticksRandomly) it.ticksRandomly()
    it.slipperiness(slipperiness)
    it.velocityMultiplier(velocityMultiplier)
    it.jumpVelocityMultiplier(jumpVelocityMultiplier)
    when (lootTableId) {
        is Identifier -> it.drops(lootTableId)
        is Block -> it.dropsLike(lootTableId)
        else -> it.dropsNothing()
    }
    if (!isOpaque) it.nonOpaque()
    if (isAir) it.air()

    if (spawnCheck != null) it.allowsSpawning(spawnCheck)
    if (isSolid != null) it.solidBlock(isSolid)
    if (causesSuffocation != null) it.suffocates(causesSuffocation)
    if (blocksVision != null) it.blockVision(blocksVision)
    if (shouldPostProcess != null) it.postProcess(shouldPostProcess)
    if (isEmissive != null) it.emissiveLighting(isEmissive)
}
