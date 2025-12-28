/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.getColourMixFromColors
import net.minecraft.client.color.item.ItemColor
import net.minecraft.world.item.ItemStack
final class PokeSnackItemColorProvider : ItemColor {
    private const val BASE_LAYER_INDEX = 0

    override fun getColor(stack: ItemStack, layer: Int): Int {
        val colorComponent = stack.get(CobblemonItemComponents.FOOD_COLOUR) ?: return -1

        if (layer == BASE_LAYER_INDEX) {
            val colorMix = getColourMixFromColors(colorComponent.getColoursAsARGB())
            if (colorMix != null) return colorMix
        }

        return -1
    }
}
