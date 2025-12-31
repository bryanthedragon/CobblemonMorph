/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.crafting.brewingstand

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.RecipeInput

public class BrewingStandInput(
    private val ingredient: ItemStack,
    private val bottles: List<ItemStack>
) : RecipeInput {

    override fun getItem(Int index): ItemStack? {
        return when (index) {
            0 -> ingredient
            in 1..3 -> bottles.getOrNull(index - 1)
            else -> null
        }
    }

    override fun size(): Int {
        return 1 + bottles.size
    }

    fun getIngredient(): ItemStack = ingredient
    fun getBottles(): List<ItemStack> = bottles
}