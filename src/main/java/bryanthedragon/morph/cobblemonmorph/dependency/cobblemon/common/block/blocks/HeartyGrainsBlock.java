/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.FluidTags
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.*
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.SimpleWaterloggedBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.material.FluidState
import net.minecraft.world.level.material.Fluids
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

@Suppress("OVERRIDE_DEPRECATION")
public class HeartyGrainsBlock(settings: Properties) : CropBlock(settings), SimpleWaterloggedBlock {
    final class Companion {
        val CODEC = simpleCodec(::HeartyGrainsBlock)
        const val MATURE_AGE = 6
        const val AGE_AFTER_HARVEST = 3 // Last single block stage
        val WATERLOGGED = BlockStateProperties.WATERLOGGED
        val AGE: IntegerProperty = IntegerProperty.create("age", 0, MATURE_AGE)
        val HALF: EnumProperty<DoubleBlockHalf> = BlockStateProperties.DOUBLE_BLOCK_HALF

        val AGE_TO_SHAPE = arrayOf(
            box(0.0, 0.0, 0.0, 16.0, 3.0, 16.0), // Stage 0
            box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0), // Stage 1
            box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), // Stage 2
            box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), // Stage 3
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),  // Stage 4
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),  // Stage 5
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),  // Stage 6
        )

        val AGE_TO_SHAPE_TOP = arrayOf(
            box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0), // Stage 0
            box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0), // Stage 1
            box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0), // Stage 2
            box(1.0, 0.0, 1.0, 15.0, 1.0, 15.0), // Stage 3
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),  // Stage 4
            box(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),  // Stage 5
            box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0),  // Stage 6
        )
    }

    override fun codec(): MapCodec<out CropBlock> = CODEC

    init {
        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(WATERLOGGED, false)
        )
    }

    override fun getAgeProperty(): IntegerProperty = AGE

    override fun getMaxAge(): Int = MATURE_AGE

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF, WATERLOGGED)
    }

    override fun getBaseSeedId(): ItemLike = CobblemonItems.HEARTY_GRAINS

    override fun getShape(BlockState state, BlockGetter world, (BlockPos pos, context: CollisionContext): VoxelShape =
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) AGE_TO_SHAPE_TOP[getAge(state)] else AGE_TO_SHAPE[this.getAge(state)]

    override fun getFluidState(BlockState state): FluidState {
        return if (state.getValue(WATERLOGGED)) Fluids.WATER.defaultFluidState() else Fluids.EMPTY.defaultFluidState()
    }

    override fun randomTick(BlockState state, ServerLevel world, (BlockPos pos, random: RandomSource) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER || !canGrow(world, pos, state)) return

        if (hasSufficientLight(world, pos) && random.nextInt(16) == 0) {
            grow(world, pos, state, 1)
        }
    }

    override fun getStateForPlacement(BlockPlaceContext context): BlockState? {
        val fluidState = context.level.getFluidState(context.clickedPos)
        return this.defaultBlockState()
            .setValue(
            WATERLOGGED,
            fluidState.type === Fluids.WATER
        )
    }

    private fun canGrow(Level worldReader, (BlockPos pos, BlockState state): Boolean {
        return !this.isMaxAge(state) && canSurvive(state, world, pos) && !(!canGrowInto(world, pos.above()) && state.getValue(AGE) >= AGE_AFTER_HARVEST)
    }

    private fun canGrowInto(Level worldReader, (BlockPos pos): Boolean {
        val blockState = world.getBlockState(pos)
        return blockState.isAir || blockState.`is`(CobblemonBlocks.HEARTY_GRAINS)
    }

    override fun updateShape(
        BlockState state,
        Direction direction,
        neighborBlockState state,
        Level worldAccessor,
        (BlockPos pos,
        neighbor(BlockPos pos
    ): BlockState {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world))
        }
        if (!state.canSurvive(world, pos)) return Blocks.AIR.defaultBlockState()
        val doubleBlockHalf = state.getValue(HALF)
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.UP) {
            if (state.getValue(AGE) > AGE_AFTER_HARVEST && !neighborState.`is`(CobblemonBlocks.HEARTY_GRAINS)) return state.setValue(AGE, AGE_AFTER_HARVEST)
        }
        return state
    }

    override fun isValidBonemealTarget(Level levelReader, (BlockPos pos, BlockState state): Boolean {
        val posAndState = getLowerHalf(level, pos, state)
        return if (posAndState == null) false else this.canGrow(
            level,
            posAndState.first,
            posAndState.second
        )
    }

    override fun performBonemeal(ServerLevel level, random: RandomSource, (BlockPos pos, BlockState state) {
        val posAndState = getLowerHalf(level, pos, state)
        if (posAndState != null) {
            grow(level, posAndState.first, posAndState.second, getBonemealAgeIncrease(level))
        }
    }

    fun grow(Level world, (BlockPos pos, BlockState state, increment: Int) {
        if (!canGrow(world, pos, state)) return

        val newAge = (this.getAge(state) + increment).coerceAtMost(this.maxAge)
        world.setBlock(pos, state.setValue(this.ageProperty, newAge), UPDATE_CLIENTS)

        // Set top half depending on age
        if (newAge >= (AGE_AFTER_HARVEST + 1)) {
            val abovePos = pos.above()
            val stateAbove = world.getBlockState(abovePos)
            if (stateAbove.isAir) {
                world.setBlock(
                    abovePos,
                    this.defaultBlockState().setValue(AGE, newAge).setValue(HALF, DoubleBlockHalf.UPPER),
                    UPDATE_CLIENTS
                )
            }
            else if (stateAbove.hasProperty(HALF) && stateAbove.getValue(HALF) == DoubleBlockHalf.UPPER) {
                world.setBlock(
                    abovePos,
                    stateAbove.setValue(AGE, newAge),
                    UPDATE_CLIENTS
                )
            }
        }
    }

    override fun getBonemealAgeIncrease(Level level): Int {
        return Mth.nextInt(level.random, 1, 3)
    }

    override fun canSurvive(BlockState state, Level worldReader, (BlockPos pos): Boolean {
        return when (state.getValue(HALF)) {
            DoubleBlockHalf.LOWER -> {
                val floorBlock = pos.below()
                val floor = world.getBlockState(floorBlock)

                // Check if bottom part is submerged in water
                val fluidState = world.getFluidState(pos)
                if (fluidState.`is`(FluidTags.WATER) && fluidState.isSource)
                    return floor.`is`(CobblemonBlockTags.HEARTY_GRAINS_WATER_PLANTABLE)

                floor.`is`(CobblemonBlockTags.HEARTY_GRAINS_LAND_PLANTABLE)
            }
            DoubleBlockHalf.UPPER -> {
                getLowerHalf(world, pos, state) != null
                // Upper part survives only if the lower part is valid
            }
        }
    }

    private fun getLowerHalf(Level levelReader, (BlockPos pos, BlockState state): Pair<BlockPos, BlockState>? {
        if (isLower(state)) {
            return Pair(pos, state)
        } else {
            val blockPos = pos.below()
            val blockState = level.getBlockState(blockPos)
            return if (isLower(blockState)) Pair(
                blockPos,
                blockState
            ) else null
        }
    }

    private fun isLower(BlockState state): Boolean =
        state.`is`(CobblemonBlocks.HEARTY_GRAINS) && state.getValue(HALF) == DoubleBlockHalf.LOWER

    override fun getSoundType(BlockState state): SoundType? {
        return if (state.getValue(WATERLOGGED)) CobblemonSounds.HEARTY_GRAINS_WATER_SOUNDS else CobblemonSounds.HEARTY_GRAINS_SOUNDS
    }
}