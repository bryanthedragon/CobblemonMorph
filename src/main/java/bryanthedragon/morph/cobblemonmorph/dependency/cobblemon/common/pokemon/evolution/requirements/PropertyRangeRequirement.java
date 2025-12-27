package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class PropertyRangeRequirement : EvolutionRequirement {
   public final val feature: String = ""
   public final val range: IntRange = new IntRange(0, 256)

   public override fun check(pokemon: Pokemon): Boolean {
      val var10000: IntSpeciesFeature = pokemon.getFeature(this.feature);
      return var10000 != null && this.range.contains(var10000.getValue());
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
