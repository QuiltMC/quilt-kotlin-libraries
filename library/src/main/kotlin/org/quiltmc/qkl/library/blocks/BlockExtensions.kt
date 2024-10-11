/*
 * Copyright 2023 The Quilt Project
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

package org.quiltmc.qkl.library.blocks

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.entity.EntityType
import net.minecraft.loot.LootTable
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings

public typealias BlockTest = (
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
 * Create a new [AbstractBlock.Settings] with the given settings.
 * - If [luminanceFunction] is null, [luminance] is used.
 * - If [hardness] is null, [resistance] is used.
 * - If any of the predicates are null, their default values are used.
 *
 * @author sschr15
 */
public fun blockSettingsOf(
    mapColor: MapColor? = null,
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
    isSolid: BlockTest? = null,
    causesSuffocation: BlockTest? = null,
    blocksVision: BlockTest? = null,
    shouldPostProcess: BlockTest? = null,
    isEmissive: BlockTest? = null,
): AbstractBlock.Settings = buildBlockSettings {
    if (mapColor != null) mapColor(mapColor)
    collidable(isCollidable)
    sounds(soundGroup)
    if (luminanceFunction == null) {
        luminance(luminance)
    } else {
        luminance(luminanceFunction)
    }
    resistance(resistance)
    hardness(hardness ?: resistance)
    if (requiresTool) toolRequired()
    if (ticksRandomly) ticksRandomly()
    slipperiness(slipperiness)
    velocityMultiplier(velocityMultiplier)
    jumpVelocityMultiplier(jumpVelocityMultiplier)
    when (lootTableId) {
        is RegistryKey<*> -> drops(lootTableId as RegistryKey<LootTable>)
        is Block -> dropsLike(lootTableId)
        is Identifier -> drops(RegistryKey.of(RegistryKeys.LOOT_TABLE, lootTableId))
        else -> dropsNothing()
    }
    if (!isOpaque) nonOpaque()
    if (isAir) air()

    if (spawnCheck != null) allowsSpawning(spawnCheck)
    if (isSolid != null) solidBlock(isSolid)
    if (causesSuffocation != null) suffocates(causesSuffocation)
    if (blocksVision != null) blockVision(blocksVision)
    if (shouldPostProcess != null) postProcess(shouldPostProcess)
    if (isEmissive != null) emissiveLighting(isEmissive)
}

/**
 * Build a [Block settings object][AbstractBlock.Settings] with the given [base] settings,
 * using builder-style syntax.
 */
public fun buildBlockSettings(
    base: AbstractBlock.Settings = AbstractBlock.Settings.create(),
    block: QuiltBlockSettings.() -> Unit
): AbstractBlock.Settings {
    val settings = QuiltBlockSettings.copyOf(base)
    settings.block()
    return settings
}
