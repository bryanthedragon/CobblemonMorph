/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.cooking

import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions.moLangFunctionMap
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokeSnackBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity

public interface PokeSnackSpawnPokemonEvent {

    record Pre(
        val pokeSnackBlockEntity: PokeSnackBlockEntity,
        val spawnAction: SpawnAction<*>,
    ) : Cancelable(), PokeSnackSpawnPokemonEvent {
        val context = mapOf<String, MoValue>()
        val functions = moLangFunctionMap(
            cancelFunc
        )
    }

    record Post(
        val pokeSnackBlockEntity: PokeSnackBlockEntity,
        val spawnAction: SpawnAction<*>,
        val pokemonEntity: PokemonEntity,
    ) : PokeSnackSpawnPokemonEvent {
        val context = mapOf<String, MoValue>()
    }

}