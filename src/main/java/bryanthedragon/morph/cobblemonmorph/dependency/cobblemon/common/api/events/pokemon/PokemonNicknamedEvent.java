package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer

public class PokemonNicknamedEvent(player: ServerPlayer, pokemon: Pokemon, nickname: MutableComponent?) : Cancelable {
   public final var nickname: MutableComponent?

   public final val nicknameString: String?
      public final get() {
         return if (this.nickname != null) this.nickname.getString() else null;
      }


   public final val player: ServerPlayer
   public final val pokemon: Pokemon

   init {
      this.player = player;
      this.pokemon = pokemon;
      this.nickname = nickname;
   }
}
