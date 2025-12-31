/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import kotlin.random.Random

@Suppress("OVERRIDE_DEPRECATION")
public class BugwortBlock(settings: Properties) : CropBlock(settings), BonemealableBlock {

    init {
        registerDefaultState(stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(IS_WILD, false))
    }

    override fun getAgeProperty(): IntegerProperty = AGE

    override fun getMaxAge(): Int = MATURE_AGE

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(this.ageProperty)
        builder.add(IS_WILD)
    }

    // TODO after 1.7
//    override fun getBaseSeedId(): ItemLike = CobblemonItems.BUGWORT

    override fun getShape(BlockState state, BlockGetter world, (BlockPos pos, context: CollisionContext): VoxelShape = AGE_TO_SHAPE[this.getAge(state)]

    override fun randomTick(BlockState state, ServerLevel world, (BlockPos pos, random: RandomSource) {
        // This is specified as growing fast like sugar cane
        // They have 15 age stages until they grow upwards, this is an attempt at a chance based but likely event
        if (this.isMaxAge(state) || random.nextInt(4) != 0) {
            return
        }
        this.growCrops(world, pos, state)
    }

    // These 3 are still around for the sake of compatibility, vanilla won't trigger it but some mods might
    // We implement applyGrowth & getGrowthAmount for them
    override fun isValidBonemealTarget(Level worldReader, (BlockPos pos, BlockState state) = state.getValue(BugwortBlock.AGE) < BugwortBlock.MATURE_AGE

    override fun isBonemealSuccess(Level world, random: RandomSource, (BlockPos pos, BlockState state) = true

    override fun performBonemeal(ServerLevel world, random: RandomSource, (BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(AGE, state.getValue(AGE) + 1), 2)
    }

    override fun canSurvive(BlockState state, Level worldReader, (BlockPos pos): Boolean {
        val floor = world.getBlockState(pos.below())
        return (world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && ((this.isWild(state) && (floor.`is`(BlockTags.DIRT) || floor.`is`(Blocks.FARMLAND)) || this.mayPlaceOn(floor, world, pos)))
    }

    fun isWild(BlockState state): Boolean = state.getValue(IS_WILD)
    override fun mayPlaceOn(BlockState state, BlockGetter world, (BlockPos pos): Boolean {
        val blockPos = pos!!.below()
        val down = pos.below()
        val floor = world.getBlockState(down)
        return floor.`is`(BlockTags.DIRT) || floor.`is`(Blocks.FARMLAND)
    }

    // TODO after 1.7
//    override fun useWithoutItem(
//            BlockState state,
//            Level world,
//            (BlockPos pos,
//            player: Player,
//            hit: BlockHitResult
//    ): InteractionResult {
//        if (state != null && world != null && pos != null) {
//            // if bugwort is at max age then revert age to 0 and drop items
//            if (player.getItemInHand(InteractionHand.MAIN_HAND).`is`(Items.BONE_MEAL) && !this.isMaxAge(state)) {
//                return InteractionResult.PASS
//            } else if (this.isMaxAge(state)) {
//                world.playSound(null, pos, CobblemonSounds.BERRY_HARVEST, SoundSource.BLOCKS, 0.4F, 1F)
//                world.setBlock(pos, state.setValue(this.ageProperty, (0).coerceAtMost(this.maxAge)), UPDATE_CLIENTS)
//                Block.popResource(world, pos, ItemStack(CobblemonItems.BUGWORT, Random.nextInt(2, 4)))
//                return InteractionResult.sidedSuccess(world.isClientSide)
//            }
//        }
//        return super.useWithoutItem(state, world, pos, player, hit)
//    }

    override fun getBonemealAgeIncrease(Level world): Int = 1

    final class Companion {

        const val MATURE_AGE = 3
        val AGE: IntegerProperty = BlockStateProperties.AGE_3
        val IS_WILD: BooleanProperty = BooleanProperty.create("is_wild")
        val AGE_TO_SHAPE = arrayOf(
                box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                box(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
                box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                box(0.0, 0.0, 0.0, 16.0, 11.0, 16.0)
        )

    }
}