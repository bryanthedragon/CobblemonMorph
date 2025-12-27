package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data EvolutionDisplayEvent(pokemon: Pokemon, display: EvolutionDisplay, evolution: Evolution) : EvolutionEvent {
   public final var display: EvolutionDisplay
   public open val evolution: Evolution
   public open val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.display = display;
      this.evolution = evolution;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): EvolutionDisplay {
      return this.display;
   }

   public operator fun component3(): Evolution {
      return this.evolution;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, display: EvolutionDisplay = this.display, evolution: Evolution = this.evolution): EvolutionDisplayEvent {
      return new EvolutionDisplayEvent(pokemon, display, evolution);
   }

   public override fun toString(): String {
      return "EvolutionDisplayEvent(pokemon=${this.pokemon}, display=${this.display}, evolution=${this.evolution})";
   }

   public override fun hashCode(): Int {
      return (this.pokemon.hashCode() * 31 + this.display.hashCode()) * 31 + this.evolution.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is EvolutionDisplayEvent) {
         return false;
      } else {
         val var2: EvolutionDisplayEvent = other as EvolutionDisplayEvent;
         if (!(this.pokemon == (other as EvolutionDisplayEvent).pokemon)) {
            return false;
         } else if (!(this.display == var2.display)) {
            return false;
         } else {
            return this.evolution == var2.evolution;
         }
      }
   }
}
