/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

public class BoxStorageSlot(
    x: Int, y: Int,
    private val parent: StorageWidget,
    private val pc: ClientPC,
    val position: PCPosition,
    onPress: OnPress
) : StorageSlot(x, y, parent, onPress) {

    override fun getPokemon(): Pokemon? {
        return pc.get(position)
    }

    override fun shouldRender(): Boolean {
        if (!super.shouldRender()) return false

        val grabbedSlot = parent.grabbedSlot
        return if (grabbedSlot == null) {
            true
        } else {
            grabbedSlot.getPokemon() != getPokemon()
        }
    }
}