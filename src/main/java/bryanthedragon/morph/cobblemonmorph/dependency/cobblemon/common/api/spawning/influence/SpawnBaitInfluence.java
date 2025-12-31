/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait.Effects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.egg.EggGroup
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.world.entity.Entity

/**
 * A [SpawningInfluence] that applies some number of SpawnBait effects.
 *
 * @author Hiroku, Plastered_Crab
 * @since March 18th, 2025
 */
open class SpawnBaitInfluence(val effects: List<SpawnBait.Effect>, val onUsed: (time: Int, entity: PokemonEntity?) -> Unit = { _, _ -> }) : SpawningInfluence {

    var usedTimes = 0

    private fun markUsed(entity: PokemonEntity? = null) {
        usedTimes++
        onUsed(usedTimes, entity)
    }

    override fun affectSpawn(action: SpawnAction<*>, Entity entity) {
        if (entity is PokemonEntity) {
            val merged = SpawnBaitUtils.mergeEffects(effects)
            merged.forEach { effect ->
                if (Math.random() <= effect.chance) {
                    markUsed(entity)
                    Effects.getEffectFunction(effect.type)?.invoke(entity, effect)
                }
            }
        }
    }

    // EV related bait effects
    override fun affectWeight(detail: SpawnDetail, spawnablePosition: SpawnablePosition, weight: Float): Float {
        val merged = SpawnBaitUtils.mergeEffects(effects)

        // if bait exists and any effects are related to EV yields
        if (merged.any { it.type == Effects.EV }){
            if (detail is PokemonSpawnDetail) {
                val detailSpecies = detail.pokemon.species?.let { PokemonSpecies.getByName(it) }
                val baitEVStat = effects.firstOrNull { it.type == Effects.EV }?.subcategory?.path?.let { Stats.getStat(it) }

                if (detailSpecies != null && baitEVStat != null) {
                    val evYieldValue = detailSpecies.evYield[baitEVStat]?.toFloat() ?: 0f
                    return when {
                        evYieldValue > 0 -> {
                            markUsed()
                            super.affectWeight(detail, spawnablePosition, weight)
                        }
                        else -> {
                            markUsed()
                            super.affectWeight(detail, spawnablePosition, 0f)
                        }
                    }
                }
            }
        }
        // if bait exists and any effects are related to Typing
        if (merged.any { it.type == Effects.TYPING }){
            if (detail is PokemonSpawnDetail) {
                val detailSpecies = detail.pokemon.species?.let { PokemonSpecies.getByName(it) }
                val baitEffect = effects.firstOrNull { it.type == Effects.TYPING }
                val baitTypingEffect = baitEffect?.subcategory?.path?.let { ElementalTypes.get(it) }

                if (detailSpecies != null && baitTypingEffect != null) {
                    val isMatchingType = detailSpecies.types.contains(baitTypingEffect)
                    return when {
                        isMatchingType -> {
                            markUsed()
                            super.affectWeight(detail, spawnablePosition, weight * baitEffect.value.toFloat())
                        }
                        else -> super.affectWeight(detail, spawnablePosition, weight)
                    }
                }
            }
        }
        // if bait exists and any effects are related to Egg Groups
        if (merged.any { it.type == Effects.EGG_GROUP }) {
            if (detail is PokemonSpawnDetail) {
                val detailSpecies = detail.pokemon.species?.let { PokemonSpecies.getByName(it) }

                if (detailSpecies != null) {
                    // Collect all the egg group effects
                    val eggGroupEffects = effects.filter { it.type == Effects.EGG_GROUP }

                    // Check if any of the egg group effects match the species' egg groups
                    val matchingEffect = eggGroupEffects.firstOrNull { effect ->
                        val effectEggGroupKey = effect.subcategory?.path ?: return@firstOrNull false
                        val eggGroup = EggGroup.fromIdentifier(effectEggGroupKey)
                        if (eggGroup == null) {
                            LOGGER.warn("Unknown egg group identifier: $effectEggGroupKey")
                            return@firstOrNull false
                        }
                        detailSpecies.eggGroups.contains(eggGroup)
                    }

                    if (matchingEffect != null) {
                        markUsed()
                        val multiplier = matchingEffect.value
                        return super.affectWeight(detail, spawnablePosition, (weight * multiplier).toFloat())
                    }
                }
            }
        }
        return super.affectWeight(detail, spawnablePosition, weight)
    }
}