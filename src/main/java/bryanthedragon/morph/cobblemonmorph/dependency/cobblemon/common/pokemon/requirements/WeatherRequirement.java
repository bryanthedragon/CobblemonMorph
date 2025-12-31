/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.EntityQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

public class WeatherRequirement : EntityQueryRequirement {
    final class Companion {
        const val ADAPTER_VARIANT = "weather"
    }

    val isRaining: Boolean? = null
    val isThundering: Boolean? = null

    override fun check(Pokemon pokemon, LivingEntity queriedEntity): Boolean {
        val world = queriedEntity.level()
        return when {
            isRaining == true && !world.isRaining -> false
            isRaining == false && world.isRaining -> false
            isThundering == true && !world.isThundering -> false
            isThundering == false && world.isThundering -> false
            else -> true
        }
    }
}