package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class ClearEvolutionsPacket(pokemon: () -> Pokemon) : PokemonUpdatePacket(pokemon) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeDetails(buffer: FriendlyByteBuf) {
   }

   public override fun applyToPokemon() {
      (this.getPokemon().invoke() as Pokemon).getEvolutionProxy().client().clear();
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): ClearEvolutionsPacket {
         return new ClearEvolutionsPacket(PokemonUpdatePacket.Companion.decodePokemon(buffer));
      }
   }
}
