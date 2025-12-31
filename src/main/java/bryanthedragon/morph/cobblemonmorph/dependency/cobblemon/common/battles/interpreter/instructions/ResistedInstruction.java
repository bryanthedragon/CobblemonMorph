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
 * Format: |-resisted|POKEMON
 *
 * POKEMON resisted the attack.
 * @author Hunter
 * @since August 18th, 2022
 */
public class ResistedInstruction(
    val publicMessage: BattleMessage,
    val instructionSet: InstructionSet,
) : InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val battlePokemon = publicMessage.battlePokemon(0, battle)
        battlePokemon ?: return
        val lastCauser  = instructionSet.getMostRecentCauser(comparedTo = this)
        battle.dispatchGo {
            if (lastCauser is MoveInstruction && lastCauser.spreadTargets.isNotEmpty()) {
                val pokemonName = battlePokemon.getName()
                battle.broadcastChatMessage(battleLang("resisted_spread", pokemonName))
            } else {
                battle.broadcastChatMessage(battleLang("resisted"))
            }
            battle.minorBattleActions[battlePokemon.uuid] = publicMessage
        }
    }
}