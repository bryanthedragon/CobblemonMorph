/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.Properties;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class TumblestoneBlock(Properties settings, Int stage, Int height, Int xzOffset, Block nextStage) extends GrowableStoneBlock(settings, stage, height, xzOffset, nextStage), SimpleWaterloggedBlock {

    // TODO(Deltric): Look into Block.CODEC for being optional more
    final class Companion {
        MapCodec<TumblestoneBlock> CODEC = RecordCodecBuilder.mapCodec { it.group(propertiesCodec(), PrimitiveCodec.INT.fieldOf("stage").forGetter { it.stage }, PrimitiveCodec.INT.fieldOf("height").forGetter { it.height }, PrimitiveCodec.INT.fieldOf("xzOffset").forGetter { it.xzOffset }, Block.CODEC.fieldOf("nextStage").forGetter { it.nextStage }).apply(it, ::TumblestoneBlock) }
    }

    init {
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.DOWN).setValue(WATERLOGGED, false));
    }

    Boolean canGrow(BlockPos pos, BlockGetter world) {
        if (stage == MAX_STAGE) return false;
        val iterator: Iterator<BlockPos> = BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1)).iterator();

        var blockBlockPos pos;
        do { if (!iterator.hasNext()) { return false }
            blockPos = iterator.next();
        } 
        while (!world.getBlockState(blockPos).`is`(CobblemonBlockTags.TUMBLESTONE_HEAT_SOURCE))

        return true;
    }

    MapCodec<DirectionalBlock> codec() {
        return CODEC;
    }

    FluidState getFluidState(BlockState state) {
        return if (state.getValue(WATERLOGGED)) {
            Fluids.WATER.getSource(false);
        }
        else super.getFluidState(state);
    }

    BlockState createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    BlockState getStateForPlacement(BlockPlaceContext ctx) ? {
        return super.getStateForPlacement(ctx)?.setValue(WATERLOGGED, ctx.level.getFluidState(ctx.clickedPos).type == Fluids.WATER)

    }

    BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, Level worldAccessor, BlockPos pos, BlockPos neighborPos) {
            if (state.getValue(WATERLOGGED)) world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }
}