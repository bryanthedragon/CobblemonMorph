package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class MoveSetUpdatePacket(pokemon: () -> Pokemon, value: MoveSet) : SingleUpdatePacket(pokemon, value) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      this.getValue().saveToBuffer(buffer);
   }

   public open fun set(pokemon: Pokemon, value: MoveSet) {
      pokemon.getMoveSet().copyFrom(value);
   }

   @SourceDebugExtension(["SMAP\nMoveSetUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSetUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/MoveSetUpdatePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,29:1\n1#2:30\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): MoveSetUpdatePacket {
         val var10000: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var2: MoveSet = new MoveSet();
         var2.loadFromBuffer(buffer);
         return new MoveSetUpdatePacket(var10000, var2);
      }
   }
}
