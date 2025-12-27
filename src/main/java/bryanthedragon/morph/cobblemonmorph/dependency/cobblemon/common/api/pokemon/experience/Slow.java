package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object Slow : CachedExperienceGroup {
   public open val name: String = "slow"

   public override fun getExperience(level: Int): Int {
      return if (level == 1) 0 else 5 * SimpleMathExtensionsKt.pow(level, 3) / 4;
   }
}
