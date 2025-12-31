/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.ParentWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.network.chat.MutableComponent

abstract class BattleActionSelection(
    val battleGUI: BattleGUI,
    val request: SingleActionRequest,
    x: Int,
    y: Int,
    Int width,
    Int height,
    name: MutableComponent
) : ParentWidget(x, y, width, height, name) {
    val opacity: Float
        get() = battleGUI.opacity

    override fun mouseClicked(pMouseX: Double, pMouseY: Double, pButton: Int): Boolean {
        if(pButton == InputConstants.MOUSE_BUTTON_LEFT){
            return mousePrimaryClicked(pMouseX, pMouseY)
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton)
    }

    abstract fun mousePrimaryClicked(pMouseX: Double, pMouseY: Double): Boolean
}