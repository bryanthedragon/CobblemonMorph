package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class TradeableUpdatePacket(pokemon: () -> Pokemon, value: Boolean) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.getValue());
   }

   public open fun set(pokemon: Pokemon, value: Boolean) {
      pokemon.setTradeable(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TradeableUpdatePacket {
         return new TradeableUpdatePacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), buffer.readBoolean());
      }
   }
}
