/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.requirement.EntityQueryRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon
import net.minecraft.core.registries.Registries
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.levelgen.structure.Structure

/**
 * A [EntityQueryRequirement] for when a [Pokemon] is expected to be in a certain structure.
 *
 * @property structureCondition The [Identifier] of the structure the queried entity is expected to be in.
 * @author whatsy
 * @since December 5th, 2023
 */
public class StructureRequirement : EntityQueryRequirement {
    val structureCondition: RegistryLikeCondition<Structure>? = null
    val structureAnticondition: RegistryLikeCondition<Structure>? = null
    override fun check(Pokemon pokemon, LivingEntity queriedEntity): Boolean {
        val structures = queriedEntity.level().getChunk(queriedEntity.blockPosition()).allReferences
        val registry = queriedEntity.level().registryAccess().registryOrThrow(Registries.STRUCTURE)
        return (structureCondition == null || structures.any { structureCondition.fits(it.key, registry) }) && (structureAnticondition == null || !structures.any { structureAnticondition.fits(it.key, registry) })
    }

    final class Companion {
        const val ADAPTER_VARIANT = "structure"
    }

}