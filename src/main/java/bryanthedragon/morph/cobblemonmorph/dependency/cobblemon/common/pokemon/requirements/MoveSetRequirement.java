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

/**
 * An [Requirement] for when a certain [MoveTemplate] is expected in the [Pokemon.moveSet].
 *
 * @property move The required [MoveTemplate].
 * @author Licious
 * @since March 21st, 2022
 */
public class MoveSetRequirement : Requirement {
    val MoveTemplate move = Moves.getByNameOrDummy("tackle")
    override fun check(Pokemon pokemon) = pokemon.moveSet.getMoves().any { move -> move.name.equals(this.move.name, true) }
    final class Companion {
        const val ADAPTER_VARIANT = "has_move"
    }
}