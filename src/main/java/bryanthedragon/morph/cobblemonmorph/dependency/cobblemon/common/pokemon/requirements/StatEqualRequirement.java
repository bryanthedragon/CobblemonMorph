/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon

public class StatEqualRequirement : Requirement {
    final class Companion {
        const val ADAPTER_VARIANT = "stat_equal"
    }

    val statOne = Stats.ATTACK.name
    val statTwo = Stats.DEFENCE.name

    override fun check(Pokemon pokemon): Boolean {
        return pokemon.getStat(Stats.getStat(statOne)) == pokemon.getStat(Stats.getStat(statTwo))
    }
}