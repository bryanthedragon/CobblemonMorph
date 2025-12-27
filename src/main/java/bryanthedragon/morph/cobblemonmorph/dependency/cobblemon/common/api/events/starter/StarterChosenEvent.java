package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public data StarterChosenEvent(player: ServerPlayer, properties: PokemonProperties, pokemon: Pokemon) : Cancelable {
   public final val player: ServerPlayer
   public final var pokemon: Pokemon
   public final val properties: PokemonProperties

   init {
      this.player = player;
      this.properties = properties;
      this.pokemon = pokemon;
   }

   public operator fun component1(): ServerPlayer {
      return this.player;
   }

   public operator fun component2(): PokemonProperties {
      return this.properties;
   }

   public operator fun component3(): Pokemon {
      return this.pokemon;
   }

   public fun copy(player: ServerPlayer = this.player, properties: PokemonProperties = this.properties, pokemon: Pokemon = this.pokemon): StarterChosenEvent {
      return new StarterChosenEvent(player, properties, pokemon);
   }

   public override fun toString(): String {
      return "StarterChosenEvent(player=${this.player}, properties=${this.properties}, pokemon=${this.pokemon})";
   }

   public override fun hashCode(): Int {
      return (this.player.hashCode() * 31 + this.properties.hashCode()) * 31 + this.pokemon.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is StarterChosenEvent) {
         return false;
      } else {
         val var2: StarterChosenEvent = other as StarterChosenEvent;
         if (!(this.player == (other as StarterChosenEvent).player)) {
            return false;
         } else if (!(this.properties == var2.properties)) {
            return false;
         } else {
            return this.pokemon == var2.pokemon;
         }
      }
   }
}
