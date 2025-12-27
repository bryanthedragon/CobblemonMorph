package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data PokemonFaintedEvent(pokemon: Pokemon, faintedTimer: Int) {
   public final var faintedTimer: Int
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.faintedTimer = faintedTimer;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): Int {
      return this.faintedTimer;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, faintedTimer: Int = this.faintedTimer): PokemonFaintedEvent {
      return new PokemonFaintedEvent(pokemon, faintedTimer);
   }

   public override fun toString(): String {
      return "PokemonFaintedEvent(pokemon=${this.pokemon}, faintedTimer=${this.faintedTimer})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + Integer.hashCode(this.faintedTimer);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonFaintedEvent) {
         return false;
      } else {
         val var2: PokemonFaintedEvent = other as PokemonFaintedEvent;
         if (!(this.pokemon == (other as PokemonFaintedEvent).pokemon)) {
            return false;
         } else {
            return this.faintedTimer == var2.faintedTimer;
         }
      }
   }
}
