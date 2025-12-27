package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data PokemonSentPostEvent(pokemon: Pokemon, pokemonEntity: PokemonEntity) {
   public final val pokemon: Pokemon
   public final val pokemonEntity: PokemonEntity

   init {
      this.pokemon = pokemon;
      this.pokemonEntity = pokemonEntity;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): PokemonEntity {
      return this.pokemonEntity;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, pokemonEntity: PokemonEntity = this.pokemonEntity): PokemonSentPostEvent {
      return new PokemonSentPostEvent(pokemon, pokemonEntity);
   }

   public override fun toString(): String {
      return "PokemonSentPostEvent(pokemon=${this.pokemon}, pokemonEntity=${this.pokemonEntity})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + this.pokemonEntity.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonSentPostEvent) {
         return false;
      } else {
         val var2: PokemonSentPostEvent = other as PokemonSentPostEvent;
         if (!(this.pokemon == (other as PokemonSentPostEvent).pokemon)) {
            return false;
         } else {
            return this.pokemonEntity == var2.pokemonEntity;
         }
      }
   }
}
