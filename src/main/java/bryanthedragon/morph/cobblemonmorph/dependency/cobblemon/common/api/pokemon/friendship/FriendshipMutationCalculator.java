package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.friendship

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public fun interface FriendshipMutationCalculator {
   public abstract fun calculate(pokemon: Pokemon): Int {
   }

   public companion object {
      public final val SWORD_AND_SHIELD_LEVEL_UP: FriendshipMutationCalculator = FriendshipMutationCalculator.Companion::SWORD_AND_SHIELD_LEVEL_UP$lambda$0

      @JvmStatic
      fun `SWORD_AND_SHIELD_LEVEL_UP$lambda$0`(pokemon: Pokemon): Int {
         return if (pokemon.getFriendship() <= 99) 3 else (if (pokemon.getFriendship() <= 199) 2 else 0);
      }
   }
}
