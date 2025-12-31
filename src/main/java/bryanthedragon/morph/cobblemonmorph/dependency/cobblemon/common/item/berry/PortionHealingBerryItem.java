/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.healing.PokemonHealedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HealingSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.genericRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveFloat
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

/**
 * A berry that heals the Pokémon by some portion of their max HP.
 *
 * @author Hiroku
 * @since August 4th, 2023
 */
public class PortionHealingBerryItem(block: BerryBlock, val canCauseConfusion: Boolean, val portion: () -> ExpressionLike): BerryItem(block), PokemonSelectingItem, HealingSource {
    override val bagItem = object : BagItem {
        override val itemName: String get() = "item.cobblemon.${this@PortionHealingBerryItem.berry()!!.identifier.path}"
        override val returnItem = Items.AIR
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?): String {
            val confuse = if (canCauseConfusion) berry()!!.dislikedBy(battlePokemon.nature) else false
            return "potion_by_portion ${genericRuntime.resolveFloat(portion(), battlePokemon)} $confuse"
        }
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    }

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = !pokemon.isFainted() && !pokemon.isFullHealth()
            && super.canUseOnPokemon(stack, pokemon)

    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!canUseOnPokemon(stack, pokemon)) {
            return InteractionResultHolder.fail(stack)
        }

        // count these berries as multiple feedings due to the amount they heal
        pokemon.feedPokemon(5)

        var amount = Integer.min(pokemon.currentHealth + (genericRuntime.resolveFloat(portion(), pokemon) * pokemon.maxHealth).toInt(), pokemon.maxHealth)
        CobblemonEvents.POKEMON_HEALED.postThen(PokemonHealedEvent(pokemon, amount, this), { cancelledEvent -> return InteractionResultHolder.fail(stack)}) { event ->
            amount = event.amount
        }
        pokemon.currentHealth = amount

        stack.consume(1, player)
        return InteractionResultHolder.success(stack)
    }

    override fun applyToBattlePokemon(ServerPlayer player, ItemStack stack, BattlePokemon battlePokemon) {
        super.applyToBattlePokemon(player, stack, battlePokemon)
        battlePokemon.originalPokemon.feedPokemon(5)
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user is ServerPlayer) {
            return use(user, user.getItemInHand(hand))
        }
        return super<BerryItem>.use(world, user, hand)
    }
}