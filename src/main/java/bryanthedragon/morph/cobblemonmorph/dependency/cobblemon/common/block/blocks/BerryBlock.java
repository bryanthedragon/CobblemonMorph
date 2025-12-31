/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

//import dev.lambdaurora.lambdynlights.util.SodiumDynamicLightHandler.pos
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationOfferEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationResultEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.Mulchable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.ShovelItem
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

public class BerryBlock(private val berryResourceLocation identifier, settings: Properties) : BaseEntityBlock(settings), BonemealableBlock, Mulchable, ShearableBlock {

    private val lookupDirections = setOf(Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH)

    /**
     * Returns the [Berry] behind this block,
     * This will be null if it doesn't exist in the [Berries] registry.
     *
     * @return The [Berry] if existing.
     */
    fun berry(): Berry? = Berries.getByIdentifier(this.berryIdentifier)

    override fun playerWillDestroy(Level world, (BlockPos pos, BlockState state, player: Player): BlockState {
        if (!player.isCreative && state.getValue(AGE) == FRUIT_AGE) {
            val treeEntity = world.getBlockEntity(pos) as BerryBlockEntity
            treeEntity.harvest(world, state, pos, player).forEach { drop -> Block.popResource(world, pos, drop) }
        }
        return super.playerWillDestroy(world, pos, state, player)
    }

    override fun newBlockEntity(BlockPos pos, BlockState state) = BerryBlockEntity(pos, state, berryIdentifier)

    override fun isValidBonemealTarget(Level worldReader, (BlockPos pos, BlockState state) = !this.isMaxAge(state)

    override fun isBonemealSuccess(Level world, random: RandomSource, (BlockPos pos, BlockState state) = !this.isMaxAge(state)

    override fun <T : BlockEntity> getTicker(Level world, blockBlockState state, blockWithEntityType: BlockEntityType<T>): BlockEntityTicker<T>? = createTickerHelper(blockWithEntityType, CobblemonBlockEntities.BERRY, BerryBlockEntity.TICKER)

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(WAS_GENERATED, false)
            .setValue(MULCH, MulchVariant.NONE)
            .setValue(AGE, 0)
            .setValue(IS_ROOTED, false))
    }

    override fun performBonemeal(ServerLevel world, random: RandomSource, (BlockPos pos, BlockState state) {
        growHelper(world, random, pos, state, true)
    }

    //grow, but cooler
    fun growHelper(ServerLevel world, random: RandomSource, (BlockPos pos, BlockState state, boneMealed: Boolean = false) {
        val berry = berry() ?: return
        if (boneMealed && random.nextFloat() > berry.boneMealChance) return
        val curAge = state.getValue(AGE)
        val newAge = curAge + 1
        if (newAge > FRUIT_AGE) return
        val newState = state.setValue(AGE, newAge)
        val treeEntity = world.getBlockEntity(pos) as BerryBlockEntity
        if (curAge == MATURE_AGE) {
            treeEntity.generateGrowthPoints(world, newState, pos, null)
            determineMutation(world, random, pos, newState)
        }

        world.setBlock(pos, newState, UPDATE_CLIENTS)
        convertMulchToEntity(world, newState, pos)
        treeEntity.goToNextStageTimer(FRUIT_AGE - curAge)
        treeEntity.setChanged()
    }

    fun determineMutation(Level world, random: RandomSource, (BlockPos pos, BlockState state) {
        val mutations = hashSetOf<Berry>()
        val treeEntity = world.getBlockEntity(pos) as BerryBlockEntity
        for (direction in this.lookupDirections) {
            val redirectedPos = pos.offset(direction.normal)
            val redirectedState = world.getBlockState(redirectedPos)
            val berryBlock = redirectedState.block as? BerryBlock ?: continue
            val berry = berryBlock.berry() ?: continue
            val mutation = this.berry()?.mutationWith(berry) ?: continue
            mutations += mutation
        }
        this.berry()?.let { berry ->
            CobblemonEvents.BERRY_MUTATION_OFFER.post(BerryMutationOfferEvent(berry, world, state, pos, mutations)) { berryMutationOffer ->
                if (berryMutationOffer.mutations.isNotEmpty()) {
                    var mutateChance = 125
                    if (getMulch(treeEntity) == MulchVariant.SURPRISE) {
                        mutateChance *= 4
                        treeEntity.decrementMulchDuration(world, pos, state)
                    }
                    val mutation = if (random.nextInt(1000) < mutateChance) mutations.random() else null
                    (world.getBlockEntity(pos) as? BerryBlockEntity)?.let { blockEntity ->
                        CobblemonEvents.BERRY_MUTATION_RESULT.post(BerryMutationResultEvent(berry, world, state, pos, berryMutationOffer.mutations, mutation)) { berryMutationResult ->
                            berryMutationResult.pickedMutation?.let { mutation -> blockEntity.mutate(mutation) }
                        }
                    }
                }
            }
        }
    }

    override fun canHaveMulchApplied(
        ServerLevel world,
        (BlockPos pos,
        BlockState state,
        variant: MulchVariant
    ): Boolean {
        val underBlockState = world.getBlockState(pos.below())
        val validSoil = state.getValue(IS_ROOTED) || underBlockState.`is`(CobblemonBlockTags.BERRY_SOIL)
        val treeEntity = world.getBlockEntity(pos) as? BerryBlockEntity ?: return false
        return getMulch(treeEntity) == MulchVariant.NONE && state.getValue(AGE) < FLOWER_AGE && validSoil
    }

    override fun applyMulch(
        ServerLevel world,
        random: RandomSource,
        (BlockPos pos,
        BlockState state,
        variant: MulchVariant
    ) {
        val treeEntity = world.getBlockEntity(pos) as BerryBlockEntity
        treeEntity.setMulch(variant, world, state, pos)
        world.playSound(null, pos, CobblemonSounds.MULCH_PLACE, SoundSource.BLOCKS, 0.6F, 1F)
    }

    override fun useWithoutItem(
        BlockState state,
        Level world,
        (BlockPos pos,
        player: Player,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        val treeEntity = world.getBlockEntity(pos) as BerryBlockEntity
        if (player.getItemInHand(InteractionHand.MAIN_HAND).item is ShovelItem && getMulch(treeEntity) != MulchVariant.NONE) {
            treeEntity.setChanged()
            world.playSound(null, pos, CobblemonSounds.MULCH_REMOVE, SoundSource.BLOCKS, 0.6F, 1F)
            this.spawnDestroyParticles(world, player, pos, state.setValue(AGE, 0))
            return InteractionResult.SUCCESS
        }

        if (player.getItemInHand(InteractionHand.MAIN_HAND).`is`(Items.BONE_MEAL) && !this.isMaxAge(state)) {
            return InteractionResult.PASS
        } else if (this.isMaxAge(state)) {
            return if (harvestBerry(world, state, pos, player)) {
                InteractionResult.sidedSuccess(world.isClientSide)
            } else {
                InteractionResult.PASS
            }
        }
        return super.useWithoutItem(state, world, pos, player, blockHitResult)
    }

    @Deprecated("Deprecated in Java")
    override fun canSurvive(BlockState state, Level worldReader, (BlockPos pos): Boolean {
        val below = world.getBlockState(pos.below())
        return (state.getValue(WAS_GENERATED) && below.`is`(CobblemonBlockTags.BERRY_WILD_SOIL))
                || below.`is`(CobblemonBlockTags.BERRY_SOIL)
                || state.getValue(IS_ROOTED)
    }

    override fun codec(): MapCodec<out BaseEntityBlock> {
        return CODEC
    }

    @Deprecated("Deprecated in Java")
    override fun updateShape(BlockState state, Direction direction, neighborBlockState state, Level worldAccessor, (BlockPos pos, neighbor(BlockPos pos): BlockState {
        return if (state.canSurvive(world, pos)) super.updateShape(state, direction, neighborState, world, pos, neighborPos) else Blocks.AIR.defaultBlockState()
    }

    override fun setPlacedBy(Level world, (BlockPos pos, BlockState state, placer: LivingEntity?, itemStack: ItemStack) {
//        if (!world.isClient) {
//            val blockEntity = world.getBlockEntity(pos) as? BerryBlockEntity ?: return
//            blockEntity.generateGrowthPoints(world, state, pos, placer)
//        }
    }

    override fun createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE)
        builder.add(WAS_GENERATED)
        builder.add(MULCH)
        builder.add(IS_ROOTED)
    }

    override fun getCloneItemStack(Level worldReader, (BlockPos pos, BlockState state): ItemStack {
        val berryItem = this.berry()?.item() ?: return ItemStack.EMPTY
        return ItemStack(berryItem)
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(BlockState state, BlockGetter world, (BlockPos pos, context: CollisionContext): VoxelShape {
        val berry = this.berry() ?: return Shapes.block()
        return when (state.getValue(AGE)) {
            0 -> PLANTED_SHAPE
            1 -> PLANTED_SHAPE
            2 -> berry.sproutShape
            else -> berry.matureShape
        }
    }

    private fun isMaxAge(BlockState state) = state.getValue(AGE) == FRUIT_AGE

    @Deprecated("Deprecated in Java")
    override fun getRenderShape(blockBlockState state) = RenderShape.MODEL

    final class Companion {
        val CODEC: MapCodec<BerryBlock> = RecordCodecBuilder.mapCodec { it.group(
            ResourceLocation.CODEC.fieldOf("berry").forGetter(BerryBlock::berryIdentifier),
            propertiesCodec()
        ).apply(it, ::BerryBlock) }

        const val MATURE_AGE = 3
        const val FLOWER_AGE = 4
        const val FRUIT_AGE = 5

        val AGE: IntegerProperty = IntegerProperty.create("age", 0, FRUIT_AGE)
        val MULCH: EnumProperty<MulchVariant> = EnumProperty.create("mulch", MulchVariant.class)
        val WAS_GENERATED: BooleanProperty = BooleanProperty.create("generated")
        val IS_ROOTED: BooleanProperty = BooleanProperty.create("rooted")
//        val PLANTED_SHAPE = VoxelShapes.union(
//            VoxelShapes.cuboid(0.3125, -0.0625, 0.3125, 0.6875, 0.0, 0.6875),
//            VoxelShapes.cuboid(0.375, 0.0, 0.375, 0.625, 0.0625, 0.625)
//        )
        val PLANTED_SHAPE = Shapes.box(0.0, -0.1, 0.0, 1.0, 0.25, 1.0)


        val STANDARD_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))
        val STANDARD_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val SHORT_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 12.0, 16.0))
        val SHORT_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))

        val VOLCANO_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0))
        val VOLCANO_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))

        val NEST_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0))
        val NEST_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 20.0, 16.0))

        val FRILL_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 8.0, 16.0))
        val FRILL_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 14.0, 16.0))

        val BLOCK_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 17.0, 16.0))
        val BLOCK_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val PYRAMID_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 17.0, 16.0))
        val PYRAMID_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val TAIL_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))
        val TAIL_MATURE = listOf(AABB(0.0, 1.0, 0.0, 16.0, 24.0, 16.0))

        val SWORD_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 5.0, 16.0))
        val SWORD_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val PLATFORM_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 7.0, 16.0))
        val PLATFORM_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 22.0, 16.0))

        val STAND_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 13.0, 16.0))
        val STAND_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val CONE_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))
        val CONE_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 23.0, 16.0))

        val SQUAT_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 12.0, 16.0))
        val SQUAT_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 22.0, 16.0))

        val LANTERN_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 20.0, 16.0))
        val LANTERN_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val BOX_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))
        val BOX_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))

        val BLOSSOM_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 4.0, 16.0))
        val BLOSSOM_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0))

        val LILYPAD_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 11.0, 16.0))
        val LILYPAD_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))

        val TALL_SPROUT = listOf(AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0))
        val TALL_MATURE = listOf(AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0))


        fun getMulch(entity: BerryBlockEntity) = entity.mulchVariant

        fun convertMulchToEntity(ServerLevel world, BlockState state, (BlockPos pos) {
            val entity = world.getBlockEntity(pos) as? BerryBlockEntity ?: return
            if (state.getValue(MULCH) != MulchVariant.NONE && state.getValue(MULCH) != entity.mulchVariant) {
                entity.mulchVariant = state.getValue(MULCH)
                world.setBlockAndUpdate(pos, state.setValue(MULCH, MulchVariant.NONE))
            }
        }
    }

    override fun attemptShear(Level world, BlockState state, (BlockPos pos, successCallback: () -> Unit): Boolean {
        return if (this.isMaxAge(state)) {
            harvestBerry(world, state, pos)
        } else {
            false
        }
    }

    private fun harvestBerry(Level world, BlockState state, (BlockPos pos, player: Player? = null): Boolean {
        val blockEntity = world.getBlockEntity(pos) as? BerryBlockEntity ?: return false
        blockEntity.harvest(world, state, pos, player).forEach { drop ->
            Block.popResource(world, pos, drop)
        }

        val sound = if (player != null) CobblemonSounds.BERRY_HARVEST else SoundEvents.SHEEP_SHEAR
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1F, 1F)

        return true
    }
}