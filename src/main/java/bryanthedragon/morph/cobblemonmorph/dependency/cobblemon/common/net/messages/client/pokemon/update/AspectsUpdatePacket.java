package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class AspectsUpdatePacket(pokemon: () -> Pokemon, value: Set<String>) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_236828_(this.getValue(), AspectsUpdatePacket::encodeValue$lambda$0);
   }

   public open fun set(pokemon: Pokemon, value: Set<String>) {
      pokemon.setAspects(value);
   }

   @JvmStatic
   fun `encodeValue$lambda$0`(pb: FriendlyByteBuf, value: java.lang.String) {
      pb.m_130070_(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): AspectsUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10000: java.util.List = buffer.m_236845_(FriendlyByteBuf::m_130277_);
         return new AspectsUpdatePacket(pokemon, CollectionsKt.toSet(var10000));
      }
   }
}
