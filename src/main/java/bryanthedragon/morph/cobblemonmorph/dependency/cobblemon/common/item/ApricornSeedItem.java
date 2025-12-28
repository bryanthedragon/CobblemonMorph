/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.state.BlockState

class ApricornSeedItem(block: ApricornSaplingBlock, val apricornBlock: ApricornBlock) : ItemNameBlockItem(block, Properties()) {

    // TODO (techdaan): ensure this is ported properly
    override fun getPlacementState(context: BlockPlaceContext): BlockState? {
        // Verify the feature is enabled similar to what's done at the top of place
        if (this.apricornBlock.isEnabled(context.level.enabledFeatures())) {
            // Get a contextualized apricorn block state
            val apricornState = this.apricornBlock.getStateForPlacement(context)
            // If placeable return otherwise let default impl run, DO NOT return a null
            if (apricornState != null && this.canPlace(context, apricornState)) {
                return apricornState
            }
        }
        return super.getPlacementState(context)
    }

}