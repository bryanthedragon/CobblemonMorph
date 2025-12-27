package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public class FinalPrecalculationResult<T>(calculation: SpawningPrecalculation<*>, mapping: Map<Any, List<SpawnDetail>>) : PrecalculationResult(calculation) {
   public final val mapping: Map<Any, List<SpawnDetail>>

   init {
      this.mapping = mapping;
   }

   public override fun retrieve(ctx: SpawningContext): List<SpawnDetail> {
      var var10000: java.util.List = this.mapping.get(this.getCalculation().select(ctx));
      if (var10000 == null) {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }
}
