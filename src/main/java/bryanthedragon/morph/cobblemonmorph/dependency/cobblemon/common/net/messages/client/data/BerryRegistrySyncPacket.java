package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nBerryRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/BerryRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1194#2,2:34\n1222#2,4:36\n*S KotlinDebug\n*F\n+ 1 BerryRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/BerryRegistrySyncPacket\n*L\n31#1:34,2\n31#1:36,4\n*E\n"])
public class BerryRegistrySyncPacket(berries: Collection<Berry>) : DataRegistrySyncPacket(berries) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: Berry) {
      entry.encode$common(buffer);
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): Berry {
      return Berry.Companion.decode$common(buffer);
   }

   public override fun synchronizeDecoded(entries: Collection<Berry>) {
      val `$this$associateBy$iv`: java.lang.Iterable = entries;
      val var12: Berries = Berries.INSTANCE;
      val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associateBy$iv`, 10)), 16)
      );

      for (Object element$iv$iv : $this$associateBy$iv) {
         `destination$iv$iv`.put((`element$iv$iv` as Berry).getIdentifier(), `element$iv$iv`);
      }

      var12.reload(`destination$iv$iv`);
   }

   @SourceDebugExtension(["SMAP\nBerryRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/BerryRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): BerryRegistrySyncPacket {
         val var2: BerryRegistrySyncPacket = new BerryRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
