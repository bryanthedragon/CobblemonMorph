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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter

/**
 * Catch-all for unimplemented instructions that need to be added to the [ShowdownInterpreter].
 *
 * @author Hiroku
 * @since December 25th, 2023
 */
public class UnknownInstruction(val battleMessage: BattleMessage) : InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        battle.dispatchGo { battle.broadcastChatMessage(battleMessage.rawMessage.red()) }
    }
}