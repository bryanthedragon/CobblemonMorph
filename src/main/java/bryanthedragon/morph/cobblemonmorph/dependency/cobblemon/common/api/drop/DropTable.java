/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.drops.LootDroppedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u001b\u0010\u001cJ;\u0010\r\u001a\u00020\f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00100\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/drop/DropTable;", "", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/Vec3;", "pos", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lkotlin/ranges/IntRange;", "amount", "", "drop", "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/server/level/ServerPlayer;Lkotlin/ranges/IntRange;)V", "", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "getDrops", "(Lkotlin/ranges/IntRange;)Ljava/util/List;", "Lkotlin/ranges/IntRange;", "getAmount", "()Lkotlin/ranges/IntRange;", "", "entries", "Ljava/util/List;", "getEntries", "()Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nDropTable.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DropTable.kt\ncom/cobblemon/mod/common/api/drop/DropTable\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n*L\n1#1,99:1\n766#2:100\n857#2,2:101\n288#2,2:103\n1855#2,2:114\n39#3,2:105\n41#3,2:110\n44#3:113\n46#3:116\n47#3:119\n17#4,2:107\n19#4:118\n13579#5:109\n13580#5:117\n39#6:112\n*S KotlinDebug\n*F\n+ 1 DropTable.kt\ncom/cobblemon/mod/common/api/drop/DropTable\n*L\n49#1:100\n49#1:101,2\n59#1:103,2\n96#1:114,2\n94#1:105,2\n94#1:110,2\n94#1:113\n94#1:116\n94#1:119\n94#1:107,2\n94#1:118\n94#1:109\n94#1:117\n94#1:112\n*E\n"})
public final class DropTable {
    @NotNull
    private final List<DropEntry> entries = new ArrayList();
    @NotNull
    private final IntRange amount = new IntRange(1, 1);

    @NotNull
    public final List<DropEntry> getEntries() {
        return this.entries;
    }

    @NotNull
    public final IntRange getAmount() {
        return this.amount;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<DropEntry> getDrops(@NotNull IntRange amount) {
        DropEntry it;
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)amount, (String)"amount");
        int chosenAmount = RangesKt.random((IntRange)amount, (Random)((Random)Random.Default));
        Iterable $this$filter$iv = this.entries;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (DropEntry)element$iv$iv;
            boolean bl = false;
            if (!(it.getQuantity() <= chosenAmount)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List possibleDrops = CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        if (possibleDrops.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        List drops = new ArrayList();
        int dropCount = 0;
        do {
            Object v0;
            block5: {
                Iterable $this$firstOrNull$iv = possibleDrops;
                boolean $i$f$firstOrNull = false;
                for (Object element$iv : $this$firstOrNull$iv) {
                    it = (DropEntry)element$iv;
                    boolean bl = false;
                    if (!(Random.Default.nextFloat() * 100.0f < it.getPercentage())) continue;
                    v0 = element$iv;
                    break block5;
                }
                v0 = null;
            }
            DropEntry drop = v0;
            if (drop == null) {
                ++dropCount;
                continue;
            }
            drops.add(drop);
            int remaining = chosenAmount - (dropCount += drop.getQuantity());
            possibleDrops.removeIf(arg_0 -> DropTable.getDrops$lambda$2((Function1)new Function1<DropEntry, Boolean>(drop, (List<DropEntry>)drops, remaining){
                final /* synthetic */ DropEntry $drop;
                final /* synthetic */ List<DropEntry> $drops;
                final /* synthetic */ int $remaining;
                {
                    this.$drop = $drop;
                    this.$drops = $drops;
                    this.$remaining = $remaining;
                    super(1);
                }

                /*
                 * WARNING - void declaration
                 * Enabled aggressive block sorting
                 */
                @NotNull
                public final Boolean invoke(@NotNull DropEntry it) {
                    boolean bl;
                    block8: {
                        block7: {
                            int n;
                            void $this$count$iv;
                            Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                            if (!Intrinsics.areEqual((Object)it, (Object)this.$drop)) break block7;
                            Iterable iterable = this.$drops;
                            DropEntry dropEntry = this.$drop;
                            int n2 = it.getMaxSelectableTimes();
                            boolean $i$f$count = false;
                            if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                                v0 = 0;
                            } else {
                                int count$iv = 0;
                                for (T element$iv : $this$count$iv) {
                                    DropEntry it2 = (DropEntry)element$iv;
                                    boolean bl2 = false;
                                    if (!Intrinsics.areEqual((Object)it2, (Object)dropEntry) || ++count$iv >= 0) continue;
                                    CollectionsKt.throwCountOverflow();
                                }
                                v0 = n = count$iv;
                            }
                            if (n2 <= n) break block8;
                        }
                        if (it.getQuantity() <= this.$remaining) {
                            bl = false;
                            return bl;
                        }
                    }
                    bl = true;
                    return bl;
                }
            }, arg_0));
        } while (dropCount < chosenAmount && !((Collection)possibleDrops).isEmpty());
        return drops;
    }

    public static /* synthetic */ List getDrops$default(DropTable dropTable, IntRange intRange, int n, Object object) {
        if ((n & 1) != 0) {
            intRange = dropTable.amount;
        }
        return dropTable.getDrops(intRange);
    }

    /*
     * WARNING - void declaration
     */
    public final void drop(@Nullable LivingEntity entity2, @NotNull ServerLevel world, @NotNull Vec3 pos, @Nullable ServerPlayer player, @NotNull IntRange amount) {
        void this_$iv$iv;
        void event$iv;
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)amount, (String)"amount");
        List drops = CollectionsKt.toMutableList((Collection)this.getDrops(amount));
        Intrinsics.checkNotNull((Object)entity2, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity");
        ItemStack heldItem2 = ((PokemonEntity)entity2).getPokemon().heldItemNoCopy$common();
        if (!heldItem2.m_41619_()) {
            ((PokemonEntity)entity2).m_19998_((ItemLike)heldItem2.m_41720_());
        }
        CancelableObservable<LootDroppedEvent> cancelableObservable = CobblemonEvents.LOOT_DROPPED;
        Cancelable cancelable = new LootDroppedEvent(this, player, entity2, drops);
        boolean $i$f$postThen = false;
        EventObservable eventObservable = (EventObservable)$this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{event$iv};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (it$iv.isCanceled()) {
                Cancelable cancelable2 = it$iv;
                boolean bl2 = false;
                it = cancelable2;
                continue;
            }
            it = (LootDroppedEvent)it$iv;
            boolean bl3 = false;
            Iterable $this$forEach$iv = ((LootDroppedEvent)it).getDrops();
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                DropEntry it2 = (DropEntry)element$iv;
                boolean bl4 = false;
                it2.drop(entity2, world, pos, player);
            }
        }
    }

    public static /* synthetic */ void drop$default(DropTable dropTable, LivingEntity livingEntity, ServerLevel serverLevel, Vec3 vec3, ServerPlayer serverPlayer, IntRange intRange, int n, Object object) {
        if ((n & 0x10) != 0) {
            intRange = dropTable.amount;
        }
        dropTable.drop(livingEntity, serverLevel, vec3, serverPlayer, intRange);
    }

    private static final boolean getDrops$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

