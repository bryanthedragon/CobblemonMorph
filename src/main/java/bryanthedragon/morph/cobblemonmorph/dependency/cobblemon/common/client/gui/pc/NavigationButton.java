/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.CobblemonRenderable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.sounds.SoundManager
import net.minecraft.network.chat.Component

public class NavigationButton(
    pX: Int, pY: Int,
    private val forward: Boolean,
    onPress: OnPress
): Button(pX, pY, (SIZE * SCALE).toInt(), (SIZE * SCALE).toInt(), Component.literal("Navigation"), onPress, DEFAULT_NARRATION), CobblemonRenderable {

    final class Companion {
        private const val SIZE = 14
        private const val SCALE = 0.5F
        private val forwardButtonResource = cobblemonResource("textures/gui/pc/pc_arrow_next.png")
        private val backwardsButtonResource = cobblemonResource("textures/gui/pc/pc_arrow_previous.png")
    }

    override fun renderWidget(context: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTicks: Float) {
        blitk(
            matrixStack = context.pose(),
            x = x / SCALE,
            y = y / SCALE,
            texture = if (forward) forwardButtonResource else backwardsButtonResource,
            width = SIZE,
            height = SIZE,
            vOffset = if (isHovered()) SIZE else 0,
            textureHeight = SIZE * 2,
            scale = SCALE
        )
    }

    override fun playDownSound(soundManager: SoundManager) {
        soundManager.play(SimpleSoundInstance.forUI(CobblemonSounds.PC_CLICK, 1.0F))
    }
}
