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
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

/**
 * A [EntityQueryRequirement] for when a [Pokemon] is expected to be in a certain area.
 *
 * @property box The [AABB] expected to be in.
 * @author Licious
 * @since March 21st, 2022
 */
public class AreaRequirement : EntityQueryRequirement {
    final class Companion {
        const val ADAPTER_VARIANT = "area"
    }

    val AABB box = AABB.unitCubeFromLowerCorner(Vec3.ZERO)
    override fun check(Pokemon pokemon, LivingEntity queriedEntity) = box.contains(queriedEntity.position())
}