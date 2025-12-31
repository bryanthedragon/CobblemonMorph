/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.stats.features

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.BarSummarySpeciesFeatureRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.client.gui.GuiGraphics

public class FullnessFeatureRenderer(
    val selectedPokemon pokemon
) : BarSummarySpeciesFeatureRenderer(
    "fullness",
    lang("ui.stats.fullness"),
    cobblemonResource("textures/gui/summary/summary_stats_other_bar.png"),
    cobblemonResource("textures/gui/summary/summary_stats_fullness_overlay.png"),
    selectedPokemon,
    0,
    selectedPokemon.getMaxFullness(),
    selectedPokemon.currentFullness
) {
    override fun render(GuiGraphics guiGraphics, Float x, Float y, Pokemon pokemon): Boolean {
        renderElement(guiGraphics, x, y, pokemon)
        return true
    }

    override fun renderBar(GuiGraphics guiGraphics, Float x, Float y, barValue: Int, barRatio: Float, barInt width) {
        val (red, green, blue) = when {
            barRatio <= 0.33 -> Triple(120F/255F, 200F/255F, 80F/255F) // Green
            barRatio <= 0.66 -> Triple(240F/255F, 200F/255F, 65F/255F) // Yellow
            else -> Triple(230F/255F, 80F/255F, 65F/255F) // Red
        }

        blitk(
            matrixStack = guiGraphics.pose(),
            texture = CobblemonResources.WHITE,
            x = x + 3,
            y = y + 13,
            height = 10,
            width = barWidth,
            red = red,
            green = green,
            blue = blue
        )
    }
}
