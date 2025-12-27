package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.full.KClasses
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nFlatContextWeightedSelector.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FlatContextWeightedSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,211:1\n1855#2:212\n1855#2:220\n1856#2:228\n1856#2:229\n1855#2,2:231\n361#3,7:213\n361#3,7:221\n1#4:230\n*S KotlinDebug\n*F\n+ 1 FlatContextWeightedSelector.kt\ncom/cobblemon/mod/common/api/spawning/selection/FlatContextWeightedSelector\n*L\n76#1:212\n82#1:220\n82#1:228\n76#1:229\n204#1:231,2\n81#1:213,7\n90#1:221,7\n*E\n"])
public open class FlatContextWeightedSelector : SpawningSelector {
   public open fun getWeight(contextType: RegisteredSpawningContext<*>): Float {
      return contextType.getWeight();
   }

   protected fun getSelectionData(spawner: Spawner, contexts: List<SpawningContext>): Map<
         RegisteredSpawningContext<*>,
         bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector.ContextSelectionData
      > {
      val contextTypesToSpawns: java.util.Map = new LinkedHashMap();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val ctx: SpawningContext = `element$iv` as SpawningContext;
         var var10000: RegisteredSpawningContext = SpawningContext.Companion.getByClass(`element$iv` as SpawningContext);
         val contextType: RegisteredSpawningContext = var10000;
         if (!spawner.getMatchingSpawns(ctx).isEmpty()) {
            val `value$iv`: Any = contextTypesToSpawns.get(var10000);
            if (`value$iv` == null) {
               val var27: Any = new FlatContextWeightedSelector.ContextSelectionData(new LinkedHashMap<>(), 0.0F);
               contextTypesToSpawns.put(var10000, var27);
               var10000 = (RegisteredSpawningContext)var27;
            } else {
               var10000 = (RegisteredSpawningContext)`value$iv`;
            }

            val contextSelectionData: FlatContextWeightedSelector.ContextSelectionData = var10000 as FlatContextWeightedSelector.ContextSelectionData;

            val `$this$forEach$ivx`: java.lang.Iterable;
            for (Object element$ivx : $this$forEach$ivx) {
               val it: SpawnDetail = `element$ivx` as SpawnDetail;
               if ((`element$ivx` as SpawnDetail).getPercentage() > 0.0F
                  && !contextSelectionData.getSpawnsToContexts().containsKey(`element$ivx` as SpawnDetail)) {
                  contextSelectionData.setPercentSum(contextSelectionData.getPercentSum() + it.getPercentage());
               }

               val `$this$getOrPut$iv`: java.util.Map = contextSelectionData.getSpawnsToContexts();
               val var20: KClass = FlatContextWeightedSelector.SelectingSpawnInformation::class;
               val `value$ivx`: Any = `$this$getOrPut$iv`.get(it);
               if (`value$ivx` == null) {
                  val var29: Any = KClasses.createInstance(var20) as FlatContextWeightedSelector.SelectingSpawnInformation;
                  `$this$getOrPut$iv`.put(it, var29);
                  var10000 = (RegisteredSpawningContext)var29;
               } else {
                  var10000 = (RegisteredSpawningContext)`value$ivx`;
               }

               (var10000 as FlatContextWeightedSelector.SelectingSpawnInformation).add(it, ctx, this.getWeight(contextType));
            }
         }
      }

