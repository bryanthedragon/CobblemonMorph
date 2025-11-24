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
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder;
import java.util.Locale;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 )2\u00020\u0001:\u0002)*B\u000f\u0012\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b'\u0010(J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ7\u0010\u0011\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0017\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0014\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J+\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\u00162\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0017\u00a2\u0006\u0004\b\u0018\u0010\u0019J7\u0010\u001d\u001a\u00020\u001c2\b\u0010\t\u001a\u0004\u0018\u00010\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001aH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0019\u0010!\u001a\u0004\u0018\u00010\u00042\u0006\u0010 \u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b!\u0010\"J\u0019\u0010#\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0017\u00a2\u0006\u0004\b#\u0010$\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/block/MonitorBlock;", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "type", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "createMultiBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lnet/minecraft/world/level/Level;", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "hasComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "MonitorScreen", "common"})
public final class MonitorBlock
extends MultiblockBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final EnumProperty<MonitorScreen> SCREEN = EnumProperty.m_61587_((String)"screen", MonitorScreen.class);
    private static final VoxelShape HITBOX;

    public MonitorBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH));
    }

    @Override
    @NotNull
    public FossilMultiblockEntity createMultiBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new FossilMultiblockEntity(pos, state, new FossilMultiblockBuilder(pos), null, 8, null);
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        return (BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_());
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{SCREEN};
        builder.m_61104_(propertyArray);
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7278_(@Nullable BlockState state) {
        return true;
    }

    @Deprecated(message="Deprecated in Java")
    public int m_6782_(@NotNull BlockState state, @Nullable Level world, @Nullable BlockPos pos) {
        MultiblockStructure multiBlockEntity;
        MultiblockEntity monitorEntity;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (world == null || pos == null) {
            return 0;
        }
        BlockEntity blockEntity = world.m_7702_(pos);
        MultiblockEntity multiblockEntity = monitorEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
        MultiblockStructure multiblockStructure = multiBlockEntity = multiblockEntity != null ? multiblockEntity.getMultiblockStructure() : null;
        if (multiBlockEntity != null) {
            return multiBlockEntity.getComparatorOutput(state, world, pos);
        }
        return 0;
    }

    @NotNull
    public VoxelShape m_5940_(@Nullable BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable CollisionContext context) {
        VoxelShape voxelShape = HITBOX;
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"HITBOX");
        return voxelShape;
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7357_(@Nullable BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable PathComputationType type) {
        return false;
    }

    static {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.0625, (double)0.875, (double)0.0625, (double)0.9375, (double)1.0, (double)0.9375), Shapes.m_83048_((double)0.8125, (double)0.375, (double)0.0625, (double)0.9375, (double)0.875, (double)0.9375), Shapes.m_83048_((double)0.1875, (double)0.375, (double)0.125, (double)0.8125, (double)0.875, (double)0.9375), Shapes.m_83048_((double)0.0625, (double)0.375, (double)0.0625, (double)0.1875, (double)0.875, (double)0.9375)};
        HITBOX = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0625, (double)0.0, (double)0.0625, (double)0.9375, (double)0.375, (double)0.9375), (VoxelShape[])voxelShapeArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R;\u0010\n\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\t0\t \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\t0\t\u0018\u00010\b0\b8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/block/MonitorBlock$Companion;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "HITBOX", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getHITBOX", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "Lcom/cobblemon/mod/common/block/MonitorBlock$MonitorScreen;", "SCREEN", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getSCREEN", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EnumProperty<MonitorScreen> getSCREEN() {
            return SCREEN;
        }

        public final VoxelShape getHITBOX() {
            return HITBOX;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0010\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/MonitorBlock$MonitorScreen;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;I)V", "OFF", "BLUE_PROGRESS_1", "BLUE_PROGRESS_2", "BLUE_PROGRESS_3", "BLUE_PROGRESS_4", "BLUE_PROGRESS_5", "BLUE_PROGRESS_6", "BLUE_PROGRESS_7", "BLUE_PROGRESS_8", "BLUE_PROGRESS_9", "GREEN_PROGRESS_9", "common"})
    public static final class MonitorScreen
    extends Enum<MonitorScreen>
    implements StringRepresentable {
        public static final /* enum */ MonitorScreen OFF = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_1 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_2 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_3 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_4 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_5 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_6 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_7 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_8 = new MonitorScreen();
        public static final /* enum */ MonitorScreen BLUE_PROGRESS_9 = new MonitorScreen();
        public static final /* enum */ MonitorScreen GREEN_PROGRESS_9 = new MonitorScreen();
        private static final /* synthetic */ MonitorScreen[] $VALUES;

        @NotNull
        public String m_7912_() {
            String string = this.name().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
            return string;
        }

        public static MonitorScreen[] values() {
            return (MonitorScreen[])$VALUES.clone();
        }

        public static MonitorScreen valueOf(String value2) {
            return Enum.valueOf(MonitorScreen.class, value2);
        }

        static {
            $VALUES = monitorScreenArray = new MonitorScreen[]{MonitorScreen.OFF, MonitorScreen.BLUE_PROGRESS_1, MonitorScreen.BLUE_PROGRESS_2, MonitorScreen.BLUE_PROGRESS_3, MonitorScreen.BLUE_PROGRESS_4, MonitorScreen.BLUE_PROGRESS_5, MonitorScreen.BLUE_PROGRESS_6, MonitorScreen.BLUE_PROGRESS_7, MonitorScreen.BLUE_PROGRESS_8, MonitorScreen.BLUE_PROGRESS_9, MonitorScreen.GREEN_PROGRESS_9};
        }
    }
}

