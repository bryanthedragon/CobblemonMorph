/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.LecternBlock
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.ContainerHelper
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public class LecternBlockEntity(blockBlockPos pos, blockBlockState state) : ViewerCountBlockEntity(CobblemonBlockEntities.LECTERN, blockPos, blockState) {
    val inventory: NonNullList<ItemStack> = NonNullList.withSize(1, ItemStack.EMPTY)

    override fun saveAdditional(compoundCompoundTag tag, registryLookup: HolderLookup.Provider) {
        super.saveAdditional(compoundTag, registryLookup)
        ContainerHelper.saveAllItems(compoundTag, inventory, true, registryLookup)
    }

    override fun loadAdditional(compoundCompoundTag tag, registryLookup: HolderLookup.Provider) {
        super.loadAdditional(compoundTag, registryLookup)
        ContainerHelper.loadAllItems(compoundTag, inventory, registryLookup)
    }

    override fun getUpdateTag(registryLookup: HolderLookup.Provider): CompoundTag {
        return this.saveWithoutMetadata(registryLookup)
    }

    override fun updateBlock(Level level, BlockState state) {
        super.updateBlock(level, state.setValue(LecternBlock.EMIT_LIGHT, hasViewer()))
    }

    fun isEmpty(): Boolean = getItemStack().isEmpty

    fun getItemStack(): ItemStack = inventory[0]

    fun setItemStack(itemStack: ItemStack) {
        level?.let {
            inventory[0] = itemStack
            updateBlock(it, blockState)
        }
    }

    fun removeItemStack(): ItemStack {
        level?.let {
            val itemStack = ContainerHelper.removeItem(inventory, 0, 1)
            updateBlock(it, blockState)
            return itemStack
        }
        return ItemStack.EMPTY
    }
}
