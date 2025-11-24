/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  net.minecraft.world.level.gameevent.GameEvent$Context
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.EntityCollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.farming.ApricornHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ShearableBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.Arrays;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00c0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 W2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001WB\u0017\u0012\u0006\u0010T\u001a\u00020S\u0012\u0006\u0010O\u001a\u00020N\u00a2\u0006\u0004\bU\u0010VJ#\u0010\t\u001a\u00020\b2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0014\u00a2\u0006\u0004\b\t\u0010\nJ5\u0010\u0013\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J/\u0010\u0017\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J/\u0010\u001c\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ'\u0010\u001f\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0017\u00a2\u0006\u0004\b\u001f\u0010 J/\u0010#\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020!H\u0002\u00a2\u0006\u0004\b#\u0010$J/\u0010(\u001a\u00020'2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020%H\u0017\u00a2\u0006\u0004\b(\u0010)J/\u0010*\u001a\u00020'2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020%H\u0017\u00a2\u0006\u0004\b*\u0010)J'\u0010,\u001a\u00020+2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b,\u0010-J\u0019\u00100\u001a\u0004\u0018\u00010\u00062\u0006\u0010/\u001a\u00020.H\u0016\u00a2\u0006\u0004\b0\u00101JA\u00107\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u00103\u001a\u0002022\u0006\u00104\u001a\u00020\u00062\u0006\u0010\f\u001a\u0002052\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u00106\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b7\u00108J/\u0010:\u001a\u00020\b2\u0006\u0010\f\u001a\u0002092\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b:\u0010;J%\u0010<\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010>\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b>\u0010?J/\u0010A\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\bA\u0010BJ/\u0010C\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\bC\u0010DJ?\u0010J\u001a\u00020I2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020!2\u0006\u0010F\u001a\u00020E2\u0006\u0010H\u001a\u00020GH\u0016\u00a2\u0006\u0004\bJ\u0010KJ/\u0010L\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u0002092\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u0015H\u0017\u00a2\u0006\u0004\bL\u0010MR\u0017\u0010O\u001a\u00020N8\u0006\u00a2\u0006\f\n\u0004\bO\u0010P\u001a\u0004\bQ\u0010R\u00a8\u0006X"}, d2={"Lcom/cobblemon/mod/common/block/ApricornBlock;", "Lnet/minecraft/world/level/block/HorizontalDirectionalBlock;", "Lnet/minecraft/world/level/block/BonemealableBlock;", "Lcom/cobblemon/mod/common/block/ShearableBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/world/level/Level;", "world", "state", "Lnet/minecraft/core/BlockPos;", "pos", "Lkotlin/Function0;", "successCallback", "", "attemptShear", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lkotlin/jvm/functions/Function0;)Z", "Lnet/minecraft/util/RandomSource;", "random", "canGrow", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "type", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lnet/minecraft/world/entity/player/Player;", "player", "doHarvest", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getCollisionShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "Lnet/minecraft/world/item/ItemStack;", "getPickStack", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "ctx", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/server/level/ServerLevel;", "grow", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "harvest", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "hasRandomTicks", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "isClient", "isFertilizable", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z", "onBlockBreakStart", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "apricorn", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "getApricorn", "()Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;Lcom/cobblemon/mod/common/api/apricorn/Apricorn;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nApricornBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ApricornBlock.kt\ncom/cobblemon/mod/common/block/ApricornBlock\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,312:1\n13579#2,2:313\n13579#2:320\n13580#2:322\n14#3,5:315\n19#3:323\n14#4:321\n*S KotlinDebug\n*F\n+ 1 ApricornBlock.kt\ncom/cobblemon/mod/common/block/ApricornBlock\n*L\n98#1:313,2\n156#1:320\n156#1:322\n156#1:315,5\n156#1:323\n156#1:321\n*E\n"})
public final class ApricornBlock
extends HorizontalDirectionalBlock
implements BonemealableBlock,
ShearableBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Apricorn apricorn;
    @NotNull
    private static final IntegerProperty AGE;
    public static final int MAX_AGE = 3;
    public static final int MIN_AGE = 0;
    private static final VoxelShape NORTH_TOP_STAGE_1;
    private static final VoxelShape NORTH_BODY_STAGE_1;
    private static final VoxelShape NORTH_BOTTOM_STAGE_1;
    private static final VoxelShape NORTH_TOP_STAGE_2;
    private static final VoxelShape NORTH_BODY_STAGE_2;
    private static final VoxelShape NORTH_BOTTOM_STAGE_2;
    private static final VoxelShape NORTH_TOP_STAGE_3;
    private static final VoxelShape NORTH_BODY_STAGE_3;
    private static final VoxelShape NORTH_BOTTOM_STAGE_3;
    private static final VoxelShape NORTH_TOP_FRUIT;
    private static final VoxelShape NORTH_BODY_FRUIT;
    private static final VoxelShape NORTH_BOTTOM_FRUIT;
    @NotNull
    private static final VoxelShape[] NORTH_AABB;
    private static final VoxelShape SOUTH_TOP_STAGE_1;
    private static final VoxelShape SOUTH_BODY_STAGE_1;
    private static final VoxelShape SOUTH_BOTTOM_STAGE_1;
    private static final VoxelShape SOUTH_TOP_STAGE_2;
    private static final VoxelShape SOUTH_BODY_STAGE_2;
    private static final VoxelShape SOUTH_BOTTOM_STAGE_2;
    private static final VoxelShape SOUTH_TOP_STAGE_3;
    private static final VoxelShape SOUTH_BODY_STAGE_3;
    private static final VoxelShape SOUTH_BOTTOM_STAGE_3;
    private static final VoxelShape SOUTH_TOP_FRUIT;
    private static final VoxelShape SOUTH_BODY_FRUIT;
    private static final VoxelShape SOUTH_BOTTOM_FRUIT;
    @NotNull
    private static final VoxelShape[] SOUTH_AABB;
    private static final VoxelShape EAST_TOP_STAGE_1;
    private static final VoxelShape EAST_BODY_STAGE_1;
    private static final VoxelShape EAST_BOTTOM_STAGE_1;
    private static final VoxelShape EAST_TOP_STAGE_2;
    private static final VoxelShape EAST_BODY_STAGE_2;
    private static final VoxelShape EAST_BOTTOM_STAGE_2;
    private static final VoxelShape EAST_TOP_STAGE_3;
    private static final VoxelShape EAST_BODY_STAGE_3;
    private static final VoxelShape EAST_BOTTOM_STAGE_3;
    private static final VoxelShape EAST_TOP_FRUIT;
    private static final VoxelShape EAST_BODY_FRUIT;
    private static final VoxelShape EAST_BOTTOM_FRUIT;
    @NotNull
    private static final VoxelShape[] EAST_AABB;
    private static final VoxelShape WEST_TOP_STAGE_1;
    private static final VoxelShape WEST_BODY_STAGE_1;
    private static final VoxelShape WEST_BOTTOM_STAGE_1;
    private static final VoxelShape WEST_TOP_STAGE_2;
    private static final VoxelShape WEST_BODY_STAGE_2;
    private static final VoxelShape WEST_BOTTOM_STAGE_2;
    private static final VoxelShape WEST_TOP_STAGE_3;
    private static final VoxelShape WEST_BODY_STAGE_3;
    private static final VoxelShape WEST_BOTTOM_STAGE_3;
    private static final VoxelShape WEST_TOP_FRUIT;
    private static final VoxelShape WEST_BODY_FRUIT;
    private static final VoxelShape WEST_BOTTOM_FRUIT;
    @NotNull
    private static final VoxelShape[] WEST_AABB;

    public ApricornBlock(@NotNull BlockBehaviour.Properties settings, @NotNull Apricorn apricorn) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        Intrinsics.checkNotNullParameter((Object)((Object)apricorn), (String)"apricorn");
        super(settings);
        this.apricorn = apricorn;
        this.m_49959_((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.NORTH)).m_61124_((Property)AGE, (Comparable)Integer.valueOf(0)));
    }

    @NotNull
    public final Apricorn getApricorn() {
        return this.apricorn;
    }

    public boolean m_6724_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)AGE);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(AGE)");
        return ((Number)((Object)comparable)).intValue() < 3;
    }

    @Deprecated(message="Deprecated in Java")
    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (world.f_46441_.m_188503_(5) == 0) {
            Integer currentAge = (Integer)state.m_61143_((Property)AGE);
            Intrinsics.checkNotNullExpressionValue((Object)currentAge, (String)"currentAge");
            if (currentAge < 3) {
                world.m_7731_(pos, (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(currentAge + 1)), 2);
            }
        }
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Comparable comparable = state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type net.minecraft.util.math.Direction");
        BlockState blockState = world.m_8055_(pos.m_121945_((Direction)comparable));
        return blockState.m_204336_(CobblemonBlockTags.APRICORN_LEAVES);
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Integer age = (Integer)state.m_61143_((Property)AGE);
        Direction direction = (Direction)state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1: {
                Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
                VoxelShape voxelShape2 = NORTH_AABB[age];
                voxelShape = voxelShape2;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"NORTH_AABB[age]");
                break;
            }
            case 2: {
                Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
                VoxelShape voxelShape3 = EAST_AABB[age];
                voxelShape = voxelShape3;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"EAST_AABB[age]");
                break;
            }
            case 3: {
                Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
                VoxelShape voxelShape4 = SOUTH_AABB[age];
                voxelShape = voxelShape4;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape4, (String)"SOUTH_AABB[age]");
                break;
            }
            case 4: {
                Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
                VoxelShape voxelShape5 = WEST_AABB[age];
                voxelShape = voxelShape5;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape5, (String)"WEST_AABB[age]");
                break;
            }
            default: {
                Intrinsics.checkNotNullExpressionValue((Object)age, (String)"age");
                VoxelShape voxelShape6 = NORTH_AABB[age];
                voxelShape = voxelShape6;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape6, (String)"NORTH_AABB[age]");
            }
        }
        return voxelShape;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5939_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (context instanceof EntityCollisionContext) {
            Entity entity2 = ((EntityCollisionContext)context).m_193113_();
            ItemEntity itemEntity = entity2 instanceof ItemEntity ? (ItemEntity)entity2 : null;
            boolean bl = itemEntity != null && (itemEntity = itemEntity.m_32055_()) != null ? itemEntity.m_204117_(CobblemonItemTags.APRICORNS) : false;
            if (bl) {
                VoxelShape voxelShape = Shapes.m_83040_();
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"empty()");
                return voxelShape;
            }
        }
        VoxelShape voxelShape = super.m_5939_(state, world, pos, context);
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"super.getCollisionShape(\u2026ate, world, pos, context)");
        return voxelShape;
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
            Object object = blockState.m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)direction);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            blockState = (BlockState)object;
            if (!blockState.m_60710_((LevelReader)worldView, blockPos2)) continue;
            return blockState;
        }
        return null;
    }

    @Nullable
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        return direction == state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_) && !state.m_60710_((LevelReader)world, pos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
    }

    public boolean m_7370_(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)AGE);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(AGE)");
        return ((Number)((Object)comparable)).intValue() < 3;
    }

    public boolean m_214167_(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return true;
    }

    public void m_214148_(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        world.m_7731_(pos, (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(((Number)((Object)state.m_61143_((Property)AGE))).intValue() + 1)), 2);
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{HorizontalDirectionalBlock.f_54117_, AGE};
        builder.m_61104_(propertyArray);
    }

    public boolean m_7357_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        return false;
    }

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        Integer n = (Integer)state.m_61143_((Property)AGE);
        int n2 = 3;
        if (n == null || n != n2) {
            InteractionResult interactionResult = super.m_6227_(state, world, pos, player, hand, hit);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.onUse(state, world, pos, player, hand, hit)");
            return interactionResult;
        }
        this.doHarvest(world, state, pos, player);
        return InteractionResult.SUCCESS;
    }

    public void m_6256_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Integer n = (Integer)state.m_61143_((Property)AGE);
        int n2 = 3;
        if (n == null || n != n2) {
            super.m_6256_(state, world, pos, player);
            return;
        }
        this.doHarvest(world, state, pos, player);
    }

    @NotNull
    public ItemStack m_7397_(@NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new ItemStack((ItemLike)this.apricorn.item());
    }

    /*
     * WARNING - void declaration
     */
    private final void doHarvest(Level world, BlockState state, BlockPos pos, Player player) {
        BlockState resetState = this.harvest(world, state, pos);
        world.m_220407_(GameEvent.f_157792_, pos, GameEvent.Context.m_223719_((Entity)((Entity)player), (BlockState)resetState));
        if (!world.f_46443_) {
            Vec3 vec3 = BlockPosExtensionsKt.toVec3d(pos);
            SoundEvent soundEvent = SoundEvents.f_12019_;
            Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"ENTITY_ITEM_PICKUP");
            WorldExtensionsKt.playSoundServer$default(world, vec3, soundEvent, null, 0.7f, 1.4f, 4, null);
            if (world instanceof ServerLevel && player instanceof ServerPlayer) {
                void $this$iv;
                EventObservable<ApricornHarvestEvent> eventObservable = CobblemonEvents.APRICORN_HARVESTED;
                ApricornHarvestEvent[] apricornHarvestEventArray = new ApricornHarvestEvent[]{new ApricornHarvestEvent((ServerPlayer)player, this.apricorn, (ServerLevel)world, pos)};
                ApricornHarvestEvent[] events$iv = apricornHarvestEventArray;
                boolean $i$f$post = false;
                $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                ApricornHarvestEvent[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach = false;
                int n = $this$forEach$iv$iv.length;
                for (int i = 0; i < n; ++i) {
                    ApricornHarvestEvent element$iv$iv;
                    ApricornHarvestEvent apricornHarvestEvent = element$iv$iv = $this$forEach$iv$iv[i];
                    boolean bl = false;
                    ApricornHarvestEvent it = apricornHarvestEvent;
                }
            }
        }
    }

    @NotNull
    public final BlockState harvest(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Block.m_49950_((BlockState)state, (Level)world, (BlockPos)pos);
        BlockState resetState = (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(0));
        world.m_7731_(pos, resetState, 2);
        Intrinsics.checkNotNullExpressionValue((Object)resetState, (String)"resetState");
        return resetState;
    }

    @Override
    public boolean attemptShear(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Function0<Unit> successCallback) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(successCallback, (String)"successCallback");
        Integer n = (Integer)state.m_61143_((Property)AGE);
        int n2 = 3;
        if (n == null || n != n2) {
            return false;
        }
        world.m_5594_(null, pos, SoundEvents.f_12344_, SoundSource.BLOCKS, 1.0f, 1.0f);
        this.harvest(world, state, pos);
        successCallback.invoke();
        world.m_142346_(null, GameEvent.f_157781_, pos);
        return true;
    }

    static {
        IntegerProperty integerProperty = BlockStateProperties.f_61407_;
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"AGE_3");
        AGE = integerProperty;
        NORTH_TOP_STAGE_1 = Block.m_49796_((double)7.0, (double)11.0, (double)0.5, (double)9.0, (double)11.5, (double)2.5);
        NORTH_BODY_STAGE_1 = Block.m_49796_((double)6.5, (double)9.0, (double)0.0, (double)9.5, (double)11.0, (double)3.0);
        NORTH_BOTTOM_STAGE_1 = Block.m_49796_((double)7.0, (double)8.5, (double)0.5, (double)9.0, (double)9.0, (double)2.5);
        NORTH_TOP_STAGE_2 = Block.m_49796_((double)6.5, (double)10.5, (double)0.5, (double)9.5, (double)11.0, (double)3.5);
        NORTH_BODY_STAGE_2 = Block.m_49796_((double)6.0, (double)7.5, (double)0.0, (double)10.0, (double)10.5, (double)4.0);
        NORTH_BOTTOM_STAGE_2 = Block.m_49796_((double)6.5, (double)7.0, (double)0.5, (double)9.5, (double)7.5, (double)3.5);
        NORTH_TOP_STAGE_3 = Block.m_49796_((double)6.0, (double)9.75, (double)0.5, (double)10.0, (double)10.5, (double)4.5);
        NORTH_BODY_STAGE_3 = Block.m_49796_((double)5.5, (double)5.75, (double)0.0, (double)10.5, (double)9.75, (double)5.0);
        NORTH_BOTTOM_STAGE_3 = Block.m_49796_((double)6.0, (double)5.0, (double)0.5, (double)10.0, (double)5.75, (double)4.5);
        NORTH_TOP_FRUIT = Block.m_49796_((double)6.0, (double)9.0, (double)1.0, (double)10.0, (double)10.0, (double)5.0);
        NORTH_BODY_FRUIT = Block.m_49796_((double)5.0, (double)4.0, (double)0.0, (double)11.0, (double)9.0, (double)6.0);
        NORTH_BOTTOM_FRUIT = Block.m_49796_((double)5.5, (double)3.0, (double)0.5, (double)10.5, (double)4.0, (double)5.5);
        VoxelShape[] voxelShapeArray = new VoxelShape[4];
        VoxelShape[] voxelShapeArray2 = new VoxelShape[]{NORTH_TOP_STAGE_1, NORTH_BOTTOM_STAGE_1};
        voxelShapeArray[0] = Shapes.m_83124_((VoxelShape)NORTH_BODY_STAGE_1, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{NORTH_TOP_STAGE_2, NORTH_BOTTOM_STAGE_2};
        voxelShapeArray[1] = Shapes.m_83124_((VoxelShape)NORTH_BODY_STAGE_2, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{NORTH_TOP_STAGE_3, NORTH_BOTTOM_STAGE_3};
        voxelShapeArray[2] = Shapes.m_83124_((VoxelShape)NORTH_BODY_STAGE_3, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{NORTH_TOP_FRUIT, NORTH_BOTTOM_FRUIT};
        voxelShapeArray[3] = Shapes.m_83124_((VoxelShape)NORTH_BODY_FRUIT, (VoxelShape[])voxelShapeArray2);
        NORTH_AABB = voxelShapeArray;
        SOUTH_TOP_STAGE_1 = Block.m_49796_((double)7.0, (double)11.0, (double)13.5, (double)9.0, (double)11.5, (double)15.5);
        SOUTH_BODY_STAGE_1 = Block.m_49796_((double)6.5, (double)9.0, (double)13.0, (double)9.5, (double)11.0, (double)16.0);
        SOUTH_BOTTOM_STAGE_1 = Block.m_49796_((double)7.0, (double)8.5, (double)13.5, (double)9.0, (double)9.0, (double)15.5);
        SOUTH_TOP_STAGE_2 = Block.m_49796_((double)6.5, (double)10.5, (double)12.5, (double)9.5, (double)11.0, (double)15.5);
        SOUTH_BODY_STAGE_2 = Block.m_49796_((double)6.0, (double)7.5, (double)12.0, (double)10.0, (double)10.5, (double)16.0);
        SOUTH_BOTTOM_STAGE_2 = Block.m_49796_((double)6.5, (double)7.0, (double)12.5, (double)9.5, (double)7.5, (double)15.5);
        SOUTH_TOP_STAGE_3 = Block.m_49796_((double)6.0, (double)9.75, (double)11.5, (double)10.0, (double)10.5, (double)15.5);
        SOUTH_BODY_STAGE_3 = Block.m_49796_((double)5.5, (double)5.75, (double)11.0, (double)10.5, (double)9.75, (double)16.0);
        SOUTH_BOTTOM_STAGE_3 = Block.m_49796_((double)6.0, (double)5.0, (double)11.5, (double)10.0, (double)5.75, (double)15.5);
        SOUTH_TOP_FRUIT = Block.m_49796_((double)6.0, (double)9.0, (double)11.0, (double)10.0, (double)10.0, (double)15.0);
        SOUTH_BODY_FRUIT = Block.m_49796_((double)5.0, (double)4.0, (double)10.0, (double)11.0, (double)9.0, (double)16.0);
        SOUTH_BOTTOM_FRUIT = Block.m_49796_((double)5.5, (double)3.0, (double)10.5, (double)10.5, (double)4.0, (double)15.5);
        voxelShapeArray = new VoxelShape[4];
        voxelShapeArray2 = new VoxelShape[]{SOUTH_TOP_STAGE_1, SOUTH_BOTTOM_STAGE_1};
        voxelShapeArray[0] = Shapes.m_83124_((VoxelShape)SOUTH_BODY_STAGE_1, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{SOUTH_TOP_STAGE_2, SOUTH_BOTTOM_STAGE_2};
        voxelShapeArray[1] = Shapes.m_83124_((VoxelShape)SOUTH_BODY_STAGE_2, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{SOUTH_TOP_STAGE_3, SOUTH_BOTTOM_STAGE_3};
        voxelShapeArray[2] = Shapes.m_83124_((VoxelShape)SOUTH_BODY_STAGE_3, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{SOUTH_TOP_FRUIT, SOUTH_BOTTOM_FRUIT};
        voxelShapeArray[3] = Shapes.m_83124_((VoxelShape)SOUTH_BODY_FRUIT, (VoxelShape[])voxelShapeArray2);
        SOUTH_AABB = voxelShapeArray;
        EAST_TOP_STAGE_1 = Block.m_49796_((double)13.5, (double)11.0, (double)7.0, (double)15.5, (double)11.5, (double)9.0);
        EAST_BODY_STAGE_1 = Block.m_49796_((double)13.0, (double)9.0, (double)6.5, (double)16.0, (double)11.0, (double)9.5);
        EAST_BOTTOM_STAGE_1 = Block.m_49796_((double)13.5, (double)8.5, (double)7.0, (double)15.5, (double)9.0, (double)9.0);
        EAST_TOP_STAGE_2 = Block.m_49796_((double)12.5, (double)10.5, (double)6.5, (double)15.5, (double)11.0, (double)9.5);
        EAST_BODY_STAGE_2 = Block.m_49796_((double)12.0, (double)7.5, (double)6.0, (double)16.0, (double)10.5, (double)10.0);
        EAST_BOTTOM_STAGE_2 = Block.m_49796_((double)12.5, (double)7.0, (double)6.5, (double)15.5, (double)7.5, (double)9.5);
        EAST_TOP_STAGE_3 = Block.m_49796_((double)11.5, (double)9.75, (double)6.0, (double)15.5, (double)10.5, (double)10.0);
        EAST_BODY_STAGE_3 = Block.m_49796_((double)11.0, (double)5.75, (double)5.5, (double)16.0, (double)9.75, (double)10.5);
        EAST_BOTTOM_STAGE_3 = Block.m_49796_((double)11.5, (double)5.0, (double)6.0, (double)15.5, (double)5.75, (double)10.0);
        EAST_TOP_FRUIT = Block.m_49796_((double)11.0, (double)9.0, (double)6.0, (double)15.0, (double)10.0, (double)10.0);
        EAST_BODY_FRUIT = Block.m_49796_((double)10.0, (double)4.0, (double)5.0, (double)16.0, (double)9.0, (double)11.0);
        EAST_BOTTOM_FRUIT = Block.m_49796_((double)10.5, (double)3.0, (double)5.5, (double)15.5, (double)4.0, (double)10.5);
        voxelShapeArray = new VoxelShape[4];
        voxelShapeArray2 = new VoxelShape[]{EAST_TOP_STAGE_1, EAST_BOTTOM_STAGE_1};
        voxelShapeArray[0] = Shapes.m_83124_((VoxelShape)EAST_BODY_STAGE_1, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{EAST_TOP_STAGE_2, EAST_BOTTOM_STAGE_2};
        voxelShapeArray[1] = Shapes.m_83124_((VoxelShape)EAST_BODY_STAGE_2, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{EAST_TOP_STAGE_3, EAST_BOTTOM_STAGE_3};
        voxelShapeArray[2] = Shapes.m_83124_((VoxelShape)EAST_BODY_STAGE_3, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{EAST_TOP_FRUIT, EAST_BOTTOM_FRUIT};
        voxelShapeArray[3] = Shapes.m_83124_((VoxelShape)EAST_BODY_FRUIT, (VoxelShape[])voxelShapeArray2);
        EAST_AABB = voxelShapeArray;
        WEST_TOP_STAGE_1 = Block.m_49796_((double)0.5, (double)11.0, (double)7.0, (double)2.5, (double)11.5, (double)9.0);
        WEST_BODY_STAGE_1 = Block.m_49796_((double)0.0, (double)9.0, (double)6.5, (double)3.0, (double)11.0, (double)9.5);
        WEST_BOTTOM_STAGE_1 = Block.m_49796_((double)0.5, (double)8.5, (double)7.0, (double)2.5, (double)9.0, (double)9.0);
        WEST_TOP_STAGE_2 = Block.m_49796_((double)0.5, (double)10.5, (double)6.5, (double)3.5, (double)11.0, (double)9.5);
        WEST_BODY_STAGE_2 = Block.m_49796_((double)0.0, (double)7.5, (double)6.0, (double)4.0, (double)10.5, (double)10.0);
        WEST_BOTTOM_STAGE_2 = Block.m_49796_((double)0.5, (double)7.0, (double)6.5, (double)3.5, (double)7.5, (double)9.5);
        WEST_TOP_STAGE_3 = Block.m_49796_((double)0.5, (double)9.75, (double)6.0, (double)4.5, (double)10.5, (double)10.0);
        WEST_BODY_STAGE_3 = Block.m_49796_((double)0.0, (double)5.75, (double)5.5, (double)5.0, (double)9.75, (double)10.5);
        WEST_BOTTOM_STAGE_3 = Block.m_49796_((double)0.5, (double)5.0, (double)6.0, (double)4.5, (double)5.75, (double)10.0);
        WEST_TOP_FRUIT = Block.m_49796_((double)1.0, (double)9.0, (double)6.0, (double)5.0, (double)10.0, (double)10.0);
        WEST_BODY_FRUIT = Block.m_49796_((double)0.0, (double)4.0, (double)5.0, (double)6.0, (double)9.0, (double)11.0);
        WEST_BOTTOM_FRUIT = Block.m_49796_((double)0.5, (double)3.0, (double)5.5, (double)5.5, (double)4.0, (double)10.5);
        voxelShapeArray = new VoxelShape[4];
        voxelShapeArray2 = new VoxelShape[]{WEST_TOP_STAGE_1, WEST_BOTTOM_STAGE_1};
        voxelShapeArray[0] = Shapes.m_83124_((VoxelShape)WEST_BODY_STAGE_1, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{WEST_TOP_STAGE_2, WEST_BOTTOM_STAGE_2};
        voxelShapeArray[1] = Shapes.m_83124_((VoxelShape)WEST_BODY_STAGE_2, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{WEST_TOP_STAGE_3, WEST_BOTTOM_STAGE_3};
        voxelShapeArray[2] = Shapes.m_83124_((VoxelShape)WEST_BODY_STAGE_3, (VoxelShape[])voxelShapeArray2);
        voxelShapeArray2 = new VoxelShape[]{WEST_TOP_FRUIT, WEST_BOTTOM_FRUIT};
        voxelShapeArray[3] = Shapes.m_83124_((VoxelShape)WEST_BODY_FRUIT, (VoxelShape[])voxelShapeArray2);
        WEST_AABB = voxelShapeArray;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b-\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bD\u0010ER\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\"\u0010\n\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\rR\u001c\u0010\u000f\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\rR\u001c\u0010\u0010\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\rR\u001c\u0010\u0011\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\rR\u001c\u0010\u0012\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\rR\u001c\u0010\u0013\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\rR\u001c\u0010\u0014\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\rR\u001c\u0010\u0015\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\rR\u001c\u0010\u0016\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\rR\u001c\u0010\u0017\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\rR\u001c\u0010\u0018\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\rR\u0014\u0010\u001a\u001a\u00020\u00198\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001c\u001a\u00020\u00198\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001bR\"\u0010\u001d\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u000bR\u001c\u0010\u001e\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\rR\u001c\u0010\u001f\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\rR\u001c\u0010 \u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\rR\u001c\u0010!\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\rR\u001c\u0010\"\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\rR\u001c\u0010#\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\rR\u001c\u0010$\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\rR\u001c\u0010%\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\rR\u001c\u0010&\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\rR\u001c\u0010'\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\rR\u001c\u0010(\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010\rR\u001c\u0010)\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\rR\"\u0010*\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010\u000bR\u001c\u0010+\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010\rR\u001c\u0010,\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010\rR\u001c\u0010-\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010\rR\u001c\u0010.\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b.\u0010\rR\u001c\u0010/\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b/\u0010\rR\u001c\u00100\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b0\u0010\rR\u001c\u00101\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u0010\rR\u001c\u00102\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b2\u0010\rR\u001c\u00103\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b3\u0010\rR\u001c\u00104\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b4\u0010\rR\u001c\u00105\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b5\u0010\rR\u001c\u00106\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b6\u0010\rR\"\u00107\u001a\u0010\u0012\f\u0012\n \t*\u0004\u0018\u00010\b0\b0\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010\u000bR\u001c\u00108\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b8\u0010\rR\u001c\u00109\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010\rR\u001c\u0010:\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b:\u0010\rR\u001c\u0010;\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010\rR\u001c\u0010<\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010\rR\u001c\u0010=\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010\rR\u001c\u0010>\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010\rR\u001c\u0010?\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010\rR\u001c\u0010@\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b@\u0010\rR\u001c\u0010A\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010\rR\u001c\u0010B\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bB\u0010\rR\u001c\u0010C\u001a\n \t*\u0004\u0018\u00010\b0\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bC\u0010\r\u00a8\u0006F"}, d2={"Lcom/cobblemon/mod/common/block/ApricornBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "AGE", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAGE", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "EAST_AABB", "[Lnet/minecraft/world/phys/shapes/VoxelShape;", "EAST_BODY_FRUIT", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "EAST_BODY_STAGE_1", "EAST_BODY_STAGE_2", "EAST_BODY_STAGE_3", "EAST_BOTTOM_FRUIT", "EAST_BOTTOM_STAGE_1", "EAST_BOTTOM_STAGE_2", "EAST_BOTTOM_STAGE_3", "EAST_TOP_FRUIT", "EAST_TOP_STAGE_1", "EAST_TOP_STAGE_2", "EAST_TOP_STAGE_3", "", "MAX_AGE", "I", "MIN_AGE", "NORTH_AABB", "NORTH_BODY_FRUIT", "NORTH_BODY_STAGE_1", "NORTH_BODY_STAGE_2", "NORTH_BODY_STAGE_3", "NORTH_BOTTOM_FRUIT", "NORTH_BOTTOM_STAGE_1", "NORTH_BOTTOM_STAGE_2", "NORTH_BOTTOM_STAGE_3", "NORTH_TOP_FRUIT", "NORTH_TOP_STAGE_1", "NORTH_TOP_STAGE_2", "NORTH_TOP_STAGE_3", "SOUTH_AABB", "SOUTH_BODY_FRUIT", "SOUTH_BODY_STAGE_1", "SOUTH_BODY_STAGE_2", "SOUTH_BODY_STAGE_3", "SOUTH_BOTTOM_FRUIT", "SOUTH_BOTTOM_STAGE_1", "SOUTH_BOTTOM_STAGE_2", "SOUTH_BOTTOM_STAGE_3", "SOUTH_TOP_FRUIT", "SOUTH_TOP_STAGE_1", "SOUTH_TOP_STAGE_2", "SOUTH_TOP_STAGE_3", "WEST_AABB", "WEST_BODY_FRUIT", "WEST_BODY_STAGE_1", "WEST_BODY_STAGE_2", "WEST_BODY_STAGE_3", "WEST_BOTTOM_FRUIT", "WEST_BOTTOM_STAGE_1", "WEST_BOTTOM_STAGE_2", "WEST_BOTTOM_STAGE_3", "WEST_TOP_FRUIT", "WEST_TOP_STAGE_1", "WEST_TOP_STAGE_2", "WEST_TOP_STAGE_3", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IntegerProperty getAGE() {
            return AGE;
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
                nArray[Direction.NORTH.ordinal()] = 1;
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
            try {
                nArray[Direction.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

