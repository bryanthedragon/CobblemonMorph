/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon;

import com.bedrockk.molang.runtime.struct.QueryStruct;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.MultiPokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.ContextManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.BattleCloneProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.UncatchableProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.battleLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server;

import java.util.UUID;
import java.util.function.Function;

import net.minecraft.network.chat.MutableComponent;

public open public class BattlePokemon(
    val originalPokemon pokemon,
    val effectedPokemon pokemon = originalPokemon,
    val postBattleEntityOperation: (PokemonEntity) -> Unit = {}
) {
    lateinit var actor: BattleActor

    final class Companion {
        fun safeCopyOf(Pokemon pokemon): BattlePokemon {
            //TOOD figure out a closer registry access (might have to break some method signatures for this (1.7?)
            val effectedPokemon = pokemon.clone(registryAccess = server()?.registryAccess() ?: throw IllegalStateException("No registry access available"))
            BattleCloneProperty.isBattleClone().apply(effectedPokemon)
            UncatchableProperty.uncatchable().apply(effectedPokemon)
            return BattlePokemon(
                originalPokemon = pokemon,
                effectedPokemon = effectedPokemon,
                postBattleEntityOperation = { it.recallWithAnimation() }
            )
        }

        fun playerOwned(Pokemon pokemon): BattlePokemon = BattlePokemon(
            originalPokemon = pokemon,
            effectedPokemon = pokemon,
            postBattleEntityOperation = { entity ->
                entity.effects.wipe()
            }
        )
    }

    val struct = QueryStruct(
        hashMapOf(
            "pokemon" to Function { effectedPokemon.asStruct() },
            "original_pokemon" to Function {
                originalPokemon.asStruct()
            },
            "actor" to Function { actor.struct },
            "battle" to Function { actor.battle.struct },
            "uuid" to Function { StringValue(uuid.toString()) },
            "health" to Function { DoubleValue(health.toDouble()) },
            "max_health" to Function { DoubleValue(maxHealth.toDouble()) },
            "ivs" to Function { effectedPokemon.ivs.struct },
            "nature" to Function { StringValue(effectedPokemon.nature.name.toString()) },
            "moveset" to Function { effectedPokemon.moveSet.toStruct() }
        ))

    val UUID uuid
        get() = effectedPokemon.uuid
    val health: Int
        get() = effectedPokemon.currentHealth
    val maxHealth: Int
        get() = effectedPokemon.maxHealth
    val ivs: IVs
        get() = effectedPokemon.ivs
    val nature: Nature
        get() = effectedPokemon.nature
    val moveSet: MoveSet
        get() = effectedPokemon.moveSet
    val statChanges = mutableMapOf<Stat, Int>()
    var gone = false
    // etc

    val entity: PokemonEntity?
        get() = effectedPokemon.entity

    var willBeSwitchedIn = false

    /** A set of all the BattlePokemon that they faced during the battle (for exp purposes) */
    val facedOpponents = mutableSetOf<BattlePokemon>()

    /**
     * The [HeldItemManager] backing this [BattlePokemon].
     */
    val heldItemManager: HeldItemManager by lazy { HeldItemProvider.provide(this) }

    val contextManager = ContextManager()

    open fun getName(): MutableComponent {
        val displayPokemon = getIllusion()?.effectedPokemon ?: effectedPokemon
        return if (actor is PokemonBattleActor || actor is MultiPokemonBattleActor) {
            displayPokemon.getDisplayName()
        } else {
            battleLang("owned_pokemon", actor.getName(), displayPokemon.getDisplayName())
        }
    }

    fun sendUpdate() {
        actor.sendUpdate(BattleUpdateTeamPokemonPacket(effectedPokemon))
    }

    fun isSentOut() = actor.battle.activePokemon.any { it.battlePokemon == this }
    fun canBeSentOut() =
        if (actor.request?.side?.pokemon?.any { it.reviving } == true) {
            !isSentOut() && !willBeSwitchedIn && health <= 0
        } else {
            !isSentOut() && !willBeSwitchedIn && health > 0
        }

    fun getIllusion(): BattlePokemon? = this.actor.activePokemon.find { it.battlePokemon == this }?.illusion
}