/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.*
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

class InfoBlockWidget(
    pX: Int,
    pY: Int,
    blockWidth: Int,
    blockHeight: Int,
    private val text: MutableComponent,
    private val withinRowVerticalTextOffset: Int,
    private val font: ResourceLocation,
) : SoundlessWidget(pX, pY, blockWidth, blockHeight, Component.literal("InfoBlockWidget")) {

    override fun renderWidget(context: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTicks: Float) {
        drawScaledText(
            context = context,
            font = font,
            text = text.bold(),
            x = x,
            y = y + withinRowVerticalTextOffset,
            shadow = true,
            pMouseX = pMouseX,
            pMouseY = pMouseY
        )
    }

}