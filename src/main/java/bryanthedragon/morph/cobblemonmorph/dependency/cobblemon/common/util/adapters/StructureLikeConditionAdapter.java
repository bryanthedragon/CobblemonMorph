/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeTagCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.StructureIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.StructureTagCondition
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.levelgen.structure.Structure

/**
 * A type adapter for [StructureLikeCondition]s.
 *
 * @author Hiroku, Licious, whatsy
 * @since December 5th, 2023
 */
public final class StructureLikeConditionAdapter : RegistryLikeAdapter<Structure> {
    override val registryLikeConditions = mutableListOf(
        RegistryLikeTagCondition.resolver(Registries.STRUCTURE, ::StructureTagCondition),
        RegistryLikeIdentifierCondition.resolver(::StructureIdentifierCondition)
    )
}