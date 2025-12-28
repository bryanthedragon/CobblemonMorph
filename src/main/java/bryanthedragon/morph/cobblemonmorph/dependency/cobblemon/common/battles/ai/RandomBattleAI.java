/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai.BattleAI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.DefaultActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.PassActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.SwitchActionResponse

/**
 * AI that randomly chooses a move from its moveset at a random target.
 *
 * @since January 16th, 2022
 * @author Deltric, Hiroku
 */
class RandomBattleAI : BattleAI {
    override fun choose(
        activeBattlePokemon: ActiveBattlePokemon,
        battle: PokemonBattle,
        aiSide: BattleSide,
        moveset: ShowdownMoveset?,
        forceSwitch: Boolean
    ): ShowdownActionResponse {
        if (forceSwitch || activeBattlePokemon.isGone()) {
            val switchTo = activeBattlePokemon.actor.pokemonList.filter { it.canBeSentOut() }.randomOrNull()
                ?: return DefaultActionResponse() //throw IllegalStateException("Need to switch but no Pokémon to switch to")
            switchTo.willBeSwitchedIn = true
            return SwitchActionResponse(switchTo.uuid)
        }

        if (moveset == null) {
            return PassActionResponse
        }
        val move = moveset.moves
            .filter { it.canBeUsed() }
            .filter { it.mustBeUsed() || it.target.targetList(activeBattlePokemon)?.isEmpty() != true }
            .randomOrNull()
            ?: return MoveActionResponse("struggle")

        val target = if (move.mustBeUsed()) null else move.target.targetList(activeBattlePokemon)
        return if (target == null) {
            MoveActionResponse(move.id)
        } else {
            // prioritize opponents rather than allies
            val chosenTarget = target.filter { !it.isAllied(activeBattlePokemon) }.randomOrNull() ?: target.random()
            MoveActionResponse(move.id, (chosenTarget as ActiveBattlePokemon).getPNX())
        }
    }
}