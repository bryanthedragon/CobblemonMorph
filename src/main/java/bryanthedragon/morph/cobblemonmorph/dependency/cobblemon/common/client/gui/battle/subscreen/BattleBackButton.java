/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.CobblemonRenderable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics

class BattleBackButton(val x: Float, val y: Float) : CobblemonRenderable {
    companion object {
        const val WIDTH = 58
        const val HEIGHT = 34
        const val SCALE = 0.5F
    }

    override fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        blitk(
            matrixStack = context.pose(),
            texture = cobblemonResource("textures/gui/battle/battle_back.png"),
            x = x * 2,
            y = y * 2,
            height = HEIGHT,
            width = WIDTH,
            vOffset = if (isHovered(mouseX.toDouble(), mouseY.toDouble())) HEIGHT else 0,
            textureHeight = HEIGHT * 2,
            scale = SCALE
        )
    }

    fun isHovered(mouseX: Double, mouseY: Double) = mouseX.toFloat() in (x..(x + (WIDTH * SCALE))) && mouseY.toFloat() in (y..(y + (HEIGHT * SCALE)))
}