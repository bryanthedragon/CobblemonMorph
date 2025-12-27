package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class GenderUpdatePacket(pokemon: () -> Pokemon, value: Gender) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130068_(this.getValue());
   }

   public open fun set(pokemon: Pokemon, value: Gender) {
      pokemon.setGender(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): GenderUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val gender: Gender = buffer.m_130066_(Gender.class) as Gender;
         return new GenderUpdatePacket(pokemon, gender);
      }
   }
}
