/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropTable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

// Used only to comply with pokemon update packet structure
internal class DummyEvolution : Evolution {

    override val id = "dummy"
    override val result: PokemonProperties = PokemonProperties()
    override val shedder: PokemonProperties? = null
    override var optional = false
    override var consumeHeldItem = false
    override val requirements: MutableSet<Requirement> = mutableSetOf()
    override val learnableMoves: MutableSet<MoveTemplate> = mutableSetOf()
    override val drops: DropTable = DropTable()

    override fun test(Pokemon pokemon) = false

    override fun evolve(Pokemon pokemon) = false

}