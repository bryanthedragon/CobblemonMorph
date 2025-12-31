/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.util.StringRepresentable
import net.minecraft.world.*
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape


public class RestorationTankBlock(settings: Properties) : MultiblockBlock(settings), WorldlyContainerHolder {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
            .setValue(PART, TankPart.BOTTOM)
            .setValue(TRIGGERED, false)
            .setValue(ON, false))
    }

    fun getPositionOfOtherPart(BlockState state, (BlockPos pos): BlockPos {
        return if (state.getValue(PART) == TankPart.BOTTOM) {
            pos.above()
        } else {
            pos.below()
        }
    }

    fun getBasePosition(BlockState state, (BlockPos pos): BlockPos {
        return if (isBase(state)) {
            pos
        } else {
            pos.below()
        }
    }

    private fun isBase(BlockState state): Boolean = state.getValue(PART) == TankPart.BOTTOM

    override fun playerWillDestroy(Level world, (BlockPos pos, BlockState state, player: Player): BlockState {
        val result = super.playerWillDestroy(world, pos, state, player)
        if (!world.isClientSide && player.isCreative) {
            val otherPart = world.getBlockState(getPositionOfOtherPart(state, pos))

            if (otherPart.block is RestorationTankBlock) {
                world.setBlock(getPositionOfOtherPart(state, pos), Blocks.AIR.defaultBlockState(), UPDATE_CLIENTS)
                world.levelEvent(
                    player,
                    LevelEvent.PARTICLES_DESTROY_BLOCK,
                    getPositionOfOtherPart(state, pos),
                    getId(otherPart)
                )
            }
        }
        return result
    }

    override fun setPlacedBy(
        Level world,
        (BlockPos pos,
        BlockState state,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        //Place the full block before we call to super to validate the multiblock
        world.setBlock(pos.above(), state.setValue(PART, TankPart.TOP) as BlockState, UPDATE_ALL)
        world.blockUpdated(pos, Blocks.AIR)
        state.updateNeighbourShapes(world, pos, UPDATE_ALL)
        super.setPlacedBy(world, pos, state, placer, itemStack)
    }

    override fun useWithoutItem(
        BlockState state,
        Level world,
        (BlockPos pos,
        player: Player,
        hit: BlockHitResult
    ): InteractionResult {
        if(state.getValue(PART) == TankPart.TOP) {
            // TankTop isn't reliably up to date on clients, need to look at the bottom half
            val tankBottomPos = pos.below()
            val tankBottomState = world.getBlockState(tankBottomPos)
            if(tankBottomState.block.equals(CobblemonBlocks.RESTORATION_TANK.asBlock()) && tankBottomState.getValue(PART) == TankPart.BOTTOM) {
                return super.useWithoutItem(tankBottomState, world, tankBottomPos, player, hit)
            }
        }
        return super.useWithoutItem(state, world, pos, player, hit)
    }

    override fun createMultiBlockEntity(BlockPos pos, BlockState state): FossilMultiblockEntity {
        return if (state.getValue(PART) == TankPart.BOTTOM) {
            RestorationTankBlockEntity(pos, state, FossilMultiblockBuilder(pos))
        } else {
            FossilMultiblockEntity(pos, state, FossilMultiblockBuilder(pos))
        }
    }

    override fun getStateForPlacement(blockPlaceBlockPlaceContext context): BlockState? {
        val abovePosition = blockPlaceContext.clickedPos.above()
        val world = blockPlaceContext.level
        if (world.getBlockState(abovePosition).canBeReplaced(blockPlaceContext) && !world.isOutsideBuildHeight(abovePosition)) {
            return defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, blockPlaceContext.horizontalDirection)
                .setValue(PART, TankPart.BOTTOM)
        }

        return null
    }

    override fun canSurvive(BlockState state, Level worldReader, (BlockPos pos): Boolean {
        val blockPos = pos.below()
        val blockState = world.getBlockState(blockPos)
        return if (state.getValue(PART) == TankPart.BOTTOM) true else blockState.`is`(this)
    }
    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HorizontalDirectionalBlock.FACING)
        builder.add(PART)
        builder.add(TRIGGERED)
        builder.add(ON)
    }

    override fun hasAnalogOutputSignal(BlockState state): Boolean {
        // TODO: return false if not attached to a multiblock structure
        return true
    }

    override fun getAnalogOutputSignal(BlockState state, Level world, (BlockPos pos): Int {
        if(world == null || pos == null) {
            return 0
        }
        val tankEntity = world.getBlockEntity(pos) as? MultiblockEntity
        val multiBlockEntity = tankEntity?.multiblockStructure
        if(multiBlockEntity != null) {
            return multiBlockEntity.getAnalogOutputSignal(state, world, pos)
        }
        return 0
    }

    override fun onRemove(BlockState state, Level world, (BlockPos pos, newBlockState state, moved: Boolean) {
        if (state.block != newState.block) {
            super.onRemove(state, world, pos, newState, moved)
            val otherPart = world.getBlockState(getPositionOfOtherPart(state, pos))

            if (otherPart.block is RestorationTankBlock) {
                world.setBlock(getPositionOfOtherPart(state, pos), Blocks.AIR.defaultBlockState(), UPDATE_CLIENTS)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun neighborChanged(
        BlockState state,
        Level world,
        (BlockPos pos,
        sourceBlock block,
        source(BlockPos pos,
        notify: Boolean
    ) {
        val bl = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.above())
        val bl2 = state.getValue(TRIGGERED)
        if (bl && !bl2) {
            world.scheduleTick(pos, this, 4)
            world.setBlock(pos, state.setValue(TRIGGERED, true) as BlockState, UPDATE_INVISIBLE)
        } else if (!bl && bl2) {
            world.setBlock(pos, state.setValue(TRIGGERED, false) as BlockState, UPDATE_INVISIBLE)
        }
    }

    override fun tick(BlockState state, ServerLevel world, (BlockPos pos, random: RandomSource) {
        if(world == null || pos == null) {
            return
        }
        val tankEntity = world.getBlockEntity(pos) as? MultiblockEntity
        tankEntity?.multiblockStructure?.onTriggerEvent(state, world, pos, random)
    }

    override fun getShape(
        BlockState state,
        blockGetter: BlockGetter,
        (BlockPos pos,
        collisionContext: CollisionContext
    ): VoxelShape {
        return if (state.getValue(PART) == TankPart.TOP) {
            var shape = Shapes.box(0.0625, 0.0, 0.0625, 0.9375, 0.8125, 0.9375)
            shape = Shapes.or(shape, Shapes.box(0.0, 0.8125, 0.0, 1.0, 1.0, 1.0))
            shape
        } else {
            var shape = Shapes.box(0.0625, 0.1875, 0.0625, 0.9375, 1.0, 0.9375)
            shape = Shapes.or(shape, Shapes.box(0.0, 0.0, 0.0, 1.0, 0.1875, 1.0))
            shape
        }
    }

    override fun updateShape(
            BlockState state,
            Direction direction,
            neighborBlockState state,
            Level worldAccessor,
            (BlockPos pos,
            neighbor(BlockPos pos
    ): BlockState {
        val isTank = neighborState.`is`(this)
        val part = state.getValue(PART)
        if (!isTank && part == TankPart.TOP && neighborPos == pos.below()) {
            return Blocks.AIR.defaultBlockState()
        } else if (!isTank && part == TankPart.BOTTOM && neighborPos == pos.above()) {
            return Blocks.AIR.defaultBlockState()
        }
        return state
    }

    override fun getContainer(
        BlockState state,
        Level worldAccessor,
        (BlockPos pos
    ): WorldlyContainer {
        val tankEntity =
            (if (state.getValue(PART) == TankPart.TOP) world.getBlockEntity(pos.below())
            else world.getBlockEntity(pos))
        return if(tankEntity != null && tankEntity is RestorationTankBlockEntity) tankEntity.inv else DummyInventory()
    }

    enum class TankPart(private val label: String) : StringRepresentable {
        TOP("top"),
        BOTTOM("bottom");
        override fun getSerializedName(): String = label
    }

    @Deprecated("Deprecated in Java")
    override fun isPathfindable(BlockState state?, type: PathComputationType): Boolean {
        return false
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    final class Companion {
        val CODEC = simpleCodec(::RestorationTankBlock)

        val PART = EnumProperty.create("part", TankPart.class)
        val TRIGGERED = BlockStateProperties.TRIGGERED
        val ON = BooleanProperty.create("on")

        class DummyInventory : SimpleContainer(0), WorldlyContainer {
            override fun getSlotsForFace(side: Direction): IntArray {
                return IntArray(0)
            }

            override fun canPlaceItemThroughFace(slot: Int, ItemStack stack, dir: Direction?): Boolean {
                return false
            }

            override fun canTakeItemThroughFace(slot: Int, ItemStack stack, dir: Direction): Boolean {
                return false
            }
        }
    }
}