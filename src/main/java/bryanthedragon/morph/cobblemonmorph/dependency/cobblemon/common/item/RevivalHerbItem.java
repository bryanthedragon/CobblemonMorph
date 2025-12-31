/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.healing.PokemonHealedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HealingSource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.PokemonSelectingItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RevivalHerbBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle.BagItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import kotlin.math.ceil
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

public class RevivalHerbItem(block: RevivalHerbBlock) : ItemNameBlockItem(block, Properties()), PokemonSelectingItem, HealingSource {

    init {
        // 65% to raise composter level
        Cobblemon.implementation.registerCompostable(this, .65F)
    }

    private val runtime = MoLangRuntime().setup()

    override val bagItem = object : BagItem {
        override val itemName = "item.cobblemon.revival_herb"
        override val returnItem = Items.AIR
        override fun canUse(ItemStack stack, battle: PokemonBattle, target: BattlePokemon) = target.health <= 0
        override fun getShowdownInput(actor: BattleActor, BattlePokemon battlePokemon, data: String?): String {
            battlePokemon.effectedPokemon.decrementFriendship(CobblemonMechanics.remedies.getFriendshipDrop("revival_herb", runtime))
            return "revive 0.25"
        }
    }

    override fun use(Level world, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (user is ServerPlayer) {
            val result = use(user, user.getItemInHand(hand))
            if (result.result != InteractionResult.PASS) {
                return result
            }
        }
        return InteractionResultHolder.success(user.getItemInHand(hand))
    }

    override fun canUseOnPokemon(ItemStack stack, Pokemon pokemon) = pokemon.isFainted()
    override fun applyToPokemon(
        ServerPlayer player,
        ItemStack stack,
        Pokemon pokemon
    ): InteractionResultHolder<ItemStack>? {
        return if (pokemon.isFainted()) {
            var amount = ceil(pokemon.maxHealth / 4F).toInt()
            CobblemonEvents.POKEMON_HEALED.postThen(PokemonHealedEvent(pokemon, amount, this), { cancelledEvent -> return InteractionResultHolder.fail(stack)}) { event ->
                amount = event.amount
            }
            pokemon.entity?.playSound(CobblemonSounds.MEDICINE_HERB_USE, 1F, 1F)

            pokemon.currentHealth = amount
            pokemon.decrementFriendship(CobblemonMechanics.remedies.getFriendshipDrop("revival_herb", runtime))
            stack.consume(1, player)
            InteractionResultHolder.success(stack)
        } else {
            InteractionResultHolder.pass(stack)
        }
    }

    override fun applyToBattlePokemon(ServerPlayer player, ItemStack stack, BattlePokemon battlePokemon) {
        super.applyToBattlePokemon(player, stack, battlePokemon)
        battlePokemon.entity?.playSound(CobblemonSounds.MEDICINE_HERB_USE, 1F, 1F)
    }
}