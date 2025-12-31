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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.ZMoveUsedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-zpower|POKEMON
 *
 * POKEMON has used the z-move variant of its move.
 * @author Segfault Guy
 * @since September 10th, 2023
 */
public class ZPowerInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val battlePokemon = message.battlePokemon(0, battle) ?: return
        battle.dispatchWaiting {
            val pokemonName = battlePokemon.getName()
            battle.broadcastChatMessage(battleLang("zpower", pokemonName).yellow())
            CobblemonEvents.ZPOWER_USED.post(ZMoveUsedEvent(battle, battlePokemon))
            battle.minorBattleActions[battlePokemon.uuid] = message
        }
    }
}