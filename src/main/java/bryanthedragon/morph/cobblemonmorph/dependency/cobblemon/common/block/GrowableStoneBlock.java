/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.DirectionalBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000 B2\u00020\u0001:\u0001BB1\u0012\u0006\u0010>\u001a\u00020=\u0012\u0006\u00109\u001a\u00020.\u0012\u0006\u0010/\u001a\u00020.\u0012\u0006\u0010?\u001a\u00020.\u0012\b\u00103\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b@\u0010AJ#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH&\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J/\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001a\u001a\u00020\u0019H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJA\u0010\"\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020 2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010!\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\"\u0010#J\u0019\u0010$\u001a\u00020\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b$\u0010%J/\u0010)\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020&2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b)\u0010*R\u0014\u0010+\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010,R\u0014\u0010-\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010,R\u0017\u0010/\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u0019\u00103\u001a\u0004\u0018\u00010\u00038\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0014\u00107\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010,R\u0014\u00108\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b8\u0010,R\u0017\u00109\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\b9\u00100\u001a\u0004\b:\u00102R\u0014\u0010;\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010,R\u0014\u0010<\u001a\u00020\u00168\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010,\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/block/GrowableStoneBlock;", "Lnet/minecraft/world/level/block/DirectionalBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/BlockGetter;", "world", "", "canGrow", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockGetter;)Z", "state", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "ctx", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "hasRandomTicks", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "downShape", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "eastShape", "", "height", "I", "getHeight", "()I", "nextStage", "Lnet/minecraft/world/level/block/Block;", "getNextStage", "()Lnet/minecraft/world/level/block/Block;", "northShape", "southShape", "stage", "getStage", "upShape", "westShape", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "xzOffset", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;IIILnet/minecraft/world/level/block/Block;)V", "Companion", "common"})
public abstract class GrowableStoneBlock
extends DirectionalBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int stage;
    private final int height;
    @Nullable
    private final Block nextStage;
    @NotNull
    private final VoxelShape upShape;
    @NotNull
    private final VoxelShape downShape;
    @NotNull
    private final VoxelShape northShape;
    @NotNull
    private final VoxelShape southShape;
    @NotNull
    private final VoxelShape eastShape;
    @NotNull
    private final VoxelShape westShape;
    public static final int STAGE_0 = 0;
    public static final int STAGE_1 = 1;
    public static final int STAGE_2 = 2;
    public static final int STAGE_3 = 3;
    public static final int MAX_STAGE = 3;
    public static final int MIN_STAGE = 0;

    public GrowableStoneBlock(@NotNull BlockBehaviour.Properties settings, int stage, int height, int xzOffset, @Nullable Block nextStage) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.stage = stage;
        this.height = height;
        this.nextStage = nextStage;
        VoxelShape voxelShape = Block.m_49796_((double)xzOffset, (double)0.0, (double)xzOffset, (double)(16 - xzOffset), (double)this.height, (double)(16 - xzOffset));
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"createCuboidShape(\n     \u20266 - xzOffset).toDouble())");
        this.upShape = voxelShape;
        VoxelShape voxelShape2 = DirectionalBlock.m_49796_((double)xzOffset, (double)(16 - this.height), (double)xzOffset, (double)(16 - xzOffset), (double)16.0, (double)(16 - xzOffset));
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"createCuboidShape(\n     \u20266 - xzOffset).toDouble())");
        this.downShape = voxelShape2;
        VoxelShape voxelShape3 = DirectionalBlock.m_49796_((double)xzOffset, (double)xzOffset, (double)(16 - this.height), (double)(16 - xzOffset), (double)(16 - xzOffset), (double)16.0);
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"createCuboidShape(\n     \u2026toDouble(),\n        16.0)");
        this.northShape = voxelShape3;
        VoxelShape voxelShape4 = DirectionalBlock.m_49796_((double)xzOffset, (double)xzOffset, (double)0.0, (double)(16 - xzOffset), (double)(16 - xzOffset), (double)this.height);
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape4, (String)"createCuboidShape(\n     \u2026       height.toDouble())");
        this.southShape = voxelShape4;
        VoxelShape voxelShape5 = DirectionalBlock.m_49796_((double)0.0, (double)xzOffset, (double)xzOffset, (double)this.height, (double)(16 - xzOffset), (double)(16 - xzOffset));
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape5, (String)"createCuboidShape(\n     \u20266 - xzOffset).toDouble())");
        this.eastShape = voxelShape5;
        VoxelShape voxelShape6 = DirectionalBlock.m_49796_((double)(16 - this.height), (double)xzOffset, (double)xzOffset, (double)16.0, (double)(16 - xzOffset), (double)(16 - xzOffset));
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape6, (String)"createCuboidShape(\n     \u20266 - xzOffset).toDouble())");
        this.westShape = voxelShape6;
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)DirectionalBlock.f_52588_, (Comparable)Direction.DOWN));
    }

    public final int getStage() {
        return this.stage;
    }

    public final int getHeight() {
        return this.height;
    }

    @Nullable
    public final Block getNextStage() {
        return this.nextStage;
    }

    public abstract boolean canGrow(@NotNull BlockPos var1, @NotNull BlockGetter var2);

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{DirectionalBlock.f_52588_};
        builder.m_61104_(propertyArray);
    }

    public boolean m_6724_(@Nullable BlockState state) {
        return this.stage < 3;
    }

    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Block block;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (world.f_46441_.m_188503_(5) == 0 && this.canGrow(pos, (BlockGetter)world) && (block = this.nextStage) != null) {
            Object object = block.m_49966_().m_61124_((Property)DirectionalBlock.f_52588_, state.m_61143_((Property)DirectionalBlock.f_52588_));
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            BlockState newState = (BlockState)object;
            world.m_46597_(pos, newState);
        }
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        BlockState blockState = this.m_49966_();
        Level worldView = ctx.m_43725_();
        BlockPos blockPos2 = ctx.m_8083_();
        Object object = blockState.m_61124_((Property)DirectionalBlock.f_52588_, (Comparable)ctx.m_43719_());
        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
        blockState = (BlockState)object;
        if (blockState.m_60710_((LevelReader)worldView, blockPos2)) {
            return blockState;
        }
        return null;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Comparable comparable = state.m_61143_((Property)DirectionalBlock.f_52588_);
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type net.minecraft.util.math.Direction");
        Direction direction = (Direction)comparable;
        BlockState blockState = world.m_8055_(pos.m_121945_(direction.m_122424_()));
        return blockState.m_60783_((BlockGetter)world, pos, direction);
    }

    @Nullable
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        return direction == ((Direction)state.m_61143_((Property)DirectionalBlock.f_52588_)).m_122424_() && !state.m_60710_((LevelReader)world, pos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Comparable comparable = state.m_61143_((Property)DirectionalBlock.f_52588_);
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type net.minecraft.util.math.Direction");
        Direction direction = (Direction)comparable;
        return switch (WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1 -> this.northShape;
            case 2 -> this.southShape;
            case 3 -> this.eastShape;
            case 4 -> this.westShape;
            case 5 -> this.downShape;
            case 6 -> this.upShape;
            default -> this.upShape;
        };
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/block/GrowableStoneBlock$Companion;", "", "", "MAX_STAGE", "I", "MIN_STAGE", "STAGE_0", "STAGE_1", "STAGE_2", "STAGE_3", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.NORTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.DOWN.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.UP.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

