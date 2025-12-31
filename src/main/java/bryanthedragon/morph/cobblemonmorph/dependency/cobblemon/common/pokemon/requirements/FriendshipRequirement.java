/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

/**
 * An [Requirement] for when a certain amount of [Pokemon.friendship] is expected.
 *
 * @property amount The required [Pokemon.friendship] amount, expects between 0 & 255.
 * @author Licious
 * @since March 21st, 2022
 */
public class FriendshipRequirement : Requirement {
    val amount = 0
    override fun check(Pokemon pokemon) = pokemon.friendship >= this.amount

    final class Companion {
        const val ADAPTER_VARIANT = "friendship"
    }
}