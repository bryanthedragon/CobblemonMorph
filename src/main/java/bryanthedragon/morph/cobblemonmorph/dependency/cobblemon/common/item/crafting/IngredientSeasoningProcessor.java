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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.components.IngredientComponent
import net.minecraft.world.item.ItemStack
public final class IngredientSeasoningProcessor : SeasoningProcessor {
    override val type = "ingredient"

    override fun apply(result: ItemStack, seasoning: List<ItemStack>) {
        val ingredients = seasoning.mapNotNull { seasoningStack ->
            val seasoningData = Seasonings.getFromItemStack(seasoningStack)
            if (seasoningData != null) {
                seasoningStack.item.builtInRegistryHolder().key().location()
            } else null
        }

        if (ingredients.isNotEmpty()) {
            result.set(CobblemonItemComponents.INGREDIENT, IngredientComponent(ingredients))
        }
    }

    override fun consumesItem(seasoning: ItemStack): Boolean {
        val seasoningData = Seasonings.getFromItemStack(seasoning)
        return seasoningData != null
    }
}