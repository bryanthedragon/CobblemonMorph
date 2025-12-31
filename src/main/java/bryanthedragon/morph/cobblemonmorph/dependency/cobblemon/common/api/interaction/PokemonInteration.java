/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.setup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.sound.UnvalidatedPlaySoundS2CPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asExpressionLike
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.giveOrDropItemStack
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withPlayerValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.withQueryValue
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack

/**
 * A
 */
record PokemonInteractionSet(
    val requirements: List<Requirement> = listOf(),
    val interactions: List<PokemonInteraction> = listOf()
)
record PokemonInteraction(
    val grouping: ResourceLocation,
    val requirements: List<Requirement> = listOf(),
    val effects: List<InteractionEffect> = listOf(),
    val cooldown: ExpressionLike = "0".asExpressionLike()
)

public interface InteractionEffect {
    fun applyEffect(Pokemon pokemonEntity, ServerPlayer player)
}

public class DropItemEffect(val item: ResourceLocation, val amount: IntRange?): InteractionEffect {
    override fun applyEffect(
        Pokemon pokemonEntity,
        ServerPlayer player
    ) {
        val item = player.registryAccess().registryOrThrow(Registries.ITEM).get(item) ?: throw IllegalArgumentException("Cannot load item with id: $item")
        val stack = ItemStack(item)
        stack.count = amount?.randomOrNull() ?: 1
        if (stack.isEmpty)
            return
        val height: Double = pokemon.eyeY - 0.3
        val itemEntity = ItemEntity(pokemon.level(), pokemon.x, height, pokemon.z, stack)
        itemEntity.setPickUpDelay(40)
        itemEntity.setThrower(pokemon)
        pokemon.level().addFreshEntity(itemEntity)
    }

    final class Companion {
        val ID = "drop_item"
    }
}

public class GiveItemEffect(val item: ResourceLocation, val amount: IntRange?): InteractionEffect {
    override fun applyEffect(
        Pokemon pokemonEntity,
        ServerPlayer player
    ) {
        val item = player.registryAccess().registryOrThrow(Registries.ITEM).get(item) ?: throw IllegalArgumentException("Cannot load item with id: $item")
        val stack = ItemStack(item)
        stack.count = amount?.randomOrNull() ?: 1
        if (stack.isEmpty)
            return
        player.giveOrDropItemStack(stack)
    }

    final class Companion {
        val ID = "give_item"
    }
}

public class PlaySoundEffect(val sound: ResourceLocation, val soundSource: SoundSource?, val playAround: Boolean = true, val distance: Double = 64.0, val Float volume = 1.0F, val Float pitch = 1.0F): InteractionEffect {
    override fun applyEffect(
        Pokemon pokemonEntity,
        ServerPlayer player
    ) {
        val packet = UnvalidatedPlaySoundS2CPacket(sound, soundSource ?: SoundSource.NEUTRAL, pokemon.x, pokemon.y, pokemon.z, volume, pitch)
        if (playAround) {
            packet.sendToPlayersAround( pokemon.x, pokemon.y, pokemon.z, distance, pokemon.level().dimension())
        } else {
            packet.sendToPlayer(player)
        }
    }

    final class Companion {
        val ID = "play_sound"
    }
}

public class ShrinkItemEffect(val amount: Int = 1): InteractionEffect {
    override fun applyEffect(
        Pokemon pokemonEntity,
        ServerPlayer player
    ) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).isDamageableItem)
            player.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(amount, player, EquipmentSlot.MAINHAND)
        else
            player.getItemInHand(InteractionHand.MAIN_HAND).consume(amount, player)
    }

    final class Companion {
        val ID = "shrink_item"
    }
}

public class ScriptEffect(val script: ExpressionLike): InteractionEffect {
    override fun applyEffect(
        Pokemon pokemonEntity,
        ServerPlayer player
    ) {
        val runtime = MoLangRuntime().setup()
        runtime.withPlayerValue("player", player)
        runtime.withQueryValue("pokemon", pokemon.asMoLangValue())
        script.resolveDouble(runtime)
    }

    final class Companion {
        val ID = "script"
    }
}