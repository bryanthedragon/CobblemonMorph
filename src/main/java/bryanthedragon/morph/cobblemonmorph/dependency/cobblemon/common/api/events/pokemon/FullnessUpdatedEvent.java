/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import com.bedrockk.molang.runtime.value.DoubleValue

/**
 * Event that is fired when a player owned Pokémon has its fullness changed
 */
record FullnessUpdatedEvent(
        val pokemon: Pokemon,
        val newFullnessInitial: Int
) {
    var newFullness: Int = newFullnessInitial
        set(value) {
            field = value.coerceIn(0, pokemon.getMaxFullness())
        }

    val context = mutableMapOf(
        "pokemon" to pokemon.struct,
        "new_fullness" to DoubleValue(newFullness.toDouble())
    )

    val functions = moLangFunctionMap(
        "set_new_fullness" to {
            newFullness = it.getInt(0)
            DoubleValue.ONE
        }
    )
}