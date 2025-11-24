/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.CropBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u000f\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/block/VivichokeBlock;", "Lnet/minecraft/world/level/block/CropBlock;", "Lnet/minecraft/world/level/Level;", "world", "", "getGrowthAmount", "(Lnet/minecraft/world/level/Level;)I", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/ItemLike;", "getSeedsItem", "()Lnet/minecraft/world/level/ItemLike;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nVivichokeBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VivichokeBlock.kt\ncom/cobblemon/mod/common/block/VivichokeBlock\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"})
public final class VivichokeBlock
extends CropBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final VoxelShape STAGE_0_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)2.0, (double)10.0);
    private static final VoxelShape STAGE_1_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)5.0, (double)10.0);
    private static final VoxelShape STAGE_2_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)7.0, (double)10.0);
    private static final VoxelShape STAGE_3_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)9.0, (double)10.0);
    private static final VoxelShape STAGE_4_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)7.0, (double)10.0);
    private static final VoxelShape STAGE_5_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)8.0, (double)10.0);
    private static final VoxelShape STAGE_6_SHAPE = CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)9.0, (double)10.0);
    private static final VoxelShape STAGE_7_SHAPE = Shapes.m_83110_((VoxelShape)CropBlock.m_49796_((double)6.0, (double)-1.0, (double)6.0, (double)10.0, (double)10.0, (double)10.0), (VoxelShape)CropBlock.m_49796_((double)5.5, (double)10.0, (double)5.5, (double)10.5, (double)14.0, (double)10.5));
    @NotNull
    private static final VoxelShape[] AGE_TO_SHAPE;

    public VivichokeBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Object object;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Object[] objectArray = AGE_TO_SHAPE;
        Comparable comparable = state.m_61143_((Property)this.m_7959_());
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(this.ageProperty)");
        int n = ((Number)((Object)comparable)).intValue();
        if (n >= 0 && n <= ArraysKt.getLastIndex((Object[])objectArray)) {
            object = objectArray[n];
        } else {
            int it = n;
            boolean bl = false;
            object = Shapes.m_83144_();
        }
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"AGE_TO_SHAPE.getOrElse(s\u2026 VoxelShapes.fullCube() }");
        return object;
    }

    protected int m_7125_(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return 1;
    }

    @NotNull
    protected ItemLike m_6404_() {
        return (ItemLike)CobblemonItems.VIVICHOKE_SEEDS;
    }

    static {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{STAGE_0_SHAPE, STAGE_1_SHAPE, STAGE_2_SHAPE, STAGE_3_SHAPE, STAGE_4_SHAPE, STAGE_5_SHAPE, STAGE_6_SHAPE, STAGE_7_SHAPE};
        AGE_TO_SHAPE = voxelShapeArray;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013R%\u0010\u0005\u001a\u0010\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR\u001c\u0010\f\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR\u001c\u0010\r\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR\u001c\u0010\u000e\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\nR\u001c\u0010\u000f\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR\u001c\u0010\u0010\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR\u001c\u0010\u0011\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\n\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/block/VivichokeBlock$Companion;", "", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "AGE_TO_SHAPE", "[Lnet/minecraft/world/phys/shapes/VoxelShape;", "getAGE_TO_SHAPE", "()[Lnet/minecraft/world/phys/shapes/VoxelShape;", "STAGE_0_SHAPE", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "STAGE_1_SHAPE", "STAGE_2_SHAPE", "STAGE_3_SHAPE", "STAGE_4_SHAPE", "STAGE_5_SHAPE", "STAGE_6_SHAPE", "STAGE_7_SHAPE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
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

