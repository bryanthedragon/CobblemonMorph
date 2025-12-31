/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItemComponents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.riding.stats.RidingStat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.pot.CookingQuality
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

public class AprijuiceItem(val type: Apricorn): CobblemonItem(Properties().stacksTo(16)), PokemonSelectingItem {
    override val bagItem = null

    fun hasRideBoosts(ItemStack stack): Boolean {
        val rideBoostComponent = stack.get(CobblemonItemComponents.RIDE_BOOST)
        return rideBoostComponent?.boosts?.isNotEmpty() == true
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = user.getItemInHand(hand)

        return if (!hasRideBoosts(stack)) {
            // act like a drink :D
            user.startUsingItem(hand)
            InteractionResultHolder.consume(stack)
        } else {
            if (world is ServerLevel && user is ServerPlayer) {
                return super<PokemonSelectingItem>.use(user, stack)
            }
            InteractionResultHolder.pass(stack)
        }
    }

    override fun getName(ItemStack stack): Component {
        val rideBoostsComponent = stack.get(CobblemonItemComponents.RIDE_BOOST)
        val hasBoosts = rideBoostsComponent?.boosts?.isNotEmpty() == true
        val quality = rideBoostsComponent?.getQuality()

        val baseNameKey = "item.cobblemon.aprijuice_${type.name.lowercase()}"
        val prefixKey = when {
            hasBoosts && quality == CookingQuality.HIGH -> "item.cobblemon.aprijuice.prefix.delicious"
            hasBoosts && quality == CookingQuality.MEDIUM -> "item.cobblemon.aprijuice.prefix.tasty"
            !hasBoosts -> "item.cobblemon.aprijuice.prefix.plain"
            else -> null
        }

        return if (prefixKey != null) {
            Component.translatable("item.cobblemon.aprijuice.quality_format",
                Component.translatable(prefixKey),
                Component.translatable(baseNameKey)
            )
        } else {
            Component.translatable(baseNameKey)
        } // todo if no boosts call it "Plain Red Aprijuice" or "Raw Red Aprijuice" maybe and have players able to drink it
    }

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon): Boolean {
        val boosts = getBoosts(stack)
        return boosts.isNotEmpty() && boosts.any { pokemon.canAddRideBoost(it.key) } && super.canUseOnPokemon(stack, pokemon)
    }

    fun getBoosts(ItemStack stack): Map<RidingStat, Int> {
        return stack.get(CobblemonItemComponents.RIDE_BOOST)?.boosts ?: emptyMap()
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
        
        val boosts = getBoosts(stack)
        pokemon.addRideBoosts(boosts.mapValues { it.value.toFloat() })

        stack.consume(1, player)

        return InteractionResultHolder.success(stack)
    }

    override fun finishUsingItem(ItemStack stack, Level world, user: LivingEntity): ItemStack {
        if (!hasRideBoosts(stack) && user is Player && !world.isClientSide) {
            user.foodData.eat(4, 1.2f)
            stack.consume(1, user)
        }

        return super.finishUsingItem(stack, world, user)
    }

    override fun getUseAnimation(ItemStack stack): UseAnim {
        return if (hasRideBoosts(stack)) UseAnim.NONE else UseAnim.DRINK
    }

    override fun getUseDuration(ItemStack stack, LivingEntity entity): Int {
        return if (hasRideBoosts(stack)) 0 else 32 // 32 ticks like drinking a potion
    }

    // todo not sure which one is needed at the moment, but I assume just the eating sound?
    override fun getDrinkingSound(): SoundEvent {
        return SoundEvents.GENERIC_DRINK
    }

    override fun getEatingSound(): SoundEvent {
        return SoundEvents.GENERIC_DRINK
    }
}
