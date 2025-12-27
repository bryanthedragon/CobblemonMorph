package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random

@SourceDebugExtension(["SMAP\nFlatSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlatSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,158:1\n1855#2:159\n1855#2:160\n1856#2:168\n1856#2:169\n361#3,7:161\n*S KotlinDebug\n*F\n+ 1 FlatSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatSelector\n*L\n74#1:159\n75#1:160\n75#1:168\n74#1:169\n83#1:161,7\n*E\n"])
public open class FlatSelector : SpawningSelector {
   protected fun getSelectionData(spawner: Spawner, contexts: List<SpawningContext>): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatSelector.ContextSelectionData {
      val spawnsToContexts: java.util.Map = new LinkedHashMap();
      var percentSum: Float = 0.0F;

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val ctx: SpawningContext = `element$iv` as SpawningContext;

         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            val it: SpawnDetail = `element$ivx` as SpawnDetail;
            if ((`element$ivx` as SpawnDetail).getPercentage() > 0.0F && !spawnsToContexts.containsKey(`element$ivx` as SpawnDetail)) {
               percentSum += it.getPercentage();
            }

            val `value$iv`: Any = spawnsToContexts.get(it);
            val var10000: Any;
            if (`value$iv` == null) {
               val var22: Any = new FlatSelector.SelectingSpawnInformation();
               spawnsToContexts.put(it, var22);
               var10000 = var22;
            } else {
               var10000 = `value$iv`;
            }

            (var10000 as FlatSelector.SelectingSpawnInformation).add(ctx, ctx.getWeight(it));
         }
      }

      return new FlatSelector.ContextSelectionData(spawnsToContexts, percentSum);
   }

   public override fun select(spawner: Spawner, contexts: List<SpawningContext>): Pair<SpawningContext, SpawnDetail>? {
      val selectionData: FlatSelector.ContextSelectionData = this.getSelectionData(spawner, contexts);
      if (selectionData.getSize() == 0) {
         return null;
      } else {
         val spawnsToContexts: java.util.Map = selectionData.getSpawnsToContexts();
         var percentSum: Float = selectionData.getPercentSum();
         if (percentSum > 0.0F) {
            if (percentSum > 100.0F) {
               Cobblemon.INSTANCE
                  .getLOGGER()
                  .warn(
                     StringsKt.trimIndent(
                        "\n                        A spawn list for ${spawner.getName()} exceeded 100% on percentage sums...\n                        This means you don't understand how this option works.\n                    "
                     )
                  );
               return null;
            }

            val selectedSpawn: Float = 100 - Random.Default.nextFloat() * 100;
            percentSum = 0.0F;

            for (Entry var8 : spawnsToContexts.entrySet()) {
               val spawnDetail: SpawnDetail = var8.getKey() as SpawnDetail;
               val info: FlatSelector.SelectingSpawnInformation = var8.getValue() as FlatSelector.SelectingSpawnInformation;
               if (spawnDetail.getPercentage() > 0.0F) {
                  percentSum += spawnDetail.getPercentage();
                  if (percentSum >= selectedSpawn) {
                     return TuplesKt.to(info.chooseContext(), spawnDetail);
                  }
               }
            }
         }

         val var10000: Any = CollectionUtilsKt.weightedSelection(CollectionsKt.toList(spawnsToContexts.entrySet()), <unrepresentable>.INSTANCE);
         return TuplesKt.to(((var10000 as Entry).getValue() as FlatSelector.SelectingSpawnInformation).chooseContext(), (var10000 as Entry).getKey());
      }
   }

   public override fun getTotalWeights(spawner: Spawner, contexts: List<SpawningContext>): Map<SpawnDetail, Float> {
      val selectionData: FlatSelector.ContextSelectionData = this.getSelectionData(spawner, contexts);
      if (selectionData.getSize() == 0) {
         return MapsKt.emptyMap();
      } else {
         val totalWeights: java.util.Map = new LinkedHashMap();

         for (Entry var6 : selectionData.getSpawnsToContexts().entrySet()) {
            totalWeights.put(var6.getKey() as SpawnDetail, (var6.getValue() as FlatSelector.SelectingSpawnInformation).getHighestWeight());
         }

         return totalWeights;
      }
   }

   override fun getProbabilities(spawner: Spawner, contexts: MutableList<SpawningContext>): MutableMap<SpawnDetail, java.lang.Float> {
      return SpawningSelector.DefaultImpls.getProbabilities(this, spawner, contexts);
   }

   protected class ContextSelectionData(spawnsToContexts: MutableMap<
               SpawnDetail,
               bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatSelector.SelectingSpawnInformation
            >,
      percentSum: Float
   ) {
      public final var percentSum: Float

      public final val size: Int
         public final get() {
            return this.spawnsToContexts.size();
         }


      public final val spawnsToContexts: MutableMap<SpawnDetail, bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatSelector.SelectingSpawnInformation>

      init {
         this.spawnsToContexts = spawnsToContexts;
         this.percentSum = percentSum;
      }
   }

   protected class SelectingSpawnInformation {
      public final var highestWeight: Float
      public final val spawningContexts: MutableMap<SpawningContext, Float> = (new LinkedHashMap()) as java.util.Map

      public fun add(spawningContext: SpawningContext, contextWeight: Float) {
         this.spawningContexts.put(spawningContext, contextWeight);
         if (contextWeight > this.highestWeight) {
            this.highestWeight = contextWeight;
         }
      }

      public fun chooseContext(): SpawningContext {
         val var10000: Any = CollectionUtilsKt.weightedSelection(this.spawningContexts.entrySet(), <unrepresentable>.INSTANCE);
         return (var10000 as Entry).getKey() as SpawningContext;
      }
   }
}
