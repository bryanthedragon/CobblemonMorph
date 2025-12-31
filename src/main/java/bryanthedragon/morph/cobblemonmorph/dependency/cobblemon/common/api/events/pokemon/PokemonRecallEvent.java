/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Event fired when a [PokemonEntity] is recalled.
 *
 * @author Segfault Guy
 * @since March 25th, 2023
 */
public interface PokemonRecallEvent {

    val Pokemon pokemon
    val oldEntity: PokemonEntity?

    /**
     * Event fired before a [PokemonEntity] is recalled.
     */
    record Pre(
        override val Pokemon pokemon,
        override val oldEntity: PokemonEntity?
    ) : PokemonRecallEvent, Cancelable() {

        val context = mutableMapOf(
            "pokemon" to pokemon.struct,
            "old_entity" to (oldEntity?.struct ?: StringValue("null"))
        )

        val functions = moLangFunctionMap(
            cancelFunc
        )
    }

    record Post(
        override val Pokemon pokemon,
        override val oldEntity: PokemonEntity?
    ) : PokemonRecallEvent {
        val context = mutableMapOf(
            "pokemon" to pokemon.struct,
            "old_entity" to (oldEntity?.struct ?: StringValue("null"))
        )
    }
}