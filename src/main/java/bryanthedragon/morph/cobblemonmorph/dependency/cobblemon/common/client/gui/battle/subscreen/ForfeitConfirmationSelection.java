/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForfeitActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.PassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.battleRequest.BattleResponseButton
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

public class ForfeitConfirmationSelection(
    battleGUI: BattleGUI,
    request: SingleActionRequest
) : BattleActionSelection(
    battleGUI,
    request,
    x = 0,
    y = 0,
    width = WIDTH,
    height = HEIGHT,
    battleLang("ui.forfeit_confirmation")
) {
    final class Companion {
        private const val WIDTH = 113
        private const val HEIGHT = 45

        private val backgroundResource = cobblemonResource("textures/gui/interact/request/confirmation_request.png")
    }

    var acceptButton: BattleResponseButton
    var declineButton: BattleResponseButton

    init {
        val xPos = (Minecraft.getInstance().window.guiScaledWidth / 2) - (WIDTH / 2)
        val yPos = (Minecraft.getInstance().window.guiScaledHeight / 2) - (HEIGHT / 2)

        acceptButton = BattleResponseButton(xPos + 22, yPos + 18, true) {}
        declineButton = BattleResponseButton(xPos + 57, yPos + 18, false) {}
    }

    override fun mousePrimaryClicked(mouseX: Double, mouseY: Double): Boolean {
        if (declineButton.isHovered(mouseX, mouseY) || acceptButton.isHovered(mouseX, mouseY)) {
            if (declineButton.isHovered(mouseX, mouseY)) {
                battleGUI.changeActionSelection(null)
            } else {
                battleGUI.selectAction(request, ForfeitActionResponse())

                // Need to fill out any other pending requests
                var pendingRequest = CobblemonClient.battle?.getFirstUnansweredRequest()
                while (pendingRequest != null) {
                    battleGUI.selectAction(pendingRequest, PassActionResponse)
                    pendingRequest = CobblemonClient.battle?.getFirstUnansweredRequest()
                }
            }
            playDownSound(Minecraft.getInstance().soundManager)
            return true
        }

        return false
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        if (opacity <= 0.05F) return

        val xPos = (Minecraft.getInstance().window.guiScaledWidth / 2) - (WIDTH / 2)
        val yPos = (Minecraft.getInstance().window.guiScaledHeight / 2) - (HEIGHT / 2)

        blitk(
            matrixStack = context.pose(),
            texture = backgroundResource,
            x = xPos,
            y = yPos,
            alpha = opacity,
            width = WIDTH,
            height = HEIGHT
        )

        drawScaledText(
            context = context,
            font = CobblemonResources.DEFAULT_LARGE,
            text = battleLang("ui.forfeit").bold(),
            x = xPos + 42,
            y = yPos + 2,
            centered = true,
            shadow = true
        )

        acceptButton.render(context, mouseX, mouseY, delta)
        declineButton.render(context, mouseX, mouseY, delta)
    }
}