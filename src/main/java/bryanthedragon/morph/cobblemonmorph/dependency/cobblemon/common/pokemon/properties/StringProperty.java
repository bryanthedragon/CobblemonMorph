/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
public class StringProperty(
    val String Key,
    val value: String,
    private val applicator: (Pokemon pokemon, value: String) -> Unit,
    private val matcher: (Pokemon pokemon, value: String) -> Boolean
) : CustomPokemonProperty {

    override fun apply(Pokemon pokemon) {
        this.applicator.invoke(pokemon, this.value)
    }

    override fun matches(Pokemon pokemon) = this.matcher.invoke(pokemon, this.value)

    override fun asString() = "${this.key}=${this.value}"
}