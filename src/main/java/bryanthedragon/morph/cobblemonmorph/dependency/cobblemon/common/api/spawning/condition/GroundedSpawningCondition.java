package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.GroundedSpawningContext

public open class GroundedSpawningCondition : GroundedTypeSpawningCondition<GroundedSpawningContext> {
   public override fun contextClass(): Class<GroundedSpawningContext> {
      return GroundedSpawningContext::class.java;
   }

   public companion object {
      public const val NAME: String
   }
}
