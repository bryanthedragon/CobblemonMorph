/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.drawProfilePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.FloatingState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.fromEulerXYZDegrees
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import org.joml.Quaternionf
import org.joml.Vector3f

class ModelWidget(
    pX: Int, pY: Int,
    pWidth: Int, pHeight: Int,
    var pokemon: RenderablePokemon,
    var baseScale: Float = 2.7F,
    var rotationY: Float = 35F,
    var offsetY: Double = 0.0
): SoundlessWidget(pX, pY, pWidth, pHeight, Component.literal("Trade - ModelWidget")) {
    var state = FloatingState()
    private var rotVec = Vector3f(13F, rotationY, 0F)

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        val matrices = context.pose()
        matrices.pushPose()
        matrices.translate(x + width * 0.5, y.toDouble() + offsetY, 0.0)
        matrices.scale(baseScale, baseScale, baseScale)

        drawProfilePokemon(
            renderablePokemon = pokemon,
            matrixStack = matrices,
            rotation = Quaternionf().fromEulerXYZDegrees(rotVec),
            state = state,
            partialTicks = delta
        )

        matrices.popPose()
    }
}