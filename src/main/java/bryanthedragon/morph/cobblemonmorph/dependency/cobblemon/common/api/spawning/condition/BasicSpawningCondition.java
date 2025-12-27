package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext

public class BasicSpawningCondition : SpawningCondition<SpawningContext> {
   public override fun contextClass(): Class<out SpawningContext> {
      return SpawningContext::class.java;
   }

   public companion object {
      public const val NAME: String
   }
}
