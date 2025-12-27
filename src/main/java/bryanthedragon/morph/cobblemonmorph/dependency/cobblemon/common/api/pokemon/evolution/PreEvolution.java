package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.StandardPreEvolution

public interface PreEvolution {
   public val form: FormData
   public val species: Species

   public companion object {
      public fun of(species: Species, form: FormData = species.getStandardForm()): PreEvolution {
         return new StandardPreEvolution(species, form);
      }
   }
}
