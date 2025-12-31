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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.healing.PokemonHealedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HealingSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.level.Level
import net.minecraft.world.item.Items

public class BerryJuiceItem : CobblemonItem(Properties()), PokemonSelectingItem, HealingSource {
    override val bagItem = object : BagItem {
        override val itemName = "item.cobblemon.berry_juice"
        override val returnItem = Items.BOWL
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "potion 20"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    }

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = !pokemon.isFullHealth() && pokemon.currentHealth > 0
            && super.canUseOnPokemon(stack, pokemon)

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user is ServerPlayer) {
            return use(user, user.getItemInHand(hand))
        }
        return InteractionResultHolder.success(user.getItemInHand(hand))
    }

    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack>? {
        if (!canUseOnPokemon(stack, pokemon)) {
            return InteractionResultHolder.fail(stack)
        }
        pokemon.feedPokemon(1)

        var amount = Integer.min(pokemon.currentHealth + 20, pokemon.maxHealth)
        CobblemonEvents.POKEMON_HEALED.postThen(PokemonHealedEvent(pokemon, amount, this), { cancelledEvent -> return InteractionResultHolder.fail(stack)}) { event ->
            amount = event.amount
        }
        pokemon.currentHealth = amount
        player.playSound(CobblemonSounds.BERRY_EAT, 1F, 1F)
        if (!player.hasInfiniteMaterials())  {
            stack.shrink(1)
            val woodenBowlItemStack = ItemStack(Items.BOWL)
            if (!player.inventory.add(woodenBowlItemStack)) {
                // Drop the item into the world if the inventory is full
                player.drop(woodenBowlItemStack, false)
            }
        }
        return InteractionResultHolder.success(stack)
    }

    override fun applyToBattlePokemon(ServerPlayer player, ItemStack stack, BattlePokemon battlePokemon) {
        super.applyToBattlePokemon(player, stack, battlePokemon)
        battlePokemon.originalPokemon.feedPokemon(1)
        if (!player.hasInfiniteMaterials())  {
            val woodenBowlItemStack = ItemStack(Items.BOWL)
            if (!player.inventory.add(woodenBowlItemStack)) {
                // Drop the item into the world if the inventory is full
                player.drop(woodenBowlItemStack, false)
            }
        }
    }
}
