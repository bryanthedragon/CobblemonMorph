package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public object BucketPrecalculation : SpawningPrecalculation<SpawnBucket> {
   public override fun select(detail: SpawnDetail): List<SpawnBucket> {
      return CollectionsKt.listOf(detail.getBucket());
   }

   public open fun select(ctx: SpawningContext): SpawnBucket {
      return ctx.getCause().getBucket();
   }

   override fun generate(details: MutableList<SpawnDetail>, next: MutableList<SpawningPrecalculation<?>>): PrecalculationResult<SpawnBucket> {
      return SpawningPrecalculation.DefaultImpls.generate(this, details, next);
   }
}
