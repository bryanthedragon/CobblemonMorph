/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter;

import java.util.List;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.renderer.RenderableStarterCategory;

public record StarterCategory(String name, String displayName, List<PokemonProperties> pokemon) {
    fun asRenderableStarterCategory() {
        RenderableStarterCategory(name, displayName, pokemon.map { it.asRenderablePokemon() })
    }
}