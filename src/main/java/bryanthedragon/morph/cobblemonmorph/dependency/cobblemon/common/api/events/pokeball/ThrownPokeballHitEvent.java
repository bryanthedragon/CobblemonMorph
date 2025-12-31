/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

/**
 * Event fired when a thrown Pokeball hits a Pokémon. Cancelling this event prevents the capture being started.
 */
public class ThrownPokeballHitEvent(
    val pokeBall : EmptyPokeBallEntity,
    val pokemon : PokemonEntity
) : Cancelable() {
    val functions = moLangFunctionMap(
        "pokeball" to { pokeBall.struct },
        "pokemon" to { pokemon.asMoLangValue() },
        cancelFunc
    )
}