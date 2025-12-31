/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.ItemStack
public final class PokeBaitItemColorProvider : ItemColor {
    override fun getColor(ItemStack stack, layer: Int): Int {
        val colourComponent = stack.get(CobblemonItemComponents.FOOD_COLOUR) ?: return -1

        val primaryColor = colourComponent.colours.getOrNull(0)
        val secondaryColor = colourComponent.colours.getOrNull(1)
        val tertiaryColor = colourComponent.colours.getOrNull(2)

        val colour = when (layer) {
            0 -> primaryColor?.textureDiffuseColor
            1 -> secondaryColor?.textureDiffuseColor
            2 -> tertiaryColor?.textureDiffuseColor
            else -> -1
        } ?: -1

        return colour
    }
}