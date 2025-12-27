package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class ShinyUpdatePacket(pokemon: () -> Pokemon, value: Boolean) : BooleanUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun set(pokemon: Pokemon, value: Boolean) {
      pokemon.setShiny(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): ShinyUpdatePacket {
         return new ShinyUpdatePacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), buffer.readBoolean());
      }
   }
}
