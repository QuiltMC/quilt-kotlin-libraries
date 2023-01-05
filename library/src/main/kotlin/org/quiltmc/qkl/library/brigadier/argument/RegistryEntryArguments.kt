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

/*
 * Preserve binary compatibility when moving extensions between files
 */
@file:JvmMultifileClass
@file:JvmName("ArgumentsKt")

package org.quiltmc.qkl.library.brigadier.argument

import com.mojang.brigadier.exceptions.CommandSyntaxException
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.argument.RegistryEntryArgumentType
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.EntityAttribute
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.registry.Holder
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.StructureFeature
import org.quiltmc.qkl.library.brigadier.*

public typealias DefaultRegistryReader<T> = DefaultArgumentReader<RegistryEntryArgumentType<T>>

/**
 * Retrieves the registry entry from the registry of [registryKey]
 * matching the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getRegistryEntry
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <T> DefaultRegistryReader<T>.getFromRegistry(registryKey: RegistryKey<Registry<T>>): Holder.Reference<T> {
    return RegistryEntryArgumentType.getRegistryEntry(context.assumeSourceNotUsed(), name, registryKey)
}

/**
 * Reads the [EntityAttribute] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getEntityAttribute
 *
 * @author Cypher121
 */
@JvmName("valueEntityAttributeRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<EntityAttribute>.value(): Holder.Reference<EntityAttribute> {
    return RegistryEntryArgumentType.getEntityAttribute(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [ConfiguredFeature] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getConfiguredFeature
 *
 * @author Cypher121
 */
@JvmName("valueConfiguredFeatureRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<ConfiguredFeature<*, *>>.value(): Holder.Reference<ConfiguredFeature<*, *>> {
    return RegistryEntryArgumentType.getConfiguredFeature(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [StructureFeature] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getStructureFeature
 *
 * @author Cypher121
 */
@JvmName("valueStructureFeatureRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<StructureFeature>.value(): Holder.Reference<StructureFeature> {
    return RegistryEntryArgumentType.getStructureFeature(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [EntityType] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getEntityType
 *
 * @author Cypher121
 */
@JvmName("valueEntityTypeRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<EntityType<*>>.value(): Holder.Reference<EntityType<*>> {
    return RegistryEntryArgumentType.getEntityType(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [EntityAttribute] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @throws CommandSyntaxException if the given entity type is not summonable
 *
 * @see RegistryEntryArgumentType.getSummonableEntityType
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun DefaultRegistryReader<EntityType<*>>.requireSummonable(): Holder.Reference<EntityType<*>> {
    return RegistryEntryArgumentType.getSummonableEntityType(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [StatusEffect] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getStatusEffect
 *
 * @author Cypher121
 */
@JvmName("valueStatusEffectRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<StatusEffect>.value(): Holder.Reference<StatusEffect> {
    return RegistryEntryArgumentType.getStatusEffect(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the [Enchantment] value from the
 * argument in the receiver [ArgumentReader].
 *
 * @see RegistryEntryArgumentType.getEnchantment
 *
 * @author Cypher121
 */
@JvmName("valueEnchantmentRegistryArg")
@BrigadierDsl
public fun DefaultRegistryReader<Enchantment>.value(): Holder.Reference<Enchantment> {
    return RegistryEntryArgumentType.getEnchantment(context.assumeSourceNotUsed(), name)
}

/**
 * Creates an argument accepting elements of the registry identified by [registryKey]
 * with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Cypher121
 */
@BrigadierDsl
public fun <S, T> registryEntry(
    name: String,
    context: CommandBuildContext,
    registryKey: RegistryKey<Registry<T>>
) : DefaultArgumentConstructor<S, RegistryEntryArgumentType<T>> {
    return argument(name, RegistryEntryArgumentType.registryEntry(context, registryKey))
}
