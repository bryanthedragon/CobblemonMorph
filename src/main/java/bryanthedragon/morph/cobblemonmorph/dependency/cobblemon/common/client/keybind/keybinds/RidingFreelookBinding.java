/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonBlockingKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.KeybindCategories

import com.mojang.blaze3d.platform.InputConstants

public final class RidingFreelookBinding : CobblemonBlockingKeyBinding(
    "key.cobblemon.ridingfreelook",
    InputConstants.Type.KEYSYM,
    InputConstants.KEY_LALT,
    KeybindCategories.COBBLEMON_CATEGORY
) {
    override fun onTick() {
    }
    override fun onRelease() {
    }
    override fun onPress() {
    }
}