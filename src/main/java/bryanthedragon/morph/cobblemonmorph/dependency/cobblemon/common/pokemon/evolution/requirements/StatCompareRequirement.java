package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class StatCompareRequirement : EvolutionRequirement {
   public final val highStat: String = Stats.ATTACK.name()
   public final val lowStat: String = Stats.DEFENCE.name()

   public override fun check(pokemon: Pokemon): Boolean {
      return pokemon.getStat(Stats.Companion.getStat(this.highStat)) > pokemon.getStat(Stats.Companion.getStat(this.lowStat));
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
