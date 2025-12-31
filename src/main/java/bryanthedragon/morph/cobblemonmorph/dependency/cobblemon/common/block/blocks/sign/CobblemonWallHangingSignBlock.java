/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;.sign

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonHangingSignBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.WallHangingSignBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.WoodType

public class CobblemonWallHangingSignBlock(settings: Properties, woodType: WoodType) : WallHangingSignBlock(woodType, settings) {

    override fun newBlockEntity(BlockPos pos, BlockState state): BlockEntity = CobblemonHangingSignBlockEntity(pos, state)

}