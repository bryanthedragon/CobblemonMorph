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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.instruction.FormeChangeEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-formechange|POKEMON|SPECIES|HP STATUS or |detailschange|POKEMON|DETAILS|HP STATUS
 *
 * POKEMON has temporarily (-formechange) or permanently (detailschange) changed form to SPECIES/DETAILS.
 * @author Segfault Guy
 * @since December 9th, 2024
 */
public class FormeChangeInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val battlePokemon = message.battlePokemon(0, battle) ?: return
        val details = message.argumentAt(1)?.split(',')?.get(0)?.lowercase() ?: return
        val speciesName = details.substringBefore('-')
        val formName = details.substringAfterLast('-').ifBlank { speciesName }
        val typeKey = if (message.id == "detailschange") "permanent" else "temporary"

        ShowdownInterpreter.broadcastOptionalAbility(battle, message.effect(), battlePokemon)

        battle.dispatchWaiting {
            battle.minorBattleActions[battlePokemon.uuid] = message

            CobblemonEvents.FORME_CHANGE.post(FormeChangeEvent(battle, battlePokemon, formName))
            val pokemonName = battlePokemon.getName()
            val lang = when(formName) {
                "busted", "hero", "complete" -> return@dispatchWaiting
                "school", "wishiwashi", "meteor", "minior" -> battleLang("formechange.$formName", pokemonName)
                speciesName -> battleLang("formechange.default.temporary.end", pokemonName)
                else -> battleLang("formechange.default.$typeKey", pokemonName, formName)
            }
            battle.broadcastChatMessage(lang)
        }
    }
}