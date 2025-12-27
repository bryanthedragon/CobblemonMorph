package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public class PokemonPropertiesRequirement : EvolutionRequirement {
   public final val target: PokemonProperties = new PokemonProperties()

   public override fun check(pokemon: Pokemon): Boolean {
      return this.target.matches(pokemon);
   }

   public companion object {
      public const val ADAPTER_VARIANT: String
   }
}
