/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.afterOnServer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket

/**
 * Format: |start
 *
 * Indicates that the battle has started.
 * @author Segfault Guy
 * @since July 24th, 2024
 */
class InitializeInstruction(val instructionSet: InstructionSet, val message: BattleMessage): InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        val incoming = instructionSet.getSubsequentInstructions(this).filterIsInstance<SwitchInstruction>()
        incoming.forEach {
            // TODO redundant, make these SwitchInstruction properties
            val (_, activePokemon) = it.publicMessage.actorAndActivePokemon(0, battle) ?: return@forEach
            val illusion = it.publicMessage.battlePokemonFromOptional(battle, "is")
            val pokemon = it.publicMessage.battlePokemon(0, battle) ?: return@forEach
            pokemon.entity?.let {
                // If a Pokémon entity is being recalled with an animation,
                // wrap up the animation and recall the Pokémon immediately.
                if (it.beamMode == 3 && battle.battlePartyStores.isEmpty()) {
                    pokemon.effectedPokemon.recall()
                }
                // If already on the field, initialize for the BattleInitializePacket
                else {
                    activePokemon.battlePokemon = pokemon
                    activePokemon.illusion = illusion
                }
            }
        }

        battle.actors.filterIsInstance<PlayerBattleActor>().forEach { actor ->
            val initializePacket = BattleInitializePacket(battle, actor.getSide())
            actor.sendUpdate(initializePacket)
            actor.sendUpdate(BattleMusicPacket(actor.battleTheme))
        }

        battle.actors.forEach { actor ->
            actor.sendUpdate(BattleSetTeamPokemonPacket(actor.pokemonList.map { it.effectedPokemon }))
            val req = actor.request ?: return@forEach
            actor.sendUpdate(BattleQueueRequestPacket(req))
        }

        battle.dispatch {
            DispatchResult { !battle.side1.stillSendingOut() && !battle.side2.stillSendingOut() }
        }

        battle.dispatchGo {
            battle.started = true
            battle.side1.playCries()
            afterOnServer(seconds = 1.0F) { battle.side2.playCries() }
        }
    }
}