/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-nothing
 *
 * Pathetic. Your move did absolutely nothing. You should feel bad for being bad.
 * @author Hiroku
 * @since August 20th, 2022
 */
public class NothingInstruction() : InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchGo { battle.broadcastChatMessage(battleLang("nothing")) }
    }
}