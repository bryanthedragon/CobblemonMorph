package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object MediumSlow : CachedExperienceGroup {
   public open val name: String = "medium_slow"

   public override fun getExperience(level: Int): Int {
      return Math.max(0, SimpleMathExtensionsKt.pow(level, 3) * 6 / 5 - 15 * SimpleMathExtensionsKt.pow(level, 2) + 100 * level - 140);
   }
}
