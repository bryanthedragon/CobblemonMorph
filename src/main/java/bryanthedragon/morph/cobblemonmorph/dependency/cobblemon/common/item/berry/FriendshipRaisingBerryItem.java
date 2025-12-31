/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.ItemEvSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.genericRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import kotlin.math.max

/**
 * A berry that raises friendship but lowers EVs in a particular stat.
 *
 * @author Hiroku
 * @since August 4th, 2023
 */
public class FriendshipRaisingBerryItem(block: BerryBlock, val Stat stat ) : BerryItem(block), PokemonSelectingItem {
    override val bagItem = null

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = (pokemon.evs.getOrDefault(stat) > 0 || pokemon.friendship < Cobblemon.config.maxPokemonFriendship)
            && super.canUseOnPokemon(stack, pokemon)

    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack> {
        if (!canUseOnPokemon(stack, pokemon)) {
            return InteractionResultHolder.fail(stack)
        }

        val friendshipRaiseAmount = genericRuntime.resolveInt(CobblemonMechanics.berries.friendshipRaiseAmount, pokemon)

        val increasedFriendship = pokemon.incrementFriendship(friendshipRaiseAmount)

        val evLowerAmount = max(genericRuntime.resolveInt(CobblemonMechanics.berries.evLowerAmount), 0)
        val decreasedEVs = pokemon.evs.add(stat, -evLowerAmount, ItemEvSource(player, stack, pokemon)) != 0

        return if (increasedFriendship || decreasedEVs) {
            pokemon.feedPokemon(1)

            stack.consume(1, player)
            InteractionResultHolder.success(stack)
        } else {
            InteractionResultHolder.pass(stack)
        }
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (world is ServerLevel && user is ServerPlayer) {
            return use(user, user.getItemInHand(hand))
        }
        return super<BerryItem>.use(world, user, hand)
    }
}