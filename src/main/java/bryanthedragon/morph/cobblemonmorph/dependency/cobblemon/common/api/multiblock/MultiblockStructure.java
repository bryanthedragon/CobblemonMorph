/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

public interface MultiblockStructure {
    val controllerBlock(BlockPos pos

    fun useWithoutItem(
        blockBlockState state,
        Level world,
        blockBlockPos pos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult

    fun playerWillDestroy(Level world, (BlockPos pos, BlockState state, player: Player?)

    fun tick(Level world)

    fun syncToClient(Level world)

    fun markDirty(Level world)
    fun writeToNbt(registryLookup: HolderLookup.Provider): CompoundTag
    fun getAnalogOutputSignal(BlockState state, Level world?, (BlockPos pos?): Int {
        return 0
    }

    fun setRemoved(Level world)
    fun onTriggerEvent(BlockState state?, ServerLevel world?, (BlockPos pos?, random: RandomSource?)
}