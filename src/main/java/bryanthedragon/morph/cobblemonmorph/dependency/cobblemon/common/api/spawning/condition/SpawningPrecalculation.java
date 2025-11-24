/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.FinalPrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.NestedPrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.PrecalculationResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001J5\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0010\u0010\u0006\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0019\u0010\f\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\f\u0010\rJ\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000e\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\f\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation;", "", "T", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "details", "next", "Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "generate", "(Ljava/util/List;Ljava/util/List;)Lcom/cobblemon/mod/common/api/spawning/condition/PrecalculationResult;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "select", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/lang/Object;", "detail", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Ljava/util/List;", "common"})
public interface SpawningPrecalculation<T> {
    @NotNull
    public List<T> select(@NotNull SpawnDetail var1);

    @Nullable
    public T select(@NotNull SpawningContext var1);

    @NotNull
    public PrecalculationResult<T> generate(@NotNull List<? extends SpawnDetail> var1, @NotNull List<? extends SpawningPrecalculation<?>> var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nSpawningPrecalculation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningPrecalculation.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,135:1\n766#2:136\n857#2,2:137\n1360#2:139\n1446#2,2:140\n1549#2:142\n1620#2,3:143\n1448#2,3:146\n1490#2:149\n1520#2,3:150\n1523#2,3:160\n1179#2,2:163\n1253#2,4:165\n361#3,7:153\n*S KotlinDebug\n*F\n+ 1 SpawningPrecalculation.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningPrecalculation$DefaultImpls\n*L\n48#1:136\n48#1:137,2\n49#1:139\n49#1:140,2\n49#1:142\n49#1:143,3\n49#1:146,3\n50#1:149\n50#1:150,3\n50#1:160,3\n60#1:163,2\n60#1:165,4\n50#1:153,7\n*E\n"})
    public static final class DefaultImpls {
        /*
         * WARNING - void declaration
         */
        @NotNull
        public static <T> PrecalculationResult<T> generate(@NotNull SpawningPrecalculation<T> $this, @NotNull List<? extends SpawnDetail> details, @NotNull List<? extends SpawningPrecalculation<?>> next) {
            void $this$associateTo$iv$iv;
            void $this$associate$iv;
            Iterable $this$groupByTo$iv$iv;
            void $this$groupBy$iv;
            Iterable list$iv$iv;
            void $this$flatMapTo$iv$iv;
            Iterable $this$flatMap$iv;
            SpawnDetail it;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv;
            Intrinsics.checkNotNullParameter(details, (String)"details");
            Intrinsics.checkNotNullParameter(next, (String)"next");
            Iterable iterable = details;
            boolean $i$f$filter = false;
            void var6_6 = $this$filter$iv;
            Object destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (SpawnDetail)element$iv$iv;
                boolean bl = false;
                boolean bl2 = !((Collection)$this.select(it)).isEmpty();
                if (!bl2) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$flatMap = false;
            $this$filterTo$iv$iv = $this$flatMap$iv;
            destination$iv$iv = new ArrayList();
            boolean $i$f$flatMapTo = false;
            for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
                void $this$mapTo$iv$iv;
                SpawnDetail detail = (SpawnDetail)element$iv$iv;
                boolean bl = false;
                Iterable $this$map$iv = $this.select(detail);
                boolean $i$f$map = false;
                Iterable iterable2 = $this$map$iv;
                Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                Iterator iterator = $this$mapTo$iv$iv.iterator();
                while (iterator.hasNext()) {
                    void it2;
                    Object item$iv$iv;
                    Object t = item$iv$iv = iterator.next();
                    Collection collection = destination$iv$iv2;
                    boolean bl3 = false;
                    collection.add(TuplesKt.to((Object)it2, (Object)detail));
                }
                list$iv$iv = (List)destination$iv$iv2;
                CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
            }
            $this$flatMap$iv = (List)destination$iv$iv;
            boolean $i$f$groupBy = false;
            $this$flatMapTo$iv$iv = $this$groupBy$iv;
            destination$iv$iv = new LinkedHashMap();
            boolean $i$f$groupByTo = false;
            for (Object element$iv$iv : $this$groupByTo$iv$iv) {
                void it3;
                Object object;
                it = (Pair)element$iv$iv;
                boolean $i$a$-groupBy-SpawningPrecalculation$generate$mapping$52 = false;
                Object key$iv$iv = it.getFirst();
                Object $this$getOrPut$iv$iv$iv = destination$iv$iv;
                boolean $i$f$getOrPut = false;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    boolean bl = false;
                    List answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                    object = answer$iv$iv$iv;
                } else {
                    object = value$iv$iv$iv;
                }
                list$iv$iv = (List)object;
                Pair $i$a$-groupBy-SpawningPrecalculation$generate$mapping$52 = (Pair)element$iv$iv;
                Iterable iterable3 = list$iv$iv;
                boolean bl = false;
                iterable3.add((SpawnDetail)it3.getSecond());
            }
            Map mapping = MapsKt.toMap((Map)destination$iv$iv);
            if (next.isEmpty()) {
                return new FinalPrecalculationResult($this, mapping);
            }
            SpawningPrecalculation immediateNext = (SpawningPrecalculation)CollectionsKt.first(next);
            List<? extends SpawningPrecalculation<?>> subNext = next.subList(1, next.size());
            $this$groupByTo$iv$iv = mapping.entrySet();
            SpawningPrecalculation<T> spawningPrecalculation = $this;
            boolean $i$f$associate = false;
            int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
            Iterator iterator = $this$associate$iv;
            Map destination$iv$iv3 = new LinkedHashMap(capacity$iv);
            boolean $i$f$associateTo = false;
            for (Object element$iv$iv : $this$associateTo$iv$iv) {
                Map map = destination$iv$iv3;
                Map.Entry it4 = (Map.Entry)element$iv$iv;
                boolean bl = false;
                Pair pair = TuplesKt.to(it4.getKey(), immediateNext.generate((List)it4.getValue(), subNext));
                map.put(pair.getFirst(), pair.getSecond());
            }
            Map map = destination$iv$iv3;
            SpawningPrecalculation<T> spawningPrecalculation2 = spawningPrecalculation;
            return new NestedPrecalculationResult<T>(spawningPrecalculation2, map);
        }
    }
}

