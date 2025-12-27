package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public object ContextPrecalculation : SpawningPrecalculation<Class<? extends SpawningContext>> {
   public override fun select(detail: SpawnDetail): List<Class<out SpawningContext>> {
      return CollectionsKt.listOf(detail.getContext().getClazz());
   }

   public open fun select(ctx: SpawningContext): Class<out SpawningContext> {
      return (Class<? extends SpawningContext>)ctx.getClass();
   }

   override fun generate(details: MutableList<SpawnDetail>, next: MutableList<SpawningPrecalculation<?>>): PrecalculationResult<Class<? extends SpawningContext>> {
      return SpawningPrecalculation.DefaultImpls.generate(this, details, next);
   }
}
