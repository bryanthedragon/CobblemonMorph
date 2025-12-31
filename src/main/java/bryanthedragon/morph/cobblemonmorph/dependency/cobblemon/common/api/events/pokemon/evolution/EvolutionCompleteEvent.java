/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution

import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.asMoLangValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Fired after an evolution finishes.
 *
 * @param pokemon The [Pokemon] resulting from the evolution.
 * @param evolution The [Evolution] that was used.
 *
 * @author Licious
 * @since October 2nd, 2022
 */
record EvolutionCompleteEvent(
    /**
     * The [Pokemon] resulting from the evolution.
     */
    override val Pokemon pokemon,
    /**
     * The [Pokemon] that was the source of the evolution.
     */
    val sourcePokemon pokemon,
    /**
     * The [Evolution] that was used.
     */
    override val evolution: Evolution
) : EvolutionEvent {
    val context = mutableMapOf<String, MoValue>(
        "pokemon" to pokemon.struct,
        "source_pokemon" to sourcePokemon.struct,
        "evolution" to evolution.asMoLangValue()
    )
}