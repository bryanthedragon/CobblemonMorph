package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class StatusUpdatePacket(pokemon: () -> Pokemon, value: PersistentStatus?) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.getValue(), StatusUpdatePacket::encodeValue$lambda$0);
   }

   public open fun set(pokemon: Pokemon, value: PersistentStatus?) {
      if (value == null) {
         pokemon.setStatus(null);
      } else {
         pokemon.applyStatus(value);
      }
   }

   @JvmStatic
   fun `encodeValue$lambda$0`(pb: FriendlyByteBuf, value: PersistentStatus) {
      pb.m_130085_(value.getName());
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): StatusUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10000: ResourceLocation = buffer.m_236868_(FriendlyByteBuf::m_130281_) as ResourceLocation;
         if (var10000 == null) {
            return new StatusUpdatePacket(pokemon, null);
         } else {
            val var5: Status = Statuses.INSTANCE.getStatus(var10000);
            return new StatusUpdatePacket(pokemon, var5 as? PersistentStatus);
         }
      }
   }
}
