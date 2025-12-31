/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Flavour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.PokePuffUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.blue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokePuffItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
public final class PokePuffTooltipGenerator : TooltipGenerator() {
    override fun generateCategoryTooltip(ItemStack stack, lines: MutableList<Component>): MutableList<Component>? {
        if (stack.item !is PokePuffItem) return null
        if (stack.get(CobblemonItemComponents.FLAVOUR) == null) return null
        return mutableListOf(lang("tooltip.poke_puff.friendship_effects").blue())
    }

    override fun generateAdditionalTooltip(ItemStack stack, lines: MutableList<Component>): MutableList<Component>? {
        if (stack.item !is PokePuffItem) return null

        val resultLines = mutableListOf<Component>()

        val dominantFlavour = PokePuffUtils.getDominantFlavour(stack)
        if (dominantFlavour != null) {
            val friendshipLiked = PokePuffUtils.getFriendshipChangeLiked(stack)
            val friendshipDisliked = PokePuffUtils.getFriendshipChangeDisliked(stack)

            // Get the nature label
            val natureLabelKey = when (dominantFlavour) {
                Flavour.MILD -> "tooltip.cobblemon.poke_puff.nature.neutral"
                else -> "cobblemon.stat.${PokePuffUtils.getFlavorNatureLabel(dominantFlavour)}.name"
            }

            resultLines.add(Component.literal(""))
            resultLines.add(lang("tooltip.poke_puff.friendship_effects").blue())

            resultLines.add(
                Component.translatable(
                    "tooltip.cobblemon.poke_puff.friendship_increase",
                    friendshipLiked.toString(),
                    Component.translatable(natureLabelKey)
                ).withStyle(ChatFormatting.GREEN)
            )

            resultLines.add(
                Component.translatable(
                    "tooltip.cobblemon.poke_puff.friendship_decrease",
                    friendshipDisliked.toString()
                ).withStyle(ChatFormatting.RED)
            )
        }

        return resultLines
    }
}