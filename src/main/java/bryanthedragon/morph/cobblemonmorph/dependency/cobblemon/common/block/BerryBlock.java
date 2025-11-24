/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.ShovelItem
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.BaseEntityBlock
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.FarmBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.StateDefinition$Builder
 *  net.minecraft.world.level.block.state.properties.BooleanProperty
 *  net.minecraft.world.level.block.state.properties.EnumProperty
 *  net.minecraft.world.level.block.state.properties.IntegerProperty
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.shapes.CollisionContext
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationOfferEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryMutationResultEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.Mulchable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00e8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 c2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001cB\u0017\u0012\u0006\u0010Z\u001a\u00020Y\u0012\u0006\u0010`\u001a\u00020_\u00a2\u0006\u0004\ba\u0010bJ#\u0010\t\u001a\u00020\b2\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004H\u0014\u00a2\u0006\u0004\b\t\u0010\nJ7\u0010\u0014\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u0004\u0018\u00010\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J/\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cJ/\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ'\u0010 \u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0017\u00a2\u0006\u0004\b \u0010!J\u001f\u0010#\u001a\u00020\"2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b#\u0010$J-\u0010%\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006\u00a2\u0006\u0004\b%\u0010&J/\u0010+\u001a\u00020*2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020'2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020(H\u0017\u00a2\u0006\u0004\b+\u0010,J-\u0010.\u001a\u00020-2\b\u0010\f\u001a\u0004\u0018\u00010'2\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b.\u0010/J\u0017\u00102\u001a\u0002012\u0006\u00100\u001a\u00020\u0006H\u0017\u00a2\u0006\u0004\b2\u00103J?\u00109\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\u00062\u0006\u0010\f\u001a\u0002072\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u00108\u001a\u00020\u000fH\u0017\u00a2\u0006\u0004\b9\u0010:J?\u0010@\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010?\"\b\b\u0000\u0010<*\u00020;2\u0006\u0010\f\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00062\f\u0010>\u001a\b\u0012\u0004\u0012\u00028\u00000=H\u0016\u00a2\u0006\u0004\b@\u0010AJ/\u0010B\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\bB\u0010CJ/\u0010E\u001a\u00020\u001a2\u0006\u0010\f\u001a\u00020\u001f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\bE\u0010FJ\u0017\u0010G\u001a\u00020\u001a2\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\bG\u0010HJ/\u0010K\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010J\u001a\u00020IH\u0016\u00a2\u0006\u0004\bK\u0010LJ9\u0010P\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00062\b\u0010N\u001a\u0004\u0018\u00010M2\u0006\u0010O\u001a\u00020-H\u0016\u00a2\u0006\u0004\bP\u0010QJ?\u0010W\u001a\u00020V2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010J\u001a\u00020I2\u0006\u0010S\u001a\u00020R2\u0006\u0010U\u001a\u00020TH\u0017\u00a2\u0006\u0004\bW\u0010XR\u0014\u0010Z\u001a\u00020Y8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bZ\u0010[R\u001a\u0010]\u001a\b\u0012\u0004\u0012\u0002040\\8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b]\u0010^\u00a8\u0006d"}, d2={"Lcom/cobblemon/mod/common/block/BerryBlock;", "Lnet/minecraft/world/level/block/BaseEntityBlock;", "Lnet/minecraft/world/level/block/BonemealableBlock;", "Lcom/cobblemon/mod/common/api/mulch/Mulchable;", "Lnet/minecraft/state/StateManager$Builder;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/state/BlockState;", "builder", "", "appendProperties", "(Lnet/minecraft/world/level/block/state/StateDefinition$Builder;)V", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/util/RandomSource;", "random", "Lnet/minecraft/core/BlockPos;", "pos", "state", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "variant", "applyMulch", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)V", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "Lnet/minecraft/world/level/Level;", "", "canGrow", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", "canHaveMulchApplied", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)Z", "Lnet/minecraft/world/level/LevelReader;", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "createBlockEntity", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "determineMutation", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Lnet/minecraft/world/level/BlockGetter;", "Lnet/minecraft/world/phys/shapes/CollisionContext;", "context", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getOutlineShape", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/world/item/ItemStack;", "getPickStack", "(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/item/ItemStack;", "blockState", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "blockWithEntityType", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTicker", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "grow", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "isClient", "isFertilizable", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z", "isMaxAge", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "itemStack", "onPlaced", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;)V", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/phys/BlockHitResult;", "hit", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/resources/ResourceLocation;", "berryIdentifier", "Lnet/minecraft/resources/ResourceLocation;", "", "lookupDirections", "Ljava/util/Set;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBerryBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryBlock.kt\ncom/cobblemon/mod/common/block/BerryBlock\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,316:1\n1855#2,2:317\n1855#2,2:330\n17#3,2:319\n17#3,2:322\n19#3:327\n19#3:329\n13579#4:321\n13579#4:324\n13580#4:326\n13580#4:328\n1#5:325\n*S KotlinDebug\n*F\n+ 1 BerryBlock.kt\ncom/cobblemon/mod/common/block/BerryBlock\n*L\n74#1:317,2\n178#1:330,2\n122#1:319,2\n131#1:322,2\n131#1:327\n122#1:329\n122#1:321\n131#1:324\n131#1:326\n122#1:328\n*E\n"})
public final class BerryBlock
extends BaseEntityBlock
implements BonemealableBlock,
Mulchable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation berryIdentifier;
    @NotNull
    private final Set<Direction> lookupDirections;
    public static final int MATURE_AGE = 3;
    public static final int FLOWER_AGE = 4;
    public static final int FRUIT_AGE = 5;
    @NotNull
    private static final IntegerProperty AGE;
    @NotNull
    private static final EnumProperty<MulchVariant> MULCH;
    @NotNull
    private static final BooleanProperty WAS_GENERATED;
    private static final VoxelShape PLANTED_SHAPE;
    @NotNull
    private static final List<AABB> STANDARD_SPROUT;
    @NotNull
    private static final List<AABB> STANDARD_MATURE;
    @NotNull
    private static final List<AABB> SHORT_SPROUT;
    @NotNull
    private static final List<AABB> SHORT_MATURE;
    @NotNull
    private static final List<AABB> VOLCANO_SPROUT;
    @NotNull
    private static final List<AABB> VOLCANO_MATURE;
    @NotNull
    private static final List<AABB> NEST_SPROUT;
    @NotNull
    private static final List<AABB> NEST_MATURE;
    @NotNull
    private static final List<AABB> FRILL_SPROUT;
    @NotNull
    private static final List<AABB> FRILL_MATURE;
    @NotNull
    private static final List<AABB> BLOCK_SPROUT;
    @NotNull
    private static final List<AABB> BLOCK_MATURE;
    @NotNull
    private static final List<AABB> PYRAMID_SPROUT;
    @NotNull
    private static final List<AABB> PYRAMID_MATURE;
    @NotNull
    private static final List<AABB> TAIL_SPROUT;
    @NotNull
    private static final List<AABB> TAIL_MATURE;
    @NotNull
    private static final List<AABB> SWORD_SPROUT;
    @NotNull
    private static final List<AABB> SWORD_MATURE;
    @NotNull
    private static final List<AABB> PLATFORM_SPROUT;
    @NotNull
    private static final List<AABB> PLATFORM_MATURE;
    @NotNull
    private static final List<AABB> STAND_SPROUT;
    @NotNull
    private static final List<AABB> STAND_MATURE;
    @NotNull
    private static final List<AABB> CONE_SPROUT;
    @NotNull
    private static final List<AABB> CONE_MATURE;
    @NotNull
    private static final List<AABB> SQUAT_SPROUT;
    @NotNull
    private static final List<AABB> SQUAT_MATURE;
    @NotNull
    private static final List<AABB> LANTERN_SPROUT;
    @NotNull
    private static final List<AABB> LANTERN_MATURE;
    @NotNull
    private static final List<AABB> BOX_SPROUT;
    @NotNull
    private static final List<AABB> BOX_MATURE;
    @NotNull
    private static final List<AABB> BLOSSOM_SPROUT;
    @NotNull
    private static final List<AABB> BLOSSOM_MATURE;
    @NotNull
    private static final List<AABB> LILYPAD_SPROUT;
    @NotNull
    private static final List<AABB> LILYPAD_MATURE;
    @NotNull
    private static final List<AABB> TALL_SPROUT;
    @NotNull
    private static final List<AABB> TALL_MATURE;

    public BerryBlock(@NotNull ResourceLocation berryIdentifier, @NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)berryIdentifier, (String)"berryIdentifier");
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        this.berryIdentifier = berryIdentifier;
        Object[] objectArray = new Direction[]{Direction.NORTH, Direction.EAST, Direction.WEST, Direction.SOUTH};
        this.lookupDirections = SetsKt.setOf((Object[])objectArray);
        this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_((Property)WAS_GENERATED, (Comparable)Boolean.valueOf(false))).m_61124_((Property)AGE, (Comparable)Integer.valueOf(0))).m_61124_((Property)MULCH, (Comparable)((Object)MulchVariant.NONE)));
    }

    @Nullable
    public final Berry berry() {
        return Berries.INSTANCE.getByIdentifier(this.berryIdentifier);
    }

    public void m_5707_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!player.m_7500_()) {
            Integer n = (Integer)state.m_61143_((Property)AGE);
            int n2 = 5;
            if (n != null && n == n2) {
                BlockEntity blockEntity = world.m_7702_(pos);
                Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
                BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
                Iterable $this$forEach$iv = treeEntity.harvest(world, state, pos, player);
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    ItemStack drop = (ItemStack)element$iv;
                    boolean bl = false;
                    Block.m_49840_((Level)world, (BlockPos)pos, (ItemStack)drop);
                }
            }
        }
        super.m_5707_(world, pos, state, player);
    }

    @NotNull
    public BerryBlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return new BerryBlockEntity(pos, state, this.berryIdentifier);
    }

    public boolean m_7370_(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return !this.isMaxAge(state);
    }

    public boolean m_214167_(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return !this.isMaxAge(state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(@NotNull Level world, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockWithEntityType) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter(blockWithEntityType, (String)"blockWithEntityType");
        return BaseEntityBlock.m_152132_(blockWithEntityType, CobblemonBlockEntities.BERRY, BerryBlockEntity.Companion.getTICKER$common());
    }

    public void m_214148_(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Integer curAge = (Integer)state.m_61143_((Property)AGE);
        int newAge = curAge + 1;
        if (newAge > 5) {
            return;
        }
        BlockState newState = (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(newAge));
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
        BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
        Integer n = curAge;
        int n2 = 3;
        if (n != null && n == n2) {
            treeEntity.generateGrowthPoints((Level)world, state, pos, null);
            this.determineMutation((Level)world, random, pos, state);
        }
        world.m_7731_(pos, newState, 2);
        Intrinsics.checkNotNullExpressionValue((Object)curAge, (String)"curAge");
        treeEntity.goToNextStageTimer(5 - curAge);
        treeEntity.m_6596_();
    }

    /*
     * WARNING - void declaration
     */
    public final void determineMutation(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        block4: {
            void this_$iv;
            Object berry;
            Object berryBlock;
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)random, (String)"random");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            HashSet mutations = new HashSet();
            BlockEntity blockEntity = world.m_7702_(pos);
            Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
            BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
            for (Direction direction : this.lookupDirections) {
                Berry berry2;
                BlockPos redirectedPos = pos.m_121955_(direction.m_122436_());
                BlockState redirectedState = world.m_8055_(redirectedPos);
                Block block = redirectedState.m_60734_();
                BerryBlock berryBlock2 = block instanceof BerryBlock ? (BerryBlock)block : null;
                if (berryBlock2 == null || ((BerryBlock)(berryBlock = berryBlock2)).berry() == null || (berry2 = this.berry()) == null || (berry2 = berry2.mutationWith((Berry)berry)) == null) continue;
                Berry mutation = berry2;
                ((Collection)mutations).add(mutation);
            }
            Berry berry3 = this.berry();
            if (berry3 == null) break block4;
            Berry berry4 = berry3;
            boolean bl = false;
            berryBlock = CobblemonEvents.BERRY_MUTATION_OFFER;
            berry = new BerryMutationOfferEvent[]{new BerryMutationOfferEvent(berry4, world, state, pos, mutations)};
            BerryMutationOfferEvent[] events$iv = berry;
            boolean $i$f$post = false;
            this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            BerryMutationOfferEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                void this_$iv2;
                BerryBlockEntity blockEntity2;
                BerryMutationOfferEvent element$iv$iv;
                BerryMutationOfferEvent berryMutationOffer = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl2 = false;
                if (!(!((Collection)berryMutationOffer.getMutations()).isEmpty())) continue;
                int mutateChance = 125;
                if (Companion.getMulch(state) == MulchVariant.SURPRISE) {
                    mutateChance *= 4;
                    treeEntity.decrementMulchDuration(world, pos, state);
                }
                Berry mutation = random.m_188503_(1000) < mutateChance ? (Berry)CollectionsKt.random((Collection)mutations, (Random)((Random)Random.Default)) : null;
                BlockEntity blockEntity3 = world.m_7702_(pos);
                if ((blockEntity3 instanceof BerryBlockEntity ? (BerryBlockEntity)blockEntity3 : null) == null) continue;
                blockEntity2 = blockEntity2;
                boolean bl3 = false;
                EventObservable<BerryMutationResultEvent> eventObservable = CobblemonEvents.BERRY_MUTATION_RESULT;
                BerryMutationResultEvent[] berryMutationResultEventArray = new BerryMutationResultEvent[]{new BerryMutationResultEvent(berry4, world, state, pos, berryMutationOffer.getMutations(), mutation)};
                BerryMutationResultEvent[] events$iv2 = berryMutationResultEventArray;
                boolean $i$f$post2 = false;
                this_$iv2.emit(Arrays.copyOf(events$iv2, events$iv2.length));
                BerryMutationResultEvent[] $this$forEach$iv$iv2 = events$iv2;
                boolean $i$f$forEach2 = false;
                int n2 = $this$forEach$iv$iv2.length;
                for (int j = 0; j < n2; ++j) {
                    Berry mutation2;
                    BerryMutationResultEvent element$iv$iv2;
                    BerryMutationResultEvent berryMutationResult = element$iv$iv2 = $this$forEach$iv$iv2[j];
                    boolean bl4 = false;
                    if (berryMutationResult.getPickedMutation() == null) continue;
                    boolean bl5 = false;
                    blockEntity2.mutate$common(mutation2);
                }
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean canHaveMulchApplied(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MulchVariant variant) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)variant), (String)"variant");
        if (Companion.getMulch(state) != MulchVariant.NONE) return false;
        Comparable comparable = state.m_61143_((Property)AGE);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(AGE)");
        if (((Number)((Object)comparable)).intValue() >= 4) return false;
        if (!world.m_8055_(pos.m_7495_()).m_60713_(Blocks.f_50093_)) return false;
        return true;
    }

    @Override
    public void applyMulch(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MulchVariant variant) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)variant), (String)"variant");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
        BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
        Companion.setMulch((Level)world, pos, state, variant);
        treeEntity.setMulchDuration(variant.getDuration());
        world.m_5594_(null, pos, CobblemonSounds.MULCH_PLACE, SoundSource.BLOCKS, 0.6f, 1.0f);
        treeEntity.refreshTimers(pos);
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        Intrinsics.checkNotNullParameter((Object)hit, (String)"hit");
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
        BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
        if (player.m_21120_(hand).m_41720_() instanceof ShovelItem && Companion.getMulch(state) != MulchVariant.NONE) {
            Companion.setMulch(world, pos, state, MulchVariant.NONE);
            treeEntity.m_6596_();
            world.m_5594_(null, pos, CobblemonSounds.MULCH_REMOVE, SoundSource.BLOCKS, 0.6f, 1.0f);
            this.m_142387_(world, player, pos, (BlockState)state.m_61124_((Property)AGE, (Comparable)Integer.valueOf(0)));
            return InteractionResult.SUCCESS;
        }
        if (player.m_21120_(hand).m_150930_(Items.f_42499_) && !this.isMaxAge(state)) {
            return InteractionResult.PASS;
        }
        if (this.isMaxAge(state)) {
            BlockEntity blockEntity2 = world.m_7702_(pos);
            BerryBlockEntity berryBlockEntity = blockEntity2 instanceof BerryBlockEntity ? (BerryBlockEntity)blockEntity2 : null;
            if (berryBlockEntity == null) {
                return InteractionResult.PASS;
            }
            BerryBlockEntity blockEntity3 = berryBlockEntity;
            Iterable $this$forEach$iv = blockEntity3.harvest(world, state, pos, player);
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                ItemStack drop = (ItemStack)element$iv;
                boolean bl = false;
                Block.m_49840_((Level)world, (BlockPos)pos, (ItemStack)drop);
            }
            world.m_5594_(null, pos, CobblemonSounds.BERRY_HARVEST, SoundSource.BLOCKS, 0.4f, 1.0f);
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)world.f_46443_);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
            return interactionResult;
        }
        InteractionResult interactionResult = super.m_6227_(state, world, pos, player, hand, hit);
        Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"super.onUse(state, world, pos, player, hand, hit)");
        return interactionResult;
    }

    @Deprecated(message="Deprecated in Java")
    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockState below = world.m_8055_(pos.m_7495_());
        Comparable comparable = state.m_61143_((Property)WAS_GENERATED);
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(WAS_GENERATED)");
        return (Boolean)comparable != false && below.m_204336_(CobblemonBlockTags.BERRY_WILD_SOIL) || below.m_204336_(CobblemonBlockTags.BERRY_SOIL) || below.m_60734_() instanceof FarmBlock;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public BlockState m_7417_(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)neighborState, (String)"neighborState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)neighborPos, (String)"neighborPos");
        if (state.m_60710_((LevelReader)world, pos)) {
            BlockState blockState2 = super.m_7417_(state, direction, neighborState, world, pos, neighborPos);
            blockState = blockState2;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"super.getStateForNeighbo\u2026 world, pos, neighborPos)");
        } else {
            BlockState blockState3 = Blocks.f_50016_.m_49966_();
            blockState = blockState3;
            Intrinsics.checkNotNullExpressionValue((Object)blockState3, (String)"AIR.defaultState");
        }
        return blockState;
    }

    public void m_6402_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack itemStack) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
    }

    protected void m_7926_(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
        Intrinsics.checkNotNullParameter(builder, (String)"builder");
        Property[] propertyArray = new Property[]{AGE};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{WAS_GENERATED};
        builder.m_61104_(propertyArray);
        propertyArray = new Property[]{MULCH};
        builder.m_61104_(propertyArray);
    }

    @NotNull
    public ItemStack m_7397_(@Nullable BlockGetter world, @Nullable BlockPos pos, @Nullable BlockState state) {
        Object object = this.berry();
        if (object == null || (object = object.item()) == null) {
            ItemStack itemStack = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
            return itemStack;
        }
        Object berryItem = object;
        return new ItemStack((ItemLike)berryItem);
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape voxelShape;
        Integer n;
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Berry berry = this.berry();
        if (berry == null) {
            VoxelShape voxelShape2 = Shapes.m_83144_();
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"fullCube()");
            return voxelShape2;
        }
        Berry berry2 = berry;
        Integer n2 = n = (Integer)state.m_61143_((Property)AGE);
        if (n2 != null && n2 == 0) {
            VoxelShape voxelShape3 = PLANTED_SHAPE;
            voxelShape = voxelShape3;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape3, (String)"PLANTED_SHAPE");
        } else {
            Integer n3 = n;
            int n4 = 1;
            if (n3 != null && n3 == n4) {
                VoxelShape voxelShape4 = PLANTED_SHAPE;
                voxelShape = voxelShape4;
                Intrinsics.checkNotNullExpressionValue((Object)voxelShape4, (String)"PLANTED_SHAPE");
            } else {
                Integer n5 = n;
                n4 = 2;
                voxelShape = n5 != null && n5 == n4 ? berry2.getSproutShape() : berry2.getMatureShape();
            }
        }
        return voxelShape;
    }

    private final boolean isMaxAge(BlockState state) {
        Integer n = (Integer)state.m_61143_((Property)AGE);
        int n2 = 5;
        return n != null && n == n2;
    }

    @Deprecated(message="Deprecated in Java")
    @NotNull
    public RenderShape m_7514_(@NotNull BlockState blockState) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        return RenderShape.MODEL;
    }

    static {
        IntegerProperty integerProperty = IntegerProperty.m_61631_((String)"age", (int)0, (int)5);
        Intrinsics.checkNotNullExpressionValue((Object)integerProperty, (String)"of(\"age\", 0, FRUIT_AGE)");
        AGE = integerProperty;
        EnumProperty enumProperty = EnumProperty.m_61587_((String)"mulch", MulchVariant.class);
        Intrinsics.checkNotNullExpressionValue((Object)enumProperty, (String)"of(\"mulch\", MulchVariant::class.java)");
        MULCH = enumProperty;
        BooleanProperty booleanProperty = BooleanProperty.m_61465_((String)"generated");
        Intrinsics.checkNotNullExpressionValue((Object)booleanProperty, (String)"of(\"generated\")");
        WAS_GENERATED = booleanProperty;
        PLANTED_SHAPE = Shapes.m_83048_((double)0.0, (double)-0.1, (double)0.0, (double)1.0, (double)0.25, (double)1.0);
        STANDARD_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        STANDARD_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        SHORT_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 12.0, 16.0));
        SHORT_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        VOLCANO_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0));
        VOLCANO_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        NEST_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0));
        NEST_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 20.0, 16.0));
        FRILL_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 8.0, 16.0));
        FRILL_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 14.0, 16.0));
        BLOCK_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 17.0, 16.0));
        BLOCK_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        PYRAMID_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 17.0, 16.0));
        PYRAMID_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        TAIL_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        TAIL_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, 1.0, 0.0, 16.0, 24.0, 16.0));
        SWORD_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 5.0, 16.0));
        SWORD_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        PLATFORM_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 7.0, 16.0));
        PLATFORM_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 22.0, 16.0));
        STAND_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 13.0, 16.0));
        STAND_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        CONE_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        CONE_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 23.0, 16.0));
        SQUAT_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 12.0, 16.0));
        SQUAT_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 22.0, 16.0));
        LANTERN_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 20.0, 16.0));
        LANTERN_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        BOX_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        BOX_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
        BLOSSOM_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 4.0, 16.0));
        BLOSSOM_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 6.0, 16.0));
        LILYPAD_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 11.0, 16.0));
        LILYPAD_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        TALL_SPROUT = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 16.0, 16.0));
        TALL_MATURE = CollectionsKt.listOf((Object)new AABB(0.0, -1.0, 0.0, 16.0, 24.0, 16.0));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b-\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bu\u0010vJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J-\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0017\u001a\u0004\b\u001b\u0010\u0019R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0017\u001a\u0004\b\u001d\u0010\u0019R\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0017\u001a\u0004\b\u001f\u0010\u0019R\u001d\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0017\u001a\u0004\b!\u0010\u0019R\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u0017\u001a\u0004\b#\u0010\u0019R\u001d\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u0017\u001a\u0004\b%\u0010\u0019R\u001d\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b&\u0010\u0017\u001a\u0004\b'\u0010\u0019R\u0014\u0010)\u001a\u00020(8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u001d\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b+\u0010\u0017\u001a\u0004\b,\u0010\u0019R\u001d\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b-\u0010\u0017\u001a\u0004\b.\u0010\u0019R\u0014\u0010/\u001a\u00020(8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b/\u0010*R\u001d\u00100\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b0\u0010\u0017\u001a\u0004\b1\u0010\u0019R\u001d\u00102\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b2\u0010\u0017\u001a\u0004\b3\u0010\u0019R\u001d\u00104\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b4\u0010\u0017\u001a\u0004\b5\u0010\u0019R\u001d\u00106\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b6\u0010\u0017\u001a\u0004\b7\u0010\u0019R\u0014\u00108\u001a\u00020(8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b8\u0010*R\u001d\u0010:\u001a\b\u0012\u0004\u0012\u00020\u0004098\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=R\u001d\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b>\u0010\u0017\u001a\u0004\b?\u0010\u0019R\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b@\u0010\u0017\u001a\u0004\bA\u0010\u0019R\u001f\u0010D\u001a\n C*\u0004\u0018\u00010B0B8\u0006\u00a2\u0006\f\n\u0004\bD\u0010E\u001a\u0004\bF\u0010GR\u001d\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bH\u0010\u0017\u001a\u0004\bI\u0010\u0019R\u001d\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bJ\u0010\u0017\u001a\u0004\bK\u0010\u0019R\u001d\u0010L\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bL\u0010\u0017\u001a\u0004\bM\u0010\u0019R\u001d\u0010N\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bN\u0010\u0017\u001a\u0004\bO\u0010\u0019R\u001d\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bP\u0010\u0017\u001a\u0004\bQ\u0010\u0019R\u001d\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bR\u0010\u0017\u001a\u0004\bS\u0010\u0019R\u001d\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bT\u0010\u0017\u001a\u0004\bU\u0010\u0019R\u001d\u0010V\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bV\u0010\u0017\u001a\u0004\bW\u0010\u0019R\u001d\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bX\u0010\u0017\u001a\u0004\bY\u0010\u0019R\u001d\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bZ\u0010\u0017\u001a\u0004\b[\u0010\u0019R\u001d\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b\\\u0010\u0017\u001a\u0004\b]\u0010\u0019R\u001d\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b^\u0010\u0017\u001a\u0004\b_\u0010\u0019R\u001d\u0010`\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\b`\u0010\u0017\u001a\u0004\ba\u0010\u0019R\u001d\u0010b\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bb\u0010\u0017\u001a\u0004\bc\u0010\u0019R\u001d\u0010d\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bd\u0010\u0017\u001a\u0004\be\u0010\u0019R\u001d\u0010f\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bf\u0010\u0017\u001a\u0004\bg\u0010\u0019R\u001d\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bh\u0010\u0017\u001a\u0004\bi\u0010\u0019R\u001d\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bj\u0010\u0017\u001a\u0004\bk\u0010\u0019R\u001d\u0010l\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bl\u0010\u0017\u001a\u0004\bm\u0010\u0019R\u001d\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0006\u00a2\u0006\f\n\u0004\bn\u0010\u0017\u001a\u0004\bo\u0010\u0019R\u0017\u0010q\u001a\u00020p8\u0006\u00a2\u0006\f\n\u0004\bq\u0010r\u001a\u0004\bs\u0010t\u00a8\u0006w"}, d2={"Lcom/cobblemon/mod/common/block/BerryBlock$Companion;", "", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "getMulch", "(Lnet/minecraft/world/level/block/state/BlockState;)Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "mulch", "", "setMulch", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/mulch/MulchVariant;)V", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "AGE", "Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "getAGE", "()Lnet/minecraft/world/level/block/state/properties/IntegerProperty;", "", "Lnet/minecraft/world/phys/AABB;", "BLOCK_MATURE", "Ljava/util/List;", "getBLOCK_MATURE", "()Ljava/util/List;", "BLOCK_SPROUT", "getBLOCK_SPROUT", "BLOSSOM_MATURE", "getBLOSSOM_MATURE", "BLOSSOM_SPROUT", "getBLOSSOM_SPROUT", "BOX_MATURE", "getBOX_MATURE", "BOX_SPROUT", "getBOX_SPROUT", "CONE_MATURE", "getCONE_MATURE", "CONE_SPROUT", "getCONE_SPROUT", "", "FLOWER_AGE", "I", "FRILL_MATURE", "getFRILL_MATURE", "FRILL_SPROUT", "getFRILL_SPROUT", "FRUIT_AGE", "LANTERN_MATURE", "getLANTERN_MATURE", "LANTERN_SPROUT", "getLANTERN_SPROUT", "LILYPAD_MATURE", "getLILYPAD_MATURE", "LILYPAD_SPROUT", "getLILYPAD_SPROUT", "MATURE_AGE", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "MULCH", "Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "getMULCH", "()Lnet/minecraft/world/level/block/state/properties/EnumProperty;", "NEST_MATURE", "getNEST_MATURE", "NEST_SPROUT", "getNEST_SPROUT", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "kotlin.jvm.PlatformType", "PLANTED_SHAPE", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getPLANTED_SHAPE", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "PLATFORM_MATURE", "getPLATFORM_MATURE", "PLATFORM_SPROUT", "getPLATFORM_SPROUT", "PYRAMID_MATURE", "getPYRAMID_MATURE", "PYRAMID_SPROUT", "getPYRAMID_SPROUT", "SHORT_MATURE", "getSHORT_MATURE", "SHORT_SPROUT", "getSHORT_SPROUT", "SQUAT_MATURE", "getSQUAT_MATURE", "SQUAT_SPROUT", "getSQUAT_SPROUT", "STANDARD_MATURE", "getSTANDARD_MATURE", "STANDARD_SPROUT", "getSTANDARD_SPROUT", "STAND_MATURE", "getSTAND_MATURE", "STAND_SPROUT", "getSTAND_SPROUT", "SWORD_MATURE", "getSWORD_MATURE", "SWORD_SPROUT", "getSWORD_SPROUT", "TAIL_MATURE", "getTAIL_MATURE", "TAIL_SPROUT", "getTAIL_SPROUT", "TALL_MATURE", "getTALL_MATURE", "TALL_SPROUT", "getTALL_SPROUT", "VOLCANO_MATURE", "getVOLCANO_MATURE", "VOLCANO_SPROUT", "getVOLCANO_SPROUT", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "WAS_GENERATED", "Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "getWAS_GENERATED", "()Lnet/minecraft/world/level/block/state/properties/BooleanProperty;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final IntegerProperty getAGE() {
            return AGE;
        }

        @NotNull
        public final EnumProperty<MulchVariant> getMULCH() {
            return MULCH;
        }

        @NotNull
        public final BooleanProperty getWAS_GENERATED() {
            return WAS_GENERATED;
        }

        public final VoxelShape getPLANTED_SHAPE() {
            return PLANTED_SHAPE;
        }

        @NotNull
        public final List<AABB> getSTANDARD_SPROUT() {
            return STANDARD_SPROUT;
        }

        @NotNull
        public final List<AABB> getSTANDARD_MATURE() {
            return STANDARD_MATURE;
        }

        @NotNull
        public final List<AABB> getSHORT_SPROUT() {
            return SHORT_SPROUT;
        }

        @NotNull
        public final List<AABB> getSHORT_MATURE() {
            return SHORT_MATURE;
        }

        @NotNull
        public final List<AABB> getVOLCANO_SPROUT() {
            return VOLCANO_SPROUT;
        }

        @NotNull
        public final List<AABB> getVOLCANO_MATURE() {
            return VOLCANO_MATURE;
        }

        @NotNull
        public final List<AABB> getNEST_SPROUT() {
            return NEST_SPROUT;
        }

        @NotNull
        public final List<AABB> getNEST_MATURE() {
            return NEST_MATURE;
        }

        @NotNull
        public final List<AABB> getFRILL_SPROUT() {
            return FRILL_SPROUT;
        }

        @NotNull
        public final List<AABB> getFRILL_MATURE() {
            return FRILL_MATURE;
        }

        @NotNull
        public final List<AABB> getBLOCK_SPROUT() {
            return BLOCK_SPROUT;
        }

        @NotNull
        public final List<AABB> getBLOCK_MATURE() {
            return BLOCK_MATURE;
        }

        @NotNull
        public final List<AABB> getPYRAMID_SPROUT() {
            return PYRAMID_SPROUT;
        }

        @NotNull
        public final List<AABB> getPYRAMID_MATURE() {
            return PYRAMID_MATURE;
        }

        @NotNull
        public final List<AABB> getTAIL_SPROUT() {
            return TAIL_SPROUT;
        }

        @NotNull
        public final List<AABB> getTAIL_MATURE() {
            return TAIL_MATURE;
        }

        @NotNull
        public final List<AABB> getSWORD_SPROUT() {
            return SWORD_SPROUT;
        }

        @NotNull
        public final List<AABB> getSWORD_MATURE() {
            return SWORD_MATURE;
        }

        @NotNull
        public final List<AABB> getPLATFORM_SPROUT() {
            return PLATFORM_SPROUT;
        }

        @NotNull
        public final List<AABB> getPLATFORM_MATURE() {
            return PLATFORM_MATURE;
        }

        @NotNull
        public final List<AABB> getSTAND_SPROUT() {
            return STAND_SPROUT;
        }

        @NotNull
        public final List<AABB> getSTAND_MATURE() {
            return STAND_MATURE;
        }

        @NotNull
        public final List<AABB> getCONE_SPROUT() {
            return CONE_SPROUT;
        }

        @NotNull
        public final List<AABB> getCONE_MATURE() {
            return CONE_MATURE;
        }

        @NotNull
        public final List<AABB> getSQUAT_SPROUT() {
            return SQUAT_SPROUT;
        }

        @NotNull
        public final List<AABB> getSQUAT_MATURE() {
            return SQUAT_MATURE;
        }

        @NotNull
        public final List<AABB> getLANTERN_SPROUT() {
            return LANTERN_SPROUT;
        }

        @NotNull
        public final List<AABB> getLANTERN_MATURE() {
            return LANTERN_MATURE;
        }

        @NotNull
        public final List<AABB> getBOX_SPROUT() {
            return BOX_SPROUT;
        }

        @NotNull
        public final List<AABB> getBOX_MATURE() {
            return BOX_MATURE;
        }

        @NotNull
        public final List<AABB> getBLOSSOM_SPROUT() {
            return BLOSSOM_SPROUT;
        }

        @NotNull
        public final List<AABB> getBLOSSOM_MATURE() {
            return BLOSSOM_MATURE;
        }

        @NotNull
        public final List<AABB> getLILYPAD_SPROUT() {
            return LILYPAD_SPROUT;
        }

        @NotNull
        public final List<AABB> getLILYPAD_MATURE() {
            return LILYPAD_MATURE;
        }

        @NotNull
        public final List<AABB> getTALL_SPROUT() {
            return TALL_SPROUT;
        }

        @NotNull
        public final List<AABB> getTALL_MATURE() {
            return TALL_MATURE;
        }

        @NotNull
        public final MulchVariant getMulch(@NotNull BlockState state) {
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            if (!state.m_61138_((Property)this.getMULCH())) {
                return MulchVariant.NONE;
            }
            Comparable comparable = state.m_61143_((Property)this.getMULCH());
            Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(MULCH)");
            return (MulchVariant)((Object)comparable);
        }

        public final void setMulch(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MulchVariant mulch) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            Intrinsics.checkNotNullParameter((Object)((Object)mulch), (String)"mulch");
            world.m_46597_(pos, (BlockState)state.m_61124_((Property)this.getMULCH(), (Comparable)((Object)mulch)));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

