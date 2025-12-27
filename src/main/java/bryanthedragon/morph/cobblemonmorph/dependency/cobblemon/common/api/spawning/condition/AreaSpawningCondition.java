package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext

public class AreaSpawningCondition : AreaTypeSpawningCondition<AreaSpawningContext> {
   public override fun contextClass(): Class<out AreaSpawningContext> {
      return AreaSpawningContext::class.java;
   }

   public companion object {
      public const val NAME: String
   }
}
