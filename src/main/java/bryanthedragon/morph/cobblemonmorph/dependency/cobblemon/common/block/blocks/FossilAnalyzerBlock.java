/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.WorldlyContainer
import net.minecraft.world.WorldlyContainerHolder
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

public class FossilAnalyzerBlock(settings: Properties) : MultiblockBlock(settings), WorldlyContainerHolder {
    init {
        registerDefaultState(stateDefinition.any()
            .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
            .setValue(ON, false))
    }

    override fun createMultiBlockEntity(BlockPos pos, BlockState state): FossilMultiblockEntity {
        return FossilAnalyzerBlockEntity(pos, state, FossilMultiblockBuilder(pos))
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    override fun <T : BlockEntity?> getTicker(
        Level world,
        BlockState state,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? = createTickerHelper(type, CobblemonBlockEntities.FOSSIL_ANALYZER, FossilMultiblockStructure.TICKER::tick)

    override fun getStateForPlacement(blockPlaceBlockPlaceContext context): BlockState {
        return defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, blockPlaceContext.horizontalDirection)
    }

    override fun getContainer(
        BlockState state,
        Level worldAccessor,
        (BlockPos pos
    ): WorldlyContainer {
        val analyzerEntity = world.getBlockEntity(pos) as FossilAnalyzerBlockEntity

        return analyzerEntity.inv
    }

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING)
        builder.add(ON)
    }

    final class Companion {
        val ON = BooleanProperty.create("on")

        val CODEC = simpleCodec(::FossilAnalyzerBlock)
    }
}