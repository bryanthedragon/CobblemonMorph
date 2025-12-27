package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.fossil

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterial
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.DataRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry.ItemTagCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

public class NaturalMaterialRegistrySyncPacket(naturalMaterials: List<NaturalMaterial>) : DataRegistrySyncPacket(naturalMaterials) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: NaturalMaterial) {
      buffer.m_236821_(entry.getItem(), NaturalMaterialRegistrySyncPacket::encodeEntry$lambda$0);
      buffer.m_236821_(entry.getTag(), NaturalMaterialRegistrySyncPacket::encodeEntry$lambda$1);
      buffer.m_236821_(entry.getReturnItem(), NaturalMaterialRegistrySyncPacket::encodeEntry$lambda$2);
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): NaturalMaterial {
      return new NaturalMaterial(
         0,
         buffer.m_236868_(NaturalMaterialRegistrySyncPacket::decodeEntry$lambda$3) as ResourceLocation,
         buffer.m_236868_(NaturalMaterialRegistrySyncPacket::decodeEntry$lambda$4) as ItemTagCondition,
         buffer.m_236868_(NaturalMaterialRegistrySyncPacket::decodeEntry$lambda$5) as ResourceLocation
      );
   }

   public override fun synchronizeDecoded(entries: Collection<NaturalMaterial>) {
      NaturalMaterials.INSTANCE.reload(MapsKt.mapOf(TuplesKt.to(MiscUtilsKt.cobblemonResource("natural_materials"), CollectionsKt.toList(entries))));
   }

   @JvmStatic
   fun `encodeEntry$lambda$0`(`$entry`: NaturalMaterial, pb: FriendlyByteBuf, type: ResourceLocation) {
      pb.m_130085_(`$entry`.getItem());
   }

   @JvmStatic
   fun `encodeEntry$lambda$1`(`$entry`: NaturalMaterial, pb: FriendlyByteBuf, type: ItemTagCondition) {
      var var4: ResourceLocation;
      var var10001: Gson;
      label12: {
         var10001 = NaturalMaterials.INSTANCE.getGson();
         val var10002: ItemTagCondition = `$entry`.getTag();
         if (var10002 != null) {
            val var3: TagKey = var10002.getTag();
            if (var3 != null) {
               var4 = var3.f_203868_();
               break label12;
            }
         }

         var4 = null;
      }

      pb.m_130070_(var10001.toJson("#$var4"));
   }

   @JvmStatic
   fun `encodeEntry$lambda$2`(`$entry`: NaturalMaterial, pb: FriendlyByteBuf, type: ResourceLocation) {
      pb.m_130085_(`$entry`.getReturnItem());
   }

   @JvmStatic
   fun `decodeEntry$lambda$3`(pb: FriendlyByteBuf): ResourceLocation {
      return pb.m_130281_();
   }

   @JvmStatic
   fun `decodeEntry$lambda$4`(`$buffer`: FriendlyByteBuf, pb: FriendlyByteBuf): ItemTagCondition {
      return NaturalMaterials.INSTANCE.getGson().fromJson(`$buffer`.m_130277_(), ItemTagCondition.class) as ItemTagCondition;
   }

   @JvmStatic
   fun `decodeEntry$lambda$5`(pb: FriendlyByteBuf): ResourceLocation {
      return pb.m_130281_();
   }

   @SourceDebugExtension(["SMAP\nNaturalMaterialRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NaturalMaterialRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/fossil/NaturalMaterialRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,49:1\n1#2:50\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): NaturalMaterialRegistrySyncPacket {
         val var2: NaturalMaterialRegistrySyncPacket = new NaturalMaterialRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
