/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
public final class FreezeFrameProperty : CustomPokemonPropertyType<FloatProperty> {
    override val keys = setOf("freeze_frame")
    override fun examples() = setOf("0.0", "1.5", "10")
    override val needsKey = true

    override fun fromString(value: String?): FloatProperty {
        return buildProperty(value?.toFloatOrNull() ?: -1F)
    }

    private fun buildProperty(value: Float) = FloatProperty(
        key = keys.first(),
        value = value,
        pokemonApplicator = { _, _ -> },
        entityApplicator = { pokemonEntity, freezeFrame -> pokemonEntity.entityData.set(PokemonEntity.FREEZE_FRAME, freezeFrame) },
        pokemonMatcher = { _, _ -> false },
        entityMatcher = { pokemonEntity, freezeFrame -> pokemonEntity.entityData.get(PokemonEntity.FREEZE_FRAME) == freezeFrame }
    )
}