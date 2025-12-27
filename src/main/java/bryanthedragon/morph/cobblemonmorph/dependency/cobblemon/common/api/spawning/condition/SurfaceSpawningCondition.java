package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SurfaceSpawningContext

public open class SurfaceSpawningCondition : SurfaceTypeSpawningCondition<SurfaceSpawningContext> {
   public override fun contextClass(): Class<SurfaceSpawningContext> {
      return SurfaceSpawningContext::class.java;
   }

   public companion object {
      public const val NAME: String
   }
}
