package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

public class NicknameUpdatePacket(pokemon: () -> Pokemon, value: MutableComponent?) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.getValue(), NicknameUpdatePacket::encodeValue$lambda$0);
   }

   public open fun set(pokemon: Pokemon, value: MutableComponent?) {
      pokemon.setNickname(value);
   }

   @JvmStatic
   fun `encodeValue$lambda$0`(`$buffer`: FriendlyByteBuf, `this$0`: NicknameUpdatePacket, var2: FriendlyByteBuf, v: MutableComponent) {
      `$buffer`.m_130083_(`this$0`.getValue() as Component);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): NicknameUpdatePacket {
         return new NicknameUpdatePacket(
            PokemonUpdatePacket.Companion.decodePokemon(buffer), buffer.m_236868_(NicknameUpdatePacket.Companion::decode$lambda$0) as MutableComponent
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): MutableComponent {
         return `$buffer`.m_130238_().m_6881_();
      }
   }
}
