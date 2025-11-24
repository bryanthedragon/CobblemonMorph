/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.WorldlyContainerHolder
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.WorldlyContainerHolder;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 %2\u00020\u00012\u00020\u0002:\u0001%B\u000f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b#\u0010$J#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0013\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018JG\u0010\u001f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001e\"\n\b\u0000\u0010\u001a*\u0004\u0018\u00010\u00192\b\u0010\u0011\u001a\u0004\u0018\u00010\u001b2\b\u0010\f\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001cH\u0016\u00a2\u0006\u0004\b\u001f\u0010 \u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/block/FossilAnalyzerBlock;", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockBlock;", "Lnet/minecraft/world/WorldlyContainerHolder;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/core/BlockPos;", "pos", "state", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "createMultiBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lnet/minecraft/world/level/LevelAccessor;", "world", "Lnet/minecraft/world/WorldlyContainer;", "getInventory", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/WorldlyContainer;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTicker", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
public final class FossilAnalyzerBlock
extends MultiblockBlock
implements WorldlyContainerHolder {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final BooleanProperty ON = BooleanProperty.m_61465_((String)"on");

    public FossilAnalyzerBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)ON, (Comparable)Boolean.valueOf(false)));
    }

    @Override
    @NotNull
    public FossilMultiblockEntity createMultiBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new FossilAnalyzerBlockEntity(pos, state, new FossilMultiblockBuilder(pos));
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@Nullable Level world, @Nullable BlockState state, @Nullable BlockEntityType<T> type) {
        BlockEntityTicker<FossilMultiblockEntity> blockEntityTicker = FossilMultiblockStructure.Companion.getTICKER();
        return BaseEntityBlock.m_152132_(type, CobblemonBlockEntities.FOSSIL_ANALYZER, (BlockEntityTicker)new BlockEntityTicker(blockEntityTicker){
            final /* synthetic */ BlockEntityTicker<FossilMultiblockEntity> $tmp0;
            {
                this.$tmp0 = $tmp0;
            }

            public final void tick(Level p0, BlockPos p1, BlockState p2, FossilMultiblockEntity p3) {
                this.$tmp0.m_155252_(p0, p1, p2, (BlockEntity)p3);
            }
        });
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        return (BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_());
    }

    @NotNull
    public WorldlyContainer m_5840_(@NotNull BlockState state, @NotNull LevelAccessor world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity");
        FossilAnalyzerBlockEntity analyzerEntity = (FossilAnalyzerBlockEntity)blockEntity;
        return analyzerEntity.getInv();
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{ON};
        builder.m_61104_(propertyArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/block/FossilAnalyzerBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "kotlin.jvm.PlatformType", "ON", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getON", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final BooleanProperty getON() {
            return ON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

