package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext

public open class SubmergedSpawningCondition : SubmergedTypeSpawningCondition<SubmergedSpawningContext> {
   public override fun contextClass(): Class<SubmergedSpawningContext> {
      return SubmergedSpawningContext::class.java;
   }

   public companion object {
      public const val NAME: String
   }
}
