/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.delayedFuture
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleFaintPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import java.util.concurrent.CompletableFuture

/**
 * Format: |faint|POKEMON
 *
 * POKEMON has fainted.
 * @author Deltric
 * @since January 22nd, 2022
 */
public class FaintInstruction(battle: PokemonBattle, val message: BattleMessage) : InterpreterInstruction {
    var waitTime = 2.5F
    val faintingPokemon = message.battlePokemon(0, battle)!!

    override fun invoke(battle: PokemonBattle) {

        battle.dispatchFuture {
            val (pnx, _) = message.pnxAndUuid(0) ?: return@dispatchFuture CompletableFuture.completedFuture(Unit)
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatchFuture CompletableFuture.completedFuture(Unit)
            battle.sendUpdate(BattleFaintPacket(pnx))
            val actor = pokemon.actor
            pokemon.effectedPokemon.currentHealth = 0
            val preamble = if (actor is EntityBackedBattleActor<*>) {
                (actor.entity as? PokemonSender)?.let { sender -> pokemon.entity?.recallWithAnimation()}
            } else {
                null
            } ?: delayedFuture(seconds = waitTime)
            val context = ShowdownInterpreter.getContextFromFaint(faintingPokemon, battle)

            preamble.thenAccept {
                faintingPokemon.effectedPokemon.currentHealth = 0
                faintingPokemon.sendUpdate()
                CobblemonEvents.BATTLE_FAINTED.post(BattleFaintedEvent(battle, pokemon, context))
                battle.getActorAndActiveSlotFromPNX(pnx).second.battlePokemon = null
                faintingPokemon.contextManager.add(context)
                faintingPokemon.contextManager.clear(BattleContext.Type.STATUS, BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST)
                battle.majorBattleActions[faintingPokemon.uuid] = message
            }
        }
        battle.dispatchWaiting(0.5F) {
            val faintMessage = battleLang("fainted", faintingPokemon.getName()).red()
            battle.broadcastChatMessage(faintMessage)
        }
    }
}