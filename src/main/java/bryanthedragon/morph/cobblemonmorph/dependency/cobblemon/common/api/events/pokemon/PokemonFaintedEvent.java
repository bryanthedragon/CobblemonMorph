/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Event that is fired when a Pokémon has fainted
 */
record PokemonFaintedEvent(
    val Pokemon pokemon,
    var faintedTimer: Int
) {
    val context = mutableMapOf(
        "pokemon" to pokemon.struct,
        "fainted_timer" to DoubleValue(faintedTimer)
    )
}
