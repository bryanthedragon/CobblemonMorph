/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Containers
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.monster.piglin.PiglinAi
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
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
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraft.world.level.material.FluidState
 *  net.minecraft.world.level.material.Fluids
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.GildedChestBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.HashMap;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00fc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 i2\u00020\u00012\u00020\u0002:\u0002ijB\u0019\u0012\u0006\u0010f\u001a\u00020e\u0012\b\b\u0002\u0010\u0010\u001a\u00020a\u00a2\u0006\u0004\bg\u0010hJ#\u0010\b\u001a\u00020\u00072\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u0014\u00a2\u0006\u0004\b\b\u0010\tJ7\u0010\u0012\u001a\u00020\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0017\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001f\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J+\u0010\u0019\u001a\u00020\u00182\b\u0010\n\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00020\u00172\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\n\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J/\u0010$\u001a\u00020#2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b$\u0010%J\u0019\u0010(\u001a\u0004\u0018\u00010\u00052\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b(\u0010)J\u0019\u0010+\u001a\u00020*2\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0016\u00a2\u0006\u0004\b+\u0010,J?\u00102\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020\u00052\u0006\u0010\f\u001a\u0002002\u0006\u0010\u000e\u001a\u00020\r2\u0006\u00101\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b2\u00103J\u0019\u00104\u001a\u00020\u00112\b\u0010\n\u001a\u0004\u0018\u00010\u0005H\u0016\u00a2\u0006\u0004\b4\u00105J\r\u00106\u001a\u00020\u0011\u00a2\u0006\u0004\b6\u00107J\u001f\u00109\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u00109\u001a\u000208H\u0016\u00a2\u0006\u0004\b9\u0010:J/\u0010=\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010<\u001a\u00020;H\u0016\u00a2\u0006\u0004\b=\u0010>J9\u0010C\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\b\u0010@\u001a\u0004\u0018\u00010?2\u0006\u0010B\u001a\u00020AH\u0016\u00a2\u0006\u0004\bC\u0010DJ7\u0010G\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010E\u001a\u00020\u00052\u0006\u0010F\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\bG\u0010HJ?\u0010N\u001a\u00020M2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010<\u001a\u00020;2\u0006\u0010J\u001a\u00020I2\u0006\u0010L\u001a\u00020KH\u0016\u00a2\u0006\u0004\bN\u0010OJ\u001f\u0010R\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010Q\u001a\u00020PH\u0017\u00a2\u0006\u0004\bR\u0010SJ7\u0010W\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u00052\b\u0010\f\u001a\u0004\u0018\u00010T2\b\u0010\u000e\u001a\u0004\u0018\u00010\r2\b\u0010V\u001a\u0004\u0018\u00010UH\u0016\u00a2\u0006\u0004\bW\u0010XJ/\u0010Z\u001a\u00020M2\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010<\u001a\u00020YH\u0002\u00a2\u0006\u0004\bZ\u0010[R0\u0010_\u001a\u001e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020]0\\j\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020]`^8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010`R\u0017\u0010\u0010\u001a\u00020a8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010b\u001a\u0004\bc\u0010d\u00a8\u0006k"}, d2={"Lcom/cobblemon/mod/common/block/chest/GildedChestBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/world/level/block/SimpleWaterloggedBlock;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "state", "Lnet/minecraft/world/level/BlockGetter;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/pathfinder/PathComputationType;", "type", "", "canPathfindThrough", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/pathfinder/PathComputationType;)Z", "Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;", "Lnet/minecraft/world/level/Level;", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "Lnet/minecraft/world/level/material/FluidState;", "getFluidState", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/material/FluidState;", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "blockPlaceContext", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "hasComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "isFake", "()Z", "Lnet/minecraft/world/level/block/Mirror;", "mirror", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Mirror;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "newState", "moved", "onStateReplaced", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/level/block/Rotation;", "rotation", "rotate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/Rotation;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "scheduledTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/server/level/ServerPlayer;", "spawnPokemon", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/world/InteractionResult;", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "facingToYaw", "Ljava/util/HashMap;", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "getType", "()Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;)V", "Companion", "Type", "common"})
public final class GildedChestBlock
extends BaseEntityBlock
implements SimpleWaterloggedBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Type type;
    @NotNull
    private final HashMap<Direction, Float> facingToYaw;
    @NotNull
    private static final String POKEMON_ARGS = "gimmighoul";
    @NotNull
    private static final IntRange LEVEL_RANGE = new IntRange(5, 30);
    private static final BooleanProperty WATERLOGGED = BooleanProperty.m_61465_((String)"waterlogged");
    private static final VoxelShape SOUTH_OUTLINE = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.25, (double)1.0, (double)1.0, (double)0.9375), (VoxelShape[])new VoxelShape[0]);
    private static final VoxelShape NORTH_OUTLINE = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0, (double)0.0, (double)0.0625, (double)1.0, (double)1.0, (double)0.75), (VoxelShape[])new VoxelShape[0]);
    private static final VoxelShape WEST_OUTLINE = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.0625, (double)0.0, (double)0.0, (double)0.75, (double)1.0, (double)1.0), (VoxelShape[])new VoxelShape[0]);
    private static final VoxelShape EAST_OUTLINE = Shapes.m_83124_((VoxelShape)Shapes.m_83048_((double)0.25, (double)0.0, (double)0.0, (double)0.9375, (double)1.0, (double)1.0), (VoxelShape[])new VoxelShape[0]);

    public GildedChestBlock(@NotNull BlockBehaviour.Properties settings, @NotNull Type type) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        super(settings);
        this.type = type;
        this.m_49959_((BlockState)((BlockState)this.m_49966_().m_61124_((Property)BlockStateProperties.f_61374_, (Comparable)Direction.SOUTH)).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(false)));
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)Direction.NORTH, (Object)Float.valueOf(-179.0f)), TuplesKt.to((Object)Direction.WEST, (Object)Float.valueOf(90.0f)), TuplesKt.to((Object)Direction.SOUTH, (Object)Float.valueOf(0.0f)), TuplesKt.to((Object)Direction.EAST, (Object)Float.valueOf(-90.0f))};
        this.facingToYaw = MapsKt.hashMapOf((Pair[])pairArray);
    }

    public /* synthetic */ GildedChestBlock(BlockBehaviour.Properties properties2, Type type, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            type = Type.RED;
        }
        this(properties2, type);
    }

    @NotNull
    public final Type getType() {
        return this.type;
    }

    @NotNull
    public GildedChestBlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new GildedChestBlockEntity(pos, state, this.type);
    }

    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape voxelShape;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Direction direction = (Direction)state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        switch (direction == null ? -1 : WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
            case 1: {
                VoxelShape voxelShape2 = NORTH_OUTLINE;
                voxelShape = voxelShape2;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"NORTH_OUTLINE");
                break;
            }
            case 2: {
                VoxelShape voxelShape3 = SOUTH_OUTLINE;
                voxelShape = voxelShape3;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"SOUTH_OUTLINE");
                break;
            }
            case 3: {
                VoxelShape voxelShape4 = WEST_OUTLINE;
                voxelShape = voxelShape4;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape4, (String)"WEST_OUTLINE");
                break;
            }
            default: {
                VoxelShape voxelShape5 = EAST_OUTLINE;
                voxelShape = voxelShape5;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape5, (String)"EAST_OUTLINE");
            }
        }
        return voxelShape;
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
        BlockState blockState = super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"super.getStateForNeighbo\u2026 world, pos, neighborPos)");
        return blockState;
    }

    @NotNull
    public FluidState m_5888_(@NotNull BlockState state) {
        FluidState fluidState;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Comparable comparable = state.m_61143_((Property)WATERLOGGED);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(WATERLOGGED)");
        if (((Boolean)comparable).booleanValue()) {
            FluidState fluidState2 = Fluids.f_76193_.m_76068_(false);
            fluidState = fluidState2;
            Intrinsics.checkNotNullExpressionValue((Object)fluidState2, (String)"{\n            Fluids.WAT\u2026getStill(false)\n        }");
        } else {
            FluidState fluidState3 = super.m_5888_(state);
            fluidState = fluidState3;
            Intrinsics.checkNotNullExpressionValue((Object)fluidState3, (String)"super.getFluidState(state)");
        }
        return fluidState;
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        super.m_7926_(builder);
        Property[] propertyArray = new Property[]{BlockStateProperties.f_61374_};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{WATERLOGGED};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    public MutableComponent m_49954_() {
        MutableComponent mutableComponent;
        if (this.isFake()) {
            MutableComponent mutableComponent2 = Component.m_237115_((String)"block.cobblemon.gilded_chest");
            mutableComponent = mutableComponent2;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"translatable(\"block.cobblemon.gilded_chest\")");
        } else {
            MutableComponent mutableComponent3 = super.m_49954_();
            mutableComponent = mutableComponent3;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"super.getName()");
        }
        return mutableComponent;
    }

    public final boolean isFake() {
        return this.type == Type.FAKE;
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!world.f_46443_) {
            GildedChestBlockEntity bEntity;
            if (this.isFake() && player instanceof ServerPlayer) {
                this.spawnPokemon(world, pos, state, (ServerPlayer)player);
            }
            world.m_46597_(pos, state.m_60819_().m_192917_((Fluid)Fluids.f_76193_) ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_());
            BlockEntity blockEntity = world.m_7702_(pos);
            GildedChestBlockEntity gildedChestBlockEntity = bEntity = blockEntity instanceof GildedChestBlockEntity ? (GildedChestBlockEntity)blockEntity : null;
            if (gildedChestBlockEntity != null) {
                gildedChestBlockEntity.m_7651_();
            }
        } else {
            super.m_5707_(world, pos, state, player);
        }
    }

    private final InteractionResult spawnPokemon(Level world, BlockPos pos, BlockState state, ServerPlayer player) {
        String properties2 = POKEMON_ARGS + " lvl=" + RangesKt.random((IntRange)LEVEL_RANGE, (Random)((Random)Random.Default));
        PokemonProperties pokemon = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, properties2, null, null, 6, null);
        PokemonEntity entity2 = pokemon.createEntity(world);
        Float f = this.facingToYaw.get(state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_));
        if (f == null) {
            f = Float.valueOf(0.0f);
        }
        float yaw = ((Number)f).floatValue();
        entity2.m_20088_().m_135381_(PokemonEntity.Companion.getSPAWN_DIRECTION(), (Object)this.facingToYaw.get(state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)));
        Direction offsetDir = (Direction)state.m_61143_((Property)HorizontalDirectionalBlock.f_54117_);
        Vec3 vec = BlockPosExtensionsKt.toVec3d(pos).m_82520_((double)offsetDir.m_122429_() * 0.1 + 0.5, 0.0, (double)offsetDir.m_122431_() * 0.1 + 0.5);
        entity2.m_7678_(vec.f_82479_, vec.f_82480_, vec.f_82481_, yaw, entity2.m_146909_());
        world.m_7967_((Entity)entity2);
        world.m_7471_(pos, false);
        SchedulingFunctionsKt.afterOnServer$default(2, 0.0f, (Function0)new Function0<Unit>(player, entity2, world, pos){
            final /* synthetic */ ServerPlayer $player;
            final /* synthetic */ PokemonEntity $entity;
            final /* synthetic */ Level $world;
            final /* synthetic */ BlockPos $pos;
            {
                this.$player = $player;
                this.$entity = $entity;
                this.$world = $world;
                this.$pos = $pos;
                super(0);
            }

            public final void invoke() {
                List list = this.$player.m_9236_().m_6907_();
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"player.world.players");
                if (!CollectionsKt.contains((Iterable)list, (Object)this.$player)) {
                    return;
                }
                PlayerPartyStore party = PlayerExtensionsKt.party(this.$player);
                if (!this.$player.m_7500_()) {
                    this.$entity.forceBattle(this.$player);
                } else {
                    this.$world.m_247517_(null, this.$pos, CobblemonSounds.GIMMIGHOUL_REVEAL, SoundSource.NEUTRAL);
                }
            }
        }, 2, null);
        return InteractionResult.SUCCESS;
    }

    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        if (this.isFake()) {
            if (player instanceof ServerPlayer) {
                return this.spawnPokemon(world, pos, state, (ServerPlayer)player);
            }
            return InteractionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.m_7702_(pos);
        GildedChestBlockEntity gildedChestBlockEntity = blockEntity instanceof GildedChestBlockEntity ? (GildedChestBlockEntity)blockEntity : null;
        if (gildedChestBlockEntity == null) {
            return InteractionResult.FAIL;
        }
        GildedChestBlockEntity entity2 = gildedChestBlockEntity;
        if (world.m_8055_(pos.m_7494_()).m_60796_((BlockGetter)world, pos.m_7494_())) {
            return InteractionResult.FAIL;
        }
        player.m_5893_((MenuProvider)entity2);
        if (!player.m_9236_().f_46443_) {
            PiglinAi.m_34873_((Player)player, (boolean)true);
        }
        return InteractionResult.SUCCESS;
    }

    public void m_6810_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean moved) {
        block1: {
            GildedChestBlockEntity chest;
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)newState, (String)"newState");
            if (state.m_60713_(newState.m_60734_()) || world.f_46443_) break block1;
            BlockEntity blockEntity = world.m_7702_(pos);
            GildedChestBlockEntity gildedChestBlockEntity = chest = blockEntity instanceof GildedChestBlockEntity ? (GildedChestBlockEntity)blockEntity : null;
            if (gildedChestBlockEntity != null) {
                GildedChestBlockEntity it = gildedChestBlockEntity;
                boolean bl = false;
                Containers.m_19010_((Level)world, (BlockPos)pos, chest.getInventoryContents());
            }
        }
    }

    @NotNull
    public RenderShape m_7514_(@Nullable BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    public BlockState m_5573_(@NotNull BlockPlaceContext blockPlaceContext) {
        Intrinsics.checkNotNullParameter((Object)blockPlaceContext, (String)"blockPlaceContext");
        return (BlockState)((BlockState)this.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)blockPlaceContext.m_8125_().m_122424_())).m_61124_((Property)WATERLOGGED, (Comparable)Boolean.valueOf(Intrinsics.areEqual((Object)blockPlaceContext.m_43725_().m_6425_(blockPlaceContext.m_8083_()).m_76152_(), (Object)Fluids.f_76193_)));
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public BlockState m_6843_(@NotNull BlockState state, @NotNull Rotation rotation) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)rotation, (String)"rotation");
        Property property = (Property)BlockStateProperties.f_61374_;
        Comparable comparable = state.m_61143_((Property)BlockStateProperties.f_61374_);
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type net.minecraft.util.math.Direction");
        Object object = state.m_61124_(property, (Comparable)rotation.m_55954_((Direction)comparable));
        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
        return (BlockState)object;
    }

    public boolean m_7278_(@Nullable BlockState state) {
        return true;
    }

    public int m_6782_(@Nullable BlockState state, @NotNull Level world, @Nullable BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        return AbstractContainerMenu.m_38918_((BlockEntity)world.m_7702_(pos));
    }

    @NotNull
    public BlockState m_6943_(@NotNull BlockState state, @NotNull Mirror mirror) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)mirror, (String)"mirror");
        Comparable comparable = state.m_61143_((Property)BlockStateProperties.f_61374_);
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type net.minecraft.util.math.Direction");
        BlockState blockState = state.m_60717_(mirror.m_54846_((Direction)comparable));
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"state.rotate(mirror.getR\u2026AL_FACING) as Direction))");
        return blockState;
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7357_(@Nullable BlockState state, @Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable PathComputationType type) {
        return false;
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
        BlockEntity blockEntity = world.m_7702_(pos);
        if (itemStack.m_41788_() && blockEntity instanceof GildedChestBlockEntity) {
            ((GildedChestBlockEntity)blockEntity).m_58638_(itemStack.m_41786_());
        }
    }

    public void m_213897_(@Nullable BlockState state, @Nullable ServerLevel world, @Nullable BlockPos pos, @Nullable RandomSource random) {
        ServerLevel serverLevel = world;
        BlockEntity blockEntity = serverLevel != null ? serverLevel.m_7702_(pos) : null;
        GildedChestBlockEntity gildedChestBlockEntity = blockEntity instanceof GildedChestBlockEntity ? (GildedChestBlockEntity)blockEntity : null;
        if (gildedChestBlockEntity == null) {
            return;
        }
        GildedChestBlockEntity blockEntity2 = gildedChestBlockEntity;
        blockEntity2.onScheduledTick();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u001f\u0010\r\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u0005\u001a\u0004\b\u000e\u0010\u0007R\u001a\u0010\u0010\u001a\u00020\u000f8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001f\u0010\u0014\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0005\u001a\u0004\b\u0015\u0010\u0007R\u001f\u0010\u0017\u001a\n \u0003*\u0004\u0018\u00010\u00160\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001f\u0010\u001b\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0005\u001a\u0004\b\u001c\u0010\u0007\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Companion;", "", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "EAST_OUTLINE", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getEAST_OUTLINE", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lkotlin/ranges/IntRange;", "LEVEL_RANGE", "Lkotlin/ranges/IntRange;", "getLEVEL_RANGE", "()Lkotlin/ranges/IntRange;", "NORTH_OUTLINE", "getNORTH_OUTLINE", "", "POKEMON_ARGS", "Ljava/lang/String;", "getPOKEMON_ARGS", "()Ljava/lang/String;", "SOUTH_OUTLINE", "getSOUTH_OUTLINE", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "WATERLOGGED", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getWATERLOGGED", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "WEST_OUTLINE", "getWEST_OUTLINE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final String getPOKEMON_ARGS() {
            return POKEMON_ARGS;
        }

        @NotNull
        public final IntRange getLEVEL_RANGE() {
            return LEVEL_RANGE;
        }

        public final BooleanProperty getWATERLOGGED() {
            return WATERLOGGED;
        }

        public final VoxelShape getSOUTH_OUTLINE() {
            return SOUTH_OUTLINE;
        }

        public final VoxelShape getNORTH_OUTLINE() {
            return NORTH_OUTLINE;
        }

        public final VoxelShape getWEST_OUTLINE() {
            return WEST_OUTLINE;
        }

        public final VoxelShape getEAST_OUTLINE() {
            return EAST_OUTLINE;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "", "Lnet/minecraft/resources/ResourceLocation;", "poserId", "Lnet/minecraft/resources/ResourceLocation;", "getPoserId", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Ljava/lang/String;ILnet/minecraft/resources/ResourceLocation;)V", "RED", "BLUE", "GREEN", "PINK", "WHITE", "BLACK", "YELLOW", "FAKE", "common"})
    public static final class Type
    extends Enum<Type> {
        @NotNull
        private final ResourceLocation poserId;
        public static final /* enum */ Type RED = new Type(MiscUtilsKt.cobblemonResource("gilded_chest"));
        public static final /* enum */ Type BLUE = new Type(MiscUtilsKt.cobblemonResource("blue_gilded_chest"));
        public static final /* enum */ Type GREEN = new Type(MiscUtilsKt.cobblemonResource("green_gilded_chest"));
        public static final /* enum */ Type PINK = new Type(MiscUtilsKt.cobblemonResource("pink_gilded_chest"));
        public static final /* enum */ Type WHITE = new Type(MiscUtilsKt.cobblemonResource("white_gilded_chest"));
        public static final /* enum */ Type BLACK = new Type(MiscUtilsKt.cobblemonResource("black_gilded_chest"));
        public static final /* enum */ Type YELLOW = new Type(MiscUtilsKt.cobblemonResource("yellow_gilded_chest"));
        public static final /* enum */ Type FAKE = new Type(MiscUtilsKt.cobblemonResource("gilded_chest"));
        private static final /* synthetic */ Type[] $VALUES;

        private Type(ResourceLocation poserId) {
            this.poserId = poserId;
        }

        @NotNull
        public final ResourceLocation getPoserId() {
            return this.poserId;
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }

        public static Type valueOf(String value2) {
            return Enum.valueOf(Type.class, value2);
        }

        static {
            $VALUES = typeArray = new Type[]{Type.RED, Type.BLUE, Type.GREEN, Type.PINK, Type.WHITE, Type.BLACK, Type.YELLOW, Type.FAKE};
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
                nArray[Direction.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Direction.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

