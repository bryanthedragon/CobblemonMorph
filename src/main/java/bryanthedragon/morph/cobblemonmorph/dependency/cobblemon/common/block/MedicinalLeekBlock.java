/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 32\u00020\u0001:\u00013B\u000f\u0012\u0006\u00100\u001a\u00020/\u00a2\u0006\u0004\b1\u00102J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0012\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0015\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\u000bH\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\n\u001a\u00020\tH\u0014\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ/\u0010\"\u001a\u00020!2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010 \u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010%\u001a\u00020$H\u0014\u00a2\u0006\u0004\b%\u0010&J5\u0010(\u001a\u00020\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u00102\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010'\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b(\u0010)J/\u0010-\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020*2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020+H\u0016\u00a2\u0006\u0004\b-\u0010.\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/block/MedicinalLeekBlock;", "Lnet/minecraft/world/level/block/CropBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "state", "applyGrowth", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Lnet/minecraft/world/level/LevelReader;", "", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/level/BlockGetter;", "canPlantOnTop", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAgeProperty", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "getGrowthAmount", "(Lnet/minecraft/world/level/Level;)I", "getMaxAge", "()I", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/ItemLike;", "getSeedsItem", "()Lnet/minecraft/world/level/ItemLike;", "isClient", "isFertilizable", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
public final class MedicinalLeekBlock
extends CropBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int MATURE_AGE = 3;
    @NotNull
    private static final IntegerProperty AGE;
    @NotNull
    private static final VoxelShape[] AGE_TO_SHAPE;

    public MedicinalLeekBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
    }

    @NotNull
    protected IntegerProperty m_7959_() {
        return AGE;
    }

    public int m_7419_() {
        return 3;
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{this.m_7959_()};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    protected ItemLike m_6404_() {
        return (ItemLike)CobblemonItems.MEDICINAL_LEEK;
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        VoxelShape voxelShape = AGE_TO_SHAPE[this.m_52305_(state)];
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"AGE_TO_SHAPE[this.getAge(state)]");
        return voxelShape;
    }

    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (this.m_52307_(state) || random.m_188503_(4) != 0) {
            return;
        }
        this.m_52263_((Level)world, pos, state);
    }

    public boolean m_7370_(@Nullable LevelReader world, @Nullable BlockPos pos, @Nullable BlockState state, boolean isClient) {
        return !this.m_52307_(state);
    }

    public void m_52263_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        world.m_7731_(pos, (BlockState)state.m_61124_((Property)this.m_7959_(), (Comparable)Integer.valueOf(RangesKt.coerceAtMost((int)(this.m_52305_(state) + 1), (int)this.m_7419_()))), 2);
    }

    protected int m_7125_(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return 1;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return this.m_6266_(state, (BlockGetter)world, pos);
    }

    protected boolean m_6266_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockPos down = pos.m_7495_();
        BlockState floor = world.m_8055_(down);
        FluidState fluidState = world.m_6425_(down);
        return floor.m_204336_(CobblemonBlockTags.MEDICINAL_LEEK_PLANTABLE) && fluidState.m_76186_() == 8 && fluidState.m_205070_(FluidTags.f_13131_);
    }

    static {
        IntegerProperty integerProperty = BlockStateProperties.f_61407_;
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"AGE_3");
        AGE = integerProperty;
        VoxelShape[] voxelShapeArray = new VoxelShape[]{CropBlock.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)2.0, (double)16.0), CropBlock.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)5.0, (double)16.0), CropBlock.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)8.0, (double)16.0), CropBlock.m_49796_((double)0.0, (double)0.0, (double)0.0, (double)16.0, (double)11.0, (double)16.0)};
        AGE_TO_SHAPE = voxelShapeArray;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R%\u0010\n\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000f\u001a\u00020\u000e8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/MedicinalLeekBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "AGE", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAGE", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "AGE_TO_SHAPE", "[Lnet/minecraft/world/phys/shapes/VoxelShape;", "getAGE_TO_SHAPE", "()[Lnet/minecraft/world/phys/shapes/VoxelShape;", "", "MATURE_AGE", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IntegerProperty getAGE() {
            return AGE;
        }

        @NotNull
        public final VoxelShape[] getAGE_TO_SHAPE() {
            return AGE_TO_SHAPE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

