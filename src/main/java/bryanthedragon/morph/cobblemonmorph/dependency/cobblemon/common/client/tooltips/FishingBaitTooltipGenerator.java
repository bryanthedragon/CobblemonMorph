/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Seasonings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.blue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.gold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.green
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.obfuscate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.PokerodItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import java.text.DecimalFormat
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
public final class FishingBaitTooltipGenerator : TooltipGenerator() {
    private val fishingBaitItemClass by lazy { lang("item_class.fishing_bait").blue() }

    override fun generateCategoryTooltip(ItemStack stack, lines: MutableList<Component>): MutableList<Component>? {
        if (!SpawnBaitEffects.isFishingBait(stack)) {
            return null
        }
        return mutableListOf(fishingBaitItemClass)
    }

    override fun generateAdditionalTooltip(ItemStack stack, lines: MutableList<Component>): MutableList<Component>? {
        val resultLines = mutableListOf<Component>()

        if (SpawnBaitEffects.isFishingBait(stack)) resultLines.add(this.fishingBaitItemClass)

        return resultLines
    }

}