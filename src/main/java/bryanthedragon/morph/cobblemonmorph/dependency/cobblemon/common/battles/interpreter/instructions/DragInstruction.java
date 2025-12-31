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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |drag|POKEMON|DETAILS|HP STATUS
 *
 * POKEMON has switched in (if there was an old Pokémon in that position, it is switched out).
 * POKEMON|DETAILS represents all the information that can be used to tell Pokémon apart.
 * The switched Pokémon has HP health points and STATUS status.
 * @author Hiroku
 * @since April 24th, 2022
 */
public class DragInstruction(val instructionSet: InstructionSet, val battleActor: BattleActor, val publicMessage: BattleMessage, val privateMessage: BattleMessage): InterpreterInstruction {
    override fun invoke(battle: PokemonBattle) {
        battle.dispatchInsert {
            val (pnx, _) = publicMessage.pnxAndUuid(0)!!
            val (_, activePokemon) = battle.getActorAndActiveSlotFromPNX(pnx)

            val imposter = instructionSet.getNextInstruction<TransformInstruction>(this)?.expectedTarget != null
            val illusion = publicMessage.battlePokemonFromOptional(battle , "is")
            val pokemon = publicMessage.battlePokemon(0, battle) ?: return@dispatchInsert emptySet()

            val newHealth = privateMessage.argumentAt(2)!!.split(" ")[0]
            val remainingHealth = newHealth.split("/")[0].toInt()
            pokemon.effectedPokemon.currentHealth = remainingHealth

            battle.broadcastChatMessage(battleLang("dragged_out", pokemon.getName()))
            activePokemon.battlePokemon?.let { oldPokemon ->
                oldPokemon.contextManager.clear(BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST)
                battle.majorBattleActions[oldPokemon.uuid] = publicMessage
            }
            battle.majorBattleActions[pokemon.uuid] = publicMessage

            val entity = if (battleActor is EntityBackedBattleActor<*>) battleActor.entity else null
            setOf(
                BattleDispatch {
                    if (entity != null) {
                        SwitchInstruction.createEntitySwitch(battle, battleActor, entity, pnx, activePokemon, pokemon, illusion, imposter)
                    } else {
                        SwitchInstruction.createNonEntitySwitch(battle, battleActor, pnx, activePokemon, pokemon, illusion)
                    }
                }
            )
        }
    }

}