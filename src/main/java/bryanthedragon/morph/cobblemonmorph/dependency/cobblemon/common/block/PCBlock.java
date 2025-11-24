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
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
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
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.SimpleWaterloggedBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.ProximityPCLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc.OpenPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00dc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 a2\u00020\u00012\u00020\u0002:\u0002abB\u000f\u0012\u0006\u0010^\u001a\u00020]\u00a2\u0006\u0004\b_\u0010`J#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\u0012\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u0017\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J!\u0010\u001b\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001d\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\r\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0019\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0014\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b \u0010!J/\u0010%\u001a\u00020$2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010#\u001a\u00020\"H\u0017\u00a2\u0006\u0004\b%\u0010&J\u0019\u0010)\u001a\u0004\u0018\u00010\u00052\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b)\u0010*J\u001d\u0010+\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\r\u00a2\u0006\u0004\b+\u0010\u001eJ\u0017\u0010-\u001a\u00020,2\u0006\u0010\n\u001a\u00020\u0005H\u0017\u00a2\u0006\u0004\b-\u0010.J?\u00104\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u00100\u001a\u00020/2\u0006\u00101\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u0002022\u0006\u0010\u0017\u001a\u00020\r2\u0006\u00103\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b4\u00105JG\u0010=\u001a\u0012\u0012\f\u0012\n <*\u0004\u0018\u00018\u00008\u0000\u0018\u00010;\"\b\b\u0000\u00107*\u0002062\u0006\u0010\u0016\u001a\u0002082\u0006\u0010\n\u001a\u00020\u00052\f\u0010:\u001a\b\u0012\u0004\u0012\u00028\u000009H\u0016\u00a2\u0006\u0004\b=\u0010>J\u0017\u0010?\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b?\u0010@J\u001f\u0010B\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010B\u001a\u00020AH\u0017\u00a2\u0006\u0004\bB\u0010CJ1\u0010F\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u0002082\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00052\b\u0010E\u001a\u0004\u0018\u00010DH\u0016\u00a2\u0006\u0004\bF\u0010GJ;\u0010L\u001a\u00020\u00072\u0006\u0010\u0016\u001a\u0002082\u0006\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u00052\b\u0010I\u001a\u0004\u0018\u00010H2\b\u0010K\u001a\u0004\u0018\u00010JH\u0016\u00a2\u0006\u0004\bL\u0010MJ9\u0010P\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u0002082\b\u0010\u0017\u001a\u0004\u0018\u00010\r2\u0006\u0010N\u001a\u00020\u00052\u0006\u0010O\u001a\u00020\u0011H\u0017\u00a2\u0006\u0004\bP\u0010QJ?\u0010W\u001a\u00020V2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u0002082\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010E\u001a\u00020D2\u0006\u0010S\u001a\u00020R2\u0006\u0010U\u001a\u00020TH\u0017\u00a2\u0006\u0004\bW\u0010XJ'\u0010[\u001a\n <*\u0004\u0018\u00010\u00050\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010Z\u001a\u00020YH\u0017\u00a2\u0006\u0004\b[\u0010\\\u00a8\u0006c"}, d2={"Lcom/cobblemon/mod/common/block/PCBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/world/level/block/SimpleWaterloggedBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "blockState", "Lnet/minecraft/world/level/BlockGetter;", "blockGetter", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "pathComputationType", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "state", "Lnet/minecraft/world/level/LevelReader;", "world", "pos", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "getBasePosition", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/material/FluidState;", "getFluidState", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/material/FluidState;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "collisionContext", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "getPositionOfOtherPart", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "BlockWithEntityType", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "kotlin.jvm.PlatformType", "getTicker", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "isBase", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/level/block/Mirror;", "mirror", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Mirror;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "newState", "moved", "onStateReplaced", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/InteractionHand;", "interactionHand", "Lnet/minecraft/world/phys/BlockHitResult;", "blockHitResult", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/level/block/Rotation;", "rotation", "rotate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Rotation;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "PCPart", "common"})
@SourceDebugExtension(value={"SMAP\nPCBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCBlock.kt\ncom/cobblemon/mod/common/block/PCBlock\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,321:1\n1#2:322\n*E\n"})
public final class PCBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final EnumProperty<PCPart> PART = EnumProperty.m_61587_((String)"part", PCPart.class);
    private static final BooleanProperty ON = BooleanProperty.m_61465_((String)"on");
    private static final BooleanProperty WATERLOGGED = BooleanProperty.m_61465_((String)"waterlogged");
    private static final VoxelShape NORTH_AABB_TOP;
    private static final VoxelShape SOUTH_AABB_TOP;
    private static final VoxelShape WEST_AABB_TOP;
    private static final VoxelShape EAST_AABB_TOP;
    private static final VoxelShape NORTH_AABB_BOTTOM;
    private static final VoxelShape SOUTH_AABB_BOTTOM;
    private static final VoxelShape WEST_AABB_BOTTOM;
    private static final VoxelShape EAST_AABB_BOTTOM;

    public PCBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)PART, (Comparable)((Object)PCPart.BOTTOM))).m_61124_((Property)ON, (Comparable)Boolean.valueOf(false))).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false)));
    }

    @Nullable
    public PCBlockEntity createBlockEntity(@NotNull BlockPos blockPos2, @NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return blockState.m_61143_((Property)PART) == PCPart.BOTTOM ? new PCBlockEntity(blockPos2, blockState) : null;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos2, @NotNull CollisionContext collisionContext) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)blockGetter, (String)"blockGetter");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)collisionContext, (String)"collisionContext");
        if (blockState.m_61143_((Property)PART) == PCPart.TOP) {
            Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
            VoxelShape voxelShape2 = switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                case 1 -> SOUTH_AABB_TOP;
                case 2 -> WEST_AABB_TOP;
                case 3 -> EAST_AABB_TOP;
                default -> NORTH_AABB_TOP;
            };
            voxelShape = voxelShape2;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"{\n            when (bloc\u2026P\n            }\n        }");
        } else {
            Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
            VoxelShape voxelShape3 = switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                case 1 -> SOUTH_AABB_BOTTOM;
                case 2 -> WEST_AABB_BOTTOM;
                case 3 -> EAST_AABB_BOTTOM;
                default -> NORTH_AABB_BOTTOM;
            };
            voxelShape = voxelShape3;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"{\n            when (bloc\u2026M\n            }\n        }");
        }
        return voxelShape;
    }

    @NotNull
    public final BlockPos getPositionOfOtherPart(@NotNull BlockState state, @NotNull BlockPos pos) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (state.m_61143_((Property)PART) == PCPart.BOTTOM) {
            BlockPos blockPos3 = pos.m_7494_();
            blockPos2 = blockPos3;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"{\n            pos.up()\n        }");
        } else {
            BlockPos blockPos4 = pos.m_7495_();
            blockPos2 = blockPos4;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"{\n            pos.down()\n        }");
        }
        return blockPos2;
    }

    @NotNull
    public final BlockPos getBasePosition(@NotNull BlockState state, @NotNull BlockPos pos) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (this.isBase(state)) {
            blockPos2 = pos;
        } else {
            BlockPos blockPos3 = pos.m_7495_();
            blockPos2 = blockPos3;
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"{\n            pos.down()\n        }");
        }
        return blockPos2;
    }

    private final boolean isBase(BlockState state) {
        return state.m_61143_((Property)PART) == PCPart.BOTTOM;
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @Nullable ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        BlockPos blockPos2 = pos.m_7494_();
        Object object = ((BlockState)state.m_61124_((Property)PART, (Comparable)((Object)PCPart.TOP))).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)world.m_6425_(pos.m_7494_()).m_76152_(), (Object)Fluids.f_76193_)));
        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
        world.m_7731_(blockPos2, (BlockState)object, 3);
        world.m_6289_(pos, Blocks.f_50016_);
        state.m_60701_((LevelAccessor)world, pos, 3);
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        BlockPos abovePosition = blockPlaceContext.m_8083_().m_7494_();
        Level world = blockPlaceContext.m_43725_();
        if (world.m_8055_(abovePosition).m_60629_(blockPlaceContext) && !world.m_151570_(abovePosition)) {
            return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_())).m_61124_((Property)PART, (Comparable)((Object)PCPart.BOTTOM))).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_()).m_76152_(), (Object)Fluids.f_76193_)));
        }
        return null;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockPos blockPos2 = pos.m_7495_();
        BlockState blockState = world.m_8055_(blockPos2);
        return state.m_61143_((Property)PART) == PCPart.BOTTOM ? blockState.m_60783_((BlockGetter)world, blockPos2, Direction.UP) : blockState.m_60713_((Block)this);
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (!world.f_46443_) {
            Player player2 = player;
            boolean bl = player2 != null ? player2.m_7500_() : false;
            if (bl) {
                BlockPos blockPos2 = null;
                BlockPos blockPos3 = BlockPos.f_121853_;
                Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"ORIGIN");
                blockPos2 = blockPos3;
                BlockState blockState = null;
                blockState = state;
                PCPart part = (PCPart)((Object)state.m_61143_((Property)PART));
                if (part == PCPart.TOP) {
                    BlockPos it;
                    BlockPos blockPos4;
                    BlockPos blockPos5 = blockPos4 = pos.m_7495_();
                    Level level = world;
                    boolean bl2 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                    blockPos2 = it;
                    it = blockPos4 = level.m_8055_(blockPos4);
                    boolean bl3 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                    blockState = it;
                    if (blockPos4.m_60713_(state.m_60734_()) && blockState.m_61143_((Property)PART) == PCPart.BOTTOM) {
                        BlockState blockState2 = blockState.m_60819_().m_192917_((Fluid)Fluids.f_76193_) ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_();
                        world.m_7731_(blockPos2, blockState2, 35);
                        world.m_5898_(player, 2001, blockPos2, BaseEntityBlock.m_49956_((BlockState)blockState));
                    }
                }
            }
        }
        super.m_5707_(world, pos, state, player);
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7357_(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos2, @NotNull PathComputationType pathComputationType) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)blockGetter, (String)"blockGetter");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)pathComputationType, (String)"pathComputationType");
        return false;
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{PART};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{ON};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{WATERLOGGED};
        builder.m_61104_(propertyArray);
    }

    @Deprecated(message="Deprecated in Java")
    public BlockState m_6843_(@NotNull BlockState blockState, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        return (BlockState)blockState.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)rotation.m_55954_((Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)));
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public BlockState m_6943_(@NotNull BlockState blockState, @NotNull Mirror mirror) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)mirror, (String)"mirror");
        BlockState blockState2 = blockState.m_60717_(mirror.m_54846_((Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)));
        Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"blockState.rotate(mirror\u2026ntalFacingBlock.FACING)))");
        return blockState2;
    }

    @Deprecated(message="Deprecated in Java")
    public void m_6810_(@NotNull BlockState state, @NotNull Level world, @Nullable BlockPos pos, @NotNull BlockState newState, boolean moved) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)newState, (String)"newState");
        if (!state.m_60713_(newState.m_60734_())) {
            super.m_6810_(state, world, pos, newState, moved);
        }
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPos2, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)interactionHand, (String)"interactionHand");
        Intrinsics.checkNotNullParameter((Object)blockHitResult, (String)"blockHitResult");
        if (!(player instanceof ServerPlayer)) {
            return InteractionResult.SUCCESS;
        }
        BlockPos basePos = this.getBasePosition(blockState, blockPos2);
        BlockEntity blockEntity = world.m_7702_(basePos.m_7494_());
        if (blockEntity != null) {
            blockEntity.m_7651_();
        }
        BlockEntity baseEntity = world.m_7702_(basePos);
        if (!(baseEntity instanceof PCBlockEntity)) {
            return InteractionResult.SUCCESS;
        }
        if (PlayerExtensionsKt.isInBattle((ServerPlayer)player)) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("pc.inbattle", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"pc.inbattle\")");
            player.m_213846_((Component)TextKt.red(mutableComponent));
            return InteractionResult.SUCCESS;
        }
        PCStore pCStore = Cobblemon.INSTANCE.getStorage().getPCForPlayer((ServerPlayer)player, (PCBlockEntity)baseEntity);
        if (pCStore == null) {
            return InteractionResult.SUCCESS;
        }
        PCStore pc = pCStore;
        UUID uUID = ((ServerPlayer)player).m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        PCLinkManager.INSTANCE.addLink(new ProximityPCLink(pc, uUID, (PCBlockEntity)baseEntity, 0.0, 8, null));
        new OpenPCPacket(pc.getUuid()).sendToPlayer((ServerPlayer)player);
        WorldExtensionsKt.playSoundServer$default(world, BlockPosExtensionsKt.toVec3d(blockPos2), CobblemonSounds.PC_ON, null, 0.5f, 1.0f, 4, null);
        return InteractionResult.SUCCESS;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level world, @NotNull BlockState blockState, @NotNull BlockEntityType<T> BlockWithEntityType) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter(BlockWithEntityType, (String)"BlockWithEntityType");
        BlockEntityTicker<PCBlockEntity> blockEntityTicker = PCBlockEntity.Companion.getTICKER$common();
        return BaseEntityBlock.m_152132_(BlockWithEntityType, CobblemonBlockEntities.PC, (BlockEntityTicker)new BlockEntityTicker(blockEntityTicker){
            final /* synthetic */ BlockEntityTicker<PCBlockEntity> $tmp0;
            {
                this.$tmp0 = $tmp0;
            }

            public final void tick(Level p0, BlockPos p1, BlockState p2, PCBlockEntity p3) {
                this.$tmp0.m_155252_(p0, p1, p2, (BlockEntity)p3);
            }
        });
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public RenderShape m_7514_(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return RenderShape.MODEL;
    }

    @Nullable
    public FluidState m_5888_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)WATERLOGGED);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(WATERLOGGED)");
        return (Boolean)comparable != false ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(state);
    }

    @NotNull
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        Comparable comparable = state.m_61143_((Property)WATERLOGGED);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(WATERLOGGED)");
        if (((Boolean)comparable).booleanValue()) {
            world.m_186469_(pos, (Fluid)Fluids.f_76193_, Fluids.f_76193_.m_6718_((LevelReader)world));
        }
        boolean isPC = neighborState.m_60713_((Block)this);
        PCPart part = (PCPart)((Object)state.m_61143_((Property)PART));
        if (!isPC && part == PCPart.TOP && Intrinsics.areEqual((Object)neighborPos, (Object)pos.m_7495_())) {
            BlockState blockState = Blocks.f_50016_.m_49966_();
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"AIR.defaultState");
            return blockState;
        }
        if (!isPC && part == PCPart.BOTTOM && Intrinsics.areEqual((Object)neighborPos, (Object)pos.m_7494_())) {
            BlockState blockState = Blocks.f_50016_.m_49966_();
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"AIR.defaultState");
            return blockState;
        }
        return state;
    }

    static {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.8125, (double)0.125, (double)0.875, (double)0.9375, (double)0.6875), Shapes.m_83048_((double)0.125, (double)0.125, (double)0.125, (double)0.875, (double)0.8125, (double)0.625), Shapes.m_83048_((double)0.0625, (double)0.0, (double)0.125, (double)0.125, (double)0.9375, (double)0.6875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.125, (double)0.6875), Shapes.m_83048_((double)0.875, (double)0.0, (double)0.125, (double)0.9375, (double)0.9375, (double)0.6875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.6875, (double)0.875, (double)0.0625, (double)0.875)};
        NORTH_AABB_TOP = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.1875, (double)0.0, (double)0.0, (double)0.8125, (double)0.875, (double)0.125), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.8125, (double)0.3125, (double)0.875, (double)0.9375, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.125, (double)0.375, (double)0.875, (double)0.8125, (double)0.875), Shapes.m_83048_((double)0.875, (double)0.0, (double)0.3125, (double)0.9375, (double)0.9375, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.3125, (double)0.875, (double)0.125, (double)0.875), Shapes.m_83048_((double)0.0625, (double)0.0, (double)0.3125, (double)0.125, (double)0.9375, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.3125)};
        SOUTH_AABB_TOP = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.1875, (double)0.0, (double)0.875, (double)0.8125, (double)0.875, (double)1.0), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.8125, (double)0.125, (double)0.6875, (double)0.9375, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.125, (double)0.125, (double)0.625, (double)0.8125, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.0625, (double)0.6875, (double)0.9375, (double)0.125), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.6875, (double)0.125, (double)0.875), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.875, (double)0.6875, (double)0.9375, (double)0.9375), Shapes.m_83048_((double)0.6875, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.875)};
        WEST_AABB_TOP = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.1875, (double)0.125, (double)0.875, (double)0.8125), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.3125, (double)0.8125, (double)0.125, (double)0.875, (double)0.9375, (double)0.875), Shapes.m_83048_((double)0.375, (double)0.125, (double)0.125, (double)0.875, (double)0.8125, (double)0.875), Shapes.m_83048_((double)0.3125, (double)0.0, (double)0.0625, (double)0.875, (double)0.9375, (double)0.125), Shapes.m_83048_((double)0.3125, (double)0.0, (double)0.125, (double)0.875, (double)0.125, (double)0.875), Shapes.m_83048_((double)0.3125, (double)0.0, (double)0.875, (double)0.875, (double)0.9375, (double)0.9375), Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.3125, (double)0.0625, (double)0.875)};
        EAST_AABB_TOP = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.875, (double)0.0, (double)0.1875, (double)1.0, (double)0.875, (double)0.8125), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.875), Shapes.m_83048_((double)0.1875, (double)0.0, (double)0.0, (double)0.8125, (double)1.0, (double)0.125)};
        NORTH_AABB_BOTTOM = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0625, (double)0.0625, (double)0.125, (double)0.9375, (double)1.0, (double)0.9375), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.875), Shapes.m_83048_((double)0.1875, (double)0.0, (double)0.875, (double)0.8125, (double)1.0, (double)1.0)};
        SOUTH_AABB_BOTTOM = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0625, (double)0.0625, (double)0.0625, (double)0.9375, (double)1.0, (double)0.875), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.875), Shapes.m_83048_((double)0.0, (double)0.0, (double)0.1875, (double)0.125, (double)1.0, (double)0.8125)};
        WEST_AABB_BOTTOM = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.125, (double)0.0625, (double)0.0625, (double)0.9375, (double)1.0, (double)0.9375), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.125, (double)0.0, (double)0.125, (double)0.875, (double)0.0625, (double)0.875), Shapes.m_83048_((double)0.875, (double)0.0, (double)0.1875, (double)1.0, (double)1.0, (double)0.8125)};
        EAST_AABB_BOTTOM = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0625, (double)0.0625, (double)0.0625, (double)0.875, (double)1.0, (double)0.9375), (VoxelShape[])voxelShapeArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u001c\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0004\u0010\u0005R\u001c\u0010\u0006\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0005R\u001c\u0010\u0007\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0005R\u001c\u0010\b\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0005R\u001f\u0010\n\u001a\n \u0003*\u0004\u0018\u00010\t0\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR;\u0010\u0010\u001a&\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u000f0\u000f \u0003*\u0012\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u000f0\u000f\u0018\u00010\u000e0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0014\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0005R\u001c\u0010\u0015\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0005R\u001f\u0010\u0016\u001a\n \u0003*\u0004\u0018\u00010\t0\t8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0017\u0010\rR\u001c\u0010\u0018\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0005R\u001c\u0010\u0019\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0005\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/block/PCBlock$Companion;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "EAST_AABB_BOTTOM", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "EAST_AABB_TOP", "NORTH_AABB_BOTTOM", "NORTH_AABB_TOP", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "ON", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getON", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "Lcom/cobblemon/mod/common/block/PCBlock$PCPart;", "PART", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getPART", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "SOUTH_AABB_BOTTOM", "SOUTH_AABB_TOP", "WATERLOGGED", "getWATERLOGGED", "WEST_AABB_BOTTOM", "WEST_AABB_TOP", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final EnumProperty<PCPart> getPART() {
            return PART;
        }

        public final BooleanProperty getON() {
            return ON;
        }

        public final BooleanProperty getWATERLOGGED() {
            return WATERLOGGED;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0011\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007j\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/block/PCBlock$PCPart;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "label", "Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "TOP", "BOTTOM", "common"})
    public static final class PCPart
    extends Enum<PCPart>
    implements StringRepresentable {
        @NotNull
        private final String label;
        public static final /* enum */ PCPart TOP = new PCPart("top");
        public static final /* enum */ PCPart BOTTOM = new PCPart("bottom");
        private static final /* synthetic */ PCPart[] $VALUES;

        private PCPart(String label) {
            this.label = label;
        }

        @NotNull
        public String m_7912_() {
            return this.label;
        }

        public static PCPart[] values() {
            return (PCPart[])$VALUES.clone();
        }

        public static PCPart valueOf(String value2) {
            return Enum.valueOf(PCPart.class, value2);
        }

        static {
            $VALUES = pCPartArray = new PCPart[]{PCPart.TOP, PCPart.BOTTOM};
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.SOUTH.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 2;
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
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

