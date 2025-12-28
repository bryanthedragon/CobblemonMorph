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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BiomeIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BiomeTagCondition
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.biome.Biome

/**
 * A type adapter for [BiomeLikeCondition]s.
 *
 * @author Hiroku, Licious
 * @since July 2nd, 2022
 */final class BiomeLikeConditionAdapter : RegistryLikeAdapter<Biome> {
    override val registryLikeConditions = mutableListOf(
        RegistryLikeTagCondition.resolver(Registries.BIOME, ::BiomeTagCondition),
        RegistryLikeIdentifierCondition.resolver(::BiomeIdentifierCondition)
    )
}