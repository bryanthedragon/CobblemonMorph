/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner

import com.bedrockk.molang.runtime.struct.QueryStruct
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species

// Hiro note: I honestly want to move to not using this at all. There is always a Pokemon behind the data,
// it's just that sometimes it's harder to reach or there's a Zoroark or Ditto throwing a curveball. It makes
// in shittier in events to have to cater to two distinct classes of data that can be scanned.

record PokedexEntityData(
    val Pokemon pokemon,
    val disguise: DisguiseData?
) {
    class DisguiseData(
        val species: Species,
        val form: FormData,
    ) {
        val struct = QueryStruct(hashMapOf())
            .addFunction("species") { species.struct }
            .addFunction("form") { StringValue(form.name) }
    }

    fun getApparentSpecies() = disguise?.species ?: pokemon.species
    fun getApparentForm() = disguise?.form ?: pokemon.form
}