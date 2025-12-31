/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.KeybindCategories
import com.mojang.blaze3d.platform.InputConstants
public final class UpShiftPartyBinding : CobblemonKeyBinding(
    "key.cobblemon.upshiftparty",
    InputConstants.Type.KEYSYM,
    InputConstants.KEY_UP,
    KeybindCategories.COBBLEMON_CATEGORY
) {
    override fun onPress() {
        CobblemonClient.storage.shiftSelected(false)
    }
}