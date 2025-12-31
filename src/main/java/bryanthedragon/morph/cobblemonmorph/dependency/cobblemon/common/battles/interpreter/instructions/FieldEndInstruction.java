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
 * Format: |-fieldend|CONDITION
 *
 * The field CONDITION has ended.
 * @author Licious
 * @since February 8th, 2023
 */
public class FieldEndInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting(1.5F) {
            val effect = message.effectAt(0) ?: return@dispatchWaiting
            val lang = battleLang("fieldend.${effect.id}")
            battle.broadcastChatMessage(lang)

            val type = BattleContext.Type.valueOf(effect.rawData.substringAfterLast(" ").uppercase())
            battle.contextManager.remove(effect.id, type)
        }
    }
}