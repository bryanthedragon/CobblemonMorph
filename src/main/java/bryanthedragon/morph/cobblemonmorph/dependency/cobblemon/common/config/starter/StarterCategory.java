/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asTranslated

record StarterCategory(
    val name: String,
    val displayName: String,
    val pokemon: List<PokemonProperties>
) {
    fun asRenderableStarterCategory() = RenderableStarterCategory(name, displayName, pokemon.map { it.asRenderablePokemon() })
}

record RenderableStarterCategory(
    val name: String,
    val displayName: String,
    val pokemon: List<RenderablePokemon>
) {
    val displayNameText = displayName.asTranslated()
}
