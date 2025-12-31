/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonAndMoveSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.giveOrDropItemStack
import kotlin.math.min
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

/**
 * Item for recovering PP for a specific move in a Pokémon's move list. Opens a move selection GUI.
 *
 * @author Hiroku
 * @since June 30th, 2023
 */
public class EtherItem(
    val max: Boolean
) : CobblemonItem(Properties().apply {
    if (max) rarity(Rarity.UNCOMMON)
}), PokemonAndMoveSelectingItem {
    override val bagItem = object : BagItem {
        override val itemName = "item.cobblemon.${ if (max) "max_ether" else "ether" }"
        override val returnItem = Items.GLASS_BOTTLE
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) = target.health > 0 && target.moveSet.any { it.currentPp < it.maxPp }
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "ether $data${ if (max) "" else " 10" }"
    }

    override fun canUseOnMove(ItemStack stack, move: Move) = move.currentPp < move.maxPp
    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = pokemon.moveSet.any { canUseOnMove(stack, it) }
    override fun applyToPokemon(ServerPlayer player, ItemStack stack, Pokemon pokemon, move: Move) {
        val moveToRecover = pokemon.moveSet.find { it.template == move.template }
        if (moveToRecover != null && moveToRecover.currentPp < moveToRecover.maxPp) {
            moveToRecover.currentPp = if (max) moveToRecover.maxPp else min(moveToRecover.maxPp, moveToRecover.currentPp + 10)
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_LIQUID_USE, 1F, 1F)
            if (!player.hasInfiniteMaterials()) {
                stack.shrink(1)
                player.giveOrDropItemStack(ItemStack(Items.GLASS_BOTTLE))
            }
        }
    }

    override fun applyToBattlePokemon(ServerPlayer player, ItemStack stack, BattlePokemon battlePokemon, move: Move) {
        super.applyToBattlePokemon(player, stack, battlePokemon, move)
        battlePokemon.entity?.playSound(CobblemonSounds.MEDICINE_LIQUID_USE, 1F, 1F)
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (world is ServerLevel && user is ServerPlayer) {
            return use(user, user.getItemInHand(hand)) ?: InteractionResultHolder.pass(user.getItemInHand(hand))
        }
        return InteractionResultHolder.success(user.getItemInHand(hand))
    }
}