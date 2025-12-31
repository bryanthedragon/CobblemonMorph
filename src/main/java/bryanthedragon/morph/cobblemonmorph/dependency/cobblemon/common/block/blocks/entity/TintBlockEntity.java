/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TintBlockEntity(BlockEntityType<*> type, BlockPos pos, blockBlockState state) extends BlockEntity(type, blockPos, blockState) {
    final class Companion {
        const String TINT = "tint";
    }

    var Int? tint = null;

    fun saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tint?.let { tag.put(TINT, IntTag.valueOf(it)) };
    }

    fun loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(TINT)) tint = tag.getInt(TINT);
    }

    CompoundTag getUpdateTag(registryLookup: HolderLookup.Provider) {
        return this.saveWithoutMetadata(registryLookup);
    }

    fun getTint() = tint ?: 0xFFFFFF;

    fun setTint(tintValue: Int) {
        level.let {tint = tintValue; setChanged(); it.blockEntityChanged(blockPos); it.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL);
        }
    }
}
