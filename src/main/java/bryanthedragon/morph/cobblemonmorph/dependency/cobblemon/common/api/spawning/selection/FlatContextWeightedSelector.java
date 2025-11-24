/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.reflect.KClass
 *  kotlin.reflect.full.KClasses
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
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
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.reflect.KClass;
import kotlin.reflect.full.KClasses;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\u0007\u00a2\u0006\u0004\b\u0018\u0010\u0019J+\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0004\u00a2\u0006\u0004\b\t\u0010\nJ5\u0010\u000f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0004\u00a2\u0006\u0004\b\u000f\u0010\u0010J1\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0010J\u001b\u0010\u0013\u001a\u00020\b2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J3\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00152\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector;", "Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "spawner", "Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector$ContextSelectionData;", "contextSelectionData", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "", "getProbabilitiesFromContextType", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector$ContextSelectionData;)Ljava/util/Map;", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "contexts", "Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;", "getSelectionData", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Ljava/util/Map;", "getTotalWeights", "contextType", "getWeight", "(Lcom/cobblemon/mod/common/api/spawning/context/RegisteredSpawningContext;)F", "Lkotlin/Pair;", "select", "(Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;Ljava/util/List;)Lkotlin/Pair;", "<init>", "()V", "ContextSelectionData", "SelectingSpawnInformation", "common"})
@SourceDebugExtension(value={"SMAP\nFlatContextWeightedSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlatContextWeightedSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,211:1\n1855#2:212\n1855#2:220\n1856#2:228\n1856#2:229\n1855#2,2:231\n361#3,7:213\n361#3,7:221\n1#4:230\n*S KotlinDebug\n*F\n+ 1 FlatContextWeightedSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector\n*L\n76#1:212\n82#1:220\n82#1:228\n76#1:229\n204#1:231,2\n81#1:213,7\n90#1:221,7\n*E\n"})
public class FlatContextWeightedSelector
implements SpawningSelector {
    public float getWeight(@NotNull RegisteredSpawningContext<?> contextType) {
        Intrinsics.checkNotNullParameter(contextType, (String)"contextType");
        return contextType.getWeight();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    protected final Map<RegisteredSpawningContext<?>, ContextSelectionData> getSelectionData(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        Map contextTypesToSpawns = new LinkedHashMap();
        Iterable $this$forEach$iv = contexts;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Object object;
            RegisteredSpawningContext<?> contextType;
            SpawningContext ctx = (SpawningContext)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNull(SpawningContext.Companion.getByClass(ctx));
            List<SpawnDetail> possible = spawner.getMatchingSpawns(ctx);
            if (!(!((Collection)possible).isEmpty())) continue;
            Map $this$getOrPut$iv = contextTypesToSpawns;
            boolean $i$f$getOrPut = false;
            Object value$iv = $this$getOrPut$iv.get(contextType);
            if (value$iv == null) {
                boolean bl2 = false;
                ContextSelectionData answer$iv = new ContextSelectionData(new LinkedHashMap(), 0.0f);
                $this$getOrPut$iv.put(contextType, answer$iv);
                object = answer$iv;
            } else {
                object = value$iv;
            }
            ContextSelectionData contextSelectionData2 = (ContextSelectionData)object;
            Iterable $this$forEach$iv2 = possible;
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Object object2;
                void $this$getOrPut$iv2;
                SpawnDetail it = (SpawnDetail)element$iv2;
                boolean bl3 = false;
                if (it.getPercentage() > 0.0f && !contextSelectionData2.getSpawnsToContexts().containsKey(it)) {
                    contextSelectionData2.setPercentSum(contextSelectionData2.getPercentSum() + it.getPercentage());
                }
                Map<SpawnDetail, SelectingSpawnInformation> map = contextSelectionData2.getSpawnsToContexts();
                KClass kClass = Reflection.getOrCreateKotlinClass(SelectingSpawnInformation.class);
                boolean $i$f$getOrPut2 = false;
                Object value$iv2 = $this$getOrPut$iv2.get(it);
                if (value$iv2 == null) {
                    boolean bl4 = false;
                    SelectingSpawnInformation answer$iv = (SelectingSpawnInformation)KClasses.createInstance((KClass)kClass);
                    $this$getOrPut$iv2.put(it, answer$iv);
                    object2 = answer$iv;
                } else {
                    object2 = value$iv2;
                }
                SelectingSpawnInformation selectingSpawnInformation = (SelectingSpawnInformation)object2;
                selectingSpawnInformation.add(it, ctx, this.getWeight(contextType));
            }
        }
        return contextTypesToSpawns;
    }

    @Override
    @Nullable
    public Pair<SpawningContext, SpawnDetail> select(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        Map<RegisteredSpawningContext<?>, ContextSelectionData> selectionData = this.getSelectionData(spawner, contexts);
        if (selectionData.isEmpty()) {
            return null;
        }
        Object t = CollectionUtilsKt.weightedSelection(CollectionsKt.toList((Iterable)selectionData.entrySet()), (Function1)new Function1<Map.Entry<? extends RegisteredSpawningContext<?>, ? extends ContextSelectionData>, Number>(this){
            final /* synthetic */ FlatContextWeightedSelector this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            @NotNull
            public final Number invoke(@NotNull Map.Entry<? extends RegisteredSpawningContext<?>, ContextSelectionData> it) {
                Intrinsics.checkNotNullParameter(it, (String)"it");
                return Float.valueOf(this.this$0.getWeight(it.getKey()) * (float)it.getValue().getSize());
            }
        });
        Intrinsics.checkNotNull(t);
        ContextSelectionData contextSelectionData2 = (ContextSelectionData)((Map.Entry)t).getValue();
        Map<SpawnDetail, SelectingSpawnInformation> spawnsToContexts = contextSelectionData2.getSpawnsToContexts();
        float percentSum = contextSelectionData2.getPercentSum();
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
        Object t2 = CollectionUtilsKt.weightedSelection(CollectionsKt.toList((Iterable)spawnsToContexts.entrySet()), select.selectedSpawn.1.INSTANCE);
        Intrinsics.checkNotNull(t2);
        Map.Entry selectedSpawn2 = (Map.Entry)t2;
        return TuplesKt.to((Object)((SelectingSpawnInformation)selectedSpawn2.getValue()).chooseContext(), selectedSpawn2.getKey());
    }

    @NotNull
    protected final Map<SpawnDetail, Float> getProbabilitiesFromContextType(@NotNull Spawner spawner, @NotNull ContextSelectionData contextSelectionData2) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter((Object)contextSelectionData2, (String)"contextSelectionData");
        float percentSum = contextSelectionData2.getPercentSum();
        float weightPortion = (float)100 - percentSum;
        float totalWeightMultiplier = (float)100 / weightPortion;
        Map<SpawnDetail, SelectingSpawnInformation> spawnsToContexts = contextSelectionData2.getSpawnsToContexts();
        if (percentSum > 100.0f) {
            Cobblemon.INSTANCE.getLOGGER().warn(StringsKt.trimIndent((String)("\n                    A spawn list for " + spawner.getName() + " exceeded 100% on percentage sums...\n                    This means you don't understand how this option works.\n                ")));
            return MapsKt.emptyMap();
        }
        Map totalWeights = new LinkedHashMap();
        float totalWeight = 0.0f;
        for (SelectingSpawnInformation spawn : spawnsToContexts.values()) {
            totalWeight += spawn.getHighestWeight();
        }
        float rescaledTotalWeight = totalWeight * totalWeightMultiplier;
        float percentageWeight = (rescaledTotalWeight - totalWeight) / percentSum;
        for (Map.Entry<SpawnDetail, SelectingSpawnInformation> entry : spawnsToContexts.entrySet()) {
            SpawnDetail spawnDetail = entry.getKey();
            SelectingSpawnInformation info = entry.getValue();
            totalWeights.put(spawnDetail, Float.valueOf(info.getHighestWeight() + (spawnDetail.getPercentage() > 0.0f ? spawnDetail.getPercentage() * percentageWeight : 0.0f)));
        }
        return totalWeights;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Map<SpawnDetail, Float> getTotalWeights(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        Intrinsics.checkNotNullParameter((Object)spawner, (String)"spawner");
        Intrinsics.checkNotNullParameter(contexts, (String)"contexts");
        Map<RegisteredSpawningContext<?>, ContextSelectionData> selectionData = this.getSelectionData(spawner, contexts);
        if (selectionData.isEmpty()) {
            return MapsKt.emptyMap();
        }
        Map totalWeights = new LinkedHashMap();
        Iterable iterable = selectionData.keySet();
        double d = 0.0;
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            void it;
            Object t = iterator.next();
            RegisteredSpawningContext registeredSpawningContext = (RegisteredSpawningContext)t;
            double d2 = d;
            boolean bl = false;
            double d3 = this.getWeight((RegisteredSpawningContext<?>)it);
            d = d2 + d3;
        }
        float totalContextWeight = (float)d;
        for (Map.Entry entry : selectionData.entrySet()) {
            RegisteredSpawningContext contextType = (RegisteredSpawningContext)entry.getKey();
            ContextSelectionData contextSelectionData2 = (ContextSelectionData)entry.getValue();
            float contextWeightCorrection = this.getWeight(contextType) / totalContextWeight;
            Map<SpawnDetail, Float> contextProbabilities = this.getProbabilitiesFromContextType(spawner, contextSelectionData2);
            Iterable $this$forEach$iv = contextProbabilities.entrySet();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Map.Entry it = (Map.Entry)element$iv;
                boolean bl = false;
                totalWeights.put(it.getKey(), Float.valueOf(((Number)it.getValue()).floatValue() * contextWeightCorrection));
            }
        }
        return totalWeights;
    }

    @Override
    @NotNull
    public Map<SpawnDetail, Float> getProbabilities(@NotNull Spawner spawner, @NotNull List<? extends SpawningContext> contexts) {
        return SpawningSelector.DefaultImpls.getProbabilities(this, spawner, contexts);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0004\u0018\u00002\u00020\u0001B#\u0012\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\f\u001a\u00020\t8F\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR#\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector$ContextSelectionData;", "", "", "percentSum", "F", "getPercentSum", "()F", "setPercentSum", "(F)V", "", "getSize", "()I", "size", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector$SelectingSpawnInformation;", "spawnsToContexts", "Ljava/util/Map;", "getSpawnsToContexts", "()Ljava/util/Map;", "<init>", "(Ljava/util/Map;F)V", "common"})
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010%\n\u0002\b\u0007\b\u0004\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0018\u0010\u0019J%\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\fR\"\u0010\r\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R#\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00060\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector$SelectingSpawnInformation;", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "spawnDetail", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "spawningContext", "", "contextTypeWeight", "", "add", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;F)V", "chooseContext", "()Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "highestWeight", "F", "getHighestWeight", "()F", "setHighestWeight", "(F)V", "", "spawningContexts", "Ljava/util/Map;", "getSpawningContexts", "()Ljava/util/Map;", "<init>", "()V", "common"})
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

        public final void add(@NotNull SpawnDetail spawnDetail, @NotNull SpawningContext spawningContext, float contextTypeWeight) {
            Intrinsics.checkNotNullParameter((Object)spawnDetail, (String)"spawnDetail");
            Intrinsics.checkNotNullParameter((Object)spawningContext, (String)"spawningContext");
            float weight = spawningContext.getWeight(spawnDetail) * contextTypeWeight;
            Float f = Float.valueOf(weight);
            this.spawningContexts.put(spawningContext, f);
            if (weight > this.highestWeight) {
                this.highestWeight = weight;
            }
        }

        @NotNull
        public final SpawningContext chooseContext() {
            Object t = CollectionUtilsKt.weightedSelection(CollectionsKt.toList((Iterable)this.spawningContexts.keySet()), (Function1)new Function1<SpawningContext, Number>(this){
                final /* synthetic */ SelectingSpawnInformation this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @NotNull
                public final Number invoke(@NotNull SpawningContext it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    Float f = this.this$0.getSpawningContexts().get(it);
                    Intrinsics.checkNotNull((Object)f);
                    return f;
                }
            });
            Intrinsics.checkNotNull(t);
            return (SpawningContext)t;
        }
    }
}

