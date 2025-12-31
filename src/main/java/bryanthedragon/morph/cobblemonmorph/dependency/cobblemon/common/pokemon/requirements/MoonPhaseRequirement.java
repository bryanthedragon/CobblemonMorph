/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.EntityQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.MoonPhase
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.world.entity.LivingEntity

/**
 * An [EntityQueryRequirement] meant to check if the world is currently on the given [moonPhase].
 * This does not check if the time of day is the intended one for the moon to appear for that effect use in conjunction with [TimeRangeRequirement].
 *
 * @param moonPhase The [MoonPhase] being targeted.
 *
 * @author Licious
 * @since January 25th, 2023
 */
public class MoonPhaseRequirement(moonPhase: MoonPhase) : EntityQueryRequirement {

    constructor() : this(MoonPhase.FULL_MOON)

    val moonPhase: MoonPhase = moonPhase

    override fun check(Pokemon pokemon, LivingEntity queriedEntity): Boolean {
        return try {
            val moonPhase = MoonPhase.ofWorld(queriedEntity.level())
            this.moonPhase == moonPhase
        } catch (e: IndexOutOfBoundsException) {
            false
        }
    }

    final class Companion {
        const val ADAPTER_VARIANT = "moon_phase"
    }

}