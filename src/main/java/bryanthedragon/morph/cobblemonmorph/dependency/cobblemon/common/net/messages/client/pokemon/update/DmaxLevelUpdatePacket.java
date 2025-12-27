package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class DmaxLevelUpdatePacket(pokemon: () -> Pokemon, value: Int) : IntUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun getSize(): IntSize {
      return IntSize.U_BYTE;
   }

   public open fun set(pokemon: Pokemon, value: Int) {
      pokemon.setDmaxLevel(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): DmaxLevelUpdatePacket {
         return new DmaxLevelUpdatePacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE));
      }
   }
}
