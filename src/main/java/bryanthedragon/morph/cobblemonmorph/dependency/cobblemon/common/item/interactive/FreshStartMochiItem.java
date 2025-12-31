/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

public class FreshStartMochiItem : CobblemonItem(Properties()), PokemonSelectingItem {
    override val bagItem = null

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = pokemon.evs.any { it.value > 0 }
            && super.canUseOnPokemon(stack, pokemon)

    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack> {
        if (!canUseOnPokemon(stack, pokemon)) {
            return InteractionResultHolder.fail(stack)
        }

        pokemon.feedPokemon(1)
        pokemon.evs.forEach {
            pokemon.evs[it.key] = 0
        }

        //pokemon.entity?.playSound(CobblemonSounds.MOCHI_USE, 1F, 1F) todo use mochi sounds for fullness levels and replace above
        stack.consume(1, player)

        return InteractionResultHolder.success(stack)
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user is ServerPlayer) {
            return use(user, user.getItemInHand(hand))
        }

        return InteractionResultHolder.success(user.getItemInHand(hand))
    }
}