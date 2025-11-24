/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.Grouping
 *  kotlin.collections.GroupingKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  kotlin.text.CharsKt
 *  kotlin.text.StringsKt
 *  net.minecraft.ResourceLocationException
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.StringTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.gameevent.GameEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthPoint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryHarvestEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.collections.GroupingKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 c2\u00020\u0001:\u0002cdB!\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010=\u001a\u00020<\u00a2\u0006\u0004\ba\u0010bB\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\ba\u00104J\u001f\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0004\b\t\u0010\nJ!\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\r0\f0\u000bH\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000fJ%\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\u001a\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0019\u001a\u0004\u0018\u00010\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\r\u0010\u001c\u001a\u00020\u0015\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u001e\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001f\u0010 J3\u0010%\u001a\b\u0012\u0004\u0012\u00020$0#2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b%\u0010&J\u000f\u0010'\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b'\u0010\u001dJ\u0017\u0010*\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\bH\u0000\u00a2\u0006\u0004\b(\u0010)J\u0017\u0010-\u001a\u00020\u00152\u0006\u0010,\u001a\u00020+H\u0016\u00a2\u0006\u0004\b-\u0010.J/\u0010/\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020!H\u0002\u00a2\u0006\u0004\b/\u00100J\u0015\u00101\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b1\u00102J\u001d\u00103\u001a\u00020\u00152\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b3\u00104J\u000f\u00105\u001a\u00020+H\u0016\u00a2\u0006\u0004\b5\u00106J\u0017\u00109\u001a\n\u0012\u0004\u0012\u000208\u0018\u000107H\u0016\u00a2\u0006\u0004\b9\u0010:J\u0017\u0010;\u001a\u00020\u00152\u0006\u0010,\u001a\u00020+H\u0014\u00a2\u0006\u0004\b;\u0010.R\"\u0010=\u001a\u00020<8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u0016\u0010D\u001a\u00020C8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bD\u0010ER$\u0010H\u001a\u0012\u0012\u0004\u0012\u00020<0Fj\b\u0012\u0004\u0012\u00020<`G8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bH\u0010IR*\u0010K\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010L\u001a\u0004\bM\u0010N\"\u0004\bO\u0010 R*\u0010P\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\bP\u0010L\u001a\u0004\bQ\u0010N\"\u0004\bR\u0010 R$\u0010T\u001a\u0004\u0018\u00010S8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bT\u0010U\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR*\u0010Z\u001a\u00020\u00042\u0006\u0010J\u001a\u00020\u00048\u0006@FX\u0086\u000e\u00a2\u0006\u0012\n\u0004\bZ\u0010L\u001a\u0004\b[\u0010N\"\u0004\b\\\u0010 R\u0014\u0010]\u001a\u00020\u00048\u0002X\u0082D\u00a2\u0006\u0006\n\u0004\b]\u0010LR\u0016\u0010_\u001a\u00020^8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b_\u0010`\u00a8\u0006e"}, d2={"Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/core/BlockPos;", "pos", "", "timer", "applyMulchModifier", "(Lnet/minecraft/core/BlockPos;I)I", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "berryAndGrowthPoint$common", "()Ljava/util/List;", "berryAndGrowthPoint", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "", "decrementMulchDuration", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "generateGrowthPoints", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)V", "generateSimpleYields", "()V", "stagesLeft", "goToNextStageTimer", "(I)V", "Lnet/minecraft/world/entity/player/Player;", "player", "", "Lnet/minecraft/world/item/ItemStack;", "harvest", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)Ljava/util/Collection;", "markDirty", "mutate$common", "(Lcom/cobblemon/mod/common/api/berry/Berry;)V", "mutate", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "refresh", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "refreshTimers", "(Lnet/minecraft/core/BlockPos;)V", "resetGrowTimers", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "toInitialChunkDataNbt", "()Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "toUpdatePacket", "()Lnet/minecraft/network/protocol/Packet;", "writeNbt", "Lnet/minecraft/resources/ResourceLocation;", "berryIdentifier", "Lnet/minecraft/resources/ResourceLocation;", "getBerryIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "setBerryIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)V", "", "growthPointSequence", "Ljava/lang/String;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "growthPoints", "Ljava/util/ArrayList;", "value", "growthTimer", "I", "getGrowthTimer", "()I", "setGrowthTimer", "mulchDuration", "getMulchDuration", "setMulchDuration", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;", "renderState", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;", "getRenderState", "()Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;", "setRenderState", "(Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;)V", "stageTimer", "getStageTimer", "setStageTimer", "ticksPerMinute", "", "wasLoading", "Z", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/resources/ResourceLocation;)V", "Companion", "RenderState", "common"})
@SourceDebugExtension(value={"SMAP\nBerryBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/BerryBlockEntity\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 6 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 7 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,343:1\n1#2:344\n1536#3:345\n800#3,11:357\n1855#3,2:368\n1549#3:370\n1620#3,3:371\n215#4,2:346\n14#5,5:348\n19#5:356\n13579#6:353\n13580#6:355\n3864#6:374\n4387#6,2:375\n14#7:354\n*S KotlinDebug\n*F\n+ 1 BerryBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/BerryBlockEntity\n*L\n211#1:345\n239#1:357,11\n239#1:368,2\n258#1:370\n258#1:371,3\n212#1:346,2\n225#1:348,5\n225#1:356\n225#1:353\n225#1:355\n287#1:374\n287#1:375,2\n225#1:354\n*E\n"})
public final class BerryBlockEntity
extends BlockEntity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public ResourceLocation berryIdentifier;
    private final int ticksPerMinute;
    @Nullable
    private RenderState renderState;
    private int growthTimer;
    private int stageTimer;
    @NotNull
    private final ArrayList<ResourceLocation> growthPoints;
    @NotNull
    private String growthPointSequence;
    private boolean wasLoading;
    private int mulchDuration;
    @NotNull
    private static final BlockEntityTicker<BerryBlockEntity> TICKER = BerryBlockEntity::TICKER$lambda$11;
    @NotNull
    private static final String GROWTH_POINTS = "GrowthPoints";
    @NotNull
    private static final String GROWTH_POINTS_SEQUENCE = "GrowthPointsSequence";
    @NotNull
    private static final String GROWTH_TIMER = "GrowthTimer";
    @NotNull
    private static final String STAGE_TIMER = "StageTimer";
    @NotNull
    private static final String BERRY = "Berry";
    @NotNull
    private static final String MULCH_DURATION = "MulchDuration";

    public BerryBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super(CobblemonBlockEntities.BERRY, pos, state);
        this.ticksPerMinute = 1200;
        this.growthTimer = 72000;
        this.stageTimer = this.growthTimer / 3;
        this.growthPoints = new ArrayList();
        this.growthPointSequence = "0123456789ABCDEF";
    }

    @NotNull
    public final ResourceLocation getBerryIdentifier() {
        ResourceLocation resourceLocation = this.berryIdentifier;
        if (resourceLocation != null) {
            return resourceLocation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"berryIdentifier");
        return null;
    }

    public final void setBerryIdentifier(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.berryIdentifier = resourceLocation;
    }

    @Nullable
    public final RenderState getRenderState() {
        return this.renderState;
    }

    public final void setRenderState(@Nullable RenderState renderState) {
        this.renderState = renderState;
    }

    public final int getGrowthTimer() {
        return this.growthTimer;
    }

    public final void setGrowthTimer(int value2) {
        if (value2 < 0) {
            throw new IllegalArgumentException("You cannot set the growth time to less than zero");
        }
        if (this.growthTimer != value2) {
            this.m_6596_();
        }
        this.growthTimer = value2;
    }

    public final int getStageTimer() {
        return this.stageTimer;
    }

    public final void setStageTimer(int value2) {
        if (this.stageTimer != value2) {
            this.m_6596_();
        }
        this.stageTimer = value2;
    }

    public final int getMulchDuration() {
        return this.mulchDuration;
    }

    public final void setMulchDuration(int value2) {
        this.mulchDuration = value2;
        this.m_6596_();
    }

    public final void decrementMulchDuration(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
        int n;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        MulchVariant currentMulch = BerryBlock.Companion.getMulch(state);
        if (currentMulch == MulchVariant.NONE || currentMulch.getDuration() == -1) {
            return;
        }
        int newDuration = this.mulchDuration - 1;
        if (newDuration <= 0) {
            BerryBlock.Companion.setMulch(world, pos, state, MulchVariant.NONE);
            n = 0;
        } else {
            n = newDuration;
        }
        this.setMulchDuration(n);
    }

    public BerryBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull ResourceLocation berryIdentifier) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)berryIdentifier, (String)"berryIdentifier");
        this(pos, state);
        this.setBerryIdentifier(berryIdentifier);
        this.resetGrowTimers(pos, state);
        Comparable comparable = state.m_61143_((Property)BerryBlock.Companion.getWAS_GENERATED());
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"state.get(BerryBlock.WAS_GENERATED)");
        if (((Boolean)comparable).booleanValue()) {
            Comparable comparable2 = state.m_61143_((Property)BerryBlock.Companion.getAGE());
            Intrinsics.checkNotNullExpressionValue((Object)comparable2, (String)"state.get(BerryBlock.AGE)");
            if (((Number)((Object)comparable2)).intValue() >= 4) {
                this.generateSimpleYields();
            }
        }
    }

    public final void resetGrowTimers(@NotNull BlockPos pos, @NotNull BlockState state) {
        Integer curAge;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Integer n = curAge = (Integer)state.m_61143_((Property)BerryBlock.Companion.getAGE());
        int n2 = 5;
        if (n != null && n == n2) {
            return;
        }
        int multiplier = 14;
        Berry berry = Berries.INSTANCE.getByIdentifier(this.getBerryIdentifier());
        Intrinsics.checkNotNull((Object)berry);
        Berry berry2 = berry;
        Integer n3 = curAge;
        int lowerGrowthLimit = (n3 != null && n3 == 0 ? berry2.getGrowthTime().getFirst() : berry2.getRefreshRate().getFirst()) * multiplier / 10;
        Integer n4 = curAge;
        int upperGrowthLimit = (n4 != null && n4 == 0 ? berry2.getGrowthTime().getLast() : berry2.getRefreshRate().getLast()) * multiplier / 10;
        IntRange growthRange = new IntRange(lowerGrowthLimit, upperGrowthLimit);
        this.setGrowthTimer(this.applyMulchModifier(pos, RangesKt.random((IntRange)growthRange, (Random)((Random)Random.Default)) * this.ticksPerMinute));
        Intrinsics.checkNotNullExpressionValue((Object)curAge, (String)"curAge");
        this.goToNextStageTimer(5 - curAge);
    }

    public final void goToNextStageTimer(int stagesLeft) {
        int avgStageTime = this.growthTimer / stagesLeft;
        Level level = this.f_58857_;
        this.setStageTimer(level != null && (level = level.f_46441_) != null ? level.m_216332_(avgStageTime * 8 / 10, avgStageTime) : (int)((Math.random() * 0.2 + 0.8) * (double)avgStageTime));
        this.setGrowthTimer(this.growthTimer - this.stageTimer);
    }

    private final int applyMulchModifier(BlockPos pos, int timer) {
        Integer curAge;
        Level level = this.f_58857_;
        Object object = level != null ? level.m_8055_(pos) : null;
        if (object == null) {
            return timer;
        }
        BlockState state = object;
        Integer n = curAge = (Integer)state.m_61143_((Property)BerryBlock.Companion.getAGE());
        int n2 = 5;
        if (n != null && n == n2) {
            return timer;
        }
        if (BerryBlock.Companion.getMulch(state) != MulchVariant.GROWTH) {
            return timer;
        }
        return (int)((double)timer * 0.66);
    }

    public final void refreshTimers(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        this.setGrowthTimer(this.applyMulchModifier(pos, this.growthTimer));
        this.setStageTimer(this.applyMulchModifier(pos, this.stageTimer));
    }

    @Nullable
    public final Berry berry() {
        return Berries.INSTANCE.getByIdentifier(this.getBerryIdentifier());
    }

    public final void generateGrowthPoints(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @Nullable LivingEntity placer) {
        char[] cArray;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Berry berry = this.berry();
        if (berry == null) {
            return;
        }
        Berry berry2 = berry;
        int yield = berry2.calculateYield(world, state, pos, placer);
        this.growthPoints.clear();
        int n = 0;
        while (n < yield) {
            int it = n++;
            boolean bl = false;
            ((Collection)this.growthPoints).add(berry2.getIdentifier());
        }
        char[] cArray2 = this.growthPointSequence.toCharArray();
        Intrinsics.checkNotNullExpressionValue((Object)cArray2, (String)"this as java.lang.String).toCharArray()");
        char[] it = cArray = cArray2;
        BerryBlockEntity berryBlockEntity = this;
        boolean bl = false;
        if (berry2.getRandomizedGrowthPoints()) {
            ArraysKt.shuffle((char[])it);
        }
        berryBlockEntity.growthPointSequence = StringsKt.concatToString((char[])cArray);
        this.m_6596_();
    }

    public final void generateSimpleYields() {
        char[] cArray;
        Berry berry = this.berry();
        if (berry == null || (berry = berry.getBaseYield()) == null) {
            return;
        }
        int numBerries = RangesKt.random((IntRange)berry, (Random)((Random)Random.Default));
        int n = 0;
        while (n < numBerries) {
            int it = n++;
            boolean bl = false;
            this.growthPoints.add(this.getBerryIdentifier());
        }
        char[] cArray2 = this.growthPointSequence.toCharArray();
        Intrinsics.checkNotNullExpressionValue((Object)cArray2, (String)"this as java.lang.String).toCharArray()");
        char[] it = cArray = cArray2;
        BerryBlockEntity berryBlockEntity = this;
        boolean bl = false;
        Berry berry2 = this.berry();
        if (!(berry2 != null ? !berry2.getRandomizedGrowthPoints() : false)) {
            ArraysKt.shuffle((char[])it);
        }
        berryBlockEntity.growthPointSequence = StringsKt.concatToString((char[])cArray);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Collection<ItemStack> harvest(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Player player) {
        Object object;
        Map unique;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        ArrayList drops = new ArrayList();
        Iterable $this$groupingBy$iv = this.growthPoints;
        boolean $i$f$groupingBy = false;
        Map $this$forEach$iv = unique = GroupingKt.eachCount((Grouping)((Grouping)new Grouping<ResourceLocation, ResourceLocation>($this$groupingBy$iv){
            final /* synthetic */ Iterable $this_groupingBy;
            {
                this.$this_groupingBy = $receiver;
            }

            @NotNull
            public Iterator<ResourceLocation> sourceIterator() {
                return this.$this_groupingBy.iterator();
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            public Object keyOf(Object element) {
                void var2_2;
                ResourceLocation it = (ResourceLocation)element;
                boolean bl = false;
                return var2_2;
            }
        }));
        boolean $i$f$forEach = false;
        for (Map.Entry element$iv : $this$forEach$iv.entrySet()) {
            int count;
            object = element$iv;
            boolean bl2 = false;
            ResourceLocation identifier = (ResourceLocation)object.getKey();
            int amount = ((Number)object.getValue()).intValue();
            Berry berry = Berries.INSTANCE.getByIdentifier(identifier);
            BerryItem berryItem = berry != null ? berry.item() : null;
            if (berryItem == null) continue;
            for (int remain = amount; remain > 0; remain -= count) {
                count = RangesKt.coerceAtMost((int)remain, (int)berryItem.m_41459_());
                ((Collection)drops).add(new ItemStack((ItemLike)berryItem, count));
            }
        }
        Berry berry = this.berry();
        if (berry != null) {
            Berry berry2 = berry;
            boolean bl = false;
            if (player instanceof ServerPlayer) {
                void $this$iv;
                object = CobblemonEvents.BERRY_HARVEST;
                BerryHarvestEvent[] bl2 = new BerryHarvestEvent[]{new BerryHarvestEvent(berry2, (ServerPlayer)player, world, pos, state, this, drops)};
                BerryHarvestEvent[] events$iv = bl2;
                boolean $i$f$post = false;
                $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                BerryHarvestEvent[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach2 = false;
                int n = $this$forEach$iv$iv.length;
                for (int i = 0; i < n; ++i) {
                    BerryHarvestEvent element$iv$iv;
                    BerryHarvestEvent berryHarvestEvent = element$iv$iv = $this$forEach$iv$iv[i];
                    boolean bl3 = false;
                    BerryHarvestEvent it = berryHarvestEvent;
                }
            }
        }
        this.refresh(world, pos, state, player);
        return drops;
    }

    /*
     * WARNING - void declaration
     */
    public void m_142466_(@NotNull CompoundTag nbt) {
        void $this$filterIsInstanceTo$iv$iv;
        Iterable it;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        String string = nbt.m_128461_(BERRY);
        String string2 = string;
        BerryBlockEntity berryBlockEntity = this;
        boolean bl = false;
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        boolean bl2 = !StringsKt.isBlank((CharSequence)((CharSequence)((Object)it)));
        String string3 = bl2 ? string : null;
        if (string3 == null) {
            string3 = "cobblemon:pecha";
        }
        String string4 = string3;
        berryBlockEntity.setBerryIdentifier(new ResourceLocation(string4));
        this.wasLoading = true;
        this.growthPoints.clear();
        this.setGrowthTimer(RangesKt.coerceAtLeast((int)nbt.m_128451_(GROWTH_TIMER), (int)0));
        this.setStageTimer(RangesKt.coerceAtLeast((int)nbt.m_128451_(STAGE_TIMER), (int)0));
        ListTag listTag = nbt.m_128437_(GROWTH_POINTS, 8);
        Intrinsics.checkNotNullExpressionValue((Object)listTag, (String)"nbt.getList(GROWTH_POINT\u2026List.STRING_TYPE.toInt())");
        Iterable $this$filterIsInstance$iv = (Iterable)listTag;
        boolean $i$f$filterIsInstance = false;
        it = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof StringTag)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            StringTag element = (StringTag)element$iv;
            boolean bl3 = false;
            try {
                ResourceLocation identifier = new ResourceLocation(element.m_7916_());
                ((Collection)this.growthPoints).add(identifier);
            }
            catch (ResourceLocationException resourceLocationException) {
            }
        }
        this.setMulchDuration(nbt.m_128451_(MULCH_DURATION));
        this.wasLoading = false;
        if (nbt.m_128441_(GROWTH_POINTS_SEQUENCE)) {
            String string5 = nbt.m_128461_(GROWTH_POINTS_SEQUENCE);
            Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"nbt.getString(GROWTH_POINTS_SEQUENCE)");
            this.growthPointSequence = string5;
        }
        RenderState renderState = this.renderState;
        if (renderState != null) {
            renderState.setNeedsRebuild(true);
        }
    }

    /*
     * WARNING - void declaration
     */
    protected void m_183515_(@NotNull CompoundTag nbt) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128405_(GROWTH_TIMER, this.growthTimer);
        nbt.m_128405_(STAGE_TIMER, this.stageTimer);
        ListTag list = new ListTag();
        Collection collection = (Collection)list;
        Iterable $this$map$iv = this.growthPoints;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            ResourceLocation resourceLocation = (ResourceLocation)item$iv$iv;
            Collection collection2 = destination$iv$iv;
            boolean bl = false;
            collection2.add(StringTag.m_129297_((String)it.toString()));
        }
        Iterable iterable2 = (List)destination$iv$iv;
        CollectionsKt.addAll((Collection)collection, (Iterable)iterable2);
        nbt.m_128365_(GROWTH_POINTS, (Tag)list);
        nbt.m_128359_(BERRY, this.getBerryIdentifier().toString());
        nbt.m_128405_(MULCH_DURATION, this.mulchDuration);
        nbt.m_128359_(GROWTH_POINTS_SEQUENCE, this.growthPointSequence);
    }

    public void m_6596_() {
        if (!this.wasLoading) {
            super.m_6596_();
        }
    }

    @Nullable
    public Packet<ClientGamePacketListener> m_58483_() {
        return (Packet)ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    @NotNull
    public CompoundTag m_5995_() {
        CompoundTag compoundTag = this.m_187482_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"this.createNbt()");
        return compoundTag;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<Pair<Berry, GrowthPoint>> berryAndGrowthPoint$common() {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        Berry berry = this.berry();
        if (berry == null) {
            return CollectionsKt.emptyList();
        }
        Berry baseBerry = berry;
        ArrayList<Pair> berryPoints = new ArrayList<Pair>();
        char[] cArray = this.growthPointSequence.toCharArray();
        Intrinsics.checkNotNullExpressionValue((Object)cArray, (String)"this as java.lang.String).toCharArray()");
        Object object = cArray;
        boolean $i$f$filter = false;
        void var6_5 = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        int n = ((void)$this$filterTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void element$iv$iv;
            void it = element$iv$iv = $this$filterTo$iv$iv[i];
            boolean bl = false;
            if (!(CharsKt.digitToInt((char)it, (int)16) < baseBerry.getGrowthPoints().length)) continue;
            destination$iv$iv.add(Character.valueOf((char)element$iv$iv));
        }
        List sequenceIndices = (List)destination$iv$iv;
        object = this.growthPoints.iterator();
        int n2 = 0;
        while (object.hasNext()) {
            Berry berry2;
            int index = n2++;
            ResourceLocation identifier = (ResourceLocation)object.next();
            if (Berries.INSTANCE.getByIdentifier(identifier) == null) continue;
            int sequenceIndexHex = CharsKt.digitToInt((char)((Character)sequenceIndices.get(index)).charValue(), (int)16);
            berryPoints.add(TuplesKt.to((Object)berry2, (Object)baseBerry.getGrowthPoints()[sequenceIndexHex]));
        }
        return berryPoints;
    }

    public final void mutate$common(@NotNull Berry berry) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        if (this.growthPoints.isEmpty()) {
            return;
        }
        int index = RangesKt.random((IntRange)CollectionsKt.getIndices((Collection)this.growthPoints), (Random)((Random)Random.Default));
        this.growthPoints.set(index, berry.getIdentifier());
        this.m_6596_();
    }

    private final void refresh(Level world, BlockPos pos, BlockState state, Player player) {
        BlockState newState = (BlockState)state.m_61124_((Property)BerryBlock.Companion.getAGE(), (Comparable)Integer.valueOf(3));
        world.m_7731_(pos, newState, 2);
        world.m_142346_((Entity)player, GameEvent.f_157792_, pos);
        Intrinsics.checkNotNullExpressionValue((Object)newState, (String)"newState");
        this.resetGrowTimers(pos, newState);
    }

    private static final void TICKER$lambda$11(Level world, BlockPos pos, BlockState state, BerryBlockEntity blockEntity) {
        if (world.f_46443_) {
            return;
        }
        if (blockEntity.stageTimer >= 0) {
            int n = blockEntity.stageTimer;
            blockEntity.setStageTimer(n + -1);
        }
        if (blockEntity.stageTimer == 0) {
            Block block = state.m_60734_();
            Intrinsics.checkNotNull((Object)block, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock");
            BerryBlock berryBlock = (BerryBlock)block;
            Intrinsics.checkNotNull((Object)world, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
            ServerLevel serverLevel = (ServerLevel)world;
            RandomSource randomSource = world.f_46441_;
            Intrinsics.checkNotNullExpressionValue((Object)randomSource, (String)"world.random");
            Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
            Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
            berryBlock.m_214148_(serverLevel, randomSource, pos, state);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0000X\u0080\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$Companion;", "", "", "BERRY", "Ljava/lang/String;", "GROWTH_POINTS", "GROWTH_POINTS_SEQUENCE", "GROWTH_TIMER", "MULCH_DURATION", "STAGE_TIMER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "TICKER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTICKER$common", "()Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BlockEntityTicker<BerryBlockEntity> getTICKER$common() {
            return TICKER;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001R\u001c\u0010\u0007\u001a\u00020\u00028&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0003\u0010\u0004\"\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity$RenderState;", "Ljava/lang/AutoCloseable;", "", "getNeedsRebuild", "()Z", "setNeedsRebuild", "(Z)V", "needsRebuild", "common"})
    public static interface RenderState
    extends AutoCloseable {
        public boolean getNeedsRebuild();

        public void setNeedsRebuild(boolean var1);
    }
}

