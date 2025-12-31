/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.Containers
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult

@Suppress("OVERRIDE_DEPRECATION")
public class DisplayCaseBlock(settings: Properties) : BaseEntityBlock(settings) {
    init {
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(ITEM_DIRECTION, Direction.NORTH))
    }

    override fun getStateForPlacement(BlockPlaceContext ctx): BlockState? {
        var blockState = defaultBlockState()
        val worldView = ctx.level
        val blockPos = ctx.clickedPos
        ctx.nearestLookingDirections.forEach { direction ->
            if (direction.axis.isHorizontal) {
                blockState = blockState
                    .setValue(FACING, direction)
                    .setValue(ITEM_DIRECTION, direction)
                        as BlockState
                if (blockState.canSurvive(worldView, blockPos)) {
                    return blockState
                }
            }
        }
        return null
    }

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING)
        builder.add(ITEM_DIRECTION)
    }

    override fun newBlockEntity(BlockPos pos, BlockState state): DisplayCaseBlockEntity {
        return DisplayCaseBlockEntity(pos, state)
    }

    override fun updateShape(
        BlockState state,
        Direction direction,
        neighborBlockState state,
        Level worldAccessor,
        (BlockPos pos,
        neighbor(BlockPos pos
    ): BlockState {
        return if (direction == state.getValue(FACING) && !state.canSurvive(world, pos)) Blocks.AIR.defaultBlockState()
        else super.updateShape(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun useWithoutItem(
        BlockState state,
        Level world,
        (BlockPos pos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        val entity = world.getBlockEntity(pos) as DisplayCaseBlockEntity
        val result = entity.updateItem(player, InteractionHand.MAIN_HAND)
        if ((blockHitResult.direction != Direction.UP && blockHitResult.direction != Direction.DOWN) && result == InteractionResult.SUCCESS) {
            world.setBlockAndUpdate(pos, state.setValue(ITEM_DIRECTION, blockHitResult.direction.opposite))
        }
        return result
    }

    override fun onRemove(
        BlockState state,
        Level world,
        (BlockPos pos,
        newBlockState state,
        moved: Boolean
    ) {
        val entity = world.getBlockEntity(pos) as DisplayCaseBlockEntity
        if (!entity.getStack().isEmpty) {
            Containers.dropContentsOnDestroy(state, newState, world, pos)
        }
        super.onRemove(state, world, pos, newState, moved)
    }

    override fun getRenderShape(BlockState state) = RenderShape.MODEL

    override fun getAnalogOutputSignal(BlockState state, Level world, (BlockPos pos): Int {
        val stack = (world.getBlockEntity(pos) as DisplayCaseBlockEntity).getStack()

        if (stack.isEmpty) return 0
        if (stack.item is PokeBallItem) return 3
        if (stack.item is BlockItem) return 2
        return 1
    }

    override fun hasAnalogOutputSignal(BlockState state): Boolean = true

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    override fun isPathfindable(BlockState state, type: PathComputationType): Boolean = false

    final class Companion {
        val CODEC = simpleCodec(::DisplayCaseBlock)
        val ITEM_DIRECTION = DirectionProperty.create("item_facing")
    }

}