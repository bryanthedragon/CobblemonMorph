package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public data ShoulderMountEvent(player: ServerPlayer, pokemon: Pokemon, isLeft: Boolean) : Cancelable {
   public final val isLeft: Boolean
   public final val player: ServerPlayer
   public final val pokemon: Pokemon

   init {
      this.player = player;
      this.pokemon = pokemon;
      this.isLeft = isLeft;
   }

   public operator fun component1(): ServerPlayer {
      return this.player;
   }

   public operator fun component2(): Pokemon {
      return this.pokemon;
   }

   public operator fun component3(): Boolean {
      return this.isLeft;
   }

   public fun copy(player: ServerPlayer = this.player, pokemon: Pokemon = this.pokemon, isLeft: Boolean = this.isLeft): ShoulderMountEvent {
      return new ShoulderMountEvent(player, pokemon, isLeft);
   }

   public override fun toString(): String {
      return "ShoulderMountEvent(player=${this.player}, pokemon=${this.pokemon}, isLeft=${this.isLeft})";
   }

   public override fun hashCode(): Int {
      val var10000: Int = (this.player.hashCode() * 31 + this.pokemon.hashCode()) * 31;
      var var10001: Byte = this.isLeft;
      if (this.isLeft) {
         var10001 = 1;
      }

      return var10000 + var10001;
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is ShoulderMountEvent) {
         return false;
      } else {
         val var2: ShoulderMountEvent = other as ShoulderMountEvent;
         if (!(this.player == (other as ShoulderMountEvent).player)) {
            return false;
         } else if (!(this.pokemon == var2.pokemon)) {
            return false;
         } else {
            return this.isLeft == var2.isLeft;
         }
      }
   }
}
