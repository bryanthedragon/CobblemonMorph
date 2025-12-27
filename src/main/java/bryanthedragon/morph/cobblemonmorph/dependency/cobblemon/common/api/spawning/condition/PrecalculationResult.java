package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public sealed class PrecalculationResult<T> protected constructor(calculation: SpawningPrecalculation<*>) {
   public final val calculation: SpawningPrecalculation<*>

   init {
      this.calculation = calculation;
   }

   public abstract fun retrieve(ctx: SpawningContext): List<SpawnDetail> {
   }
}
