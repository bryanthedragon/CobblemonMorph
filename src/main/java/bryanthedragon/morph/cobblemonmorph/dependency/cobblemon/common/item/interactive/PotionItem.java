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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.CobblemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.genericRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.giveOrDropItemStack
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.resolveInt
import java.lang.Integer.min
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.Rarity
import net.minecraft.world.level.Level

public class PotionItem(
    val type: PotionType
) : CobblemonItem(Properties().apply {
    when (type.name) {
        PotionType.MAX_POTION.name -> rarity(Rarity.UNCOMMON)
        PotionType.FULL_RESTORE.name -> rarity(Rarity.UNCOMMON)
    }
}), PokemonSelectingItem, HealingSource {

    override val bagItem = type
    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = !pokemon.isFullHealth() && pokemon.currentHealth > 0
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
        if (pokemon.isFullHealth()) {
            return InteractionResultHolder.fail(stack)
        }
        val potionHealAmount = genericRuntime.resolveInt(type.amountToHeal())
        var healthToRestore = potionHealAmount
        CobblemonEvents.POKEMON_HEALED.postThen(PokemonHealedEvent(pokemon, potionHealAmount, this), { cancelledEvent -> return InteractionResultHolder.fail(stack)}) { event ->
            healthToRestore = event.amount
        }
        pokemon.currentHealth = min(pokemon.currentHealth + healthToRestore, pokemon.maxHealth)
        if (type.curesStatus) {
            pokemon.status = null
        }
        pokemon.entity?.playSound(CobblemonSounds.MEDICINE_SPRAY_USE, 1F, 1F)
        if (!player.hasInfiniteMaterials()) {
            stack.shrink(1)
            player.giveOrDropItemStack(ItemStack(Items.GLASS_BOTTLE))
        }
        return InteractionResultHolder.success(stack)
    }

    override fun applyToBattlePokemon(ServerPlayer player, ItemStack stack, BattlePokemon battlePokemon) {
        super.applyToBattlePokemon(player, stack, battlePokemon)
        battlePokemon.entity?.playSound(CobblemonSounds.MEDICINE_SPRAY_USE, 1F, 1F)
    }
}

public enum PotionType(val amountToHeal: () -> ExpressionLike, val curesStatus: Boolean) : BagItem {
    POTION({ bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics.potions.potionRestoreAmount }, false) {
        override val itemName = "item.cobblemon.potion"
        override val returnItem = Items.GLASS_BOTTLE
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "potion ${genericRuntime.resolveInt(amountToHeal(), battlePokemon)}"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    },
    SUPER_POTION({ bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics.potions.superPotionRestoreAmount }, false) {
        override val itemName = "item.cobblemon.super_potion"
        override val returnItem = Items.GLASS_BOTTLE
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "potion ${genericRuntime.resolveInt(amountToHeal(), battlePokemon)}"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    },
    HYPER_POTION({ bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics.potions.hyperPotionRestoreAmount }, false) {
        override val itemName = "item.cobblemon.hyper_potion"
        override val returnItem = Items.GLASS_BOTTLE
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "potion ${genericRuntime.resolveInt(amountToHeal(), battlePokemon)}"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    },
    MAX_POTION({ 999999.0.asExpressionLike() }, false) {
        override val itemName = "item.cobblemon.max_potion"
        override val returnItem = Items.GLASS_BOTTLE
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "potion ${battlePokemon.maxHealth - battlePokemon.health}"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    },
    FULL_RESTORE({ 999999.0.asExpressionLike() }, true) {
        override val itemName = "item.cobblemon.full_restore"
        override val returnItem = Items.GLASS_BOTTLE
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?) = "full_restore"
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) =  target.health < target.maxHealth && target.health > 0
    }
}
