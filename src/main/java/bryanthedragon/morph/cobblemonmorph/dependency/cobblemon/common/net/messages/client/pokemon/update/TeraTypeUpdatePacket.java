package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.tera.TeraTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class TeraTypeUpdatePacket(pokemon: () -> Pokemon, value: TeraType) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.getValue().getId());
   }

   public open fun set(pokemon: Pokemon, value: TeraType) {
      pokemon.setTeraType(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): TeraTypeUpdatePacket {
         val var10002: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10003: ResourceLocation = buffer.m_130281_();
         val var2: TeraType = TeraTypes.get(var10003);
         return new TeraTypeUpdatePacket(var10002, var2);
      }
   }
}
