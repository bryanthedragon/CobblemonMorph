/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.PokemonStats;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000J\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\b\u0004\u001a9\u0010\u0006\u001a\u00020\u00052*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00010\u0000\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a9\u0010\b\u001a\u00020\u00052*\u0010\u0004\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00010\u0000\"\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001\u00a2\u0006\u0004\b\b\u0010\u0007\u001a-\u0010\u000e\u001a\u00020\r\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00028\u00000\n2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000f\u001a\u0017\u0010\u0013\u001a\u00020\u0012*\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\u0004\b\u0013\u0010\u0014\u001a3\u0010\u0019\u001a\u0004\u0018\u00018\u0000\"\u0004\b\u0000\u0010\t*\b\u0012\u0004\u0012\u00028\u00000\u00152\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001b"}, d2={"", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "", "entries", "Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "evsOf", "([Lkotlin/Pair;)Lcom/cobblemon/mod/common/pokemon/PokemonStats;", "ivsOf", "T", "", "index1", "index2", "", "swap", "(Ljava/util/List;II)V", "", "Lnet/minecraft/nbt/Tag;", "Lnet/minecraft/nbt/ListTag;", "toNbtList", "(Ljava/util/Collection;)Lnet/minecraft/nbt/ListTag;", "", "Lkotlin/Function1;", "", "weightFunction", "weightedSelection", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "common"})
@SourceDebugExtension(value={"SMAP\nCollectionUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CollectionUtils.kt\ncom/cobblemon/mod/common/util/CollectionUtilsKt\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n13579#2,2:62\n13579#2,2:64\n1855#3,2:66\n1855#3,2:68\n1855#3,2:70\n*S KotlinDebug\n*F\n+ 1 CollectionUtils.kt\ncom/cobblemon/mod/common/util/CollectionUtilsKt\n*L\n22#1:62,2\n28#1:64,2\n34#1:66,2\n37#1:68,2\n59#1:70,2\n*E\n"})
public final class CollectionUtilsKt {
    @NotNull
    public static final PokemonStats ivsOf(Pair<? extends Stat, Integer> ... entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        IVs stats = new IVs();
        Pair<? extends Stat, Integer>[] $this$forEach$iv = entries;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            Pair<? extends Stat, Integer> element$iv;
            Pair<? extends Stat, Integer> pair = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Stat stat = (Stat)pair.component1();
            int amount = ((Number)pair.component2()).intValue();
            stats.set(stat, amount);
        }
        return stats;
    }

    @NotNull
    public static final PokemonStats evsOf(Pair<? extends Stat, Integer> ... entries) {
        Intrinsics.checkNotNullParameter(entries, (String)"entries");
        EVs stats = new EVs();
        Pair<? extends Stat, Integer>[] $this$forEach$iv = entries;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            Pair<? extends Stat, Integer> element$iv;
            Pair<? extends Stat, Integer> pair = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            Stat stat = (Stat)pair.component1();
            int amount = ((Number)pair.component2()).intValue();
            stats.set(stat, amount);
        }
        return stats;
    }

    @Nullable
    public static final <T> T weightedSelection(@NotNull Iterable<? extends T> $this$weightedSelection, @NotNull Function1<? super T, ? extends Number> weightFunction) {
        Intrinsics.checkNotNullParameter($this$weightedSelection, (String)"<this>");
        Intrinsics.checkNotNullParameter(weightFunction, (String)"weightFunction");
        float weightSum = 0.0f;
        Iterable<T> $this$forEach$iv = $this$weightedSelection;
        boolean $i$f$forEach = false;
        Iterator<T> iterator = $this$forEach$iv.iterator();
        while (iterator.hasNext()) {
            T element$iv;
            T it = element$iv = iterator.next();
            boolean bl = false;
            weightSum += Math.max(0.0f, ((Number)weightFunction.invoke(it)).floatValue());
        }
        float chosenSum = Random.Default.nextFloat() * weightSum;
        weightSum = 0.0f;
        Iterable<T> $this$forEach$iv2 = $this$weightedSelection;
        boolean $i$f$forEach2 = false;
        Iterator<T> iterator2 = $this$forEach$iv2.iterator();
        while (iterator2.hasNext()) {
            T element$iv;
            T it = element$iv = iterator2.next();
            boolean bl = false;
            float weight = ((Number)weightFunction.invoke(it)).floatValue();
            if (!(weight > 0.0f) || !((weightSum += weight) >= chosenSum)) continue;
            return it;
        }
        return null;
    }

    public static final <T> void swap(@NotNull List<T> $this$swap, int index1, int index2) {
        Intrinsics.checkNotNullParameter($this$swap, (String)"<this>");
        T t1 = $this$swap.get(index1);
        T t2 = $this$swap.get(index2);
        $this$swap.set(index1, t2);
        $this$swap.set(index2, t1);
    }

    @NotNull
    public static final ListTag toNbtList(@NotNull Collection<? extends Tag> $this$toNbtList) {
        Intrinsics.checkNotNullParameter($this$toNbtList, (String)"<this>");
        ListTag nbtList = new ListTag();
        Iterable $this$forEach$iv = $this$toNbtList;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Tag p0 = (Tag)element$iv;
            boolean bl = false;
            nbtList.add((Object)p0);
        }
        return nbtList;
    }
}

