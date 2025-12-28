/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.PokemonSeenEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ChallengeManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeNotificationPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.canInteractWith
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.party
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

/**
 * Processes a player's interaction request to battle with another player or Pokemon.
 *
 * If valid player interaction, creates a respective [BattleChallenge] and sends a [BattleChallengeNotificationPacket]
 * to the player to decide upon.
 *
 * If valid Pokemon interaction, initiates a PVE battle.
 *
 * @author Hiroku
 * @since April 23rd, 2022
 */final class ChallengeHandler : ServerNetworkPacketHandler<BattleChallengePacket> {
    override fun handle(packet: BattleChallengePacket, server: MinecraftServer, player: ServerPlayer) {
        val targetedEntity = player.level().getEntity(packet.targetedEntityId)?.let {
            when (it) {
                is PokemonEntity -> it.owner ?: it
                is ServerPlayer -> it
                else -> null
            }
        } ?: return

        val leadingPokemon = player.party()[packet.selectedPokemonId]?.uuid ?: return   // validate id
        if (targetedEntity is PokemonEntity && player.canInteractWith(targetedEntity, Cobblemon.config.battleWildMaxDistance) && targetedEntity.canBattle(player)) {
            BattleBuilder.pve(player, targetedEntity, leadingPokemon)
                .ifSuccessful { battle -> this.flagAsSeen(battle, targetedEntity) }
                .ifErrored { it.sendTo(player) { it.red() } }
        }
        else if (targetedEntity is ServerPlayer) {
            ChallengeManager.setLead(player, leadingPokemon)
            val challenge =
                if (packet.battleFormat.battleType.name == BattleTypes.MULTI.name)
                    ChallengeManager.MultiBattleChallenge(player, targetedEntity, leadingPokemon, packet.battleFormat)
                else
                    ChallengeManager.SinglesBattleChallenge(player, targetedEntity, leadingPokemon, packet.battleFormat)

            // player interaction validation is done on sendRequest
            ChallengeManager.sendRequest(challenge)
        }
    }

    private fun flagAsSeen(battle: PokemonBattle, entity: PokemonEntity) {
        // Wild actor is tied to Pokemon UUID not entity
        val actor = battle.getActor(entity.pokemon.uuid) as? PokemonBattleActor ?: return
        val battlePokemon = actor.pokemonList.firstOrNull { it.uuid == entity.pokemon.uuid } ?: return
        battle.playerUUIDs.forEach { uuid ->
            CobblemonEvents.POKEMON_SEEN.post(PokemonSeenEvent(uuid, battlePokemon.effectedPokemon))
        }
    }

}