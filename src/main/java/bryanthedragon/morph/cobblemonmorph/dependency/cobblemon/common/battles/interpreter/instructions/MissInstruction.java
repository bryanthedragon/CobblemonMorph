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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-miss|SOURCE|TARGET
 *
 * The move used by the SOURCE Pokémon missed (maybe absent) the TARGET Pokémon.
 * @author Hiroku
 * @since October 3rd, 2022
 */
class MissInstruction(val battle: PokemonBattle, val message: BattleMessage): InterpreterInstruction {
    val target = message.battlePokemon(1, battle)

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchGo {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchGo
            battle.broadcastChatMessage(battleLang("missed").red())
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}