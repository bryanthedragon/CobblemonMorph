package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object MediumFast : CachedExperienceGroup {
   public open val name: String = "medium_fast"

   public override fun getExperience(level: Int): Int {
      return if (level == 1) 0 else SimpleMathExtensionsKt.pow(level, 3);
   }
}
