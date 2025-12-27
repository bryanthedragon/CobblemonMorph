package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import java.util.LinkedHashMap

public class NestedPrecalculationResult<T>(calculation: SpawningPrecalculation<Any>,
   mapping: Map<Any, PrecalculationResult<*>> = (new LinkedHashMap()) as java.util.Map
) : PrecalculationResult(calculation) {
   public final val mapping: Map<Any, PrecalculationResult<*>>

   init {
      this.mapping = mapping;
   }

   public override fun retrieve(ctx: SpawningContext): List<SpawnDetail> {
      val var10000: PrecalculationResult = this.mapping.get(this.getCalculation().select(ctx));
      if (var10000 != null) {
         val var2: java.util.List = var10000.retrieve(ctx);
         if (var2 != null) {
            return var2;
         }
      }

      return CollectionsKt.emptyList();
   }
}
