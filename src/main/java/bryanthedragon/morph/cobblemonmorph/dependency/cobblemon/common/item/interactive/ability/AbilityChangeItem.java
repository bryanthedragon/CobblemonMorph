/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.ability

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonEntityInteraction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.ability.AbilityChanger
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.lang
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.item.Rarity

open class AbilityChangeItem<T : PotentialAbility>(
    val changer: AbilityChanger<T>
) : CobblemonItem(Properties().apply {
    when(changer) {
        AbilityChanger.HIDDEN_ABILITY -> rarity(Rarity.EPIC)
        AbilityChanger.COMMON_ABILITY -> rarity(Rarity.RARE)
    }
}), PokemonEntityInteraction {

    override val accepted: Set<PokemonEntityInteraction.Ownership> = setOf(PokemonEntityInteraction.Ownership.OWNER)

    override fun processInteraction(ServerPlayer player, entity: PokemonEntity, ItemStack stack): Boolean {
        if (this.changer.performChange(entity.pokemon)) {
            stack.consume(1, player)
            val feedback = lang(
                "ability_changer.changed",
                entity.pokemon.getDisplayName(),
                entity.pokemon.ability.displayName.asTranslated()
            )
            player.sendSystemMessage(feedback)
            return true
        }
        return false
    }

}