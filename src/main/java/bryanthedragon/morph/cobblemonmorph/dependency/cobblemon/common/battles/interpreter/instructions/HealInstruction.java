/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang

/**
 * Format: |-heal|POKEMON|HP STATUS
 *
 * POKEMON has healed damage and is now at HP STATUS.
 * @author Licious
 * @since February 6th, 2023
 */
public class HealInstruction(val actor: BattleActor, val publicMessage: BattleMessage, val privateMessage: BattleMessage): InterpreterInstruction {

    override fun invoke(battle: PokemonBattle) {
        val activePokemon = privateMessage.actorAndActivePokemon(0, battle)?.second // this can be null if pokemon is healed in bench
        val battlePokemon = privateMessage.battlePokemon(0, battle) ?: return
        val rawHpAndStatus = privateMessage.argumentAt(1)?.split(" ") ?: return
        val rawHpRatio = rawHpAndStatus.getOrNull(0) ?: return
        val newHealth = rawHpRatio.split("/").map { it.toFloatOrNull() ?: return }
        val newHealthRatio = rawHpRatio.split("/").map { it.toFloatOrNull()?.div(newHealth[1]) ?: return }
        val effect = privateMessage.effect()
        ShowdownInterpreter.broadcastOptionalAbility(battle, effect, battlePokemon)

        battle.dispatchWaiting {
            activePokemon?.let {
                // dynamax changes max health
                battle.sendSidedUpdate(actor, BattleHealthChangePacket(it.getPNX(), newHealth[0], newHealth[1]), BattleHealthChangePacket(it.getPNX(), newHealthRatio[0]))
            }
            val silent = privateMessage.hasOptionalArgument("silent")
            if (!silent) {
                val lang = when {
                    privateMessage.hasOptionalArgument("zeffect") -> battleLang("heal.zeffect", battlePokemon.getName())
                    privateMessage.hasOptionalArgument("wisher") -> {
                        val name = privateMessage.optionalArgument("wisher")!!
                        val showdownId = name.lowercase().replace(ShowdownIdentifiable.REGEX, "")
                        val wisher = actor.pokemonList.firstOrNull { it.effectedPokemon.showdownId() == showdownId }
                        // If no Pokémon is found this is a nickname
                        battleLang("heal.wish", wisher?.getName() ?: actor.nameOwned(name))
                    }
                    privateMessage.hasOptionalArgument("from") -> {
                        when (effect!!.type) {
                            Effect.Type.ITEM -> when (effect.id) {
                                "leftovers", "shellbell", "blacksludge" -> battleLang("heal.leftovers", battlePokemon.getName(), effect.typelessData)
                                else -> battleLang("heal.item", battlePokemon.getName(), effect.typelessData)
                            }
                            else -> when (effect.id) {
                                "drain" -> {
                                    val drained = privateMessage.battlePokemonFromOptional(battle) ?: return@dispatchWaiting
                                    battleLang("heal.drain", drained.getName())
                                }
                                else -> battleLang("heal.${effect.id}", battlePokemon.getName())
                            }
                        }
                    }
                    else -> {
                        battleLang("heal.generic", battlePokemon.getName())
                    }
                }
                battle.broadcastChatMessage(lang)
            }
            battle.minorBattleActions[battlePokemon.uuid] = privateMessage
            battlePokemon.effectedPokemon.currentHealth = newHealth[0].toInt()

            // This part is not always present
            val rawStatus = rawHpAndStatus.getOrNull(1) ?: return@dispatchWaiting
            val status = Statuses.getStatus(rawStatus) ?: return@dispatchWaiting
            if (status is PersistentStatus && battlePokemon.effectedPokemon.status?.status != status) {
                battlePokemon.effectedPokemon.applyStatus(status)
                activePokemon?.let { battle.sendUpdate(BattlePersistentStatusPacket(it.getPNX(), status)) }
                if (!silent) {
                    status.applyMessage.let { battle.broadcastChatMessage(it.asTranslated(battlePokemon.getName())) }
                }
            }
        }
    }
}