/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.ContextEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * Represents a universal requirement suitable for various checks on pokemon..
 * Requirements are constant and will never change regardless of the backing action.
 * For dynamic requirements see [ContextEvolution].
 *
 * See [Evolution.requirements] & [Evolution.test] & [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonInteractions.findInteraction] for usages.
 *
 * @author Licious
 * @since March 19th, 2022
 */
fun interface Requirement {

    /**
     * Checks if the given [Pokemon] satisfies the requirement.
     *
     * @param pokemon The [Pokemon] being queried.
     * @return If the requirement was satisfied.
     */
    fun check(Pokemon pokemon): Boolean

}