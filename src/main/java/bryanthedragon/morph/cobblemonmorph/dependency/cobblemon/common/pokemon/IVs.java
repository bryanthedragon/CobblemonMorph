package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon

public class IVs : PokemonStats {
   public open val acceptableRange: IntRange = new IntRange(0, 31)
   public open val defaultValue: Int

   public companion object {
      public const val MAX_VALUE: Int

      public fun createRandomIVs(minPerfectIVs: Int = 0): IVs {
         return Cobblemon.INSTANCE.getStatProvider().createEmptyIVs(minPerfectIVs);
      }
   }
}
