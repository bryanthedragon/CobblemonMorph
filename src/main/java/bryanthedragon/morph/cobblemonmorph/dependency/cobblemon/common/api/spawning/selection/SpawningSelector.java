/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedFloatingPointRange
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J1\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ1\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0004\b\f\u0010\u000bJ3\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0018\u00010\r2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "contexts", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "", "getProbabilities", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Ljava/util/Map;", "getTotalWeights", "Lkotlin/Pair;", "select", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Lkotlin/Pair;", "common"})
public interface SpawningSelector {
    @Nullable
    public Pair<SpawningContext, SpawnDetail> select(@NotNull Spawner var1, @NotNull List<? extends SpawningContext> var2);

    @NotNull
    public Map<SpawnDetail, Float> getProbabilities(@NotNull Spawner var1, @NotNull List<? extends SpawningContext> var2);

    @NotNull
    public Map<SpawnDetail, Float> getTotalWeights(@NotNull Spawner var1, @NotNull List<? extends SpawningContext> var2);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nSpawningSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/SpawningSelector$DefaultImpls\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,36:1\n215#2,2:37\n*S KotlinDebug\n*F\n+ 1 SpawningSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/SpawningSelector$DefaultImpls\n*L\n30#1:37,2\n*E\n"})
    public static final class DefaultImpls {
        @NotNull
        public static Map<SpawnDetail, Float> getProbabilities(@NotNull SpawningSelector $this, @NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
            Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
            Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
            Map<SpawnDetail, Float> weights = $this.getTotalWeights(spawner, contexts);
            float totalWeight = CollectionsKt.sumOfFloat((Iterable)weights.values());
            Map percentages = new LinkedHashMap();
            Map<SpawnDetail, Float> $this$forEach$iv = weights;
            boolean $i$f$forEach = false;
            Iterator<Map.Entry<SpawnDetail, Float>> iterator = $this$forEach$iv.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<SpawnDetail, Float> element$iv;
                Map.Entry<SpawnDetail, Float> entry = element$iv = iterator.next();
                boolean bl = false;
                SpawnDetail spawnDetail = entry.getKey();
                float weight = ((Number)entry.getValue()).floatValue();
                percentages.put(spawnDetail, RangesKt.coerceIn((Comparable)Float.valueOf(weight / totalWeight * 100.0f), (ClosedFloatingPointRange)RangesKt.rangeTo((float)0.0f, (float)100.0f)));
            }
            return percentages;
        }
    }
}

