package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class OriginalTrainerUpdatePacket(pokemon: () -> Pokemon, username: String?) : SingleUpdatePacket(pokemon, username) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.getValue(), OriginalTrainerUpdatePacket::encodeValue$lambda$0);
   }

   public open fun set(pokemon: Pokemon, value: String?) {
      pokemon.setOriginalTrainerName(value);
   }

   @JvmStatic
   fun `encodeValue$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: java.lang.String) {
      `$buffer`.m_130070_(v);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): OriginalTrainerUpdatePacket {
         return new OriginalTrainerUpdatePacket(
            PokemonUpdatePacket.Companion.decodePokemon(buffer), buffer.m_236868_(OriginalTrainerUpdatePacket.Companion::decode$lambda$0) as java.lang.String
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
         return `$buffer`.m_130277_();
      }
   }
}
