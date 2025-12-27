package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class StatEqualRequirement : EvolutionRequirement {
   public final val statOne: String = Stats.ATTACK.name()
   public final val statTwo: String = Stats.DEFENCE.name()

   public override fun check(pokemon: Pokemon): Boolean {
      return pokemon.getStat(Stats.Companion.getStat(this.statOne)) == pokemon.getStat(Stats.Companion.getStat(this.statTwo));
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
