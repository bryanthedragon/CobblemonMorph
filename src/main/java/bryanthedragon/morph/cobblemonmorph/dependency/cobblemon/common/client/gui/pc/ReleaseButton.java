/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.CobblemonRenderable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component

public class ReleaseButton(
    x: Int, y: Int,
    private val parent: StorageWidget,
    onPress: OnPress
) : Button(x, y, WIDTH, HEIGHT, Component.literal("Release"), onPress, DEFAULT_NARRATION), CobblemonRenderable {

    final class Companion {
        private const val WIDTH = 58
        private const val HEIGHT = 16

        private val buttonResource = cobblemonResource("textures/gui/pc/pc_release_button.png")
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        if (parent.canDeleteSelected() && !parent.displayConfirmRelease) {
            blitk(
                matrixStack = context.pose(),
                texture = buttonResource,
                x = x,
                y = y,
                width = WIDTH,
                height = HEIGHT,
                vOffset = if (isHovered(mouseX.toDouble(), mouseY.toDouble())) HEIGHT else 0,
                textureHeight = HEIGHT * 2
            )

            drawScaledText(
                context = context,
                font = CobblemonResources.DEFAULT_LARGE,
                text = lang("ui.pc.release").bold(),
                x = x + (WIDTH / 2),
                y = y + 3.5,
                centered = true,
                shadow = true
            )
        }
    }

    override fun playDownSound(pHandler: SoundManager) {
    }

    fun isHovered(mouseX: Double, mouseY: Double) = mouseX.toFloat() in (x.toFloat()..(x.toFloat() + WIDTH)) && mouseY.toFloat() in (y.toFloat()..(y.toFloat() + HEIGHT))
}