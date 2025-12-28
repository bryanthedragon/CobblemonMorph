/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated

/**
 * Format: |-status|POKEMON|STATUS
 *
 * POKEMON has been inflicted with STATUS.
 * @author Hiroku
 * @since October 3rd, 2022
 */
class StatusInstruction(val message: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val (pnx, _) = message.pnxAndUuid(0) ?: return
        val pokemon = message.battlePokemon(0, battle) ?: return
        val otherPokemon = message.actorAndActivePokemonFromOptional(battle, "of")?.second?.battlePokemon
        val statusLabel = message.argumentAt(1) ?: return
        val status = Statuses.getStatus(statusLabel) ?: return Cobblemon.LOGGER.error("Unrecognized status: $statusLabel")

        ShowdownInterpreter.broadcastOptionalAbility(battle, message.effect(), otherPokemon ?: pokemon)

        battle.dispatchWaiting {
            if (status is PersistentStatus) {
                pokemon.effectedPokemon.applyStatus(status)
                battle.sendUpdate(BattlePersistentStatusPacket(pnx, status))
                pokemon.sendUpdate()
            }

            battle.broadcastChatMessage(status.applyMessage.asTranslated(pokemon.getName()))
            pokemon.contextManager.add(ShowdownInterpreter.getContextFromAction(message, BattleContext.Type.STATUS, battle))
            battle.minorBattleActions[pokemon.uuid] = message
        }
    }
}