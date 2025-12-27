package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt

public object Erratic : CachedExperienceGroup {
   public open val name: String = "erratic"

   public override fun getExperience(level: Int): Int {
      return if (level == 1)
         0
         else
         (
            if (level < 50)
               SimpleMathExtensionsKt.pow(level, 3) * (100 - level) / 50
               else
               (
                  if (level < 68)
                     SimpleMathExtensionsKt.pow(level, 3) * (150 - level) / 100
                     else
                     (
                        if (level < 98)
                           SimpleMathExtensionsKt.pow(level, 3) * (1911 - 10 * level) / 3 / 500
                           else
                           SimpleMathExtensionsKt.pow(level, 3) * (160 - level) / 100
                     )
               )
         );
   }
}
