package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class BenchedMovesUpdatePacket(pokemon: () -> Pokemon, value: BenchedMoves) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      this.getValue().saveToBuffer(buffer);
   }

   public open fun set(pokemon: Pokemon, value: BenchedMoves) {
      pokemon.getBenchedMoves().doThenEmit((new Function0<Unit>(pokemon, value) {
         {
            super(0);
            this.$pokemon = `$pokemon`;
            this.$value = `$value`;
         }

         public final void invoke() {
            this.$pokemon.getBenchedMoves().clear();
            this.$pokemon.getBenchedMoves().addAll(this.$value);
         }
      }) as () -> Unit);
   }

   @SourceDebugExtension(["SMAP\nBenchedMovesUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BenchedMovesUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/BenchedMovesUpdatePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,37:1\n1#2:38\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BenchedMovesUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var4: BenchedMoves = new BenchedMoves();
         var4.loadFromBuffer(buffer);
         return new BenchedMovesUpdatePacket(pokemon, var4);
      }
   }
}
