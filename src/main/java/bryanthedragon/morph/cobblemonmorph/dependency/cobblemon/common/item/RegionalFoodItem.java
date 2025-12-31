/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

public class RegionalFoodItem(properties: Properties) : Item(properties), PokemonSelectingItem {
    override val bagItem = null

    override fun use(Level world, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)

        if (player !is ServerPlayer) {
            return InteractionResultHolder.pass(stack);
        }

        // Prioritizes healing pokémon with the item
        val superInteractionResult = super<PokemonSelectingItem>.use(player, stack)
        if (superInteractionResult.result != InteractionResult.PASS) {
            return superInteractionResult
        }

        // Otherwise allow eating normally if player needs food OR in creative
        if (player.foodData.needsFood() || player.isCreative) {
            player.startUsingItem(hand)
            return InteractionResultHolder.consume(stack)
        }

        return InteractionResultHolder.pass(stack)
    }

    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack> {
        if (pokemon.status != null) {
            pokemon.status = null
            pokemon.entity?.playSound(SoundEvents.GENERIC_EAT, 1F, 1F)
            stack.consume(1, player)
            return InteractionResultHolder.success(stack)
        }

        return InteractionResultHolder.fail(stack)
    }

    override fun getUseAnimation(ItemStack stack): UseAnim = UseAnim.EAT

    override fun getUseDuration(ItemStack stack, LivingEntity entity): Int = 32

    override fun getEatingSound(): SoundEvent = SoundEvents.GENERIC_EAT

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon): Boolean {
        return pokemon.status != null && pokemon.currentHealth > 0
    }
}