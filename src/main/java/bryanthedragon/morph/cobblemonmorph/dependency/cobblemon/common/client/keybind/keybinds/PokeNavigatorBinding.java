/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonPartyLockedKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.KeybindCategories
import com.mojang.blaze3d.platform.InputConstants

// ToDo PartyOverlay#render needs to be replaced back to this keybindfinal class PokeNavigatorBinding : CobblemonPartyLockedKeyBinding(
    "key.cobblemon.pokenavigator",
    InputConstants.Type.KEYSYM,
    InputConstants.KEY_N,
    KeybindCategories.COBBLEMON_CATEGORY
) {
    override fun onPress() {
        // Minecraft.getInstance().setScreen(PokeNav())
        try {
            Summary.open(CobblemonClient.storage.party.slots, true, CobblemonClient.storage.selectedSlot)
        } catch (e: Exception) {
            Cobblemon.LOGGER.debug("Failed to open the summary from the PokeNav keybind", e)
        }
    }
}