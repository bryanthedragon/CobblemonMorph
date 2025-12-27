package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object Fluctuating : CachedExperienceGroup {
   public open val name: String = "fluctuating"

   public override fun getExperience(level: Int): Int {
      return if (level == 1)
         0
         else
         (
            if (level < 15)
               SimpleMathExtensionsKt.pow(level, 3) * ((level + 1) / 3 + 24) / 50
               else
               (if (level < 36) SimpleMathExtensionsKt.pow(level, 3) * (level + 14) / 50 else SimpleMathExtensionsKt.pow(level, 3) * (level / 2 + 32) / 50)
         );
   }
}
