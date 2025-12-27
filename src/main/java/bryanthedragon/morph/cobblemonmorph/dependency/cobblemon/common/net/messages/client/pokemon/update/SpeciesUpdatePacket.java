package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class SpeciesUpdatePacket(pokemon: () -> Pokemon, value: Species) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.getValue().getResourceIdentifier());
   }

   public open fun set(pokemon: Pokemon, value: Species) {
      pokemon.setSpecies(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpeciesUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         val var10001: ResourceLocation = buffer.m_130281_();
         val var4: Species = var10000.getByIdentifier(var10001);
         return new SpeciesUpdatePacket(pokemon, var4);
      }
   }
}
