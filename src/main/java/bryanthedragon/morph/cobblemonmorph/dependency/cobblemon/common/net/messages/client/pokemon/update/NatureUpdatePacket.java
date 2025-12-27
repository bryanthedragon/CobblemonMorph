package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class NatureUpdatePacket(pokemon: () -> Pokemon, nature: Nature?, minted: Boolean) : PokemonUpdatePacket(pokemon) {
   public open val id: ResourceLocation
   public final val minted: Boolean
   public final val nature: Nature?

   init {
      this.nature = nature;
      this.minted = minted;
      this.id = ID;
   }

   public override fun encodeDetails(buffer: FriendlyByteBuf) {
      buffer.m_236821_(this.nature, NatureUpdatePacket::encodeDetails$lambda$0);
      buffer.writeBoolean(this.minted);
   }

   public override fun applyToPokemon() {
      if (this.minted && this.nature == null) {
         (this.getPokemon().invoke() as Pokemon).setMintedNature(null);
      } else if (this.nature == null) {
         Cobblemon.INSTANCE.getLOGGER().warn("A null nature was attempted to be put onto: '${this.getPokemon()}'");
      } else {
         if (!this.minted) {
            (this.getPokemon().invoke() as Pokemon).setNature(this.nature);
         } else {
            (this.getPokemon().invoke() as Pokemon).setMintedNature(this.nature);
         }
      }
   }

   @JvmStatic
   fun `encodeDetails$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, v: Nature) {
      `$buffer`.m_130085_(v.getName());
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): NatureUpdatePacket {
         return new NatureUpdatePacket(
            PokemonUpdatePacket.Companion.decodePokemon(buffer),
            buffer.m_236868_(NatureUpdatePacket.Companion::decode$lambda$0) as Nature,
            buffer.readBoolean()
         );
      }

      @JvmStatic
      fun `decode$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): Nature {
         val var10000: Natures = Natures.INSTANCE;
         val var10001: ResourceLocation = `$buffer`.m_130281_();
         return var10000.getNature(var10001);
      }
   }
}
