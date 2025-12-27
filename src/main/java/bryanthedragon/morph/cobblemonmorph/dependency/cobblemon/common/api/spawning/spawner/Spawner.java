package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.Random
import kotlin.jvm.internal.SourceDebugExtension

public interface Spawner {
   public val influences: MutableList<SpawningInfluence>
   public val name: String

   public abstract fun getSpawningSelector(): SpawningSelector {
   }

   public abstract fun setSpawningSelector(selector: SpawningSelector) {
   }

   public abstract fun getSpawnPool(): SpawnPool {
   }

   public abstract fun setSpawnPool(spawnPool: SpawnPool) {
   }

   public open fun <R> afterSpawn(action: SpawnAction<Any>, result: Any) {
   }

   public abstract fun canSpawn(): Boolean {
   }

   public open fun getMatchingSpawns(ctx: SpawningContext): List<SpawnDetail> {
   }

   public open fun copyInfluences(): MutableList<SpawningInfluence> {
   }

   public open fun chooseBucket(): SpawnBucket {
   }

   public companion object

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nSpawner.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Spawner.kt\ncom/cobblemon/mod/common/api/spawning/spawner/Spawner$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n766#2:71\n857#2,2:72\n766#2:74\n857#2,2:75\n*S KotlinDebug\n*F\n+ 1 Spawner.kt\ncom/cobblemon/mod/common/api/spawning/spawner/Spawner$DefaultImpls\n*L\n42#1:71\n42#1:72,2\n43#1:74\n43#1:75,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun <R> afterSpawn(`$this`: Spawner, action: SpawnAction<R>, result: R) {
      }

      @JvmStatic
      fun getMatchingSpawns(`$this`: Spawner, ctx: SpawningContext): MutableList<SpawnDetail> {
         val `$this$filter$iv`: java.lang.Iterable = `$this`.getSpawnPool().retrieve(ctx);
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if ((`element$iv$iv` as SpawnDetail).isSatisfiedBy(ctx)) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return `destination$iv$iv` as MutableList<SpawnDetail>;
      }

      @JvmStatic
      fun copyInfluences(`$this`: Spawner): MutableList<SpawningInfluence> {
         val `$this$filter$iv`: java.lang.Iterable = `$this`.getInfluences();
         val `destination$iv$iv`: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filter$iv) {
            if (!(`element$iv$iv` as SpawningInfluence).isExpired()) {
               `destination$iv$iv`.add(`element$iv$iv`);
            }
         }

         return CollectionsKt.toMutableList(`destination$iv$iv` as java.util.List);
      }

      @JvmStatic
      fun chooseBucket(`$this`: Spawner): SpawnBucket {
         val buckets: java.util.List = Cobblemon.INSTANCE.getBestSpawner().getConfig().getBuckets();
         val influences: java.util.List = `$this`.copyInfluences();
         val weightMap: java.util.Map = new LinkedHashMap();

         for (SpawnBucket bucket : buckets) {
            var sum: Float = chosenSum.getWeight();

            for (SpawningInfluence influence : influences) {
               sum = bucket.affectBucketWeight(chosenSum, sum);
            }

            weightMap.put(chosenSum, sum);
         }

         val var9: Float = CollectionsKt.sumOfFloat(weightMap.values());
         val var10: Float = var9 - new Random().nextFloat(var9);
         var var11: Float = 0.0F;

         for (SpawnBucket bucket : buckets) {
            val var10001: Any = weightMap.get(var14);
            var11 += (var10001 as java.lang.Number).floatValue();
            if (var11 >= var10) {
               return var14;
            }
         }

         return CollectionsKt.first(buckets) as SpawnBucket;
      }
   }
}
