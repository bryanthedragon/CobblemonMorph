package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class CaughtBallUpdatePacket(pokemon: () -> Pokemon, value: PokeBall) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.getValue().getName());
   }

   public open fun set(pokemon: Pokemon, value: PokeBall) {
      pokemon.setCaughtBall(value);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): CaughtBallUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var10000: PokeBalls = PokeBalls.INSTANCE;
         val var10001: ResourceLocation = buffer.m_130281_();
         var var4: PokeBall = var10000.getPokeBall(var10001);
         if (var4 == null) {
            var4 = PokeBalls.INSTANCE.getPOKE_BALL();
         }

         return new CaughtBallUpdatePacket(pokemon, var4);
      }
   }
}
