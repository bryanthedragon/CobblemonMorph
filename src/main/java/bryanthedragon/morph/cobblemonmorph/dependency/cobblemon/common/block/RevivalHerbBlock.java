/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.Mulchable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 :2\u00020\u00012\u00020\u0002:\u0002:;B\u000f\u0012\u0006\u00107\u001a\u000206\u00a2\u0006\u0004\b8\u00109J#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ'\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J/\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0013J7\u0010\u0019\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ/\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ'\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010!\u001a\u00020 H\u0014\u00a2\u0006\u0004\b!\u0010\"J\u000f\u0010$\u001a\u00020#H\u0016\u00a2\u0006\u0004\b$\u0010%J/\u0010*\u001a\u00020)2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020&2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b*\u0010+J\u000f\u0010-\u001a\u00020,H\u0014\u00a2\u0006\u0004\b-\u0010.J\u0015\u0010/\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0004\b/\u00100J\u0015\u00102\u001a\u0002012\u0006\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0004\b2\u00103J/\u00104\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b4\u00105\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/block/RevivalHerbBlock;", "Lnet/minecraft/world/level/block/CropBlock;", "Lcom/cobblemon/mod/common/api/mulch/Mulchable;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "state", "applyGrowth", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "", "useRandomGrowthAmount", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "variant", "applyMulch", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)V", "canHaveMulchApplied", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)Z", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAgeProperty", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "getMaxAge", "()I", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/ItemLike;", "getSeedsItem", "()Lnet/minecraft/world/level/ItemLike;", "isMutated", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lcom/cobblemon/mod/common/block/RevivalHerbBlock$Mutation;", "mutationOf", "(Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/RevivalHerbBlock$Mutation;", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "Mutation", "common"})
@SourceDebugExtension(value={"SMAP\nRevivalHerbBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RevivalHerbBlock.kt\ncom/cobblemon/mod/common/block/RevivalHerbBlock\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,158:1\n4117#2:159\n4217#2,2:160\n*S KotlinDebug\n*F\n+ 1 RevivalHerbBlock.kt\ncom/cobblemon/mod/common/block/RevivalHerbBlock\n*L\n67#1:159\n67#1:160,2\n*E\n"})
public final class RevivalHerbBlock
extends CropBlock
implements Mulchable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 8;
    public static final int MUTABLE_MAX_AGE = 6;
    private static final IntegerProperty AGE = IntegerProperty.m_61631_((String)"age", (int)0, (int)8);
    private static final BooleanProperty IS_WILD = BooleanProperty.m_61465_((String)"is_wild");
    private static final EnumProperty<Mutation> MUTATION = EnumProperty.m_61587_((String)"mutation", Mutation.class);
    @NotNull
    private static final VoxelShape[] AGE_SHAPES;

    public RevivalHerbBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)AGE, (Comparable)Integer.valueOf(0))).m_61124_((Property)IS_WILD, (Comparable)Boolean.valueOf(false))).m_61124_((Property)MUTATION, (Comparable)((Object)Mutation.NONE)));
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{AGE};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{IS_WILD};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{MUTATION};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    protected IntegerProperty m_7959_() {
        IntegerProperty integerProperty = AGE;
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"AGE");
        return integerProperty;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockState floor = world.m_8055_(pos.m_7495_());
        BlockState block = world.m_8055_(pos);
        if (!block.m_204336_(BlockTags.f_278411_) && !block.m_60713_(Blocks.f_50016_)) {
            if (!block.m_60713_((Block)this)) return false;
        }
        Comparable comparable = state.m_61143_((Property)IS_WILD);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(IS_WILD)");
        if (((Boolean)comparable).booleanValue()) {
            if (floor.m_204336_(BlockTags.f_144274_)) return true;
        }
        if (!this.m_6266_(floor, (BlockGetter)world, pos)) return false;
        return true;
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        VoxelShape voxelShape = (VoxelShape)ArraysKt.getOrNull((Object[])AGE_SHAPES, (int)this.m_52305_(state));
        if (voxelShape == null) {
            VoxelShape voxelShape2 = Shapes.m_83144_();
            voxelShape = voxelShape2;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"fullCube()");
        }
        return voxelShape;
    }

    @NotNull
    protected ItemLike m_6404_() {
        return (ItemLike)CobblemonItems.REVIVAL_HERB;
    }

    @Override
    public boolean canHaveMulchApplied(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MulchVariant variant) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)variant), (String)"variant");
        return variant == MulchVariant.SURPRISE && this.m_52305_(state) <= 6 && !this.isMutated(state);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void applyMulch(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MulchVariant variant) {
        void $this$filterNotTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)variant), (String)"variant");
        Mutation[] $this$filterNot$iv = Mutation.values();
        boolean $i$f$filterNot = false;
        Mutation[] mutationArray = $this$filterNot$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterNotTo = false;
        int n = ((void)$this$filterNotTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$filterNotTo$iv$iv[i];
            boolean bl = false;
            if (it == Mutation.NONE) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Mutation picked = (Mutation)((Object)CollectionsKt.random((Collection)((List)destination$iv$iv), (Random)((Random)Random.Default)));
        world.m_46597_(pos, (BlockState)state.m_61124_((Property)MUTATION, (Comparable)((Object)picked)));
    }

    @NotNull
    public final Mutation mutationOf(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)MUTATION);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(MUTATION)");
        return (Mutation)((Object)comparable);
    }

    public final boolean isMutated(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return this.mutationOf(state) != Mutation.NONE;
    }

    public int m_7419_() {
        return 8;
    }

    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (world.m_45524_(pos, 0) < 9 || this.m_52307_(state)) {
            return;
        }
        float currentMoisture = CropBlock.m_52272_((Block)((Block)this), (BlockGetter)((BlockGetter)world), (BlockPos)pos);
        if (random.m_188503_((int)(25.0f / currentMoisture) + 1) == 0) {
            this.applyGrowth((Level)world, pos, state, false);
        }
    }

    public void m_52263_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        this.applyGrowth(world, pos, state, true);
    }

    private final void applyGrowth(Level world, BlockPos pos, BlockState state, boolean useRandomGrowthAmount) {
        int growthAmount = useRandomGrowthAmount ? this.m_7125_(world) : 1;
        int newAge = RangesKt.coerceAtMost((int)(this.m_52305_(state) + growthAmount), (int)this.m_7419_());
        world.m_7731_(pos, (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(newAge)), 2);
    }

    static {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.1, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.2, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.3, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.4, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.5, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.7, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.7, (double)1.0), Shapes.m_83048_((double)0.0, (double)-0.9, (double)0.0, (double)1.0, (double)0.9, (double)1.0), Shapes.m_83144_()};
        AGE_SHAPES = voxelShapeArray;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R%\u0010\n\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\t0\t0\b8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u001f\u0010\u000f\u001a\n \u0003*\u0004\u0018\u00010\u000e0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00138\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00138\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0015R;\u0010\u001a\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00190\u0019 \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00190\u0019\u0018\u00010\u00180\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/block/RevivalHerbBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "kotlin.jvm.PlatformType", "AGE", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAGE", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "AGE_SHAPES", "[Lnet/minecraft/world/phys/shapes/VoxelShape;", "getAGE_SHAPES", "()[Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "IS_WILD", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getIS_WILD", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "", "MAX_AGE", "I", "MIN_AGE", "MUTABLE_MAX_AGE", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "Lcom/cobblemon/mod/common/block/RevivalHerbBlock$Mutation;", "MUTATION", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getMUTATION", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final IntegerProperty getAGE() {
            return AGE;
        }

        public final BooleanProperty getIS_WILD() {
            return IS_WILD;
        }

        public final EnumProperty<Mutation> getMUTATION() {
            return MUTATION;
        }

        @NotNull
        public final VoxelShape[] getAGE_SHAPES() {
            return AGE_SHAPES;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/block/RevivalHerbBlock$Mutation;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;I)V", "NONE", "MENTAL", "POWER", "WHITE", "MIRROR", "common"})
    public static final class Mutation
    extends Enum<Mutation>
    implements StringRepresentable {
        public static final /* enum */ Mutation NONE = new Mutation();
        public static final /* enum */ Mutation MENTAL = new Mutation();
        public static final /* enum */ Mutation POWER = new Mutation();
        public static final /* enum */ Mutation WHITE = new Mutation();
        public static final /* enum */ Mutation MIRROR = new Mutation();
        private static final /* synthetic */ Mutation[] $VALUES;

        @NotNull
        public String m_7912_() {
            String string = this.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            return string;
        }

        public static Mutation[] values() {
            return (Mutation[])$VALUES.clone();
        }

        public static Mutation valueOf(String value2) {
            return Enum.valueOf(Mutation.class, value2);
        }

        static {
            $VALUES = mutationArray = new Mutation[]{Mutation.NONE, Mutation.MENTAL, Mutation.POWER, Mutation.WHITE, Mutation.MIRROR};
        }
    }
}

