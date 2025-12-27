package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data EvolutionCompleteEvent(pokemon: Pokemon, evolution: Evolution) : EvolutionEvent {
   public open val evolution: Evolution
   public open val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.evolution = evolution;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): Evolution {
      return this.evolution;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, evolution: Evolution = this.evolution): EvolutionCompleteEvent {
      return new EvolutionCompleteEvent(pokemon, evolution);
   }

   public override fun toString(): String {
      return "EvolutionCompleteEvent(pokemon=${this.pokemon}, evolution=${this.evolution})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + this.evolution.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is EvolutionCompleteEvent) {
         return false;
      } else {
         val var2: EvolutionCompleteEvent = other as EvolutionCompleteEvent;
         if (!(this.pokemon == (other as EvolutionCompleteEvent).pokemon)) {
            return false;
         } else {
            return this.evolution == var2.evolution;
         }
      }
   }
}
