package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class LevelRequirement : EvolutionRequirement {
   public final val maxLevel: Int = Integer.MAX_VALUE
   public final val minLevel: Int = 1

   public override fun check(pokemon: Pokemon): Boolean {
      val var2: Int = this.minLevel;
      val var3: Int = this.maxLevel;
      val var4: Int = pokemon.getLevel();
      return var2 <= var4 && var4 <= var3;
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
