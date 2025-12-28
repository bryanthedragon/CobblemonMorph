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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemIdentifierCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import net.minecraft.core.registries.Registries
import net.minecraft.world.item.Item

/**
 * A type adapter for [ItemLikeCondition]s.
 *
 * @author Licious
 * @since October 28th, 2022
 */final class ItemLikeConditionAdapter : RegistryLikeAdapter<Item> {
    override val registryLikeConditions = mutableListOf(
        RegistryLikeTagCondition.resolver(Registries.ITEM, ::ItemTagCondition),
        RegistryLikeIdentifierCondition.resolver(::ItemIdentifierCondition)
    )
}