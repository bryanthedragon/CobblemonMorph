/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.addBattleMessageFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.addFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.GO
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import java.util.concurrent.CompletableFuture
import net.minecraft.resources.ResourceLocation

/**
 * Format: |-prepare|ATTACKER|MOVE and |-prepare|ATTACKER|MOVE|DEFENDER
 *
 * ATTACKER Pokémon is preparing to use a charge MOVE on DEFENDER or an unknown target.
 * @author Renaissance
 * @since March 24th, 2023
 */
public class PrepareInstruction(val message: BattleMessage): ActionEffectInstruction {
    override var future: CompletableFuture<*> = CompletableFuture.completedFuture(Unit)
    override var holds = mutableSetOf<String>()
    override val id = cobblemonResource("prepare")

    override fun addMolangQueries(MoLangRuntime runtime) {
        super.addMolangQueries(runtime)
        runtime.environment.query.addBattleMessageFunctions(message)
    }

    override fun preActionEffect(battle: PokemonBattle) {
        val pokemon = message.battlePokemon(0, battle) ?: return
        val effect = message.effectAt(1) ?: return
        ShowdownInterpreter.broadcastOptionalAbility(battle, effect, pokemon)

        battle.dispatch{
            ShowdownInterpreter.lastCauser[battle.battleId] = message
            battle.minorBattleActions[pokemon.uuid] = message
            GO
        }
    }

    override fun runActionEffect(battle: PokemonBattle, MoLangRuntime runtime) {
        val effect = message.effectAt(1)
        val battlePokemon = message.battlePokemon(0, battle) ?: return
        battle.dispatch {
            val actionEffect = effect?.let { ActionEffects.actionEffects["prepare_${it.id}".asIdentifierDefaultingNamespace()] }
                ?: return@dispatch GO // not likely

            val providers = mutableListOf<Any>(battle)
            battlePokemon.effectedPokemon.entity?.let { UsersProvider(it) }?.let(providers::add)

            val context = ActionEffectContext(
                actionEffect = actionEffect,
                runtime = runtime,
                providers = providers,
                level = battle.players.firstOrNull()?.level()
            )
            this.future = actionEffect.run(context)
            holds = context.holds // Reference so future things can check on this action effect's holds
            future.thenApply { holds.clear() }
            return@dispatch GO
        }
    }

    override fun postActionEffect(battle: PokemonBattle) {
        battle.dispatch {
            val pokemon = message.battlePokemon(0, battle) ?: return@dispatch GO
            val pokemonName = pokemon.getName()
            val effectID = message.effectAt(1)?.id ?: return@dispatch GO
            //Prevents spam when the move Role Play is used
            val lang = when (effectID) {
                "shadowforce" -> battleLang("prepare.phantomforce", pokemonName) //Phantom Force and Shadow Force share the same text
                "solarblade" -> battleLang("prepare.solarbeam", pokemonName) //Solar Beam and Solar Blade share the same text
                else -> battleLang("prepare.$effectID", pokemonName)
            }
            battle.broadcastChatMessage(lang)
            battle.minorBattleActions[pokemon.uuid] = message
            UntilDispatch { "effects" !in holds }
        }
    }
}