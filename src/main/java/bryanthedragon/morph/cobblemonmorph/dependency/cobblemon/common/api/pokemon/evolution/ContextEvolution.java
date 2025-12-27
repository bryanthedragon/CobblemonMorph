package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public interface ContextEvolution<RC, TC> : Evolution {
   public val requiredContext: Any

   public open fun attemptEvolution(pokemon: Pokemon, context: Any): Boolean {
   }

   public abstract fun testContext(pokemon: Pokemon, context: Any): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <RC, TC> attemptEvolution(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon, context: RC): Boolean {
         return `$this`.testContext(pokemon, context) && Evolution.DefaultImpls.test(`$this`, pokemon) && Evolution.DefaultImpls.evolve(`$this`, pokemon);
      }

      @JvmStatic
      fun <RC, TC> test(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon): Boolean {
         return Evolution.DefaultImpls.test(`$this`, pokemon);
      }

      @JvmStatic
      fun <RC, TC> evolve(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon): Boolean {
         return Evolution.DefaultImpls.evolve(`$this`, pokemon);
      }

      @JvmStatic
      fun <RC, TC> forceEvolve(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon) {
         Evolution.DefaultImpls.forceEvolve(`$this`, pokemon);
      }

      @JvmStatic
      fun <RC, TC> evolutionMethod(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon) {
         Evolution.DefaultImpls.evolutionMethod(`$this`, pokemon);
      }

      @JvmStatic
      fun <RC, TC> applyTo(`$this`: ContextEvolution<RC, TC>, pokemon: Pokemon) {
         Evolution.DefaultImpls.applyTo(`$this`, pokemon);
      }
   }
}
