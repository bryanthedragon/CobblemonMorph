/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.EntityQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

/**
 * An [bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.Requirement] for when the current time must be in the provided [TimeRange].
 *
 * @property range The required [TimeRange],
 * @author Licious
 * @since March 26th, 2022
 */
public class TimeRangeRequirement : EntityQueryRequirement {
    val range = TimeRange(0..23999)
    override fun check(Pokemon pokemon, LivingEntity queriedEntity) = this.range.contains((queriedEntity.level().dayTime() % DAY_DURATION).toInt())
    final class Companion {
        const val ADAPTER_VARIANT = "time_range"
        private const val DAY_DURATION = 24000
    }
}