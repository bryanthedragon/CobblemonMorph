package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf

public abstract class SingleUpdatePacket<T, N extends NetworkPacket<N>> : PokemonUpdatePacket<N> {
   public final val value: Any

   open fun SingleUpdatePacket(pokemon: () -> Pokemon, value: T) {
      super(pokemon);
      this.value = (T)value;
   }

   public override fun encodeDetails(buffer: FriendlyByteBuf) {
      this.encodeValue(buffer);
   }

   public override fun applyToPokemon() {
      this.set(this.getPokemon().invoke() as Pokemon, this.value);
   }

   public abstract fun encodeValue(buffer: FriendlyByteBuf) {
   }

   public abstract fun set(pokemon: Pokemon, value: Any) {
   }
}
