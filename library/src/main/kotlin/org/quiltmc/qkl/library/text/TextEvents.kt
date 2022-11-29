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

package org.quiltmc.qkl.library.text

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.text.ClickEvent
import net.minecraft.text.HoverEvent
import net.minecraft.text.Text
import java.util.*

/**
 * Container object for [ClickEvent] builders.
 *
 * @sample samples.qkl.text.TextDslSamples.sampleTextEvents
 *
 * @author Cypher121
 */
public object ClickEvents {
    /**
     * Builds a [ClickEvent] to open a [url].
     *
     * @param url The url to open
     * @return The click event to open the target [url]
     */
    public fun openUrl(url: String): ClickEvent {
        return ClickEvent(ClickEvent.Action.OPEN_URL, url)
    }

    /**
     * Builds a [ClickEvent] to open a file at a specified [path].
     *
     * @param path The path of the file to open
     * @return The click event to open the target file
     */
    public fun openFile(path: String): ClickEvent {
        return ClickEvent(ClickEvent.Action.OPEN_FILE, path)
    }

    /**
     * Builds a [ClickEvent] run a given [command].
     *
     * @param command The command to suggest
     * @return The click event to run the target [command]
     */
    public fun runCommand(command: String): ClickEvent {
        return ClickEvent(ClickEvent.Action.RUN_COMMAND, command)
    }

    /**
     * Builds a [ClickEvent] to suggest a [command].
     *
     * @param command The command to suggest
     * @return The click event to suggest a given command
     */
    public fun suggestCommand(command: String): ClickEvent {
        return ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, command)
    }

    /**
     * Builds a [ClickEvent] to change a page to the given [page].
     *
     * @param page The page to change too
     * @return The click event to change the page to the provided [page]
     */
    public fun changePage(page: Int): ClickEvent {
        return ClickEvent(ClickEvent.Action.CHANGE_PAGE, page.toString())
    }

    /**
     * Builds a [ClickEvent] to copy [toCopy] to the clipboard.
     *
     * @param toCopy The text to copy
     * @return The click event to copy the provided text
     */
    public fun copyToClipboard(toCopy: String): ClickEvent {
        return ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, toCopy)
    }
}

/**
 * Container object for [HoverEvent] builders.
 *
 * @sample samples.qkl.text.TextDslSamples.sampleTextEvents
 */
public object HoverEvents {
    /**
     * Creates a [HoverEvent] showing the given [itemStack].
     *
     * @see HoverEvent.Action.SHOW_ITEM
     *
     * @author NoComment1105
     */
    public fun showItem(itemStack: ItemStack): HoverEvent {
        return HoverEvent(HoverEvent.Action.SHOW_ITEM, HoverEvent.ItemStackContent(itemStack))
    }

    /**
     * Creates a [HoverEvent] showing an [ItemStack] created from the given [NBT Compound][nbt].
     *
     * @see HoverEvent.Action.SHOW_ITEM
     *
     * @author NoComment1105
     */
    public fun showItem(nbt: NbtCompound): HoverEvent {
        return showItem(ItemStack.fromNbt(nbt))
    }

    /**
     * Creates a [HoverEvent] showing an [ItemStack] created from the given [item]
     * with an optional [NBT tag][nbt].
     *
     * @see HoverEvent.Action.SHOW_ITEM
     *
     * @author NoComment1105
     */
    public fun showItem(item: Item, nbt: NbtCompound? = null): HoverEvent {
        return showItem(item.defaultStack.also { it.nbt = nbt })
    }

    /**
     * Creates a [HoverEvent] showing an [Entity] of a specified [type][entityType]
     * with the specified [uuid] and an optional [name].
     *
     * @see HoverEvent.Action.SHOW_ENTITY
     *
     * @author NoComment1105
     */
    public fun showEntity(entityType: EntityType<*>, uuid: UUID, name: Text? = null): HoverEvent {
        return HoverEvent(HoverEvent.Action.SHOW_ENTITY, HoverEvent.EntityContent(entityType, uuid, name))
    }

    /**
     * Creates a [HoverEvent] showing specified [text].
     *
     * @author NoComment1105
     */
    public fun showText(text: Text): HoverEvent {
        return HoverEvent(HoverEvent.Action.SHOW_TEXT, text)
    }
}
