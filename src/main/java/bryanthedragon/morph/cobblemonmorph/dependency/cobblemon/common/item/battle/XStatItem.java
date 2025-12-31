/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

public class XStatItem(val Stat stat , stages: Int = 2) : CobblemonItem(Properties()), SimpleBagItemLike {
    override val bagItem = object : BagItem {
        override val itemName = "item.cobblemon.x_${stat.identifier.path}"
        override val returnItem = Items.AIR
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) = target.health > 0
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?): String {
            battlePokemon.effectedPokemon.incrementFriendship(1)
            return "x_stat ${stat.showdownId} $stages"
        }
    }
}