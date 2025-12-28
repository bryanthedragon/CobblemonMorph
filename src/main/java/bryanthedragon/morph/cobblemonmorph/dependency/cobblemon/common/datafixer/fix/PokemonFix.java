/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.datafixer.fix

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.datafixer.CobblemonTypeReferences
import com.mojang.datafixers.DSL
import com.mojang.datafixers.DataFix
import com.mojang.datafixers.TypeRewriteRule
import com.mojang.datafixers.schemas.Schema
import com.mojang.serialization.Dynamic

abstract class PokemonFix(outputSchema: Schema) : DataFix(outputSchema, false) {
    override fun makeRule(): TypeRewriteRule {
        val oldPokemonType = inputSchema.getType(CobblemonTypeReferences.POKEMON)
        return this.fixTypeEverywhereTyped(this::class.simpleName, oldPokemonType) { pokemon ->
            pokemon.update(DSL.remainderFinder(), ::fixPokemonData)
        }
    }

    protected abstract fun fixPokemonData(dynamic: Dynamic<*>): Dynamic<*>
}