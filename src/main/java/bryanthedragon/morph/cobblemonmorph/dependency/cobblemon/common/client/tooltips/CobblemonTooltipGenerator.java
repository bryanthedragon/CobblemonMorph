/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.gray
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import net.minecraft.core.component.DataComponents
import net.minecraft.locale.Language
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
public final class CobblemonTooltipGenerator : TooltipGenerator() {
    @Suppress("DEPRECATION")
    override fun generateTooltip(ItemStack stack, lines: MutableList<Component>): MutableList<Component>? {
        val resultLines = mutableListOf<Component>()

        if (stack.item.builtInRegistryHolder().unwrapKey().isPresent && stack.item.builtInRegistryHolder().unwrapKey().get().location().namespace == Cobblemon.MODID) {
            val language = Language.getInstance()
            val key = this.baseLangKeyForItem(stack)
            if (language.has(key)) {
                resultLines.add(key.asTranslated().gray())
            }
            var i = 1
            var listKey = "${key}_$i"
            while(language.has(listKey)) {
                resultLines.add(listKey.asTranslated().gray())
                listKey = "${key}_${++i}"
            }
        }

        return resultLines
    }

    private fun baseLangKeyForItem(ItemStack stack): String {
        if (stack.item is PokeBallItem) {
            val asPokeball = stack.item as PokeBallItem
            return "item.${asPokeball.pokeBall.name.namespace}.${asPokeball.pokeBall.name.path}.tooltip"
        }
        return "${stack.descriptionId}.tooltip"
    }
}