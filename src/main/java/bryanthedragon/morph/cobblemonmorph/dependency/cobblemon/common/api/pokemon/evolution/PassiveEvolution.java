package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public interface PassiveEvolution : Evolution {
   public val permanent: Boolean

   public open fun attemptEvolution(pokemon: Pokemon): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun attemptEvolution(`$this`: PassiveEvolution, pokemon: Pokemon): Boolean {
         return Evolution.DefaultImpls.test(`$this`, pokemon) && Evolution.DefaultImpls.evolve(`$this`, pokemon);
      }

      @JvmStatic
      fun test(`$this`: PassiveEvolution, pokemon: Pokemon): Boolean {
         return Evolution.DefaultImpls.test(`$this`, pokemon);
      }

      @JvmStatic
      fun evolve(`$this`: PassiveEvolution, pokemon: Pokemon): Boolean {
         return Evolution.DefaultImpls.evolve(`$this`, pokemon);
      }

      @JvmStatic
      fun forceEvolve(`$this`: PassiveEvolution, pokemon: Pokemon) {
         Evolution.DefaultImpls.forceEvolve(`$this`, pokemon);
      }

      @JvmStatic
      fun evolutionMethod(`$this`: PassiveEvolution, pokemon: Pokemon) {
         Evolution.DefaultImpls.evolutionMethod(`$this`, pokemon);
      }

      @JvmStatic
      fun applyTo(`$this`: PassiveEvolution, pokemon: Pokemon) {
         Evolution.DefaultImpls.applyTo(`$this`, pokemon);
      }
   }
}
