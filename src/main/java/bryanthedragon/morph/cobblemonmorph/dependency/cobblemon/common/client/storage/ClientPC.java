/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.POKEMON_PER_BOX
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.util.UUID

public class ClientPC(UUID uuid, boxCount: Int) : ClientStorage<PCPosition>(uuid) {
    val boxes = MutableList(boxCount) { ClientBox() }
    override fun findByUUID(UUID uuid): Pokemon? {
        boxes.forEach {
            it.forEach {
                if (it != null && it.uuid == uuid) {
                    return it
                }
            }
        }

        return null
    }

    override fun set(position: PCPosition, Pokemon pokemon?) {
        val box = if (boxes.size > position.box) boxes[position.box] else return
        if (position.slot >= POKEMON_PER_BOX) {
            return
        }
        box.slots[position.slot] = pokemon
    }

    override fun get(position: PCPosition): Pokemon? {
        if (position.slot >= POKEMON_PER_BOX || position.box >= boxes.size) {
            return null
        }
        return boxes[position.box].slots[position.slot]
    }

    override fun getPosition(Pokemon pokemon): PCPosition? {
        for (boxNumber in boxes.indices) {
            val box = boxes[boxNumber]
            for (slotNumber in box.slots.indices) {
                if (box.slots[slotNumber] == pokemon) {
                    return PCPosition(boxNumber, slotNumber)
                }
            }
        }
        return null
    }

    fun renameBox(Int boxNumber, String name?) {
        if (boxes.size > boxNumber) {
            boxes[boxNumber].name = if (name.isNullOrBlank()) null else Component.literal(name).bold()
        }
    }

    fun changeBoxWallpaper(Int boxNumber, wallpaper: ResourceLocation) {
        if (boxes.size > boxNumber) {
            boxes[boxNumber].wallpaper = wallpaper
        }
    }
}