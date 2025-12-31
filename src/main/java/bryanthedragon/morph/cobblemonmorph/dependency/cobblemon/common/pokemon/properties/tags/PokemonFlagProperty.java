/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.StringProperty
public final class PokemonFlagProperty : CustomPokemonPropertyType<StringProperty> {

    private const val KEY = "tag"

    override val keys = setOf(KEY)
    override val needsKey = true

    override fun fromString(value: String?) = if (value == null) null else StringProperty(KEY, value, { _, _ -> }, { pokemon, underlyingValue -> pokemon.hasLabels(underlyingValue) })

    override fun examples() = emptySet<String>()
}