/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonAndMoveSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

public class PPUpItem(
    val amount: Int
) : CobblemonItem(Properties().apply {
    if (amount>1) rarity(Rarity.UNCOMMON)
}), PokemonAndMoveSelectingItem {

    override val bagItem = null
    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = pokemon.moveSet.any { canUseOnMove(stack, it) }
    override fun canUseOnMove(ItemStack stack, move: Move) = move.raisedPpStages < 3
    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon,
        move: Move
    ) {
        if (move.raiseMaxPP(amount)) {
            stack.consume(1, player)
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_PILLS_USE, 1F, 1F)
        }
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user is ServerPlayer) {
            use(user, user.getItemInHand(hand))?.let { return it }
        }
        return InteractionResultHolder.success(user.getItemInHand(hand))
    }
}