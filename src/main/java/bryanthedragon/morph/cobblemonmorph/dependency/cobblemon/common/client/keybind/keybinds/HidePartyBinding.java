/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.KeybindCategories
import com.mojang.blaze3d.platform.InputConstants
public final class HidePartyBinding : CobblemonKeyBinding(
    "key.cobblemon.hideparty",
    InputConstants.Type.KEYSYM,
    InputConstants.KEY_O,
    KeybindCategories.COBBLEMON_CATEGORY
) {
    var shouldHide = false

    override fun onPress() {
        shouldHide = !shouldHide
    }
}