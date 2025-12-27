package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class FormUpdatePacket(pokemon: () -> Pokemon, form: FormData) : SingleUpdatePacket(pokemon, form) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      this.getValue().encode(buffer);
   }

   public open fun set(pokemon: Pokemon, value: FormData) {
      pokemon.setForm(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): FormUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val form: FormData = new FormData(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            -1,
            3,
            null
         );
         form.setSpecies((pokemon.invoke() as Pokemon).getSpecies());
         form.decode(buffer);
         return new FormUpdatePacket(pokemon, form);
      }
   }
}
