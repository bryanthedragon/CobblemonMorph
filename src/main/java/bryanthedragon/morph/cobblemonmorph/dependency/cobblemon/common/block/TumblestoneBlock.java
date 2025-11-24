/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.DirectionalBlock
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.GrowableStoneBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedChestBlock;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\u0018\u0000 )2\u00020\u00012\u00020\u0002:\u0001)B1\u0012\u0006\u0010!\u001a\u00020 \u0012\u0006\u0010#\u001a\u00020\"\u0012\u0006\u0010$\u001a\u00020\"\u0012\u0006\u0010%\u001a\u00020\"\u0012\b\u0010&\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b'\u0010(J#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018JA\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u001c2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001f\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/block/TumblestoneBlock;", "Lcom/cobblemon/mod/common/block/GrowableStoneBlock;", "Lnet/minecraft/world/level/block/SimpleWaterloggedBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/BlockGetter;", "world", "", "canGrow", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockGetter;)Z", "state", "Lnet/minecraft/world/level/material/FluidState;", "getFluidState", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/material/FluidState;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "", "stage", "height", "xzOffset", "nextStage", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;IIILnet/minecraft/world/level/block/Block;)V", "Companion", "common"})
public final class TumblestoneBlock
extends GrowableStoneBlock
implements SimpleWaterloggedBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final BooleanProperty WATERLOGGED = BooleanProperty.m_61465_((String)"waterlogged");

    public TumblestoneBlock(@NotNull BlockBehaviour.Properties settings, int stage, int height, int xzOffset, @Nullable Block nextStage) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings, stage, height, xzOffset, nextStage);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)DirectionalBlock.f_52588_, (Comparable)Direction.DOWN)).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    public boolean canGrow(@NotNull BlockPos pos, @NotNull BlockGetter world) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        if (this.getStage() == 3) {
            return false;
        }
        Iterator iterator = BlockPos.m_121940_((BlockPos)pos.m_7918_(-1, -1, -1), (BlockPos)pos.m_7918_(1, 1, 1)).iterator();
        BlockPos blockPos2 = null;
        do {
            if (iterator.hasNext()) continue;
            return false;
        } while (!world.m_8055_(blockPos2 = (BlockPos)iterator.next()).m_204336_(CobblemonBlockTags.TUMBLESTONE_HEAT_SOURCE));
        return true;
    }

    @NotNull
    public FluidState m_5888_(@NotNull BlockState state) {
        FluidState fluidState;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)GildedChestBlock.Companion.getWATERLOGGED());
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(GildedChestBlock.WATERLOGGED)");
        if (((Boolean)comparable).booleanValue()) {
            FluidState fluidState2 = Fluids.f_76193_.m_76068_(false);
            fluidState = fluidState2;
            Intrinsics.checkNotNullExpressionValue((Object)fluidState2, (String)"{\n            Fluids.WAT\u2026getStill(false)\n        }");
        } else {
            FluidState fluidState3 = super.m_5888_(state);
            fluidState = fluidState3;
            Intrinsics.checkNotNullExpressionValue((Object)fluidState3, (String)"super.getFluidState(state)");
        }
        return fluidState;
    }

    @Override
    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        super.m_7926_(builder);
        Property[] propertyArray = new Property[]{GildedChestBlock.Companion.getWATERLOGGED()};
        builder.m_61104_(propertyArray);
    }

    @Override
    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        BlockState blockState = super.m_5573_(blockPlaceContext);
        return blockState != null ? (BlockState)blockState.m_61124_((Property)GildedChestBlock.Companion.getWATERLOGGED(), (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_()).m_76152_(), (Object)Fluids.f_76193_))) : null;
    }

    @Override
    @Nullable
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        Comparable comparable = state.m_61143_((Property)GildedChestBlock.Companion.getWATERLOGGED());
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(GildedChestBlock.WATERLOGGED)");
        if (((Boolean)comparable).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        }
        return super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/block/TumblestoneBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "kotlin.jvm.PlatformType", "WATERLOGGED", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getWATERLOGGED", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final BooleanProperty getWATERLOGGED() {
            return WATERLOGGED;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

