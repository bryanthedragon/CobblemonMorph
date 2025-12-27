package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data EvolutionTestedEvent(pokemon: Pokemon, evolution: Evolution, originalResult: Boolean, result: Boolean) : EvolutionEvent {
   public open val evolution: Evolution
   public final val originalResult: Boolean
   public open val pokemon: Pokemon
   public final var result: Boolean

   init {
      this.pokemon = pokemon;
      this.evolution = evolution;
      this.originalResult = originalResult;
      this.result = result;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): Evolution {
      return this.evolution;
   }

   public operator fun component3(): Boolean {
      return this.originalResult;
   }

   public operator fun component4(): Boolean {
      return this.result;
   }

   public fun copy(
      pokemon: Pokemon = this.pokemon,
      evolution: Evolution = this.evolution,
      originalResult: Boolean = this.originalResult,
      result: Boolean = this.result
   ): EvolutionTestedEvent {
      return new EvolutionTestedEvent(pokemon, evolution, originalResult, result);
   }

   public override fun toString(): String {
      return "EvolutionTestedEvent(pokemon=${this.pokemon}, evolution=${this.evolution}, originalResult=${this.originalResult}, result=${this.result})";
   }

   public override fun hashCode(): Int {
      var var10000: Int = (this.pokemon.hashCode() * 31 + this.evolution.hashCode()) * 31;
      var var10001: Byte = this.originalResult;
      if (this.originalResult) {
         var10001 = 1;
      }

      var10000 = (var10000 + var10001) * 31;
      var10001 = this.result;
      if (this.result) {
         var10001 = 1;
      }

      return var10000 + var10001;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is EvolutionTestedEvent) {
         return false;
      } else {
         val var2: EvolutionTestedEvent = other as EvolutionTestedEvent;
         if (!(this.pokemon == (other as EvolutionTestedEvent).pokemon)) {
            return false;
         } else if (!(this.evolution == var2.evolution)) {
            return false;
         } else if (this.originalResult != var2.originalResult) {
            return false;
         } else {
            return this.result == var2.result;
         }
      }
   }
}
