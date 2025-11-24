/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.Containers
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.BlockItem
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
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.DirectionProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 92\u00020\u0001:\u00019B\u000f\u0012\u0006\u00106\u001a\u000205\u00a2\u0006\u0004\b7\u00108J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ/\u0010\u0011\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0014\u001a\u00020\u00132\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J'\u0010\u0018\u001a\u00020\u00172\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0019\u0010\u001c\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0019\u0010\u001f\u001a\u00020\u001e2\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J?\u0010&\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020$2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010%\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b&\u0010'J\u0019\u0010(\u001a\u00020\u00102\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b(\u0010)J/\u0010,\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010+\u001a\u00020*H\u0016\u00a2\u0006\u0004\b,\u0010-J?\u00103\u001a\u0002022\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00162\u0006\u0010\r\u001a\u00020\f2\u0006\u0010+\u001a\u00020*2\u0006\u0010/\u001a\u00020.2\u0006\u00101\u001a\u000200H\u0016\u00a2\u0006\u0004\b3\u00104\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/block/DisplayCaseBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "type", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;", "Lnet/minecraft/world/level/Level;", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "ctx", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "hasComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDisplayCaseBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DisplayCaseBlock.kt\ncom/cobblemon/mod/common/block/DisplayCaseBlock\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,121:1\n13579#2,2:122\n*S KotlinDebug\n*F\n+ 1 DisplayCaseBlock.kt\ncom/cobblemon/mod/common/block/DisplayCaseBlock\n*L\n47#1:122,2\n*E\n"})
public final class DisplayCaseBlock
extends BaseEntityBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final DirectionProperty ITEM_DIRECTION = DirectionProperty.m_156003_((String)"item_facing");

    public DisplayCaseBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)ITEM_DIRECTION, (Comparable)Direction.NORTH));
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
            Object object = ((BlockState)blockState.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)direction)).m_61124_((Property)ITEM_DIRECTION, (Comparable)direction);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            blockState = (BlockState)object;
            if (!blockState.m_60710_((LevelReader)worldView, blockPos2)) continue;
            return blockState;
        }
        return null;
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{ITEM_DIRECTION};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    public DisplayCaseBlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new DisplayCaseBlockEntity(pos, state);
    }

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

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity");
        DisplayCaseBlockEntity entity2 = (DisplayCaseBlockEntity)blockEntity;
        InteractionResult result = entity2.updateItem(player, hand);
        if (hit.m_82434_() != Direction.UP && hit.m_82434_() != Direction.DOWN && result == InteractionResult.SUCCESS) {
            world.m_46597_(pos, (BlockState)state.m_61124_((Property)ITEM_DIRECTION, (Comparable)hit.m_82434_().m_122424_()));
        }
        return result;
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity");
        DisplayCaseBlockEntity entity2 = (DisplayCaseBlockEntity)blockEntity;
        if (!entity2.getStack().m_41619_() && !player.m_7500_()) {
            Containers.m_19010_((Level)world, (BlockPos)pos, entity2.getInv());
        }
        super.m_5707_(world, pos, state, player);
    }

    @NotNull
    public RenderShape m_7514_(@Nullable BlockState state) {
        return RenderShape.MODEL;
    }

    public int m_6782_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity");
        ItemStack stack = ((DisplayCaseBlockEntity)blockEntity).getStack();
        if (stack.m_41619_()) {
            return 0;
        }
        if (stack.m_41720_() instanceof PokeBallItem) {
            return 3;
        }
        if (stack.m_41720_() instanceof BlockItem) {
            return 2;
        }
        return 1;
    }

    public boolean m_7278_(@Nullable BlockState state) {
        return true;
    }

    public boolean m_7357_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        return false;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/block/DisplayCaseBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/DirectionProperty;", "kotlin.jvm.PlatformType", "ITEM_DIRECTION", "Lnet/minecraft/world/level/block/state/properties/DirectionProperty;", "getITEM_DIRECTION", "()Lnet/minecraft/world/level/block/state/properties/DirectionProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final DirectionProperty getITEM_DIRECTION() {
            return ITEM_DIRECTION;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

