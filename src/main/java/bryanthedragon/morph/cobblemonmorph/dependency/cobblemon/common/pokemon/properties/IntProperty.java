/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

open class IntProperty(
    val String Key,
    val Int value,
    private val pokemonApplicator: (Pokemon pokemon, Int value) -> Unit,
    private val entityApplicator: (pokemonEntity: PokemonEntity, Int value) -> Unit,
    private val pokemonMatcher: (Pokemon pokemon, Int value) -> Boolean,
    private val entityMatcher: (pokemonEntity: PokemonEntity, Int value) -> Boolean
) : CustomPokemonProperty {

    override fun asString() = "${this.key}=${this.value}"

    override fun apply(Pokemon pokemon) {
        this.pokemonApplicator.invoke(pokemon, this.value)
    }

    override fun apply(pokemonEntity: PokemonEntity) {
        this.entityApplicator.invoke(pokemonEntity, this.value)
    }

    override fun matches(Pokemon pokemon) = this.pokemonMatcher.invoke(pokemon, this.value)

    override fun matches(pokemonEntity: PokemonEntity) = this.entityMatcher.invoke(pokemonEntity, this.value)

}