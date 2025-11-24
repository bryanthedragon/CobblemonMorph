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
 *  kotlin.random.Random
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
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
import kotlin.random.Random;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001:\u0002\u0014\u0015B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0013J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0004\u00a2\u0006\u0004\b\b\u0010\tJ1\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ3\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u000f2\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector;", "Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "contexts", "Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector$ContextSelectionData;", "getSelectionData", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector$ContextSelectionData;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "", "getTotalWeights", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Ljava/util/Map;", "Lkotlin/Pair;", "select", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Lkotlin/Pair;", "<init>", "()V", "ContextSelectionData", "SelectingSpawnInformation", "common"})
@SourceDebugExtension(value={"SMAP\nFlatSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlatSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,158:1\n1855#2:159\n1855#2:160\n1856#2:168\n1856#2:169\n361#3,7:161\n*S KotlinDebug\n*F\n+ 1 FlatSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatSelector\n*L\n74#1:159\n75#1:160\n75#1:168\n74#1:169\n83#1:161,7\n*E\n"})
public class FlatSelector
implements SpawningSelector {
    @NotNull
    protected final ContextSelectionData getSelectionData(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        Map spawnsToContexts = new LinkedHashMap();
        float percentSum = 0.0f;
        Iterable $this$forEach$iv = contexts;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SpawningContext ctx = (SpawningContext)element$iv;
            boolean bl = false;
            Iterable $this$forEach$iv2 = spawner.getMatchingSpawns(ctx);
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Object object;
                SpawnDetail it = (SpawnDetail)element$iv2;
                boolean bl2 = false;
                if (it.getPercentage() > 0.0f && !spawnsToContexts.containsKey(it)) {
                    percentSum += it.getPercentage();
                }
                Map $this$getOrPut$iv = spawnsToContexts;
                boolean $i$f$getOrPut = false;
                Object value$iv = $this$getOrPut$iv.get(it);
                if (value$iv == null) {
                    boolean bl3 = false;
                    SelectingSpawnInformation answer$iv = new SelectingSpawnInformation();
                    $this$getOrPut$iv.put(it, answer$iv);
                    object = answer$iv;
                } else {
                    object = value$iv;
                }
                SelectingSpawnInformation selectingSpawnInformation = (SelectingSpawnInformation)object;
                selectingSpawnInformation.add(ctx, ctx.getWeight(it));
            }
        }
        return new ContextSelectionData(spawnsToContexts, percentSum);
    }

    @Override
    @Nullable
    public Pair<SpawningContext, SpawnDetail> select(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        ContextSelectionData selectionData = this.getSelectionData(spawner, contexts);
        if (selectionData.getSize() == 0) {
            return null;
        }
        Map<SpawnDetail, SelectingSpawnInformation> spawnsToContexts = selectionData.getSpawnsToContexts();
        float percentSum = selectionData.getPercentSum();
        if (percentSum > 0.0f) {
            if (percentSum > 100.0f) {
                Cobblemon.INSTANCE.getLOGGER().warn(StringsKt.trimIndent((String)("\n                        A spawn list for " + spawner.getName() + " exceeded 100% on percentage sums...\n                        This means you don't understand how this option works.\n                    ")));
                return null;
            }
            float selectedPercentage = (float)100 - Random.Default.nextFloat() * (float)100;
            percentSum = 0.0f;
            for (Map.Entry<SpawnDetail, SelectingSpawnInformation> entry : spawnsToContexts.entrySet()) {
                SpawnDetail spawnDetail = entry.getKey();
                SelectingSpawnInformation info = entry.getValue();
                if (!(spawnDetail.getPercentage() > 0.0f) || !((percentSum += spawnDetail.getPercentage()) >= selectedPercentage)) continue;
                return TuplesKt.to((Object)info.chooseContext(), (Object)spawnDetail);
            }
        }
        Object t = CollectionUtilsKt.weightedSelection(CollectionsKt.toList((Iterable)spawnsToContexts.entrySet()), select.selectedSpawn.1.INSTANCE);
        Intrinsics.checkNotNull(t);
        Map.Entry selectedSpawn2 = (Map.Entry)t;
        return TuplesKt.to((Object)((SelectingSpawnInformation)selectedSpawn2.getValue()).chooseContext(), selectedSpawn2.getKey());
    }

    @Override
    @NotNull
    public Map<SpawnDetail, Float> getTotalWeights(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        ContextSelectionData selectionData = this.getSelectionData(spawner, contexts);
        if (selectionData.getSize() == 0) {
            return MapsKt.emptyMap();
        }
        Map totalWeights = new LinkedHashMap();
        for (Map.Entry<SpawnDetail, SelectingSpawnInformation> entry : selectionData.getSpawnsToContexts().entrySet()) {
            SpawnDetail spawnDetail = entry.getKey();
            SelectingSpawnInformation info = entry.getValue();
            totalWeights.put(spawnDetail, Float.valueOf(info.getHighestWeight()));
        }
        return totalWeights;
    }

    @Override
    @NotNull
    public Map<SpawnDetail, Float> getProbabilities(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        return SpawningSelector.DefaultImpls.getProbabilities(this, spawner, contexts);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0004\u0018\u00002\u00020\u0001B#\u0012\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\f\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR#\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector$ContextSelectionData;", "", "", "percentSum", "F", "getPercentSum", "()F", "setPercentSum", "(F)V", "", "getSize", "()I", "size", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector$SelectingSpawnInformation;", "spawnsToContexts", "Ljava/util/Map;", "getSpawnsToContexts", "()Ljava/util/Map;", "<init>", "(Ljava/util/Map;F)V", "common"})
    protected static final class ContextSelectionData {
        @NotNull
        private final Map<SpawnDetail, SelectingSpawnInformation> spawnsToContexts;
        private float percentSum;

        public ContextSelectionData(@NotNull Map<SpawnDetail, SelectingSpawnInformation> spawnsToContexts, float percentSum) {
            Intrinsics.checkNotNullParameter(spawnsToContexts, (String)"spawnsToContexts");
            this.spawnsToContexts = spawnsToContexts;
            this.percentSum = percentSum;
        }

        @NotNull
        public final Map<SpawnDetail, SelectingSpawnInformation> getSpawnsToContexts() {
            return this.spawnsToContexts;
        }

        public final float getPercentSum() {
            return this.percentSum;
        }

        public final void setPercentSum(float f) {
            this.percentSum = f;
        }

        public final int getSize() {
            return this.spawnsToContexts.size();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010%\n\u0002\b\u0007\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nR\"\u0010\u000b\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R#\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00040\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatSelector$SelectingSpawnInformation;", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "spawningContext", "", "contextWeight", "", "add", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;F)V", "chooseContext", "()Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "highestWeight", "F", "getHighestWeight", "()F", "setHighestWeight", "(F)V", "", "spawningContexts", "Ljava/util/Map;", "getSpawningContexts", "()Ljava/util/Map;", "<init>", "()V", "common"})
    protected static final class SelectingSpawnInformation {
        @NotNull
        private final Map<SpawningContext, Float> spawningContexts = new LinkedHashMap();
        private float highestWeight;

        @NotNull
        public final Map<SpawningContext, Float> getSpawningContexts() {
            return this.spawningContexts;
        }

        public final float getHighestWeight() {
            return this.highestWeight;
        }

        public final void setHighestWeight(float f) {
            this.highestWeight = f;
        }

        public final void add(@NotNull SpawningContext spawningContext, float contextWeight) {
            Intrinsics.checkNotNullParameter((Object)spawningContext, (String)"spawningContext");
            Float f = Float.valueOf(contextWeight);
            this.spawningContexts.put(spawningContext, f);
            if (contextWeight > this.highestWeight) {
                this.highestWeight = contextWeight;
            }
        }

        @NotNull
        public final SpawningContext chooseContext() {
            Object t = CollectionUtilsKt.weightedSelection((Iterable)this.spawningContexts.entrySet(), chooseContext.1.INSTANCE);
            Intrinsics.checkNotNull(t);
            return (SpawningContext)((Map.Entry)t).getKey();
        }
    }
}

