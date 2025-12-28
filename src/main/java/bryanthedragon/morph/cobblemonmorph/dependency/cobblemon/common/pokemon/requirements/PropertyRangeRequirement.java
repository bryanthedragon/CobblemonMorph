/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

class PropertyRangeRequirement : Requirement {
    val range = IntRange(0, 256)
    val feature: String = ""

    override fun check(pokemon: Pokemon): Boolean {
        val feature: IntSpeciesFeature = pokemon.getFeature(feature) ?: return false
        return this.range.contains(feature.value)
    }

    companion object {
        const val ADAPTER_VARIANT = "property_range"
    }
}