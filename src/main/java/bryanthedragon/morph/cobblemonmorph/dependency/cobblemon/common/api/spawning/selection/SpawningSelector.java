package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension

public interface SpawningSelector {
   public abstract fun select(spawner: Spawner, contexts: List<SpawningContext>): Pair<SpawningContext, SpawnDetail>? {
   }

   public open fun getProbabilities(spawner: Spawner, contexts: List<SpawningContext>): Map<SpawnDetail, Float> {
   }

   public abstract fun getTotalWeights(spawner: Spawner, contexts: List<SpawningContext>): Map<SpawnDetail, Float> {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nSpawningSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/SpawningSelector$DefaultImpls\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,36:1\n215#2,2:37\n*S KotlinDebug\n*F\n+ 1 SpawningSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/SpawningSelector$DefaultImpls\n*L\n30#1:37,2\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun getProbabilities(`$this`: SpawningSelector, spawner: Spawner, contexts: MutableList<SpawningContext>): MutableMap<SpawnDetail, java.lang.Float> {
         val weights: java.util.Map = `$this`.getTotalWeights(spawner, contexts);
         val totalWeight: Float = CollectionsKt.sumOfFloat(weights.values());
         val percentages: java.util.Map = new LinkedHashMap();

         for (Entry element$iv : weights.entrySet()) {
            percentages.put(
               `element$iv`.getKey() as SpawnDetail,
               RangesKt.coerceIn((`element$iv`.getValue() as java.lang.Number).floatValue() / totalWeight * 100.0F, RangesKt.rangeTo(0.0F, 100.0F))
            );
         }

         return percentages;
      }
   }
}
