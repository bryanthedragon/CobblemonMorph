package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nFossilRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,44:1\n1194#2,2:45\n1222#2,4:47\n*S KotlinDebug\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket\n*L\n42#1:45,2\n42#1:47,4\n*E\n"])
public class FossilRegistrySyncPacket(fossils: List<Fossil>) : DataRegistrySyncPacket(fossils) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: Fossil) {
      buffer.m_130085_(entry.getIdentifier());
      buffer.m_130070_(Fossils.INSTANCE.getGson().toJson(entry.getResult(), PokemonProperties::class.java));
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): Fossil {
      val var10002: ResourceLocation = buffer.m_130281_();
      val var10003: Any = Fossils.INSTANCE.getGson().fromJson(buffer.m_130277_(), PokemonProperties.class);
      return new Fossil(var10002, var10003 as PokemonProperties, CollectionsKt.emptyList());
   }

   public override fun synchronizeDecoded(entries: Collection<Fossil>) {
      val `$this$associateBy$iv`: java.lang.Iterable = entries;
      val var12: Fossils = Fossils.INSTANCE;
      val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associateBy$iv`, 10)), 16)
      );

      for (Object element$iv$iv : $this$associateBy$iv) {
         `destination$iv$iv`.put((`element$iv$iv` as Fossil).getIdentifier(), `element$iv$iv`);
      }

      var12.reload(`destination$iv$iv`);
   }

   @SourceDebugExtension(["SMAP\nFossilRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/FossilRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,44:1\n1#2:45\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): FossilRegistrySyncPacket {
         val var2: FossilRegistrySyncPacket = new FossilRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
