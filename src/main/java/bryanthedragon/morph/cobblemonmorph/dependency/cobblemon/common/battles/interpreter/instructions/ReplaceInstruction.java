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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.yellow
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleReplacePokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleTransformPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |replace|POKEMON|DETAILS|HP STATUS
 *
 * Illusion has ended for POKEMON. Syntax is the same as [SwitchInstruction].
 * @author Segfault Guy
 * @since March 18th, 2024
 */
public class ReplaceInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {

        val (pnx, _) = message.pnxAndUuid(0) ?: return
        val (actor, activePokemon) = battle.getActorAndActiveSlotFromPNX(pnx)
        val pokemon = message.battlePokemon(0, battle) ?: return

        battle.dispatchGo {
            val entity = pokemon.entity
            entity?.let { it.effects.mockEffect?.end(it) }

            battle.sendSidedUpdate(
                source = actor,
                allyPacket = BattleReplacePokemonPacket(pnx, pokemon, true),
                opponentPacket = BattleReplacePokemonPacket(pnx, pokemon, false)
            )

            activePokemon.illusion = null
            //lang done by EndInstruction
        }
    }
}