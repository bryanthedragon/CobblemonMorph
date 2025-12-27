package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species

public interface EvolutionDisplay : EvolutionLike {
   public val aspects: Set<String>
   public val species: Species
}
