/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.blitk;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.bold;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.text;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.stats.StatWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.drawScaledText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

/**
 * Renders an [IntSpeciesFeature] as a bar in the summary screen.
 *
 * @author Hiroku
 * @since November 13th, 2023
 */
public class BarSummarySpeciesFeatureRenderer(String name, MutableComponent displayName, ResourceLocation underlay, ResourceLocation overlay, Pokemon pokemon, Int min, Int max, Int value = 0, Vec3 colour = null) extends SummarySpeciesFeatureRenderer<IntSpeciesFeature> {
    fun render(GuiGraphics guiGraphics, Float x, Float y, Pokemon pokemon, IntSpeciesFeature feature) {
        renderElement(guiGraphics, x, y, pokemon, feature.value);
    }

    open fun renderElement(GuiGraphics guiGraphics, Float x, Float y, Pokemon pokemon, Int barValue = value) {
        barRatio = ((barValue - min) / (max - min).toFloat()).coerceAtMost(1f);
        barWidth = Mth.ceil(barRatio * 110);

        blitk(matrixStack = guiGraphics.pose(), texture = underlay, x = x, y = y, height = 24, width = 116);

        renderBar(guiGraphics, x, y, barValue, barRatio, barWidth);

        blitk(matrixStack = guiGraphics.pose(),texture = overlay, x = (x + 3) / StatWidget.SCALE, y = (y + 13) / StatWidget.SCALE, height = 20, width = 220, scale = StatWidget.SCALE)

        // Label
        drawScaledText(context = guiGraphics, font = CobblemonResources.DEFAULT_LARGE, text = displayName.bold(), x = x + 58, y = y + 2.5, centered = true, shadow = true);

        drawScaledText(context = guiGraphics, text = barValue.toString().text(), x = x + 9, y = y + 6, scale = StatWidget.SCALE, centered = true);
        
        drawScaledText(context = guiGraphics, text = "${Mth.floor(barRatio * 100)}%".text(), x = x + 107, y = y + 6, scale = StatWidget.SCALE, centered = true)
    }

    open fun renderBar(GuiGraphics guiGraphics, Float x, Float y, Int barValue, Float barRatio, Int barWidth) {red = (colour?.x ?: 255.0) / 255.0; green = (colour?.y ?: 255.0) / 255.0; blue = (colour?.z ?: 255.0) / 255.0;

        blitk(matrixStack = guiGraphics.pose(), texture = CobblemonResources.WHITE, x = x + 3, y = y + 13, height = 10, width = barWidth, red = red, green = green, blue = blue);
    }
}
