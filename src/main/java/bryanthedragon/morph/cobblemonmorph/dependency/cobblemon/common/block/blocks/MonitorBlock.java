/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

public class MonitorBlock(settings: Properties) : MultiblockBlock(settings) {
    init {
        registerDefaultState(stateDefinition.any()
            .setValue(HORIZONTAL_FACING, Direction.NORTH))
    }

    override fun createMultiBlockEntity(
        (BlockPos pos,
        BlockState state
    ): FossilMultiblockEntity {
        return FossilMultiblockEntity(
            pos, state, FossilMultiblockBuilder(pos)
        )
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    override fun getStateForPlacement(blockPlaceBlockPlaceContext context): BlockState {
        return defaultBlockState().setValue(HORIZONTAL_FACING, blockPlaceContext.horizontalDirection)
    }

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING)
        builder.add(SCREEN)
    }

    override fun hasAnalogOutputSignal(BlockState state): Boolean {
        // TODO: return false if not attached to a multiblock structure
        return true
    }

    override fun getAnalogOutputSignal(BlockState state, Level world, (BlockPos pos): Int {
        val monitorEntity = world.getBlockEntity(pos) as? MultiblockEntity
        val multiBlockEntity = monitorEntity?.multiblockStructure
        if(multiBlockEntity != null) {
            return multiBlockEntity.getAnalogOutputSignal(state, world, pos)
        }
        return 0
    }

    override fun getShape(
        BlockState state,
        blockGetter: BlockGetter,
        (BlockPos pos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return when (state.getValue(HorizontalDirectionalBlock.FACING)) {
            Direction.WEST -> HITBOX_WEST
            Direction.EAST -> HITBOX_EAST
            Direction.SOUTH -> HITBOX_SOUTH
            else -> HITBOX_NORTH
        }
    }

    @Deprecated("Deprecated in Java")
    override fun rotate(BlockState state, rotation: Rotation): BlockState {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)))
    }

    @Deprecated("Deprecated in Java")
    override fun isPathfindable(BlockState state, type: PathComputationType): Boolean {
        return false
    }

    final class Companion {
        val CODEC = simpleCodec(::MonitorBlock)

        //0 is off
        val SCREEN = EnumProperty.create("screen", MonitorScreen.class)
        val HITBOX_SOUTH = Shapes.or(
            Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.375, 0.9375),
            Shapes.box(0.0625, 0.875, 0.0625, 0.9375, 1.0, 0.9375),
            Shapes.box(0.8125, 0.375, 0.0625, 0.9375, 0.875, 0.9375),
            Shapes.box(0.1875, 0.375, 0.125, 0.8125, 0.875, 0.9375),
            Shapes.box(0.0625, 0.375, 0.0625, 0.1875, 0.875, 0.9375)
        )
        val HITBOX_NORTH = Shapes.or(
            Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.375, 0.9375),
            Shapes.box(0.0625, 0.875, 0.0625, 0.9375, 1.0, 0.9375),
            Shapes.box(0.0625, 0.375, 0.0625, 0.1875, 0.875, 0.9375),
            Shapes.box(0.1875, 0.375, 0.0625, 0.8125, 0.875, 0.875),
            Shapes.box(0.8125, 0.375, 0.0625, 0.9375, 0.875, 0.9375)
        )
        val HITBOX_EAST = Shapes.or(
            Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.375, 0.9375),
            Shapes.box(0.0625, 0.875, 0.0625, 0.9375, 1.0, 0.9375),
            Shapes.box(0.0625, 0.375, 0.0625, 0.9375, 0.875, 0.1875),
            Shapes.box(0.125, 0.375, 0.1875, 0.9375, 0.875, 0.8125),
            Shapes.box(0.0625, 0.375, 0.8125, 0.9375, 0.875, 0.9375)
        )
        val HITBOX_WEST = Shapes.or(
            Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.375, 0.9375),
            Shapes.box(0.0625, 0.875, 0.0625, 0.9375, 1.0, 0.9375),
            Shapes.box(0.0625, 0.375, 0.8125, 0.9375, 0.875, 0.9375),
            Shapes.box(0.0625, 0.375, 0.1875, 0.875, 0.875, 0.8125),
            Shapes.box(0.0625, 0.375, 0.0625, 0.9375, 0.875, 0.1875)
        )
    }
    enum class MonitorScreen : StringRepresentable {
        OFF,
        BLUE_PROGRESS_1,
        BLUE_PROGRESS_2,
        BLUE_PROGRESS_3,
        BLUE_PROGRESS_4,
        BLUE_PROGRESS_5,
        BLUE_PROGRESS_6,
        BLUE_PROGRESS_7,
        BLUE_PROGRESS_8,
        BLUE_PROGRESS_9,
        GREEN_PROGRESS_9;

        override fun getSerializedName(): String = this.name.lowercase()
    }
}