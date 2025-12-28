/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpastureAllPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component

class PastureWidget(
    val storageWidget: StorageWidget,
    val pasturePCGUIConfiguration: PasturePCGUIConfiguration,
    x: Int,
    y: Int
): SoundlessWidget(
    x, y, PCGUI.RIGHT_PANEL_WIDTH, PCGUI.RIGHT_PANEL_HEIGHT, Component.literal("PastureWidget")) {

    companion object {
        private val baseResource = cobblemonResource("textures/gui/pasture/pasture_panel.png")
    }

    private val recallButton = RecallButton(
        x = x + 6,
        y = y + 153
    ) {
        storageWidget.pcGui.playSound(CobblemonSounds.PC_CLICK)
        UnpastureAllPokemonPacket(pasturePCGUIConfiguration.pastureId).sendToServer()
    }

    val pastureScrollList = PasturePokemonScrollList(
        listX = x + 6,
        listY = y + 31,
        parent = this
    )

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        blitk(
            matrixStack = context.pose(),
            texture = baseResource,
            x = x,
            y = y,
            width = PCGUI.RIGHT_PANEL_WIDTH,
            height = PCGUI.RIGHT_PANEL_HEIGHT
        )

        drawScaledText(
            context = context,
            font = CobblemonResources.DEFAULT_LARGE,
            text = lang("ui.pasture").bold(),
            x = x + 31.5,
            y = y + 3.5,
            centered = true
        )

        pastureScrollList.render(context, mouseX, mouseY, delta)

        recallButton.render(context, mouseX, mouseY, delta)
    }

    override fun mouseClicked(pMouseX: Double, pMouseY: Double, pButton: Int): Boolean {
        if (recallButton.isHovered(pMouseX, pMouseY)) recallButton.mouseClicked(pMouseX, pMouseY, pButton)
        if (pastureScrollList.isHovered(pMouseX, pMouseY)) pastureScrollList.mouseClicked(pMouseX, pMouseY, pButton)
        return super.mouseClicked(pMouseX, pMouseY, pButton)
    }
}