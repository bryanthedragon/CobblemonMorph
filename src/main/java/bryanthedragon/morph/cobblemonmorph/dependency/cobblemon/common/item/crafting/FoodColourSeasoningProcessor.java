/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.crafting

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Seasonings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.FoodColourComponent
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.ItemStack
public final class FoodColourSeasoningProcessor : SeasoningProcessor {
    override val type = "food_colour"
    override fun apply(result: ItemStack, seasoning: List<ItemStack>) {
        val colours = mutableListOf<DyeColor>()
        for (seasoningStack in seasoning) {
            val seasoningData = Seasonings.getFromItemStack(seasoningStack)
            seasoningData?.colour?.run(colours::add)
        }
        result.set(CobblemonItemComponents.FOOD_COLOUR, FoodColourComponent(colours))
    }

    override fun consumesItem(seasoning: ItemStack): Boolean {
        val seasoningData = Seasonings.getFromItemStack(seasoning)
        return seasoningData != null
    }
}