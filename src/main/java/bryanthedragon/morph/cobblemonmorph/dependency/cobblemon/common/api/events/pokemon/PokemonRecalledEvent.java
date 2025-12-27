package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data PokemonRecalledEvent(pokemon: Pokemon, oldEntity: PokemonEntity?) {
   public final val oldEntity: PokemonEntity?
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.oldEntity = oldEntity;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): PokemonEntity? {
      return this.oldEntity;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, oldEntity: PokemonEntity? = this.oldEntity): PokemonRecalledEvent {
      return new PokemonRecalledEvent(pokemon, oldEntity);
   }

   public override fun toString(): String {
      return "PokemonRecalledEvent(pokemon=${this.pokemon}, oldEntity=${this.oldEntity})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + (if (this.oldEntity == null) 0 else this.oldEntity.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonRecalledEvent) {
         return false;
      } else {
         val var2: PokemonRecalledEvent = other as PokemonRecalledEvent;
         if (!(this.pokemon == (other as PokemonRecalledEvent).pokemon)) {
            return false;
         } else {
            return this.oldEntity == var2.oldEntity;
         }
      }
   }
}
