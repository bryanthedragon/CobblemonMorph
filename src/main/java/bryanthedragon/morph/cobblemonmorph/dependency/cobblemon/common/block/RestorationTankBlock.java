/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.WorldlyContainerHolder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 T2\u00020\u00012\u00020\u0002:\u0002TUB\u000f\u0012\u0006\u0010Q\u001a\u00020P\u00a2\u0006\u0004\bR\u0010SJ#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ7\u0010\u0012\u001a\u00020\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0017\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0015\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u001d\u0010\u001a\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u001a\u0010\u001bJ+\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\n\u001a\u00020\u00052\b\u0010\f\u001a\u0004\u0018\u00010\u001c2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0017\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010\"\u001a\u00020!2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020 2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\"\u0010#J5\u0010'\u001a\u00020&2\u0006\u0010\n\u001a\u00020\u00052\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010%\u001a\u0004\u0018\u00010$H\u0016\u00a2\u0006\u0004\b'\u0010(J\u0019\u0010+\u001a\u0004\u0018\u00010\u00052\u0006\u0010*\u001a\u00020)H\u0016\u00a2\u0006\u0004\b+\u0010,J\u001d\u0010-\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b-\u0010\u001bJ\u0019\u0010.\u001a\u00020\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0017\u00a2\u0006\u0004\b.\u0010/J\u0017\u00100\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b0\u0010/JC\u00104\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\r2\b\u00101\u001a\u0004\u0018\u00010\u00042\b\u00102\u001a\u0004\u0018\u00010\r2\u0006\u00103\u001a\u00020\u0011H\u0017\u00a2\u0006\u0004\b4\u00105J1\u00108\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\b\u00107\u001a\u0004\u0018\u000106H\u0016\u00a2\u0006\u0004\b8\u00109J;\u0010>\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\b\u0010;\u001a\u0004\u0018\u00010:2\b\u0010=\u001a\u0004\u0018\u00010<H\u0016\u00a2\u0006\u0004\b>\u0010?J9\u0010B\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u001c2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010@\u001a\u00020\u00052\u0006\u0010A\u001a\u00020\u0011H\u0017\u00a2\u0006\u0004\bB\u0010CJ?\u0010I\u001a\u00020H2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u00107\u001a\u0002062\u0006\u0010E\u001a\u00020D2\u0006\u0010G\u001a\u00020FH\u0017\u00a2\u0006\u0004\bI\u0010JJ7\u0010N\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\f\u001a\u0004\u0018\u00010K2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010M\u001a\u0004\u0018\u00010LH\u0017\u00a2\u0006\u0004\bN\u0010O\u00a8\u0006V"}, d2={"Lcom/cobblemon/mod/common/block/RestorationTankBlock;", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockBlock;", "Lnet/minecraft/world/WorldlyContainerHolder;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "type", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "createMultiBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "getBasePosition", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/Level;", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "Lnet/minecraft/world/level/LevelAccessor;", "Lnet/minecraft/world/WorldlyContainer;", "getInventory", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/WorldlyContainer;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "getPositionOfOtherPart", "hasComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "isBase", "sourceBlock", "sourcePos", "notify", "neighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Block;Lnet/minecraft/core/BlockPos;Z)V", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "newState", "moved", "onStateReplaced", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "scheduledTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "TankPart", "common"})
public final class RestorationTankBlock
extends MultiblockBlock
implements WorldlyContainerHolder {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final EnumProperty<TankPart> PART = EnumProperty.m_61587_((String)"part", TankPart.class);
    private static final BooleanProperty TRIGGERED = BlockStateProperties.f_61360_;
    private static final BooleanProperty ON = BooleanProperty.m_61465_((String)"on");

    public RestorationTankBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)PART, (Comparable)((Object)TankPart.BOTTOM))).m_61124_((Property)TRIGGERED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)ON, (Comparable)Boolean.valueOf(false)));
    }

    @NotNull
    public final BlockPos getPositionOfOtherPart(@NotNull BlockState state, @NotNull BlockPos pos) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (state.m_61143_((Property)PART) == TankPart.BOTTOM) {
            BlockPos blockPos3 = pos.m_7494_();
            blockPos2 = blockPos3;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"{\n            pos.up()\n        }");
        } else {
            BlockPos blockPos4 = pos.m_7495_();
            blockPos2 = blockPos4;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"{\n            pos.down()\n        }");
        }
        return blockPos2;
    }

    @NotNull
    public final BlockPos getBasePosition(@NotNull BlockState state, @NotNull BlockPos pos) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (this.isBase(state)) {
            blockPos2 = pos;
        } else {
            BlockPos blockPos3 = pos.m_7495_();
            blockPos2 = blockPos3;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"{\n            pos.down()\n        }");
        }
        return blockPos2;
    }

    private final boolean isBase(BlockState state) {
        return state.m_61143_((Property)PART) == TankPart.BOTTOM;
    }

    @Override
    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
        BlockState otherPart;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super.m_5707_(world, pos, state, player);
        if (!world.f_46443_ && (otherPart = world.m_8055_(this.getPositionOfOtherPart(state, pos))).m_60734_() instanceof RestorationTankBlock) {
            world.m_7731_(this.getPositionOfOtherPart(state, pos), Blocks.f_50016_.m_49966_(), 3);
            world.m_5898_(player, 2001, this.getPositionOfOtherPart(state, pos), BaseEntityBlock.m_49956_((BlockState)otherPart));
        }
    }

    @Override
    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @Nullable ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        BlockPos blockPos2 = pos.m_7494_();
        Object object = state.m_61124_((Property)PART, (Comparable)((Object)TankPart.TOP));
        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
        world.m_7731_(blockPos2, (BlockState)object, 3);
        world.m_6289_(pos, Blocks.f_50016_);
        state.m_60701_((LevelAccessor)world, pos, 3);
        super.m_6402_(world, pos, state, placer, itemStack);
    }

    @Override
    @Deprecated(message="Deprecated in Java")
    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        BlockPos tankBottomPos;
        BlockState tankBottomState;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        if (state.m_61143_((Property)PART) == TankPart.TOP && (tankBottomState = world.m_8055_(tankBottomPos = pos.m_7495_())).m_60734_().equals((Object)CobblemonBlocks.RESTORATION_TANK.m_7374_()) && tankBottomState.m_61143_((Property)PART) == TankPart.BOTTOM) {
            Intrinsics.checkNotNullExpressionValue((Object)tankBottomState, (String)"tankBottomState");
            Intrinsics.checkNotNullExpressionValue((Object)tankBottomPos, (String)"tankBottomPos");
            return this.m_6227_(tankBottomState, world, tankBottomPos, player, hand, hit);
        }
        return super.m_6227_(state, world, pos, player, hand, hit);
    }

    @Override
    @NotNull
    public FossilMultiblockEntity createMultiBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return state.m_61143_((Property)PART) == TankPart.BOTTOM ? (FossilMultiblockEntity)new RestorationTankBlockEntity(pos, state, new FossilMultiblockBuilder(pos)) : new FossilMultiblockEntity(pos, state, new FossilMultiblockBuilder(pos), null, 8, null);
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        BlockPos abovePosition = blockPlaceContext.m_8083_().m_7494_();
        Level world = blockPlaceContext.m_43725_();
        if (world.m_8055_(abovePosition).m_60629_(blockPlaceContext) && !world.m_151570_(abovePosition)) {
            return (BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_())).m_61124_((Property)PART, (Comparable)((Object)TankPart.BOTTOM));
        }
        return null;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockPos blockPos2 = pos.m_7495_();
        BlockState blockState = world.m_8055_(blockPos2);
        return state.m_61143_((Property)PART) == TankPart.BOTTOM ? true : blockState.m_60713_((Block)this);
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{PART};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{TRIGGERED};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{ON};
        builder.m_61104_(propertyArray);
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7278_(@Nullable BlockState state) {
        return true;
    }

    @Deprecated(message="Deprecated in Java")
    public int m_6782_(@NotNull BlockState state, @Nullable Level world, @Nullable BlockPos pos) {
        MultiblockStructure multiBlockEntity;
        MultiblockEntity tankEntity;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (world == null || pos == null) {
            return 0;
        }
        BlockEntity blockEntity = world.m_7702_(pos);
        MultiblockEntity multiblockEntity = tankEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
        MultiblockStructure multiblockStructure = multiBlockEntity = multiblockEntity != null ? multiblockEntity.getMultiblockStructure() : null;
        if (multiBlockEntity != null) {
            return multiBlockEntity.getComparatorOutput(state, world, pos);
        }
        return 0;
    }

    @Deprecated(message="Deprecated in Java")
    public void m_6810_(@NotNull BlockState state, @NotNull Level world, @Nullable BlockPos pos, @NotNull BlockState newState, boolean moved) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)newState, (String)"newState");
        if (!state.m_60713_(newState.m_60734_())) {
            super.m_6810_(state, world, pos, newState, moved);
        }
    }

    @Deprecated(message="Deprecated in Java")
    public void m_6861_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @Nullable Block sourceBlock, @Nullable BlockPos sourcePos, boolean notify) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        boolean bl = world.m_276867_(pos) || world.m_276867_(pos.m_7494_());
        Boolean bl2 = (Boolean)state.m_61143_((Property)TRIGGERED);
        if (bl && !bl2.booleanValue()) {
            world.m_186460_(pos, (Block)this, 4);
            Object object = state.m_61124_((Property)TRIGGERED, (Comparable)Boolean.valueOf(true));
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            world.m_7731_(pos, (BlockState)object, 4);
        } else if (!bl) {
            Intrinsics.checkNotNullExpressionValue((Object)bl2, (String)"bl2");
            if (bl2.booleanValue()) {
                Object object = state.m_61124_((Property)TRIGGERED, (Comparable)Boolean.valueOf(false));
                Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
                world.m_7731_(pos, (BlockState)object, 4);
            }
        }
    }

    @Deprecated(message="Deprecated in Java")
    public void m_213897_(@Nullable BlockState state, @Nullable ServerLevel world, @Nullable BlockPos pos, @Nullable RandomSource random) {
        block1: {
            if (world == null || pos == null) {
                return;
            }
            BlockEntity blockEntity = world.m_7702_(pos);
            MultiblockEntity tankEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
            Object object = tankEntity;
            if (object == null || (object = object.getMultiblockStructure()) == null) break block1;
            object.onTriggerEvent(state, world, pos, random);
        }
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable CollisionContext context) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (state.m_61143_((Property)PART) == TankPart.TOP) {
            VoxelShape shape = Shapes.m_83048_((double)0.0625, (double)0.0, (double)0.0625, (double)0.9375, (double)0.8125, (double)0.9375);
            VoxelShape voxelShape2 = shape = Shapes.m_83110_((VoxelShape)shape, (VoxelShape)Shapes.m_83048_((double)0.0, (double)0.8125, (double)0.0, (double)1.0, (double)1.0, (double)1.0));
            voxelShape = voxelShape2;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"{\n            var shape \u2026          shape\n        }");
        } else {
            VoxelShape shape = Shapes.m_83048_((double)0.0625, (double)0.1875, (double)0.0625, (double)0.9375, (double)1.0, (double)0.9375);
            VoxelShape voxelShape3 = shape = Shapes.m_83110_((VoxelShape)shape, (VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.0, (double)1.0, (double)0.1875, (double)1.0));
            voxelShape = voxelShape3;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"{\n            var shape \u2026          shape\n        }");
        }
        return voxelShape;
    }

    @NotNull
    public WorldlyContainer m_5840_(@NotNull BlockState state, @NotNull LevelAccessor world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockEntity tankEntity = state.m_61143_((Property)PART) == TankPart.TOP ? world.m_7702_(pos.m_7495_()) : world.m_7702_(pos);
        return tankEntity != null && tankEntity instanceof RestorationTankBlockEntity ? (WorldlyContainer)((RestorationTankBlockEntity)tankEntity).getInv() : (WorldlyContainer)new Companion.DummyInventory();
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7357_(@Nullable BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable PathComputationType type) {
        return false;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\u0012B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R;\u0010\n\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\t0\t \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\t0\t\u0018\u00010\b0\b8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001f\u0010\u000e\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0005\u001a\u0004\b\u000f\u0010\u0007\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/RestorationTankBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "kotlin.jvm.PlatformType", "ON", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getON", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "Lcom/cobblemon/mod/common/block/RestorationTankBlock$TankPart;", "PART", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getPART", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "TRIGGERED", "getTRIGGERED", "<init>", "()V", "DummyInventory", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EnumProperty<TankPart> getPART() {
            return PART;
        }

        public final BooleanProperty getTRIGGERED() {
            return TRIGGERED;
        }

        public final BooleanProperty getON() {
            return ON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ)\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\f\u0010\u000bJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/RestorationTankBlock$Companion$DummyInventory;", "Lnet/minecraft/world/SimpleContainer;", "Lnet/minecraft/world/WorldlyContainer;", "", "slot", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/core/Direction;", "dir", "", "canExtract", "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", "canInsert", "side", "", "getAvailableSlots", "(Lnet/minecraft/core/Direction;)[I", "<init>", "()V", "common"})
        public static final class DummyInventory
        extends SimpleContainer
        implements WorldlyContainer {
            public DummyInventory() {
                super(0);
            }

            @NotNull
            public int[] m_7071_(@NotNull Direction side) {
                Intrinsics.checkNotNullParameter((Object)side, (String)"side");
                return new int[0];
            }

            public boolean m_7155_(int slot, @NotNull ItemStack stack, @Nullable Direction dir) {
                Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
                return false;
            }

            public boolean m_7157_(int slot, @NotNull ItemStack stack, @NotNull Direction dir) {
                Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
                Intrinsics.checkNotNullParameter((Object)dir, (String)"dir");
                return false;
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0011\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007j\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/block/RestorationTankBlock$TankPart;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "label", "Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "TOP", "BOTTOM", "common"})
    public static final class TankPart
    extends Enum<TankPart>
    implements StringRepresentable {
        @NotNull
        private final String label;
        public static final /* enum */ TankPart TOP = new TankPart("top");
        public static final /* enum */ TankPart BOTTOM = new TankPart("bottom");
        private static final /* synthetic */ TankPart[] $VALUES;

        private TankPart(String label) {
            this.label = label;
        }

        @NotNull
        public String m_7912_() {
            return this.label;
        }

        public static TankPart[] values() {
            return (TankPart[])$VALUES.clone();
        }

        public static TankPart valueOf(String value2) {
            return Enum.valueOf(TankPart.class, value2);
        }

        static {
            $VALUES = tankPartArray = new TankPart[]{TankPart.TOP, TankPart.BOTTOM};
        }
    }
}

