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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BlockIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.BlockTagCondition
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.Block
public final class BlockLikeConditionAdapter : RegistryLikeAdapter<Block> {
    override val registryLikeConditions = mutableListOf(
        RegistryLikeTagCondition.resolver(Registries.BLOCK, ::BlockTagCondition),
        RegistryLikeIdentifierCondition.resolver(::BlockIdentifierCondition)
    )
}