package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public data PokemonCapturedEvent(pokemon: Pokemon, player: ServerPlayer, pokeBallEntity: EmptyPokeBallEntity) {
   public final val player: ServerPlayer
   public final val pokeBallEntity: EmptyPokeBallEntity
   public final val pokemon: Pokemon

   init {
      this.pokemon = pokemon;
      this.player = player;
      this.pokeBallEntity = pokeBallEntity;
   }

   public operator fun component1(): Pokemon {
      return this.pokemon;
   }

   public operator fun component2(): ServerPlayer {
      return this.player;
   }

   public operator fun component3(): EmptyPokeBallEntity {
      return this.pokeBallEntity;
   }

   public fun copy(pokemon: Pokemon = this.pokemon, player: ServerPlayer = this.player, pokeBallEntity: EmptyPokeBallEntity = this.pokeBallEntity): PokemonCapturedEvent {
      return new PokemonCapturedEvent(pokemon, player, pokeBallEntity);
   }

   public override fun toString(): String {
      return "PokemonCapturedEvent(pokemon=${this.pokemon}, player=${this.player}, pokeBallEntity=${this.pokeBallEntity})";
   }

   public override fun hashCode(): Int {
      return (this.pokemon.hashCode() * 31 + this.player.hashCode()) * 31 + this.pokeBallEntity.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is PokemonCapturedEvent) {
         return false;
      } else {
         val var2: PokemonCapturedEvent = other as PokemonCapturedEvent;
         if (!(this.pokemon == (other as PokemonCapturedEvent).pokemon)) {
            return false;
         } else if (!(this.player == var2.player)) {
            return false;
         } else {
            return this.pokeBallEntity == var2.pokeBallEntity;
         }
      }
   }
}
