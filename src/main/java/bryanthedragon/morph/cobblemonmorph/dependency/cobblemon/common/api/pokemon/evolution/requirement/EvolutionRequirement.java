package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public fun interface EvolutionRequirement {
   public abstract fun check(pokemon: Pokemon): Boolean {
   }
}
