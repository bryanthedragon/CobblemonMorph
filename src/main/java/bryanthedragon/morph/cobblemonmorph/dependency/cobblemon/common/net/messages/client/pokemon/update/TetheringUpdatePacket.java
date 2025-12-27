package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class TetheringUpdatePacket(pokemon: () -> Pokemon, tetheringId: UUID?) : SingleUpdatePacket(pokemon, tetheringId) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.getValue(), TetheringUpdatePacket::encodeValue$lambda$0);
   }

   public open fun set(pokemon: Pokemon, value: UUID?) {
      pokemon.setTetheringId(value);
   }

   @JvmStatic
   fun `encodeValue$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: UUID) {
      `$buffer`.m_130077_(v);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TetheringUpdatePacket {
         return new TetheringUpdatePacket(
            PokemonUpdatePacket.Companion.decodePokemon(buffer), buffer.m_236868_(TetheringUpdatePacket.Companion::decode$lambda$0) as UUID
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): UUID {
         return `$buffer`.m_130259_();
      }
   }
}
