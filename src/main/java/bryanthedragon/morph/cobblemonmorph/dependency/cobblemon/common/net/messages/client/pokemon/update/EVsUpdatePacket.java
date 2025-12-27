package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Map.Entry
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nStatsUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n1855#2,2:60\n*S KotlinDebug\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket\n*L\n29#1:60,2\n*E\n"])
public class EVsUpdatePacket(pokemon: () -> Pokemon, eVs: EVs) : SingleUpdatePacket(pokemon, eVs) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      this.getValue().saveToBuffer(buffer);
   }

   public open fun set(pokemon: Pokemon, value: EVs) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         pokemon.getEvs().set((`element$iv` as Entry).getKey() as Stat, ((`element$iv` as Entry).getValue() as java.lang.Number).intValue());
      }
   }

   @SourceDebugExtension(["SMAP\nStatsUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StatsUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/EVsUpdatePacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,59:1\n1#2:60\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): EVsUpdatePacket {
         val var10000: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val var2: EVs = new EVs();
         var2.loadFromBuffer(buffer);
         return new EVsUpdatePacket(var10000, var2);
      }
   }
}
