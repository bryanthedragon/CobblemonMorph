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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-block|POKEMON|EFFECT|MOVE|ATTACKER|(of)SOURCE
 *
 * An effect targeted at POKEMON was blocked by EFFECT. This may optionally specify that the effect was a MOVE from ATTACKER.
 *
 * An optional SOURCE will note the owner of the EFFECT, in the case that it's not EFFECT (for instance, an ally with Aroma Veil.)
 * @author jeffw773
 * @since November 6th, 2023
 */
public class BlockInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting(1.5F) {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchWaiting
            val pokemonName = pokemon.getName()
            val effectID = message.effectAt(1)?.id ?: return@dispatchWaiting
            val lang = battleLang("block.$effectID", pokemonName)

            battle.broadcastChatMessage(lang)
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}