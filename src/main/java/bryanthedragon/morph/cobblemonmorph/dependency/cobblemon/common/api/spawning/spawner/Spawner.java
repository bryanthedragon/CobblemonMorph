/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u0000 +2\u00020\u0001:\u0001+J+\u0010\u0007\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0005\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001d\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u001a\u001a\u00020\u0019H&\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001d\u001a\u00020\u001cH&\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010 \u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0019H&\u00a2\u0006\u0004\b \u0010!J\u0017\u0010#\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u001cH&\u00a2\u0006\u0004\b#\u0010$R\u001a\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u0012R\u0014\u0010*\u001a\u00020'8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010)\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner;", "", "R", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;", "action", "result", "", "afterSpawn", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnAction;Ljava/lang/Object;)V", "", "canSpawn", "()Z", "Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "chooseBucket", "()Lcom/cobblemon/mod/common/api/spawning/SpawnBucket;", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "copyInfluences", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "getMatchingSpawns", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Ljava/util/List;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "getSpawnPool", "()Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "getSpawningSelector", "()Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;", "spawnPool", "setSpawnPool", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;)V", "selector", "setSpawningSelector", "(Lcom/cobblemon/mod/common/api/spawning/selection/SpawningSelector;)V", "getInfluences", "influences", "", "getName", "()Ljava/lang/String;", "name", "Companion", "common"})
public interface Spawner {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner$Companion.$$INSTANCE;

    @NotNull
    public String getName();

    @NotNull
    public List<SpawningInfluence> getInfluences();

    @NotNull
    public SpawningSelector getSpawningSelector();

    public void setSpawningSelector(@NotNull SpawningSelector var1);

    @NotNull
    public SpawnPool getSpawnPool();

    public void setSpawnPool(@NotNull SpawnPool var1);

    public <R> void afterSpawn(@NotNull SpawnAction<R> var1, R var2);

    public boolean canSpawn();

    @NotNull
    public List<SpawnDetail> getMatchingSpawns(@NotNull SpawningContext var1);

    @NotNull
    public List<SpawningInfluence> copyInfluences();

    @NotNull
    public SpawnBucket chooseBucket();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/Spawner$Companion;", "", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nSpawner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Spawner.kt\ncom/cobblemon/mod/common/api/spawning/spawner/Spawner$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n766#2:71\n857#2,2:72\n766#2:74\n857#2,2:75\n*S KotlinDebug\n*F\n+ 1 Spawner.kt\ncom/cobblemon/mod/common/api/spawning/spawner/Spawner$DefaultImpls\n*L\n42#1:71\n42#1:72,2\n43#1:74\n43#1:75,2\n*E\n"})
    public static final class DefaultImpls {
        public static <R> void afterSpawn(@NotNull Spawner $this, @NotNull SpawnAction<R> action2, R result) {
            Intrinsics.checkNotNullParameter(action2, (String)"action");
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static List<SpawnDetail> getMatchingSpawns(@NotNull Spawner $this, @NotNull SpawningContext ctx) {
            void $this$filterTo$iv$iv;
            Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
            Iterable $this$filter$iv = $this.getSpawnPool().retrieve(ctx);
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                SpawnDetail it = (SpawnDetail)element$iv$iv;
                boolean bl = false;
                if (!it.isSatisfiedBy(ctx)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return (List)destination$iv$iv;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public static List<SpawningInfluence> copyInfluences(@NotNull Spawner $this) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = $this.getInfluences();
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                SpawningInfluence it = (SpawningInfluence)element$iv$iv;
                boolean bl = false;
                if (!(!it.isExpired())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            return CollectionsKt.toMutableList((Collection)((List)destination$iv$iv));
        }

        @NotNull
        public static SpawnBucket chooseBucket(@NotNull Spawner $this) {
            List<SpawnBucket> buckets = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
            List<SpawningInfluence> influences = $this.copyInfluences();
            Map weightMap = new LinkedHashMap();
            for (SpawnBucket bucket : buckets) {
                float weight = bucket.getWeight();
                for (SpawningInfluence spawningInfluence : influences) {
                    weight = spawningInfluence.affectBucketWeight(bucket, weight);
                }
                Float f = Float.valueOf(weight);
                weightMap.put(bucket, f);
            }
            float weightSum = CollectionsKt.sumOfFloat((Iterable)weightMap.values());
            float chosenSum = weightSum - new Random().nextFloat(weightSum);
            float sum = 0.0f;
            for (SpawnBucket spawnBucket : buckets) {
                Object v = weightMap.get(spawnBucket);
                Intrinsics.checkNotNull(v);
                if (!((sum += ((Number)v).floatValue()) >= chosenSum)) continue;
                return spawnBucket;
            }
            return (SpawnBucket)CollectionsKt.first(buckets);
        }
    }
}

