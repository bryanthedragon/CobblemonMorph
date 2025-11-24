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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLink;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PastureLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissionControllers;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PreEmptsExplosion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.VectorShapeExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00e0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 e2\u00020\u00012\u00020\u00022\u00020\u0003:\u0002efB\u000f\u0012\u0006\u0010b\u001a\u00020a\u00a2\u0006\u0004\bc\u0010dJ#\u0010\t\u001a\u00020\b2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0014\u00a2\u0006\u0004\b\t\u0010\nJ/\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J'\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ%\u0010\u001c\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u000e\u00a2\u0006\u0004\b\u001c\u0010\u001dJ!\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u001d\u0010!\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u000e\u00a2\u0006\u0004\b!\u0010\"J\u0019\u0010$\u001a\u0004\u0018\u00010#2\u0006\u0010\u0015\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b$\u0010%J/\u0010)\u001a\u00020(2\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010'\u001a\u00020&H\u0017\u00a2\u0006\u0004\b)\u0010*J\u0019\u0010-\u001a\u0004\u0018\u00010\u00062\u0006\u0010,\u001a\u00020+H\u0016\u00a2\u0006\u0004\b-\u0010.J\u001d\u0010/\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u000e\u00a2\u0006\u0004\b/\u0010\"J\u0017\u00101\u001a\u0002002\u0006\u0010\u0015\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b1\u00102J?\u00107\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u00104\u001a\u0002032\u0006\u00105\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u001b2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u00106\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b7\u00108JA\u0010?\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010>\"\n\b\u0000\u0010:*\u0004\u0018\u0001092\u0006\u0010\u0017\u001a\u00020;2\u0006\u0010\u0015\u001a\u00020\u00062\f\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000<H\u0016\u00a2\u0006\u0004\b?\u0010@J\u0017\u0010A\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\bA\u0010BJ\u001f\u0010D\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010D\u001a\u00020CH\u0016\u00a2\u0006\u0004\bD\u0010EJ1\u0010H\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020;2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00062\b\u0010G\u001a\u0004\u0018\u00010FH\u0016\u00a2\u0006\u0004\bH\u0010IJ;\u0010N\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020;2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00062\b\u0010K\u001a\u0004\u0018\u00010J2\b\u0010M\u001a\u0004\u0018\u00010LH\u0016\u00a2\u0006\u0004\bN\u0010OJ9\u0010R\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020;2\b\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\u0006\u0010P\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\bR\u0010SJ?\u0010Y\u001a\u00020X2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020;2\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010G\u001a\u00020F2\u0006\u0010U\u001a\u00020T2\u0006\u0010W\u001a\u00020VH\u0017\u00a2\u0006\u0004\bY\u0010ZJ\u001f\u0010]\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\\\u001a\u00020[H\u0016\u00a2\u0006\u0004\b]\u0010^J'\u0010_\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020;2\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b_\u0010`\u00a8\u0006g"}, d2={"Lcom/cobblemon/mod/common/block/PastureBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/world/level/block/SimpleWaterloggedBlock;", "Lcom/cobblemon/mod/common/block/PreEmptsExplosion;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "blockState", "Lnet/minecraft/world/level/BlockGetter;", "blockGetter", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "pathComputationType", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "state", "Lnet/minecraft/world/level/LevelReader;", "world", "pos", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/level/LevelAccessor;", "checkBreakEntity", "(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "getBasePosition", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/level/material/FluidState;", "getFluidState", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/material/FluidState;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "collisionContext", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "getPositionOfOtherPart", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/Level;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTicker", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "isBase", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/level/block/Mirror;", "mirror", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Mirror;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "newState", "moved", "onStateReplaced", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/level/block/Rotation;", "rotation", "rotate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Rotation;)Lnet/minecraft/world/level/block/state/BlockState;", "whenExploded", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "PasturePart", "common"})
@SourceDebugExtension(value={"SMAP\nPastureBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PastureBlock.kt\ncom/cobblemon/mod/common/block/PastureBlock\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,345:1\n1#2:346\n1#2:357\n1603#3,9:347\n1855#3:356\n1856#3:358\n1612#3:359\n*S KotlinDebug\n*F\n+ 1 PastureBlock.kt\ncom/cobblemon/mod/common/block/PastureBlock\n*L\n268#1:357\n268#1:347,9\n268#1:356\n268#1:358\n268#1:359\n*E\n"})
public final class PastureBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock,
PreEmptsExplosion {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final EnumProperty<PasturePart> PART;
    @NotNull
    private static final BooleanProperty ON;
    @NotNull
    private static final BooleanProperty WATERLOGGED;
    @NotNull
    private static final VoxelShape SOUTH_AABB_TOP;
    @NotNull
    private static final VoxelShape NORTH_AABB_TOP;
    @NotNull
    private static final VoxelShape WEST_AABB_TOP;
    @NotNull
    private static final VoxelShape EAST_AABB_TOP;
    @NotNull
    private static final VoxelShape SOUTH_AABB_BOTTOM;
    @NotNull
    private static final VoxelShape NORTH_AABB_BOTTOM;
    @NotNull
    private static final VoxelShape WEST_AABB_BOTTOM;
    @NotNull
    private static final VoxelShape EAST_AABB_BOTTOM;

    public PastureBlock(@NotNull BlockBehaviour.Properties properties2) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        super(properties2);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)PART, (Comparable)((Object)PasturePart.BOTTOM))).m_61124_((Property)ON, (Comparable)Boolean.valueOf(false)));
    }

    @Nullable
    public PokemonPastureBlockEntity createBlockEntity(@NotNull BlockPos blockPos2, @NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return blockState.m_61143_((Property)PART) == PasturePart.BOTTOM ? new PokemonPastureBlockEntity(blockPos2, blockState) : null;
    }

    @NotNull
    public RenderShape m_7514_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return RenderShape.MODEL;
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        BlockPos abovePosition = blockPlaceContext.m_8083_().m_7494_();
        Level world = blockPlaceContext.m_43725_();
        if (world.m_8055_(abovePosition).m_60629_(blockPlaceContext) && !world.m_151570_(abovePosition)) {
            return (BlockState)((BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_())).m_61124_((Property)PART, (Comparable)((Object)PasturePart.BOTTOM))).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_()).m_76152_(), (Object)Fluids.f_76193_)));
        }
        return null;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return true;
    }

    @NotNull
    public final BlockPos getPositionOfOtherPart(@NotNull BlockState state, @NotNull BlockPos pos) {
        BlockPos blockPos2;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (state.m_61138_((Property)PART) && state.m_61143_((Property)PART) == PasturePart.BOTTOM) {
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
        return state.m_61138_((Property)PART) && state.m_61143_((Property)PART) == PasturePart.BOTTOM;
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
        propertyArray = new Property[]{PART};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{ON};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{WATERLOGGED};
        builder.m_61104_(propertyArray);
    }

    public final void checkBreakEntity(@NotNull LevelAccessor world, @NotNull BlockState state, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        if (state.m_61143_((Property)PART) == PasturePart.TOP) {
            return;
        }
        BlockEntity blockEntity = world.m_7702_(pos);
        if (blockEntity instanceof PokemonPastureBlockEntity) {
            ((PokemonPastureBlockEntity)blockEntity).onBroken();
        }
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        this.checkBreakEntity((LevelAccessor)world, state, pos);
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
                PasturePart part = (PasturePart)((Object)state.m_61143_((Property)PART));
                if (part == PasturePart.TOP) {
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
                    if (blockPos4.m_60713_(state.m_60734_()) && blockState.m_61143_((Property)PART) == PasturePart.BOTTOM) {
                        this.checkBreakEntity((LevelAccessor)world, blockState, blockPos2);
                        BlockState blockState2 = blockState.m_60819_().m_192917_((Fluid)Fluids.f_76193_) ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_();
                        world.m_7731_(blockPos2, blockState2, 35);
                        world.m_5898_(player, 2001, blockPos2, BaseEntityBlock.m_49956_((BlockState)blockState));
                    }
                }
            }
        }
        super.m_5707_(world, pos, state, player);
    }

    @Override
    public void whenExploded(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockEntity blockEntity = world.m_7702_(pos);
        PokemonPastureBlockEntity pokemonPastureBlockEntity = blockEntity instanceof PokemonPastureBlockEntity ? (PokemonPastureBlockEntity)blockEntity : null;
        if (pokemonPastureBlockEntity == null) {
            return;
        }
        PokemonPastureBlockEntity blockEntity2 = pokemonPastureBlockEntity;
        blockEntity2.onBroken();
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level world, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        BlockEntityTicker<PokemonPastureBlockEntity> blockEntityTicker = PokemonPastureBlockEntity.Companion.getTICKER$common();
        return BaseEntityBlock.m_152132_(type, CobblemonBlockEntities.PASTURE, (BlockEntityTicker)new BlockEntityTicker(blockEntityTicker){
            final /* synthetic */ BlockEntityTicker<PokemonPastureBlockEntity> $tmp0;
            {
                this.$tmp0 = $tmp0;
            }

            public final void tick(Level p0, BlockPos p1, BlockState p2, PokemonPastureBlockEntity p3) {
                this.$tmp0.m_155252_(p0, p1, p2, (BlockEntity)p3);
            }
        });
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @Nullable ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        BlockPos blockPos2 = pos.m_7494_();
        Object object = ((BlockState)state.m_61124_((Property)PART, (Comparable)((Object)PasturePart.TOP))).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)world.m_6425_(pos.m_7494_()).m_76152_(), (Object)Fluids.f_76193_)));
        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
        world.m_7731_(blockPos2, (BlockState)object, 3);
        world.m_6289_(pos, Blocks.f_50016_);
        state.m_60701_((LevelAccessor)world, pos, 3);
        if (world instanceof ServerLevel && placer instanceof ServerPlayer) {
            BlockEntity blockEntity = world.m_7702_(pos);
            PokemonPastureBlockEntity pokemonPastureBlockEntity = blockEntity instanceof PokemonPastureBlockEntity ? (PokemonPastureBlockEntity)blockEntity : null;
            if (pokemonPastureBlockEntity == null) {
                return;
            }
            PokemonPastureBlockEntity blockEntity2 = pokemonPastureBlockEntity;
            blockEntity2.setOwnerId(((ServerPlayer)placer).m_20148_());
            String string = ((ServerPlayer)placer).m_36316_().getName();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"placer.gameProfile.name");
            blockEntity2.setOwnerName(string);
            blockEntity2.m_6596_();
        }
    }

    /*
     * WARNING - void declaration
     */
    @Deprecated(message="Deprecated in Java")
    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        if (player instanceof ServerPlayer && !PlayerExtensionsKt.isInBattle((ServerPlayer)player)) {
            List list;
            void $this$mapNotNullTo$iv$iv;
            void $this$mapNotNull$iv;
            BlockPos basePos = this.getBasePosition(state, pos);
            BlockEntity blockEntity = world.m_7702_(basePos.m_7494_());
            if (blockEntity != null) {
                blockEntity.m_7651_();
            }
            BlockEntity baseEntity = world.m_7702_(basePos);
            if (!(baseEntity instanceof PokemonPastureBlockEntity)) {
                return InteractionResult.SUCCESS;
            }
            PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
            UUID uUID = ((ServerPlayer)player).m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
            UUID pcId = pokemonStoreManager.getPC(uUID).getUuid();
            UUID linkId = UUID.randomUUID();
            PasturePermissions perms = PasturePermissionControllers.INSTANCE.permit((ServerPlayer)player, (PokemonPastureBlockEntity)baseEntity);
            int n = ((PokemonPastureBlockEntity)baseEntity).getMaxTethered();
            Iterable iterable = ((PokemonPastureBlockEntity)baseEntity).getTetheredPokemon();
            ServerPlayer serverPlayer = (ServerPlayer)player;
            CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
            boolean $i$f$mapNotNull = false;
            void var16_17 = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                OpenPasturePacket.PasturePokemonDataDTO it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl = false;
                PokemonPastureBlockEntity.Tethering it = (PokemonPastureBlockEntity.Tethering)element$iv$iv;
                boolean bl2 = false;
                if (it.toDTO((ServerPlayer)player) == null) continue;
                boolean bl3 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            List list2 = list = (List)destination$iv$iv;
            Intrinsics.checkNotNullExpressionValue((Object)linkId, (String)"linkId");
            cobblemonNetwork.sendPacketToPlayer(serverPlayer, new OpenPasturePacket(pcId, linkId, n, list2, perms));
            UUID uUID2 = ((ServerPlayer)player).m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
            ResourceLocation resourceLocation = world.m_220362_().m_135782_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"world.dimensionKey.value");
            PastureLinkManager.INSTANCE.createLink(uUID2, new PastureLink(linkId, pcId, resourceLocation, this.getBasePosition(state, pos), perms));
            WorldExtensionsKt.playSoundServer$default(world, BlockPosExtensionsKt.toVec3d(pos), CobblemonSounds.PC_ON, null, 0.5f, 1.0f, 4, null);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos2, @NotNull CollisionContext collisionContext) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)blockGetter, (String)"blockGetter");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)collisionContext, (String)"collisionContext");
        if (blockState.m_61143_((Property)PART) == PasturePart.TOP) {
            Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
            switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                case 1: {
                    voxelShape = SOUTH_AABB_TOP;
                    break;
                }
                case 2: {
                    voxelShape = WEST_AABB_TOP;
                    break;
                }
                case 3: {
                    voxelShape = EAST_AABB_TOP;
                    break;
                }
                default: {
                    voxelShape = NORTH_AABB_TOP;
                    break;
                }
            }
        } else {
            Direction direction = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
            switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
                case 1: {
                    voxelShape = SOUTH_AABB_BOTTOM;
                    break;
                }
                case 2: {
                    voxelShape = WEST_AABB_BOTTOM;
                    break;
                }
                case 3: {
                    voxelShape = EAST_AABB_BOTTOM;
                    break;
                }
                default: {
                    voxelShape = NORTH_AABB_BOTTOM;
                }
            }
        }
        return voxelShape;
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
        boolean isPasture = neighborState.m_60713_((Block)this);
        PasturePart part = (PasturePart)((Object)state.m_61143_((Property)PART));
        if (!isPasture && part == PasturePart.TOP && Intrinsics.areEqual((Object)neighborPos, (Object)pos.m_7495_())) {
            BlockState blockState = Blocks.f_50016_.m_49966_();
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"AIR.defaultState");
            return blockState;
        }
        if (!isPasture && part == PasturePart.BOTTOM && Intrinsics.areEqual((Object)neighborPos, (Object)pos.m_7494_())) {
            this.checkBreakEntity(world, state, pos);
            BlockState blockState = Blocks.f_50016_.m_49966_();
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"AIR.defaultState");
            return blockState;
        }
        return state;
    }

    static {
        EnumProperty enumProperty = EnumProperty.m_61587_((String)"part", PasturePart.class);
        Intrinsics.checkNotNullExpressionValue((Object)enumProperty, (String)"of(\"part\", PasturePart::class.java)");
        PART = enumProperty;
        BooleanProperty booleanProperty = BooleanProperty.m_61465_((String)"on");
        Intrinsics.checkNotNullExpressionValue((Object)booleanProperty, (String)"of(\"on\")");
        ON = booleanProperty;
        BooleanProperty booleanProperty2 = BooleanProperty.m_61465_((String)"waterlogged");
        Intrinsics.checkNotNullExpressionValue((Object)booleanProperty2, (String)"of(\"waterlogged\")");
        WATERLOGGED = booleanProperty2;
        SOUTH_AABB_TOP = PastureBlock.Companion.buildCollider(true, Direction.NORTH);
        NORTH_AABB_TOP = PastureBlock.Companion.buildCollider(true, Direction.SOUTH);
        WEST_AABB_TOP = PastureBlock.Companion.buildCollider(true, Direction.WEST);
        EAST_AABB_TOP = PastureBlock.Companion.buildCollider(true, Direction.EAST);
        SOUTH_AABB_BOTTOM = PastureBlock.Companion.buildCollider(false, Direction.SOUTH);
        NORTH_AABB_BOTTOM = PastureBlock.Companion.buildCollider(false, Direction.NORTH);
        WEST_AABB_BOTTOM = PastureBlock.Companion.buildCollider(false, Direction.WEST);
        EAST_AABB_BOTTOM = PastureBlock.Companion.buildCollider(false, Direction.EAST);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\nR\u0014\u0010\u001a\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\nR\u0017\u0010\u001b\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0010\u001a\u0004\b\u001c\u0010\u0012R\u0014\u0010\u001d\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\nR\u0014\u0010\u001e\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\n\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/block/PastureBlock$Companion;", "", "", "top", "Lnet/minecraft/core/Direction;", "direction", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "buildCollider", "(ZLnet/minecraft/core/Direction;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "EAST_AABB_BOTTOM", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "EAST_AABB_TOP", "NORTH_AABB_BOTTOM", "NORTH_AABB_TOP", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "ON", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getON", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "Lcom/cobblemon/mod/common/block/PastureBlock$PasturePart;", "PART", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getPART", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "SOUTH_AABB_BOTTOM", "SOUTH_AABB_TOP", "WATERLOGGED", "getWATERLOGGED", "WEST_AABB_BOTTOM", "WEST_AABB_TOP", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final EnumProperty<PasturePart> getPART() {
            return PART;
        }

        @NotNull
        public final BooleanProperty getON() {
            return ON;
        }

        @NotNull
        public final BooleanProperty getWATERLOGGED() {
            return WATERLOGGED;
        }

        private final VoxelShape buildCollider(boolean top, Direction direction) {
            if (top) {
                VoxelShape[] voxelShapeArray = new VoxelShape[]{VectorShapeExtensionsKt.voxelShape(0.125, 0.0, 0.375, 0.875, 0.1875, 0.9375, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.1875, 0.4375, 0.8125, 0.6875, 0.9375, direction), VectorShapeExtensionsKt.voxelShape(0.8125, 0.1875, 0.375, 0.875, 0.6875, 0.9375, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.1875, 0.375, 0.1875, 0.6875, 0.9375, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.6875, 0.375, 0.875, 0.75, 0.9375, direction)};
                VoxelShape voxelShape = Shapes.m_83124_((VoxelShape)VectorShapeExtensionsKt.voxelShape(0.1875, 0.0, 0.0625, 0.8125, 0.0625, 0.3125, direction), (VoxelShape[])voxelShapeArray);
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"union(\n                 \u2026ection)\n                )");
                return voxelShape;
            }
            VoxelShape[] voxelShapeArray = new VoxelShape[]{VectorShapeExtensionsKt.voxelShape(0.125, 0.0, 0.0, 0.875, 0.125, 0.125, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.875, 0.0, 0.875, 1.0, 0.125, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.125, 0.0625, 0.875, 0.875, 0.125, direction), VectorShapeExtensionsKt.voxelShape(0.0625, 0.125, 0.125, 0.125, 0.875, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.0, 0.0, 0.125, 1.0, 0.125, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.0, 0.125, 0.875, 0.125, 1.0, direction), VectorShapeExtensionsKt.voxelShape(0.875, 0.125, 0.125, 0.9375, 0.875, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.125, 0.875, 0.125, 0.875, 1.0, 1.0, direction), VectorShapeExtensionsKt.voxelShape(0.875, 0.0, 0.875, 1.0, 1.0, 1.0, direction), VectorShapeExtensionsKt.voxelShape(0.875, 0.0, 0.125, 1.0, 0.125, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.875, 0.875, 0.125, 1.0, 1.0, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.875, 0.125, 0.125, 1.0, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.0, 0.125, 0.125, 0.125, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.0, 0.875, 0.125, 1.0, 1.0, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.125, 0.375, 0.0625, 0.875, 0.625, direction), VectorShapeExtensionsKt.voxelShape(0.9375, 0.125, 0.375, 1.0, 0.875, 0.625, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.1875, 0.05625, 0.8125, 0.75, 0.05625, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.125, 0.3125, 0.8125, 0.3125, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.125, 0.3125, 0.8125, 0.3125, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.0625, 0.875, 0.8125, 0.25, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.25, 0.25, 0.1875, 0.4375, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.8125, 0.25, 0.25, 0.8125, 0.4375, 0.875, direction), VectorShapeExtensionsKt.voxelShape(0.1875, 0.3125, 0.3125, 0.8125, 0.5, 0.3125, direction), VectorShapeExtensionsKt.voxelShape(0.25, 0.75, 0.3125, 0.75, 1.0, 0.8125, direction), VectorShapeExtensionsKt.voxelShape(0.0, 0.0, 0.0, 0.0625, 1.0, 1.0, direction), VectorShapeExtensionsKt.voxelShape(0.9375, 0.0, 0.0, 1.0, 1.0, 1.0, direction)};
            VoxelShape voxelShape = Shapes.m_83124_((VoxelShape)VectorShapeExtensionsKt.voxelShape(0.875, 0.0, 0.0, 1.0, 1.0, 0.125, direction), (VoxelShape[])voxelShapeArray);
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"union(\n                 \u2026ction),\n                )");
            return voxelShape;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\u0011\b\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00038\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007j\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/block/PastureBlock$PasturePart;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "label", "Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "TOP", "BOTTOM", "common"})
    public static final class PasturePart
    extends Enum<PasturePart>
    implements StringRepresentable {
        @NotNull
        private final String label;
        public static final /* enum */ PasturePart TOP = new PasturePart("top");
        public static final /* enum */ PasturePart BOTTOM = new PasturePart("bottom");
        private static final /* synthetic */ PasturePart[] $VALUES;

        private PasturePart(String label) {
            this.label = label;
        }

        @NotNull
        public String m_7912_() {
            return this.label;
        }

        public static PasturePart[] values() {
            return (PasturePart[])$VALUES.clone();
        }

        public static PasturePart valueOf(String value2) {
            return Enum.valueOf(PasturePart.class, value2);
        }

        static {
            $VALUES = pasturePartArray = new PasturePart[]{PasturePart.TOP, PasturePart.BOTTOM};
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

