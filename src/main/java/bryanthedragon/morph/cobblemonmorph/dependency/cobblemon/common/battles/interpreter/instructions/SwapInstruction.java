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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwapPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.setPositionSafely
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.swap


/**
 * Format:
 * |swap|POKEMON|(from)EFFECT
 *
 * Indicates that a pokemon has swapped its field position with an ally.
 * @author JazzMcNade
 */
public class SwapInstruction(val message: BattleMessage, val instructionSet: InstructionSet): InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting {
            val battlePokemonA = message.battlePokemon(0, battle) ?: return@dispatchWaiting
            val pnxA = message.argumentAt(0)?.substring(0, 3)
            val (actor, activePokemonA) = battle.getActorAndActiveSlotFromPNX(pnxA!!)


            val activeBattlePokemonB = activePokemonA.getAdjacentAllies().firstOrNull()
            if (activeBattlePokemonB != null) {
                val pnxB = activeBattlePokemonB.getPNX()
                val (actorB, activePokemonB) = battle.getActorAndActiveSlotFromPNX(pnxB)
                // update party positions
                actor.pokemonList.swap(actor.activePokemon.indexOf(activePokemonA), actor.activePokemon.indexOf(activeBattlePokemonB))
                // swap active references
                actor.activePokemon.swap((pnxA[2] - 'a'), (pnxB[2] - 'a'))

                val posA = activePokemonA.getSendOutPosition() ?: activePokemonA.position?.second
                if (posA != null && battlePokemonA.entity != null) {
                    battlePokemonA.entity?.setPositionSafely(battlePokemonA.entity!!.getAdjustedSendoutPosition(posA))
                }

                val posB = activePokemonB.getSendOutPosition() ?: activePokemonB.position?.second
                val battlePokemonB = activePokemonB.battlePokemon
                if (posB != null && battlePokemonB?.entity != null) {
                    activePokemonB.battlePokemon?.entity?.setPositionSafely(battlePokemonB.entity!!.getAdjustedSendoutPosition(posB))
                }

                // Notify clients of the swap
                battle.sendUpdate(BattleSwapPokemonPacket(pnxA))

                if (!message.hasOptionalArgument("silent")) {
                    // Send battle message
                    val lastCauser = instructionSet.getMostRecentCauser(comparedTo = this)
                    val lang = if (lastCauser is MoveInstruction && lastCauser.move.name == "allyswitch") {
                        // Ally Switch
                        battleLang("activate.allyswitch", battlePokemonA.getName(), activePokemonB.battlePokemon?.getName() ?: "")
                    } else {
                        // Triple battle shift
                        battleLang("shift", battlePokemonA.getName())
                    }
                    battle.broadcastChatMessage(lang)
                }
            }
        }

    }
}