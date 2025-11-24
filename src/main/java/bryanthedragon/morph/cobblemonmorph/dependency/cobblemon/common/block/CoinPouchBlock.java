/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 (2\u00020\u0001:\u0001(B\u0017\u0012\u0006\u0010%\u001a\u00020$\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b&\u0010'J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ?\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00102\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0019\u0010\u0016\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J?\u0010\u001d\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u001b2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\fH\u0017\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/block/CoinPouchBlock;", "Lnet/minecraft/world/level/block/HorizontalDirectionalBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "ctx", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "", "small", "Z", "getSmall", "()Z", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;Z)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nCoinPouchBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CoinPouchBlock.kt\ncom/cobblemon/mod/common/block/CoinPouchBlock\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,74:1\n13579#2,2:75\n*S KotlinDebug\n*F\n+ 1 CoinPouchBlock.kt\ncom/cobblemon/mod/common/block/CoinPouchBlock\n*L\n43#1:75,2\n*E\n"})
public final class CoinPouchBlock
extends HorizontalDirectionalBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean small;
    @NotNull
    private static final BooleanProperty NATURAL;

    public CoinPouchBlock(@NotNull BlockBehaviour.Properties settings, boolean small) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.small = small;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)NATURAL, (Comparable)Boolean.valueOf(false))).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH));
    }

    public final boolean getSmall() {
        return this.small;
    }

    public VoxelShape m_5940_(@Nullable BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable CollisionContext context) {
        return !this.small ? super.m_5940_(state, world, pos, context) : Shapes.m_83048_((double)0.3125, (double)0.0, (double)0.3125, (double)0.6875, (double)0.3125, (double)0.6875);
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        BlockState blockState = null;
        blockState = this.m_49966_();
        Level worldView = ctx.m_43725_();
        BlockPos blockPos2 = ctx.m_8083_();
        Direction[] directionArray = ctx.m_6232_();
        Intrinsics.checkNotNullExpressionValue((Object)directionArray, (String)"ctx.placementDirections");
        Object[] $this$forEach$iv = directionArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Direction direction = (Direction)element$iv;
            boolean bl = false;
            if (!direction.m_122434_().m_122479_()) continue;
            Object object = blockState.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)direction.m_122424_());
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            blockState = (BlockState)object;
            if (!blockState.m_60710_((LevelReader)worldView, blockPos2)) continue;
            return blockState;
        }
        return null;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        if (direction == state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_) && !state.m_60710_((LevelReader)world, pos)) {
            BlockState blockState2 = Blocks.f_50016_.m_49966_();
            blockState = blockState2;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"AIR.defaultState");
        } else {
            BlockState blockState3 = super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
            blockState = blockState3;
            Intrinsics.checkNotNullExpressionValue((Object)blockState3, (String)"super.getStateForNeighbo\u2026 world, pos, neighborPos)");
        }
        return blockState;
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_, NATURAL};
        builder.m_61104_(propertyArray);
    }

    static {
        BooleanProperty booleanProperty = BooleanProperty.m_61465_((String)"natural");
        Intrinsics.checkNotNullExpressionValue((Object)booleanProperty, (String)"of(\"natural\")");
        NATURAL = booleanProperty;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/block/CoinPouchBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "NATURAL", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getNATURAL", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BooleanProperty getNATURAL() {
            return NATURAL;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

