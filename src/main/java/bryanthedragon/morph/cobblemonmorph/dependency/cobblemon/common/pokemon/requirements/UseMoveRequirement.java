/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress

/**
 * An [Requirement] meant to require a move to have been used a specific amount of times.
 *
 * @param move The [MoveTemplate] expected to be used.
 * @param amount The amount of times it has been used.
 *
 * @author Licious
 * @since January 25th, 2023
 */
class UseMoveRequirement(move: MoveTemplate, amount: Int) : Requirement {

    constructor() : this(Moves.getByNameOrDummy(""), 1)

    val move: MoveTemplate = move
    val amount: Int = amount

    override fun check(pokemon: Pokemon): Boolean = pokemon.evolutionProxy.current()
        .progress()
        .filterIsInstance<UseMoveEvolutionProgress>()
        .any { progress -> progress.currentProgress().move == this.move && progress.currentProgress().amount >= this.amount }

    companion object {
        const val ADAPTER_VARIANT = "use_move"
    }

}