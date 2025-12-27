package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public object RootPrecalculation : SpawningPrecalculation<Object> {
   public override fun select(detail: SpawnDetail): List<Any> {
      return CollectionsKt.listOf(Unit.INSTANCE);
   }

   public override fun select(ctx: SpawningContext): Any {
      return Unit.INSTANCE;
   }

   override fun generate(details: MutableList<SpawnDetail>, next: MutableList<SpawningPrecalculation<?>>): PrecalculationResult<Object> {
      return SpawningPrecalculation.DefaultImpls.generate(this, details, next);
   }
}
