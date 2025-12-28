/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-enditem|POKEMON|ITEM|(from)EFFECT
 *
 * ITEM held by POKEMON has been destroyed by a move or ability EFFECT, and it now holds no item.
 *
 * Alt format: |-enditem|POKEMON|ITEM
 *
 * POKEMON's ITEM has destroyed itself (consumed or used).
 * @author Licious
 * @since December 30th, 2022
 */
class EndItemInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        battle.dispatchWaiting(1.5F) {
            // All logic regarding broadcasting battle messages is handled in CobblemonHeldItemManager
            val battlePokemon = message.battlePokemon(0, battle) ?: return@dispatchWaiting
            val itemEffect = message.effectAt(1) ?: return@dispatchWaiting
            battlePokemon.heldItemManager.handleEndInstruction(battlePokemon, battle, message)
            battle.minorBattleActions[battlePokemon.uuid] = message
            battlePokemon.contextManager.remove(itemEffect.id, BattleContext.Type.ITEM)
            if (message.hasOptionalArgument("eat")) {
                battlePokemon.entity?.playSound(CobblemonSounds.BERRY_EAT, 1F, 1F)
            }
        }
    }
}