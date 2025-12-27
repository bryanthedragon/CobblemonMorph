package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public data FossilRevivedEvent(pokemon: Pokemon, player: ServerPlayer?) {
   public final val player: ServerPlayer?
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.player = player;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): ServerPlayer? {
      return this.player;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, player: ServerPlayer? = this.player): FossilRevivedEvent {
      return new FossilRevivedEvent(pokemon, player);
   }

   public override fun toString(): String {
      return "FossilRevivedEvent(pokemon=${this.pokemon}, player=${this.player})";
   }

   public override fun hashCode(): Int {
      return this.pokemon.hashCode() * 31 + (if (this.player == null) 0 else this.player.hashCode());
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is FossilRevivedEvent) {
         return false;
      } else {
         val var2: FossilRevivedEvent = other as FossilRevivedEvent;
         if (!(this.pokemon == (other as FossilRevivedEvent).pokemon)) {
            return false;
         } else {
            return this.player == var2.player;
         }
      }
   }
}
