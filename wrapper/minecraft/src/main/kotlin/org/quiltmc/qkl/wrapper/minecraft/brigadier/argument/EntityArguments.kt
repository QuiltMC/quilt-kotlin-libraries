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

package org.quiltmc.qkl.wrapper.minecraft.brigadier.argument

import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.command.argument.EntitySummonArgumentType
import net.minecraft.entity.Entity
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier
import org.quiltmc.qkl.wrapper.minecraft.brigadier.*

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing a single entity to be selected.
 *
 * @see entity
 * @see EntityArgumentType.entity
 *
 * @author Cypher121
 */
public object SingleEntityArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>

/**
 * [ArgumentDescriptor] for an [EntityArgumentType]
 * allowing multiple entities to be selected.
 *
 * @see entities
 * @see EntityArgumentType.entities
 *
 * @author Cypher121
 */
public object ListEntityArgumentDescriptor : ArgumentDescriptor<EntityArgumentType>

/**
 * Reads the [entity anchor][EntityAnchorArgumentType.EntityAnchor] value
 * of the argument in the receiver [ArgumentReader].
 *
 * @see EntityAnchorArgumentType.getEntityAnchor
 *
 * @author Cypher121
 */
@JvmName("valueEntityAnchorArg")
@BrigadierDsl
public fun DefaultArgumentReader<EntityAnchorArgumentType>.value(): EntityAnchorArgumentType.EntityAnchor {
    return EntityAnchorArgumentType.getEntityAnchor(
        context.assumeSourceNotUsed(),
        name
    )
}

/**
 * Reads the entity summon [Identifier] value of the argument
 * in the receiver [ArgumentReader].
 *
 * @see EntitySummonArgumentType.getEntitySummon
 *
 * @author Cypher121
 */
@JvmName("valueEntitySummonArg")
@BrigadierDsl
public fun ArgumentReader<
        *,
        DefaultArgumentDescriptor<EntitySummonArgumentType>
        >.value(): Identifier {
    return EntitySummonArgumentType.getEntitySummon(context.assumeSourceNotUsed(), name)
}

/**
 * Reads the collection of entities from the argument in
 * the receiver [ArgumentReader].
 *
 * Throws an exception if no entities are matched.
 *
 * @see EntityArgumentType.getEntities
 *
 * @author Cypher121
 */
@JvmName("requiredEntityArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        ListEntityArgumentDescriptor
        >.required(): Collection<Entity> {
    return EntityArgumentType.getEntities(context, name)
}

/**
 * Reads the collection of entities from the argument in
 * the receiver [ArgumentReader].
 *
 * Returns an empty collection if no entities are matched.
 *
 * @see EntityArgumentType.getOptionalEntities
 *
 * @author Cypher121
 */
@JvmName("optionalEntityArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        ListEntityArgumentDescriptor
        >.optional(): Collection<Entity> {
    return EntityArgumentType.getOptionalEntities(context, name)
}

/**
 * Reads the [Entity] value from the argument in
 * the receiver [ArgumentReader].
 *
 * @see EntityArgumentType.getEntity
 *
 * @author Cypher121
 */
@JvmName("valueSingleEntityArg")
@BrigadierDsl
public fun ArgumentReader<
        ServerCommandSource,
        SingleEntityArgumentDescriptor
        >.value(): Entity {
    return EntityArgumentType.getEntity(context, name)
}

/**
 * Creates an entity anchor argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> entityAnchor(
    name: String
): DefaultArgumentConstructor<S, EntityAnchorArgumentType> {
    return argument(name, EntityAnchorArgumentType.entityAnchor())
}

/**
 * Creates entity argument allowing multiple entities with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> entities(
    name: String
): RequiredArgumentConstructor<
        S,
        ListEntityArgumentDescriptor
        > {
    return argument(name, EntityArgumentType.entities(), ListEntityArgumentDescriptor)
}

/**
 * Creates an entity selector argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 */
@BrigadierDsl
public fun <S> entity(
    name: String
): RequiredArgumentConstructor<
        S,
        SingleEntityArgumentDescriptor
        > {
    return argument(name, EntityArgumentType.entity(), SingleEntityArgumentDescriptor)
}

/**
 * Creates an entity summon argument with [name] as the parameter name.
 *
 * @author Oliver-makes-code (Emma)
 * @author Cypher121
 */
@BrigadierDsl
public fun <S> entitySummon(
    name: String
): DefaultArgumentConstructor<S, EntitySummonArgumentType> {
    return argument(name, EntitySummonArgumentType.entitySummon())
}
