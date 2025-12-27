package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

public data FriendshipUpdatedEvent(pokemon: Pokemon, newFriendship: Int) {
   public final var newFriendship: Int
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.newFriendship = newFriendship;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): Int {
      return this.newFriendship;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, newFriendship: Int = this.newFriendship): FriendshipUpdatedEvent {
      return new FriendshipUpdatedEvent(pokemon, newFriendship);
   }

   public override fun toString(): String {
      return "FriendshipUpdatedEvent(pokemon=${this.pokemon}, newFriendship=${this.newFriendship})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + Integer.hashCode(this.newFriendship);
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is FriendshipUpdatedEvent) {
         return false;
      } else {
         val var2: FriendshipUpdatedEvent = other as FriendshipUpdatedEvent;
         if (!(this.pokemon == (other as FriendshipUpdatedEvent).pokemon)) {
            return false;
         } else {
            return this.newFriendship == var2.newFriendship;
         }
      }
   }
}
