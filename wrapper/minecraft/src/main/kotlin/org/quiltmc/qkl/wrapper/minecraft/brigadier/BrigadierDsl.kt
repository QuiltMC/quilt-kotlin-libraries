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

package org.quiltmc.qkl.wrapper.minecraft.brigadier

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.*
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandBuildContext
import net.minecraft.command.EntitySelector
import net.minecraft.command.argument.*
import net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor
import net.minecraft.command.argument.NbtPathArgumentType.NbtPath
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.particle.ParticleEffect
import net.minecraft.predicate.NumberRange
import net.minecraft.scoreboard.ScoreboardCriterion
import net.minecraft.test.TestFunction
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.DynamicRegistryManager
import java.util.*

public typealias RequiredArgumentAction<S> = RequiredArgumentBuilder<S, *>.() -> Unit
public typealias LiteralArgumentAction<S> = LiteralArgumentBuilder<S>.() -> Unit
public typealias CommandActionReturn<S> = CommandContext<S>.() -> Int
public typealias CommandAction<S> = CommandContext<S>.() -> Unit

/**
 * Registers a command under [command] as the name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S> CommandDispatcher<S>.register(
    command: String,
    action: LiteralArgumentAction<S>
) {
    val argument = LiteralArgumentBuilder.literal<S>(command)
    argument.apply(action)
    register(argument)
}

public fun commandBuildContext(
    dynamicRegistryManager: DynamicRegistryManager,
    action: CommandBuildContext.() -> Unit
): CommandBuildContext {
    val context = CommandBuildContext(dynamicRegistryManager)
    context.apply(action)
    return context
}

//region Brigadier built in argument types

/**
 * Adds a literal argument with [name] as the literal.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.literal(
    name: String,
    action: LiteralArgumentAction<S>
) {
    val argument = LiteralArgumentBuilder.literal<S>(name)
    argument.apply(action)
    then(argument)
}

/**
 * Adds a string argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.string(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.string()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a greedy string argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.greedyString(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.greedyString()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a word argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.word(
    name: String,
    action: RequiredArgumentBuilder<S, String>.() -> Unit = { }
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        StringArgumentType.word()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a boolean argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.boolean(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Boolean>(
        name,
        BoolArgumentType.bool()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a double argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.double(
    name: String,
    min: Double = -Double.MAX_VALUE,
    max: Double = Double.MAX_VALUE,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Double>(
        name,
        DoubleArgumentType.doubleArg(min, max)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a float argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.float(
    name: String,
    min: Float = -Float.MAX_VALUE,
    max: Float = Float.MAX_VALUE,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Float>(
        name,
        FloatArgumentType.floatArg(min, max)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an integer argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.integer(
    name: String,
    min: Int = -Int.MAX_VALUE,
    max: Int = Int.MAX_VALUE,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        IntegerArgumentType.integer(min, max)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a long argument with [name] as the parameter name.
 *
 * @param min the minimum value.
 * @param max the maximum value.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.long(
    name: String,
    min: Long = -Long.MAX_VALUE,
    max: Long = Long.MAX_VALUE,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Long>(
        name,
        LongArgumentType.longArg(min, max)
    )
    argument.apply(action)
    then(argument)
}

//endregion

//region Minecraft argument types

/**
 * Adds an angle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.angle(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, AngleArgumentType.Angle>(
        name,
        AngleArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a block pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.blockPos(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        BlockPosArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a block predicate argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.blockPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, BlockPredicateArgumentType.BlockPredicate>(
        name,
        BlockPredicateArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a block state argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.blockState(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, BlockStateArgument>(
        name,
        BlockStateArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a color argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.color(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Formatting>(
        name,
        ColorArgumentType.color()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a column pos argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.columnPos(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        ColumnPosArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a command function argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.commandFunction(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<
            S,
            CommandFunctionArgumentType.FunctionArgument
    >(
        name,
        CommandFunctionArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a dimension argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.dimension(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Identifier>(
        name,
        DimensionArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an enchantment argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.enchantment(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Enchantment>(
        name,
        EnchantmentArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an entity anchor argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.entityAnchor(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntityAnchor>(
        name,
        EntityAnchorArgumentType()
    )
    argument.apply(action)
    then(argument)
}


/**
 * Adds multiple entity selectors argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.entities(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.entities()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an entity selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.entity(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.entity()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an entity summon argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.entitySummon(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Identifier>(
        name,
        EntitySummonArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a float range argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.floatRange(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NumberRange.FloatRange>(
        name,
        NumberRangeArgumentType.floatRange()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a game profile argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.gameProfile(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, GameProfileArgumentType.GameProfileArgument>(
        name,
        GameProfileArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an identifier argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.identifier(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Identifier>(
        name,
        IdentifierArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an int range argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.intRange(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NumberRange.IntRange>(
        name,
        NumberRangeArgumentType.intRange()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an item predicate argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.itemPredicate(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<
            S,
            ItemPredicateArgumentType.ItemPredicateArgument
    >(
        name,
        ItemPredicateArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.itemSlot(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        ItemSlotArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an item slot argument with [name] as the parameter name.
 *
 * @param context The command build context
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.itemStack(
    name: String,
    context: CommandBuildContext,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ItemStackArgument>(
        name,
        ItemStackArgumentType(context)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a message argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.message(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, MessageArgumentType.MessageFormat>(
        name,
        MessageArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a nbt compound argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.nbtCompound(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtCompound>(
        name,
        NbtCompoundArgumentType.nbtCompound()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an NBT element argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.nbtElement(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtElement>(
        name,
        NbtElementArgumentType.nbtElement()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an NBT path argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.nbtPath(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, NbtPath>(
        name,
        NbtPathArgumentType.nbtPath()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds an operation compound argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.operation(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, OperationArgumentType.Operation>(
        name,
        OperationArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a particle effect argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.particleEffect(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ParticleEffect>(
        name,
        ParticleEffectArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a multiple player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.players(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.players()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a player selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.player(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EntitySelector>(
        name,
        EntityArgumentType.player()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a rotation argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.rotation(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        RotationArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a scoreboard criterion argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.scoreboardCriterion(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ScoreboardCriterion>(
        name,
        ScoreboardCriterionArgumentType.scoreboardCriterion()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a scoreboard objective argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.scoreboardObjective(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        ScoreboardObjectiveArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a scoreboard slot argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.scoreboardSlot(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        ScoreboardSlotArgumentType.scoreboardSlot()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a score holder argument with [name] as the parameter name.
 *
 * @param multiple whether there are multiple scores
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.scoreHolder(
    name: String,
    multiple: Boolean = false,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, ScoreHolderArgumentType.ScoreHolder>(
        name,
        ScoreHolderArgumentType(multiple)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a status effect argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.statusEffect(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, StatusEffect>(
        name,
        StatusEffectArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a swizzle argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.swizzle(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, EnumSet<Direction.Axis>>(
        name,
        SwizzleArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a team argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.team(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        TeamArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a test class argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.testClass(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, String>(
        name,
        TestClassArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a test function argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.testFunction(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, TestFunction>(
        name,
        TestFunctionArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a text argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.text(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Text>(
        name,
        TextArgumentType.text()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a time argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.time(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, Int>(
        name,
        TimeArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a UUID argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.uuid(
    name: String,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, UUID>(
        name,
        UuidArgumentType()
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a vec2 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.vec2(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        Vec2ArgumentType(centerIntegers)
    )
    argument.apply(action)
    then(argument)
}

/**
 * Adds a vec3 argument with [name] as the parameter name.
 *
 * @param centerIntegers whether the integers are centered on the block
 *
 * @author Oliver-makes-code (Emma)
 */
public fun <S, T : ArgumentBuilder<S, T>?> ArgumentBuilder<S, T>.vec3(
    name: String,
    centerIntegers: Boolean = false,
    action: RequiredArgumentAction<S>
) {
    val argument = RequiredArgumentBuilder.argument<S, PosArgument>(
        name,
        Vec3ArgumentType(centerIntegers)
    )
    argument.apply(action)
    then(argument)
}

//endregion
