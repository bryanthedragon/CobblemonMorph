/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import net.minecraft.client.gui.GuiGraphics

class MoveCategoryIcon(
    val x: Number,
    val y: Number,
    val category: DamageCategory,
    val opacity: Float = 1F
) {
    companion object {
        private const val WIDTH = 24
        private const val HEIGHT = 16
        private const val SCALE = 0.5F

        private val categoriesResource = cobblemonResource("textures/gui/categories.png")
    }

    fun render(context: GuiGraphics) {
        blitk(
            matrixStack = context.pose(),
            texture = categoriesResource,
            x = x.toFloat() / SCALE,
            y = y.toFloat() / SCALE,
            width = WIDTH,
            height = HEIGHT,
            vOffset = HEIGHT * category.textureXMultiplier,
            textureHeight = HEIGHT * 3,
            alpha = opacity,
            scale = SCALE
        )
    }
}