/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleVictoryEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.gold
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.plus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.GO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import java.util.UUID

/**
 * Format: |win|USER
 *
 * USER has won the battle.
 * @author Deltric
 * @since January 22nd, 2022
 */
class WinInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val user = message.argumentAt(0) ?: return
        val ids = user.split("&").map { it.trim() }
        val winners = ids.map { battle.getActor(UUID.fromString(it))!! }
        val losers = battle.actors.filter { !winners.contains(it) }
        val winnersText = winners.map { it.getName() }.reduce { acc, next -> acc + " & " + next }
        val losersText = losers.map { it.getName() }.reduce { acc, next -> acc + " & " + next }
        val wasCaught = battle.showdownMessages.any { "capture" in it }

        battle.dispatch {
            // If the battle was a PvW battle, we need to set the killer of the wild Pokémon to the player
            if (battle.isPvW) {
                val nonPlayerActor = battle.actors.first { it.type == ActorType.WILD }
                val wildPokemon: BattlePokemon = nonPlayerActor.pokemonList.first()

                if (!wasCaught && losers.any { it.uuid == wildPokemon.uuid }) {
                    wildPokemon.effectedPokemon.entity?.killer = (battle.actors.firstOrNull { it.type == ActorType.PLAYER } as? PlayerBattleActor)?.entity
                }
            }

            // broadcast victory / defeat
            if (!wasCaught) {
                val blackedOut = battle.isPvW && losers.any { it is PlayerBattleActor }
                val lang = if (blackedOut) battleLang("lose", losersText).red() else battleLang("win", winnersText).gold()
                winners.forEach { winner -> winner.win(winners.filterNot { it == winner }, losers ) }
                losers.forEach { loser -> loser.lose(winners, losers.filterNot { it == loser }) }
                battle.broadcastChatMessage(lang)
                return@dispatch WaitDispatch(2F)
            }
            else {
                return@dispatch GO  // see BattleCaptureAction
            }
        }
        battle.dispatchGo {
            battle.winners = winners
            battle.losers = losers
            battle.end()
            CobblemonEvents.BATTLE_VICTORY.post(BattleVictoryEvent(battle, winners, losers, wasCaught))
            ShowdownInterpreter.lastCauser.remove(battle.battleId)
        }
    }
}