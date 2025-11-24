/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.BonemealableBlock
 *  net.minecraft.world.level.block.RenderShape
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.gameevent.GameEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world.BigRootPropagatedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonBlockTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RootBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ShearableBlock;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 G2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001GB\u000f\u0012\u0006\u0010D\u001a\u00020C\u00a2\u0006\u0004\bE\u0010FJ5\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJJ\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\b2!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0014\u0012\u0004\u0012\u00020\r0\u0011H\u0004\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\u001a\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ'\u0010\u001c\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ'\u0010\u001e\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0004\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010 \u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\bH\u0004\u00a2\u0006\u0004\b \u0010\u001dJ\u0017\u0010\"\u001a\u00020!2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\"\u0010#J?\u0010)\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020'2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010(\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b)\u0010*J/\u0010,\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020+2\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b,\u0010-J\u0017\u0010.\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b.\u0010/J\u001d\u00100\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b0\u00101J/\u00103\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u00102\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b3\u00104J/\u00105\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020+2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b5\u00106J\u000f\u00108\u001a\u000207H$\u00a2\u0006\u0004\b8\u00109J\u000f\u0010:\u001a\u00020\u0006H$\u00a2\u0006\u0004\b:\u0010;J%\u0010<\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020+2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010>\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0018H\u0004\u00a2\u0006\u0004\b>\u0010?R\u001a\u0010A\u001a\b\u0012\u0004\u0012\u00020$0@8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010B\u00a8\u0006H"}, d2={"Lcom/cobblemon/mod/common/block/RootBlock;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/BonemealableBlock;", "Lcom/cobblemon/mod/common/block/ShearableBlock;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/core/BlockPos;", "pos", "Lkotlin/Function0;", "", "successCallback", "", "attemptShear", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lkotlin/jvm/functions/Function0;)Z", "Lnet/minecraft/world/level/LevelReader;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "ceiling", "ceilingValidator", "canGoOn", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lkotlin/jvm/functions/Function1;)Z", "Lnet/minecraft/util/RandomSource;", "random", "canGrow", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", "canPlaceAt", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", "canSpread", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", "canSpreadTo", "Lnet/minecraft/world/level/block/RenderShape;", "getRenderType", "(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/RenderShape;", "Lnet/minecraft/core/Direction;", "direction", "neighborState", "Lnet/minecraft/world/level/LevelAccessor;", "neighborPos", "getStateForNeighborUpdate", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/Direction;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", "Lnet/minecraft/server/level/ServerLevel;", "grow", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "hasRandomTicks", "(Lnet/minecraft/world/level/block/state/BlockState;)Z", "hasReachedSpreadCap", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z", "isClient", "isFertilizable", "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Z", "randomTick", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "Lnet/minecraft/world/item/ItemStack;", "shearedDrop", "()Lnet/minecraft/world/item/ItemStack;", "shearedResultingState", "()Lnet/minecraft/world/level/block/state/BlockState;", "spreadFrom", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "spreadingRoot", "(Lnet/minecraft/util/RandomSource;)Lnet/minecraft/world/level/block/state/BlockState;", "", "possibleDirections", "Ljava/util/Set;", "Lnet/minecraft/block/AbstractBlock$Settings;", "settings", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nRootBlock.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RootBlock.kt\ncom/cobblemon/mod/common/block/RootBlock\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,191:1\n39#2,2:192\n41#2,2:197\n44#2,3:200\n47#2:205\n17#3,2:194\n19#3:204\n13579#4:196\n13580#4:203\n39#5:199\n1747#6,3:206\n*S KotlinDebug\n*F\n+ 1 RootBlock.kt\ncom/cobblemon/mod/common/block/RootBlock\n*L\n97#1:192,2\n97#1:197,2\n97#1:200,3\n97#1:205\n97#1:194,2\n97#1:204\n97#1:196\n97#1:203\n97#1:199\n131#1:206,3\n*E\n"})
public abstract class RootBlock
extends Block
implements BonemealableBlock,
ShearableBlock {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Set<Direction> possibleDirections;
    public static final int MAX_PROPAGATING_LIGHT_LEVEL = 11;

    public RootBlock(@NotNull BlockBehaviour.Properties settings) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        super(settings);
        Object[] objectArray = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        this.possibleDirections = SetsKt.setOf((Object[])objectArray);
    }

    public boolean m_6724_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return true;
    }

    public void m_213898_(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (random.m_188500_() < Cobblemon.INSTANCE.getConfig().getBigRootPropagationChance() && world.m_46803_(pos) < 11 && !this.hasReachedSpreadCap((Level)world, pos)) {
            this.spreadFrom(world, pos, random);
        }
    }

    public final boolean hasReachedSpreadCap(@NotNull Level world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        int nearby = 0;
        for (BlockPos blockPos2 : BlockPos.m_121940_((BlockPos)pos.m_7918_(-4, -1, -4), (BlockPos)pos.m_7918_(4, 1, 4))) {
            if (!world.m_8055_(blockPos2).m_204336_(CobblemonBlockTags.ROOTS) || ++nearby < Cobblemon.INSTANCE.getConfig().getMaxRootsInArea()) continue;
            return true;
        }
        return false;
    }

    public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return this.canGoOn(state, world, pos, (Function1<? super BlockState, Boolean>)((Function1)canPlaceAt.1.INSTANCE));
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
        if (direction == Direction.UP && !this.m_7898_(state, (LevelReader)world, pos)) {
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

    public boolean m_7370_(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return this.canSpread(world, pos, state);
    }

    public boolean m_214167_(@NotNull Level world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return this.canSpread((LevelReader)world, pos, state);
    }

    public void m_214148_(@NotNull ServerLevel world, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        this.spreadFrom(world, pos, random);
    }

    @NotNull
    public RenderShape m_7514_(@NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return RenderShape.MODEL;
    }

    /*
     * WARNING - void declaration
     */
    public final void spreadFrom(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Set possibleDirections = CollectionsKt.toMutableSet((Iterable)this.possibleDirections);
        while (!((Collection)possibleDirections).isEmpty()) {
            void this_$iv$iv;
            Direction picked = (Direction)CollectionsKt.random((Collection)possibleDirections, (Random)((Random)Random.Default));
            possibleDirections.remove(picked);
            BlockPos adjacent = pos.m_121945_(picked);
            BlockState blockState = this.m_49966_();
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"this.defaultState");
            LevelReader levelReader = (LevelReader)world;
            Intrinsics.checkNotNullExpressionValue((Object)adjacent, (String)"adjacent");
            if (!this.canSpreadTo(blockState, levelReader, adjacent)) continue;
            BlockState resultingSpread = this.spreadingRoot(random);
            BigRootPropagatedEvent event = new BigRootPropagatedEvent(world, pos, adjacent, resultingSpread);
            CancelableObservable<BigRootPropagatedEvent> $this$iv = CobblemonEvents.BIG_ROOT_PROPAGATED;
            boolean $i$f$postThen = false;
            EventObservable eventObservable = $this$iv;
            Cancelable[] cancelableArray = new Cancelable[]{event};
            Cancelable[] events$iv$iv = cancelableArray;
            boolean $i$f$post = false;
            this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
            Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                Cancelable element$iv$iv$iv;
                Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
                boolean bl = false;
                if (it$iv.isCanceled()) {
                    Cancelable cancelable = it$iv;
                    boolean bl2 = false;
                    Cancelable it = cancelable;
                    continue;
                }
                BigRootPropagatedEvent ev = (BigRootPropagatedEvent)it$iv;
                boolean bl3 = false;
                world.m_46597_(ev.getNewRootPosition(), ev.getResultingSpread());
            }
        }
    }

    @Override
    public boolean attemptShear(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Function0<Unit> successCallback) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(successCallback, (String)"successCallback");
        world.m_5594_(null, pos, SoundEvents.f_12344_, SoundSource.BLOCKS, 1.0f, 1.0f);
        world.m_46597_(pos, this.shearedResultingState());
        ItemStack shearedDrop = this.shearedDrop();
        if (!shearedDrop.m_41619_()) {
            Block.m_49840_((Level)world, (BlockPos)pos, (ItemStack)shearedDrop);
        }
        world.m_142346_(null, GameEvent.f_157781_, pos);
        return true;
    }

    protected final boolean canSpread(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockState state) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            Iterable $this$any$iv = this.possibleDirections;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    Direction direction = (Direction)element$iv;
                    boolean bl2 = false;
                    BlockPos adjacent = pos.m_121945_(direction);
                    BlockState blockState = this.m_49966_();
                    Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"this.defaultState");
                    Intrinsics.checkNotNullExpressionValue((Object)adjacent, (String)"adjacent");
                    if (!this.canSpreadTo(blockState, world, adjacent)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    protected final boolean canSpreadTo(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        BlockState existingState = world.m_8055_(pos);
        return (existingState.m_60795_() || existingState.m_247087_()) && this.canGoOn(state, world, pos, (Function1<? super BlockState, Boolean>)((Function1)canSpreadTo.1.INSTANCE));
    }

    @NotNull
    protected final BlockState spreadingRoot(@NotNull RandomSource random) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if ((double)random.m_188501_() < Cobblemon.INSTANCE.getConfig().getEnergyRootChance()) {
            BlockState blockState2 = CobblemonBlocks.ENERGY_ROOT.m_49966_();
            blockState = blockState2;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"ENERGY_ROOT.defaultState");
        } else {
            BlockState blockState3 = this.m_49966_();
            blockState = blockState3;
            Intrinsics.checkNotNullExpressionValue((Object)blockState3, (String)"this.defaultState");
        }
        return blockState;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected final boolean canGoOn(@NotNull BlockState state, @NotNull LevelReader world, @NotNull BlockPos pos, @NotNull Function1<? super BlockState, Boolean> ceilingValidator) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter(ceilingValidator, (String)"ceilingValidator");
        BlockPos up = pos.m_7494_();
        BlockState upState = world.m_8055_(up);
        if (!upState.m_60783_((BlockGetter)world, up, Direction.DOWN)) return false;
        Intrinsics.checkNotNullExpressionValue((Object)upState, (String)"upState");
        if ((Boolean)ceilingValidator.invoke((Object)upState) == false) return false;
        return true;
    }

    @NotNull
    protected abstract BlockState shearedResultingState();

    @NotNull
    protected abstract ItemStack shearedDrop();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/block/RootBlock$Companion;", "", "", "MAX_PROPAGATING_LIGHT_LEVEL", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

