package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object Fast : CachedExperienceGroup {
   public open val name: String = "fast"

   public override fun getExperience(level: Int): Int {
      return if (level == 1) 0 else 4 * SimpleMathExtensionsKt.pow(level, 3) / 5;
   }
}