      return contextTypesToSpawns;
   }

   public override fun select(spawner: Spawner, contexts: List<SpawningContext>): Pair<SpawningContext, SpawnDetail>? {
      val selectionData: java.util.Map = this.getSelectionData(spawner, contexts);
      if (selectionData.isEmpty()) {
         return null;
      } else {
         var var10000: Any = CollectionUtilsKt.weightedSelection(
            CollectionsKt.toList(selectionData.entrySet()),
            (
               new Function1<Entry<? extends RegisteredSpawningContext<?>, ? extends FlatContextWeightedSelector.ContextSelectionData>, java.lang.Number>(this) {
                  {
                     super(1);
                     this.this$0 = `$receiver`;
                  }

                  @NotNull
                  public final java.lang.Number invoke(
                     @NotNull Entry<? extends RegisteredSpawningContext<?>, FlatContextWeightedSelector.ContextSelectionData> it
                  ) {
                     return this.this$0.getWeight(it.getKey() as RegisteredSpawningContext<?>)
                        * (float)(it.getValue() as FlatContextWeightedSelector.ContextSelectionData).getSize();
                  }
               }
            ) as Function1
         );
         val contextSelectionData: FlatContextWeightedSelector.ContextSelectionData = (var10000 as Entry).getValue() as FlatContextWeightedSelector.ContextSelectionData;
         val spawnsToContexts: java.util.Map = contextSelectionData.getSpawnsToContexts();
         var percentSum: Float = contextSelectionData.getPercentSum();
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

            for (Entry var9 : spawnsToContexts.entrySet()) {
               val spawnDetail: SpawnDetail = var9.getKey() as SpawnDetail;
               val info: FlatContextWeightedSelector.SelectingSpawnInformation = var9.getValue() as FlatContextWeightedSelector.SelectingSpawnInformation;
               if (spawnDetail.getPercentage() > 0.0F) {
                  percentSum += spawnDetail.getPercentage();
                  if (percentSum >= selectedSpawn) {
                     return TuplesKt.to(info.chooseContext(), spawnDetail);
                  }
               }
            }
         }

         var10000 = CollectionUtilsKt.weightedSelection(CollectionsKt.toList(spawnsToContexts.entrySet()), <unrepresentable>.INSTANCE);
         return TuplesKt.to(
            ((var10000 as Entry).getValue() as FlatContextWeightedSelector.SelectingSpawnInformation).chooseContext(), (var10000 as Entry).getKey()
         );
      }
   }

   protected fun getProbabilitiesFromContextType(
      spawner: Spawner,
      contextSelectionData: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector.ContextSelectionData
   ): Map<SpawnDetail, Float> {
      val percentSum: Float = contextSelectionData.getPercentSum();
      val totalWeightMultiplier: Float = 100 / (100 - percentSum);
      val spawnsToContexts: java.util.Map = contextSelectionData.getSpawnsToContexts();
      if (percentSum > 100.0F) {
         Cobblemon.INSTANCE
            .getLOGGER()
            .warn(
               StringsKt.trimIndent(
                  "\n                    A spawn list for ${spawner.getName()} exceeded 100% on percentage sums...\n                    This means you don't understand how this option works.\n                "
               )
            );
         return MapsKt.emptyMap();
      } else {
         val totalWeights: java.util.Map = new LinkedHashMap();
         var totalWeight: Float = 0.0F;

         for (FlatContextWeightedSelector.SelectingSpawnInformation spawn : spawnsToContexts.values()) {
            totalWeight += percentageWeight.getHighestWeight();
         }

         val var16: Float = (totalWeight * totalWeightMultiplier - totalWeight) / percentSum;

         for (Entry var12 : spawnsToContexts.entrySet()) {
            val spawnDetail: SpawnDetail = var12.getKey() as SpawnDetail;
            totalWeights.put(
               spawnDetail,
               (var12.getValue() as FlatContextWeightedSelector.SelectingSpawnInformation).getHighestWeight()
                  + (if (spawnDetail.getPercentage() > 0.0F) spawnDetail.getPercentage() * var16 else 0.0F)
            );
         }

         return totalWeights;
      }
   }

   public override fun getTotalWeights(spawner: Spawner, contexts: List<SpawningContext>): Map<SpawnDetail, Float> {
      val selectionData: java.util.Map = this.getSelectionData(spawner, contexts);
      if (selectionData.isEmpty()) {
         return MapsKt.emptyMap();
      } else {
         val totalWeights: java.util.Map = new LinkedHashMap();
         val var6: java.lang.Iterable = selectionData.keySet();
         var var7: Double = 0.0;

         for (Object contextWeightCorrection : var6) {
            var7 += this.getWeight(contextWeightCorrection as RegisteredSpawningContext<?>);
         }

         val totalContextWeight: Float = (float)var7;

         for (Entry var23 : selectionData.entrySet()) {
            val contextType: RegisteredSpawningContext = var23.getKey() as RegisteredSpawningContext;
            val var24: FlatContextWeightedSelector.ContextSelectionData = var23.getValue() as FlatContextWeightedSelector.ContextSelectionData;
            val var25: Float = this.getWeight(contextType) / totalContextWeight;

            val var27: java.lang.Iterable;
            for (Object element$iv : var27) {
               totalWeights.put((`element$iv` as Entry).getKey(), ((`element$iv` as Entry).getValue() as java.lang.Number).floatValue() * var25);
            }
         }

         return totalWeights;
      }
   }

   override fun getProbabilities(spawner: Spawner, contexts: MutableList<SpawningContext>): MutableMap<SpawnDetail, java.lang.Float> {
      return SpawningSelector.DefaultImpls.getProbabilities(this, spawner, contexts);
   }

   protected class ContextSelectionData(spawnsToContexts: MutableMap<
               SpawnDetail,
               bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector.SelectingSpawnInformation
            >,
      percentSum: Float
   ) {
      public final var percentSum: Float

      public final val size: Int
         public final get() {
            return this.spawnsToContexts.size();
         }


      public final val spawnsToContexts: MutableMap<
         SpawnDetail,
         bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.FlatContextWeightedSelector.SelectingSpawnInformation
      >

      init {
         this.spawnsToContexts = spawnsToContexts;
         this.percentSum = percentSum;
      }
   }

   protected class SelectingSpawnInformation {
      public final var highestWeight: Float
      public final val spawningContexts: MutableMap<SpawningContext, Float> = (new LinkedHashMap()) as java.util.Map

      public fun add(spawnDetail: SpawnDetail, spawningContext: SpawningContext, contextTypeWeight: Float) {
         val weight: Float = spawningContext.getWeight(spawnDetail) * contextTypeWeight;
         this.spawningContexts.put(spawningContext, weight);
         if (weight > this.highestWeight) {
            this.highestWeight = weight;
         }
      }

      public fun chooseContext(): SpawningContext {
         val var10000: Any = CollectionUtilsKt.weightedSelection(
            CollectionsKt.toList(this.spawningContexts.keySet()), (new Function1<SpawningContext, java.lang.Number>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               @NotNull
               public final java.lang.Number invoke(@NotNull SpawningContext it) {
                  val var10000: Any = this.this$0.getSpawningContexts().get(it);
                  return var10000 as java.lang.Number;
               }
            }) as Function1
         );
         return var10000 as SpawningContext;
      }
   }
}
