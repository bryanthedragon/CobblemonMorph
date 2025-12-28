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

class StatCompareRequirement : Requirement {
    companion object {
        const val ADAPTER_VARIANT = "stat_compare"
    }

    val highStat = Stats.ATTACK.name
    val lowStat = Stats.DEFENCE.name

    override fun check(pokemon: Pokemon): Boolean {
        return pokemon.getStat(Stats.getStat(highStat)) > pokemon.getStat(Stats.getStat(lowStat))
    }
}