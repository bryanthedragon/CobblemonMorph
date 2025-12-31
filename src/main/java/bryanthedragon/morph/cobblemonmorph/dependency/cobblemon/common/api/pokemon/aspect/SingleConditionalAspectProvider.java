/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * A specific type of [AspectProvider] which, upon satisfying some condition,
 * returns a single aspect. This is just a convenient interface for common usages.
 *
 * @author Hiroku
 * @since May 13th, 2022
 */
public interface SingleConditionalAspectProvider : AspectProvider {
    final class Companion {
        fun getForFeature(String name): SingleConditionalAspectProvider {
            return object : SingleConditionalAspectProvider {
                override val aspect: String = name
                override fun meetsCondition(Pokemon pokemon) = pokemon.getFeature<FlagSpeciesFeature>(name)?.enabled == true
                override fun meetsCondition(pokemonProperties: PokemonProperties) = pokemonProperties
                    .customProperties
                    .filterIsInstance<FlagSpeciesFeature>()
                    .any { it.name == name && it.enabled }

            }
        }
    }

    /** The aspect to add if the conditions are met. */
    val aspect: String
    fun meetsCondition(Pokemon pokemon): Boolean
    fun meetsCondition(pokemonProperties: PokemonProperties): Boolean

    override fun provide(properties: PokemonProperties): Set<String> {
        return if (meetsCondition(properties)) {
            setOf(aspect)
        } else {
            emptySet()
        }
    }

    override fun provide(Pokemon pokemon): Set<String> {
        return if (meetsCondition(pokemon)) {
            setOf(aspect)
        } else {
            emptySet()
        }
    }
}