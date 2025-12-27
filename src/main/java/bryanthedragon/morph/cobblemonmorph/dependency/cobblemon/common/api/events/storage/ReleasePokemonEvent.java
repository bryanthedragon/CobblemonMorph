package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.storage

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public interface ReleasePokemonEvent {
   public val player: ServerPlayer
   public val pokemon: Pokemon
   public val storage: PokemonStore<*>

   public class Post(player: ServerPlayer, pokemon: Pokemon, storage: PokemonStore<*>) : ReleasePokemonEvent {
      public open val player: ServerPlayer
      public open val pokemon: Pokemon
      public open val storage: PokemonStore<*>

      init {
         this.player = player;
         this.pokemon = pokemon;
         this.storage = storage;
      }
   }

   public class Pre(player: ServerPlayer, pokemon: Pokemon, storage: PokemonStore<*>) : Cancelable, ReleasePokemonEvent {
      public open val player: ServerPlayer
      public open val pokemon: Pokemon
      public open val storage: PokemonStore<*>

      init {
         this.player = player;
         this.pokemon = pokemon;
         this.storage = storage;
      }
   }
}
