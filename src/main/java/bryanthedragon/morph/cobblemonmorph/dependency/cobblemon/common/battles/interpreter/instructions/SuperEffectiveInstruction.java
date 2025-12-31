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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-supereffective|POKEMON
 *
 * A move was super effective against POKEMON.
 * @author Hunter
 * @since August 18th, 2022
 */
public class SuperEffectiveInstruction(val message: BattleMessage,val instructionSet: InstructionSet,
): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val lastCauser = instructionSet.getMostRecentCauser(comparedTo = this)
        battle.dispatchGo {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchGo
            if (lastCauser is MoveInstruction && lastCauser.spreadTargets.isNotEmpty()) {
                val pokemonName = pokemon.getName()
                battle.broadcastChatMessage(battleLang("superEffective_spread", pokemonName))
            } else {
                battle.broadcastChatMessage(battleLang("superEffective"))
            }
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}