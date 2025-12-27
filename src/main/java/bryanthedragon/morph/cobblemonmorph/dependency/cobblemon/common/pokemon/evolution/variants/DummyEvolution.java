package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.LinkedHashSet

internal class DummyEvolution : Evolution {
   public open var consumeHeldItem: Boolean
   public open val id: String = "dummy"
   public open val learnableMoves: MutableSet<MoveTemplate> = (new LinkedHashSet()) as java.util.Set
   public open var optional: Boolean
   public open val requirements: MutableSet<EvolutionRequirement> = (new LinkedHashSet()) as java.util.Set
   public open val result: PokemonProperties = new PokemonProperties()

   public override fun test(pokemon: Pokemon): Boolean {
      return false;
   }

   public override fun evolve(pokemon: Pokemon): Boolean {
      return false;
   }

   override fun forceEvolve(pokemon: Pokemon) {
      Evolution.DefaultImpls.forceEvolve(this, pokemon);
   }

   override fun evolutionMethod(pokemon: Pokemon) {
      Evolution.DefaultImpls.evolutionMethod(this, pokemon);
   }

   override fun applyTo(pokemon: Pokemon) {
      Evolution.DefaultImpls.applyTo(this, pokemon);
   }
}
