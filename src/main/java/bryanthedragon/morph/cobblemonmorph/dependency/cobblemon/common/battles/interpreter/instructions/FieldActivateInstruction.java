/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-fieldactivate|EFFECT
 *
 * A miscellaneous EFFECT has activated for the entire field.
 * @author Segfault Guy
 * @since April 5th, 2023
 */
public class FieldActivateInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting(2.5F) {
            val effectID = message.effectAt(0)?.id ?: return@dispatchWaiting
            val lang = battleLang("fieldactivate.$effectID")
            battle.broadcastChatMessage(lang.red())

            // share this action with all active Pokemon
            battle.activePokemon.forEach {
                it.battlePokemon?.contextManager?.addUnique(ShowdownInterpreter.getContextFromAction(message, BattleContext.Type.VOLATILE, battle))
            }
        }
    }
}