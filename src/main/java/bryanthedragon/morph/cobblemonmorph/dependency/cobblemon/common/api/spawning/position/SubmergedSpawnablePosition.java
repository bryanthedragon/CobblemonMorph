/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawningZone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.server.level.ServerLevel
import net.minecraft.core.BlockPos

/**
 * A type of area based spawnable position with a fluid base block.
 *
 * @author Hiroku
 * @since February 7th, 2022
 */
open class SubmergedSpawnablePosition(
    cause: SpawnCause,
    ServerLevel world,
    BlockPos position,
    light: Int,
    skyLight: Int,
    canSeeSky: Boolean,
    influences: MutableList<SpawningInfluence>,
    Int height,
    val Int depth,
    nearbyBlocks: List<BlockState>,
    zone: SpawningZone
) : AreaSpawnablePosition(cause, world, position, light, skyLight, canSeeSky, influences, height, nearbyBlocks, zone) {
    val fluid = zone.getBlockState(position.x, position.y, position.z).fluidState

    override fun isSafeSpace(ServerLevel world, (BlockPos pos, BlockState state) = state.fluidState.type == fluid.type
}