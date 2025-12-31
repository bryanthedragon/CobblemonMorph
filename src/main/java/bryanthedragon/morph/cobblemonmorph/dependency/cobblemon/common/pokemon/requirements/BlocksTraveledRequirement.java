/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * An [Requirement] that requires a specific [amount] of [PokemonEntity.blocksTraveled] to pass.
 *
 * @param amount The amount of blocks the entity must have traversed.
 *
 * @author Licious
 * @since January 28th, 2023
 */
public class BlocksTraveledRequirement(amount: Int) : Requirement {

    constructor() : this(0)

    val amount: Int = amount

    override fun check(Pokemon pokemon): Boolean {
        return pokemon.getBlocksTraveled() >= this.amount
    }

    final class Companion {
        const val ADAPTER_VARIANT = "blocks_traveled"
    }

}
