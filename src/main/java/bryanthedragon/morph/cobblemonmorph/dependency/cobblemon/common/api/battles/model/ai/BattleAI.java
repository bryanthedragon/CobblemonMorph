/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket

/**
 * Interface for an actor battle AI
 *
 * @since January 16th, 2022
 * @author Deltric, Polymeta
 */
interface BattleAI {
    /**
     * Requests that the AI choose an action for the given Pokémon
     * @param activeBattlePokemon The Pokémon slot that is choosing an action
     * @param battle The ongoing [PokemonBattle]
     * @param aiSide The [BattleSide] the AI is fighting on, useful for allies and opponent determination
     * @param moveset The [ShowdownMoveset] for this slot. This can be null if [forceSwitch] is true. Otherwise, it is the available move information from Showdown.
     * @param forceSwitch Whether this is a force switch situation.
     * @return the action response
     */
    fun choose(
        activeBattlePokemon: ActiveBattlePokemon,
        battle: PokemonBattle,
        aiSide: BattleSide,
        moveset: ShowdownMoveset?,
        forceSwitch: Boolean
    ): ShowdownActionResponse

    fun onHealthChange(packet: BattleHealthChangePacket) {}
}