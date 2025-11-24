/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleTypes
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.TooltipFlag
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.Mirror
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.Rotation
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.HealingMachineBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00d4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 [2\u00020\u0001:\u0001[B\u000f\u0012\u0006\u0010X\u001a\u00020W\u00a2\u0006\u0004\bY\u0010ZJ#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ;\u0010\u0012\u001a\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\t2\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J/\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001f\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010$\u001a\u00020#2\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\f\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0016H\u0016\u00a2\u0006\u0004\b$\u0010%J/\u0010)\u001a\u00020(2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010'\u001a\u00020&H\u0017\u00a2\u0006\u0004\b)\u0010*J\u0017\u0010-\u001a\u00020\u00042\u0006\u0010,\u001a\u00020+H\u0016\u00a2\u0006\u0004\b-\u0010.J\u0017\u00100\u001a\u00020/2\u0006\u0010\u0014\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b0\u00101J?\u00106\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000105\"\b\b\u0000\u00102*\u00020\u001d2\u0006\u0010\f\u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u00042\f\u00104\u001a\b\u0012\u0004\u0012\u00028\u000003H\u0016\u00a2\u0006\u0004\b6\u00107J\u0017\u00108\u001a\u00020\u001a2\u0006\u0010 \u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b8\u00109J\u001f\u0010;\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010;\u001a\u00020:H\u0016\u00a2\u0006\u0004\b;\u0010<J9\u0010@\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020!2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0014\u001a\u00020\u00042\b\u0010>\u001a\u0004\u0018\u00010=2\u0006\u0010?\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b@\u0010AJ9\u0010D\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\f\u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u00162\u0006\u0010B\u001a\u00020\u00042\u0006\u0010C\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\bD\u0010EJ?\u0010M\u001a\u00020L2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020!2\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010G\u001a\u00020F2\u0006\u0010I\u001a\u00020H2\u0006\u0010K\u001a\u00020JH\u0016\u00a2\u0006\u0004\bM\u0010NJ/\u0010Q\u001a\u00020\u00062\u0006\u0010 \u001a\u00020\u00042\u0006\u0010\f\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00162\u0006\u0010P\u001a\u00020OH\u0016\u00a2\u0006\u0004\bQ\u0010RJ\u001f\u0010U\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010T\u001a\u00020SH\u0016\u00a2\u0006\u0004\bU\u0010V\u00a8\u0006\\"}, d2={"Lcom/cobblemon/mod/common/block/HealingMachineBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/world/level/BlockGetter;", "world", "", "Lnet/minecraft/network/chat/Component;", "tooltip", "Lnet/minecraft/world/item/TooltipFlag;", "options", "appendTooltip", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/BlockGetter;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", "blockState", "blockGetter", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "pathComputationType", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/entity/BlockEntity;", "state", "Lnet/minecraft/world/level/Level;", "pos", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "collisionContext", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "T", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "blockWithEntityType", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTicker", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "hasComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/level/block/Mirror;", "mirror", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Mirror;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/entity/LivingEntity;", "livingEntity", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "newState", "moved", "onStateReplaced", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/entity/player/Player;", "player", "Lnet/minecraft/world/InteractionHand;", "interactionHand", "Lnet/minecraft/world/phys/BlockHitResult;", "blockHitResult", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/util/RandomSource;", "random", "randomDisplayTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/world/level/block/Rotation;", "rotation", "rotate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Rotation;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nHealingMachineBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealingMachineBlock.kt\ncom/cobblemon/mod/common/block/HealingMachineBlock\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,205:1\n2624#2,3:206\n1855#2,2:209\n*S KotlinDebug\n*F\n+ 1 HealingMachineBlock.kt\ncom/cobblemon/mod/common/block/HealingMachineBlock\n*L\n144#1:206,3\n161#1:209,2\n*E\n"})
public final class HealingMachineBlock
extends BaseEntityBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final VoxelShape NORTH_SOUTH_AABB;
    private static final VoxelShape WEST_EAST_AABB;
    public static final int MAX_CHARGE_LEVEL = 5;
    @NotNull
    private static final IntegerProperty CHARGE_LEVEL;

    public HealingMachineBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)CHARGE_LEVEL, (Comparable)Integer.valueOf(0)));
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos2, @NotNull CollisionContext collisionContext) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)blockGetter, (String)"blockGetter");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)collisionContext, (String)"collisionContext");
        Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1: {
                VoxelShape voxelShape2 = WEST_EAST_AABB;
                voxelShape = voxelShape2;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"WEST_EAST_AABB");
                break;
            }
            case 2: {
                VoxelShape voxelShape3 = WEST_EAST_AABB;
                voxelShape = voxelShape3;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"WEST_EAST_AABB");
                break;
            }
            default: {
                VoxelShape voxelShape4 = NORTH_SOUTH_AABB;
                voxelShape = voxelShape4;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape4, (String)"NORTH_SOUTH_AABB");
            }
        }
        return voxelShape;
    }

    @NotNull
    public BlockEntity m_142194_(@NotNull BlockPos blockPos2, @NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return new HealingMachineBlockEntity(blockPos2, blockState);
    }

    @NotNull
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        Object object = this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_());
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"this.defaultState.with(H\u2026t.horizontalPlayerFacing)");
        return (BlockState)object;
    }

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
        propertyArray = new Property[]{CHARGE_LEVEL};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    public BlockState m_6843_(@NotNull BlockState blockState, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        Object object = blockState.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)rotation.m_55954_((Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"blockState.with(Horizont\u2026ntalFacingBlock.FACING)))");
        return (BlockState)object;
    }

    @NotNull
    public BlockState m_6943_(@NotNull BlockState blockState, @NotNull Mirror mirror) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)mirror, (String)"mirror");
        BlockState blockState2 = blockState.m_60717_(mirror.m_54846_((Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)));
        Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"blockState.rotate(mirror\u2026ntalFacingBlock.FACING)))");
        return blockState2;
    }

    public void m_6810_(@NotNull BlockState state, @NotNull Level world, @Nullable BlockPos pos, @NotNull BlockState newState, boolean moved) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)newState, (String)"newState");
        if (!state.m_60713_(newState.m_60734_())) {
            super.m_6810_(state, world, pos, newState, moved);
        }
    }

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPos2, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        boolean bl;
        PlayerPartyStore party;
        BlockEntity blockEntity;
        block13: {
            Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)interactionHand, (String)"interactionHand");
            Intrinsics.checkNotNullParameter((Object)blockHitResult, (String)"blockHitResult");
            if (world.f_46443_ || interactionHand == InteractionHand.OFF_HAND) {
                return InteractionResult.SUCCESS;
            }
            blockEntity = world.m_7702_(blockPos2);
            if (!(blockEntity instanceof HealingMachineBlockEntity)) {
                return InteractionResult.SUCCESS;
            }
            if (((HealingMachineBlockEntity)blockEntity).isInUse()) {
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.alreadyinuse", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.alreadyinuse\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                return InteractionResult.SUCCESS;
            }
            ServerPlayer serverPlayerEntity = (ServerPlayer)player;
            if (PlayerExtensionsKt.isInBattle(serverPlayerEntity)) {
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.inbattle", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.inbattle\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                return InteractionResult.SUCCESS;
            }
            party = PlayerExtensionsKt.party(serverPlayerEntity);
            if (CollectionsKt.none((Iterable)party)) {
                MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.nopokemon", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.nopokemon\")");
                player.m_5661_((Component)TextKt.red(mutableComponent), true);
                return InteractionResult.SUCCESS;
            }
            Iterable $this$none$iv = party;
            boolean $i$f$none = false;
            if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                bl = true;
            } else {
                for (Object element$iv : $this$none$iv) {
                    Pokemon pokemon = (Pokemon)element$iv;
                    boolean bl2 = false;
                    if (!pokemon.canBeHealed()) continue;
                    bl = false;
                    break block13;
                }
                bl = true;
            }
        }
        if (bl) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.alreadyhealed", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.alreadyhealed\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
            return InteractionResult.SUCCESS;
        }
        if (HealingMachineBlockEntity.Companion.isUsingHealer(player)) {
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.alreadyhealing", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.alreadyhealing\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
            return InteractionResult.SUCCESS;
        }
        if (((HealingMachineBlockEntity)blockEntity).canHeal((ServerPlayer)player)) {
            ((HealingMachineBlockEntity)blockEntity).activate((ServerPlayer)player);
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.healing", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.healing\")");
            player.m_5661_((Component)TextKt.green(mutableComponent), true);
        } else {
            float neededCharge = PlayerExtensionsKt.party((ServerPlayer)player).getHealingRemainderPercent() - ((HealingMachineBlockEntity)blockEntity).getHealingCharge();
            Object[] objectArray = new Object[]{(int)(neededCharge / (float)CollectionsKt.count((Iterable)party) * 100.0f) + "%"};
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("healingmachine.notenoughcharge", objectArray);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"healingmachine.not\u2026ount())*100f).toInt()}%\")");
            player.m_5661_((Component)TextKt.red(mutableComponent), true);
        }
        Iterable $this$forEach$iv = party;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon it = (Pokemon)element$iv;
            boolean bl3 = false;
            it.tryRecallWithAnimation();
        }
        return InteractionResult.CONSUME;
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos blockPos2, @NotNull BlockState blockState, @Nullable LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
        super.m_6402_(world, blockPos2, blockState, livingEntity, itemStack);
        if (!world.f_46443_ && livingEntity instanceof ServerPlayer && ((ServerPlayer)livingEntity).m_7500_()) {
            BlockEntity blockEntity = world.m_7702_(blockPos2);
            if (!(blockEntity instanceof HealingMachineBlockEntity)) {
                return;
            }
            ((HealingMachineBlockEntity)blockEntity).setInfinite(true);
        }
    }

    public void m_214162_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        BlockEntity blockEntity = world.m_7702_(pos);
        if (!(blockEntity instanceof HealingMachineBlockEntity)) {
            return;
        }
        if (random.m_188503_(2) == 0 && ((HealingMachineBlockEntity)blockEntity).getHealTimeLeft() > 0) {
            double posX = (double)pos.m_123341_() + 0.5 + (double)(random.m_188501_() * 0.3f * (float)(random.m_188503_(2) > 0 ? 1 : -1));
            double posY = (double)pos.m_123342_() + 0.9;
            double posZ = (double)pos.m_123343_() + 0.5 + (double)(random.m_188501_() * 0.3f * (float)(random.m_188503_(2) > 0 ? 1 : -1));
            world.m_7106_((ParticleOptions)ParticleTypes.f_123748_, posX, posY, posZ, 0.0, 0.0, 0.0);
        }
    }

    public boolean m_7278_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return true;
    }

    public int m_6782_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockEntity blockEntity = world.m_7702_(pos);
        HealingMachineBlockEntity healingMachineBlockEntity = blockEntity instanceof HealingMachineBlockEntity ? (HealingMachineBlockEntity)blockEntity : null;
        return healingMachineBlockEntity != null ? healingMachineBlockEntity.getCurrentSignal() : 0;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level world, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockWithEntityType) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter(blockWithEntityType, (String)"blockWithEntityType");
        BlockEntityTicker<HealingMachineBlockEntity> blockEntityTicker = HealingMachineBlockEntity.Companion.getTICKER$common();
        return BaseEntityBlock.m_152132_(blockWithEntityType, CobblemonBlockEntities.HEALING_MACHINE, (BlockEntityTicker)new BlockEntityTicker(blockEntityTicker){
            final /* synthetic */ BlockEntityTicker<HealingMachineBlockEntity> $tmp0;
            {
                this.$tmp0 = $tmp0;
            }

            public final void tick(Level p0, BlockPos p1, BlockState p2, HealingMachineBlockEntity p3) {
                this.$tmp0.m_155252_(p0, p1, p2, (BlockEntity)p3);
            }
        });
    }

    @NotNull
    public RenderShape m_7514_(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return RenderShape.MODEL;
    }

    public void m_5871_(@Nullable ItemStack stack, @Nullable BlockGetter world, @NotNull List<Component> tooltip, @Nullable TooltipFlag options) {
        Intrinsics.checkNotNullParameter(tooltip, (String)"tooltip");
        MutableComponent mutableComponent = MiscUtilsKt.asTranslated("block.cobblemon.healing_machine.tooltip1");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"block.${Cobblemon.MODID}\u2026.tooltip1\".asTranslated()");
        tooltip.add((Component)TextKt.gray(mutableComponent));
        MutableComponent mutableComponent2 = MiscUtilsKt.asTranslated("block.cobblemon.healing_machine.tooltip2");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"block.${Cobblemon.MODID}\u2026.tooltip2\".asTranslated()");
        tooltip.add((Component)TextKt.gray(mutableComponent2));
    }

    static {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.0625, (double)0.625, (double)0.0, (double)0.9375, (double)0.875, (double)0.125), Shapes.m_83048_((double)0.0625, (double)0.625, (double)0.875, (double)0.9375, (double)0.875, (double)1.0), Shapes.m_83048_((double)0.0625, (double)0.625, (double)0.125, (double)0.1875, (double)0.75, (double)0.875), Shapes.m_83048_((double)0.8125, (double)0.625, (double)0.125, (double)0.9375, (double)0.75, (double)0.875), Shapes.m_83048_((double)0.1875, (double)0.625, (double)0.125, (double)0.8125, (double)0.6875, (double)0.875)};
        NORTH_SOUTH_AABB = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.0, (double)1.0, (double)0.625, (double)1.0), (VoxelShape[])voxelShapeArray);
        voxelShapeArray = new VoxelShape[]{Shapes.m_83048_((double)0.875, (double)0.625, (double)0.0625, (double)1.0, (double)0.875, (double)0.9375), Shapes.m_83048_((double)0.0, (double)0.625, (double)0.0625, (double)0.125, (double)0.875, (double)0.9375), Shapes.m_83048_((double)0.125, (double)0.625, (double)0.0625, (double)0.875, (double)0.75, (double)0.1875), Shapes.m_83048_((double)0.125, (double)0.625, (double)0.8125, (double)0.875, (double)0.75, (double)0.9375), Shapes.m_83048_((double)0.125, (double)0.625, (double)0.1875, (double)0.875, (double)0.6875, (double)0.8125)};
        WEST_EAST_AABB = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.0, (double)1.0, (double)0.625, (double)1.0), (VoxelShape[])voxelShapeArray);
        IntegerProperty integerProperty = IntegerProperty.m_61631_((String)"charge", (int)0, (int)6);
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"of(\"charge\", 0, MAX_CHARGE_LEVEL + 1)");
        CHARGE_LEVEL = integerProperty;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u001c\u0010\f\u001a\n \u000b*\u0004\u0018\u00010\n0\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\n \u000b*\u0004\u0018\u00010\n0\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\r\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/block/HealingMachineBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "CHARGE_LEVEL", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getCHARGE_LEVEL", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "MAX_CHARGE_LEVEL", "I", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "NORTH_SOUTH_AABB", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "WEST_EAST_AABB", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IntegerProperty getCHARGE_LEVEL() {
            return CHARGE_LEVEL;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Direction.values().length];
            try {
                nArray[Direction.WEST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.EAST.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

