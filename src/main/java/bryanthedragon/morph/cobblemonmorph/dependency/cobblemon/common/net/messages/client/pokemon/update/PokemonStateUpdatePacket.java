package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class PokemonStateUpdatePacket(pokemon: () -> Pokemon, value: PokemonState) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      this.getValue().writeToBuffer(buffer);
   }

   public open fun set(pokemon: Pokemon, value: PokemonState) {
      pokemon.setState(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): PokemonStateUpdatePacket {
         return new PokemonStateUpdatePacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), PokemonState.Companion.fromBuffer(buffer));
      }
   }
}
