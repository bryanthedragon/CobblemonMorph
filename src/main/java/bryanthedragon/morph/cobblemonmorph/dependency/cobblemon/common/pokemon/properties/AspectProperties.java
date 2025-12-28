/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Applies an aspect via [Pokemon.forcedAspects]. If you apply an aspect using this property type that is also
 * an inbuilt, derived aspect, it will almost certainly not make any data changes that are expected with it.
 *
 * For example, adding the female aspect to a male Pokémon will not make it male in any other way than visually.
 *
 * @author Hiroku
 * @since May 13th, 2024
 */final class AspectPropertyType : CustomPokemonPropertyType<StringProperty> {
    override val keys = setOf("aspect")
    override val needsKey = true
    override fun examples() = emptySet<String>()
    override fun fromString(value: String?) = if (value == null) null else StringProperty(
        key = keys.first(),
        value = value,
        applicator = { pokemon, value -> pokemon.forcedAspects += value },
        matcher = { pokemon, value -> value in pokemon.aspects }
    )
}

/**
 * Removes an aspect from [Pokemon.forcedAspects]. You cannot remove an inbuilt, derived aspect.
 *
 * For example, attempting to remove the female aspect from a male Pokémon will do nothing.
 *
 * @author Hiroku
 * @since May 13th, 2024
 */final class UnaspectPropertyType : CustomPokemonPropertyType<StringProperty> {
    override val keys = setOf("unaspect")
    override val needsKey = true
    override fun examples() = emptySet<String>()
    override fun fromString(value: String?) = if (value == null) null else StringProperty(
        key = keys.first(),
        value = value,
        applicator = { pokemon, value -> pokemon.forcedAspects -= value },
        matcher = { pokemon, value -> value !in pokemon.aspects }
    )
}