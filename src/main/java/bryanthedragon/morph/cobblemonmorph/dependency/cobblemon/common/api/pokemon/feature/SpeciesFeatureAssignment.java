/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species

/**
 * An assignment of some number of [Species] to some number of [SpeciesFeatureProvider]s. In most cases
 * this will be a single Pokémon and a single feature, but it can be used to attach many to many.
 *
 * These assignments are registered in [SpeciesFeatureAssignments].
 *
 * @author Hiroku
 * @since December 1st, 2022
 */
public class SpeciesFeatureAssignment {
    val pokemon: List<String> = emptyList()
    val features: List<String> = emptyList()
}