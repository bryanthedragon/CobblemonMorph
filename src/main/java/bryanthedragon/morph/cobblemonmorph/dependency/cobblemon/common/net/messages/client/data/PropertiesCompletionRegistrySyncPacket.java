package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider.SuggestionHolder
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension
public (["SMAP\nPropertiesCompletionRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,41:1\n1855#2,2:42\n*S KotlinDebug\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket\n*L\n31#1:42,2\n*E\n"])
internal class PropertiesCompletionRegistrySyncPacket(suggestions: Collection<SuggestionHolder>) : DataRegistrySyncPacket(suggestions) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: SuggestionHolder) {
      buffer.m_236828_(entry.getKeys(), PropertiesCompletionRegistrySyncPacket::encodeEntry$lambda$0);
      buffer.m_236828_(entry.getSuggestions(), PropertiesCompletionRegistrySyncPacket::encodeEntry$lambda$1);
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): SuggestionHolder {
      val keys: java.util.List = buffer.m_236845_(PropertiesCompletionRegistrySyncPacket::decodeEntry$lambda$2);
      val suggestions: java.util.List = buffer.m_236845_(PropertiesCompletionRegistrySyncPacket::decodeEntry$lambda$3);
      val var10002: java.util.Collection = keys;
      return new PropertiesCompletionProvider.SuggestionHolder(var10002, suggestions);
   }

   public override fun synchronizeDecoded(entries: Collection<SuggestionHolder>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         PropertiesCompletionProvider.INSTANCE
            .inject(
               (`element$iv` as PropertiesCompletionProvider.SuggestionHolder).getKeys(),
               (`element$iv` as PropertiesCompletionProvider.SuggestionHolder).getSuggestions()
            );
      }
   }

   @JvmStatic
   fun `encodeEntry$lambda$0`(pb: FriendlyByteBuf, value: java.lang.String) {
      pb.m_130070_(value);
   }

   @JvmStatic
   fun `encodeEntry$lambda$1`(pb: FriendlyByteBuf, value: java.lang.String) {
      pb.m_130070_(value);
   }

   @JvmStatic
   fun `decodeEntry$lambda$2`(pb: FriendlyByteBuf): java.lang.String {
      return pb.m_130277_();
   }

   @JvmStatic
   fun `decodeEntry$lambda$3`(pb: FriendlyByteBuf): java.lang.String {
      return pb.m_130277_();
   }

   @SourceDebugExtension(["SMAP\nPropertiesCompletionRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PropertiesCompletionRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/PropertiesCompletionRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,41:1\n1#2:42\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): PropertiesCompletionRegistrySyncPacket {
         val var2: PropertiesCompletionRegistrySyncPacket = new PropertiesCompletionRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
