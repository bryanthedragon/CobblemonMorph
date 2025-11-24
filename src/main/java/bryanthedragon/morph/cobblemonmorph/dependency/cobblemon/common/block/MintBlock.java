/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.MintLeafItem;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 12\u00020\u00012\u00020\u0002:\u000212B\u0017\u0012\u0006\u0010+\u001a\u00020*\u0012\u0006\u0010.\u001a\u00020-\u00a2\u0006\u0004\b/\u00100J#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ'\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J/\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0013J'\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\nH\u0014\u00a2\u0006\u0004\b\u0018\u0010\u0019J/\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010!\u001a\u00020 H\u0014\u00a2\u0006\u0004\b!\u0010\"J\u0015\u0010#\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0004\b#\u0010$J/\u0010(\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020%2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b(\u0010)R\u0014\u0010+\u001a\u00020*8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010,\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/block/MintBlock;", "Lnet/minecraft/world/level/block/CropBlock;", "Lnet/minecraft/world/level/block/BonemealableBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "state", "applyGrowth", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "", "useRandomGrowthAmount", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "", "getGrowthAmount", "(Lnet/minecraft/world/level/Level;)I", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/ItemLike;", "getSeedsItem", "()Lnet/minecraft/world/level/ItemLike;", "isWild", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "mintType", "Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lcom/cobblemon/mod/common/block/MintBlock$MintType;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "MintType", "common"})
public final class MintBlock
extends CropBlock
implements BonemealableBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MintType mintType;
    @NotNull
    private static final IntegerProperty AGE;
    public static final int MATURE_AGE = 7;
    @NotNull
    private static final BooleanProperty IS_WILD;
    private static final VoxelShape AGE_0_SHAPE;
    private static final VoxelShape AGE_1_TO_2_SHAPE;
    private static final VoxelShape AGE_3_SHAPE;
    private static final VoxelShape AGE_4_SHAPE;
    private static final VoxelShape AGE_5_SHAPE;
    private static final VoxelShape AGE_6_SHAPE;
    private static final VoxelShape AGE_7_SHAPE;
    @NotNull
    private static final VoxelShape[] AGE_TO_SHAPE;

    public MintBlock(@NotNull MintType mintType, @NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)((Object)mintType), (String)"mintType");
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.mintType = mintType;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)AGE, (Comparable)Integer.valueOf(0))).m_61124_((Property)IS_WILD, (Comparable)Boolean.valueOf(false)));
    }

    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (world.m_45524_(pos, 0) < 9 || this.m_52307_(state) || random.m_188503_(8) != 0) {
            return;
        }
        this.applyGrowth((Level)world, pos, state, false);
    }

    public void m_52263_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        this.applyGrowth(world, pos, state, true);
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{AGE};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{IS_WILD};
        builder.m_61104_(propertyArray);
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockState floor = world.m_8055_(pos.m_7495_());
        return (world.m_45524_(pos, 0) >= 8 || world.m_45527_(pos)) && (this.isWild(state) && floor.m_204336_(BlockTags.f_144274_) || this.m_6266_(floor, (BlockGetter)world, pos));
    }

    @NotNull
    protected ItemLike m_6404_() {
        return (ItemLike)this.mintType.getSeed();
    }

    protected int m_7125_(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return 1;
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

    public final boolean isWild(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)IS_WILD);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(IS_WILD)");
        return (Boolean)comparable;
    }

    private final void applyGrowth(Level world, BlockPos pos, BlockState state, boolean useRandomGrowthAmount) {
        int growthAmount = useRandomGrowthAmount ? this.m_7125_(world) : 1;
        int newAge = RangesKt.coerceAtMost((int)(this.m_52305_(state) + growthAmount), (int)7);
        world.m_7731_(pos, (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(newAge)), 2);
    }

    static {
        IntegerProperty integerProperty = CropBlock.f_52244_;
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"AGE");
        AGE = integerProperty;
        BooleanProperty booleanProperty = BooleanProperty.m_61465_((String)"is_wild");
        Intrinsics.checkNotNullExpressionValue((Object)booleanProperty, (String)"of(\"is_wild\")");
        IS_WILD = booleanProperty;
        AGE_0_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.1, (double)1.0);
        AGE_1_TO_2_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.2, (double)1.0);
        AGE_3_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.3, (double)1.0);
        AGE_4_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.4, (double)1.0);
        AGE_5_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.5, (double)1.0);
        AGE_6_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.6, (double)1.0);
        AGE_7_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.7, (double)1.0);
        VoxelShape[] voxelShapeArray = new VoxelShape[]{AGE_0_SHAPE, AGE_1_TO_2_SHAPE, AGE_1_TO_2_SHAPE, AGE_3_SHAPE, AGE_4_SHAPE, AGE_5_SHAPE, AGE_6_SHAPE, AGE_7_SHAPE};
        AGE_TO_SHAPE = voxelShapeArray;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\t\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR\u001c\u0010\f\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR\u001c\u0010\r\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR\u001c\u0010\u000e\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\nR\u001c\u0010\u000f\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR\u001c\u0010\u0010\u001a\n \b*\u0004\u0018\u00010\u00070\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR%\u0010\u0012\u001a\u0010\u0012\f\u0012\n \b*\u0004\u0018\u00010\u00070\u00070\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001c\u001a\u00020\u001b8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/block/MintBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "AGE", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAGE", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "AGE_0_SHAPE", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "AGE_1_TO_2_SHAPE", "AGE_3_SHAPE", "AGE_4_SHAPE", "AGE_5_SHAPE", "AGE_6_SHAPE", "AGE_7_SHAPE", "", "AGE_TO_SHAPE", "[Lnet/minecraft/world/phys/shapes/VoxelShape;", "getAGE_TO_SHAPE", "()[Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "IS_WILD", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getIS_WILD", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "", "MATURE_AGE", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IntegerProperty getAGE() {
            return AGE;
        }

        @NotNull
        public final BooleanProperty getIS_WILD() {
            return IS_WILD;
        }

        @NotNull
        public final VoxelShape[] getAGE_TO_SHAPE() {
            return AGE_TO_SHAPE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/MintBlock$MintType;", "", "Lcom/cobblemon/mod/common/block/MintBlock;", "getCropBlock", "()Lcom/cobblemon/mod/common/block/MintBlock;", "Lcom/cobblemon/mod/common/item/MintLeafItem;", "getLeaf", "()Lcom/cobblemon/mod/common/item/MintLeafItem;", "Lnet/minecraft/world/item/Item;", "getSeed", "()Lnet/minecraft/world/item/Item;", "<init>", "(Ljava/lang/String;I)V", "RED", "BLUE", "CYAN", "PINK", "GREEN", "WHITE", "common"})
    public static final class MintType
    extends Enum<MintType> {
        public static final /* enum */ MintType RED = new MintType();
        public static final /* enum */ MintType BLUE = new MintType();
        public static final /* enum */ MintType CYAN = new MintType();
        public static final /* enum */ MintType PINK = new MintType();
        public static final /* enum */ MintType GREEN = new MintType();
        public static final /* enum */ MintType WHITE = new MintType();
        private static final /* synthetic */ MintType[] $VALUES;

        @NotNull
        public final Item getSeed() {
            return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
                case 1 -> CobblemonItems.RED_MINT_SEEDS;
                case 2 -> CobblemonItems.BLUE_MINT_SEEDS;
                case 3 -> CobblemonItems.CYAN_MINT_SEEDS;
                case 4 -> CobblemonItems.PINK_MINT_SEEDS;
                case 5 -> CobblemonItems.GREEN_MINT_SEEDS;
                case 6 -> CobblemonItems.WHITE_MINT_SEEDS;
                default -> throw new NoWhenBranchMatchedException();
            };
        }

        @NotNull
        public final MintLeafItem getLeaf() {
            return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
                case 1 -> CobblemonItems.RED_MINT_LEAF;
                case 2 -> CobblemonItems.BLUE_MINT_LEAF;
                case 3 -> CobblemonItems.CYAN_MINT_LEAF;
                case 4 -> CobblemonItems.PINK_MINT_LEAF;
                case 5 -> CobblemonItems.GREEN_MINT_LEAF;
                case 6 -> CobblemonItems.WHITE_MINT_LEAF;
                default -> throw new NoWhenBranchMatchedException();
            };
        }

        @NotNull
        public final MintBlock getCropBlock() {
            return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
                case 1 -> CobblemonBlocks.INSTANCE.getRED_MINT();
                case 2 -> CobblemonBlocks.BLUE_MINT;
                case 3 -> CobblemonBlocks.CYAN_MINT;
                case 4 -> CobblemonBlocks.PINK_MINT;
                case 5 -> CobblemonBlocks.GREEN_MINT;
                case 6 -> CobblemonBlocks.WHITE_MINT;
                default -> throw new NoWhenBranchMatchedException();
            };
        }

        public static MintType[] values() {
            return (MintType[])$VALUES.clone();
        }

        public static MintType valueOf(String value2) {
            return Enum.valueOf(MintType.class, value2);
        }

        static {
            $VALUES = mintTypeArray = new MintType[]{MintType.RED, MintType.BLUE, MintType.CYAN, MintType.PINK, MintType.GREEN, MintType.WHITE};
        }

        @Metadata(mv={1, 8, 0}, k=3, xi=48)
        public final class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] nArray = new int[MintType.values().length];
                try {
                    nArray[MintType.RED.ordinal()] = 1;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[MintType.BLUE.ordinal()] = 2;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[MintType.CYAN.ordinal()] = 3;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[MintType.PINK.ordinal()] = 4;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[MintType.GREEN.ordinal()] = 5;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                try {
                    nArray[MintType.WHITE.ordinal()] = 6;
                }
                catch (NoSuchFieldError noSuchFieldError) {
                    // empty catch block
                }
                $EnumSwitchMapping$0 = nArray;
            }
        }
    }
}

