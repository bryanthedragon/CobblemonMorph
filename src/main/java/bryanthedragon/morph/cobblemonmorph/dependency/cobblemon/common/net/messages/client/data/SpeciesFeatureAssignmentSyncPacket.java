package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignments
import java.util.LinkedHashMap
import kotlin.collections.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nSpeciesFeatureAssignmentSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n1179#2,2:49\n1253#2,4:51\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket\n*L\n41#1:49,2\n41#1:51,4\n*E\n"])
public class SpeciesFeatureAssignmentSyncPacket(data: Map<ResourceLocation, MutableSet<String>>) : DataRegistrySyncPacket(data.entrySet()) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): Entry<ResourceLocation, MutableSet<String>> {
      val key: ResourceLocation = buffer.m_130281_();
      val var10000: java.util.List = buffer.m_236845_(SpeciesFeatureAssignmentSyncPacket::decodeEntry$lambda$0);
      val assignments: java.util.Set = CollectionsKt.toMutableSet(var10000);
      return new java.util.Map.Entry<ResourceLocation, java.util.Set<java.lang.String>>(key, assignments) {
         private final ResourceLocation key;
         @NotNull
         private final java.util.Set<java.lang.String> value;

         {
            this.key = `$key`;
            this.value = `$assignments`;
         }

         public ResourceLocation getKey() {
            return this.key;
         }

         @NotNull
         public java.util.Set<java.lang.String> getValue() {
            return this.value;
         }

         public java.util.Set<java.lang.String> setValue(java.util.Set<java.lang.String> newValue) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      };
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: Entry<ResourceLocation, MutableSet<String>>) {
      buffer.m_130085_(entry.getKey() as ResourceLocation);
      buffer.m_236828_(entry.getValue() as java.util.Collection, SpeciesFeatureAssignmentSyncPacket::encodeEntry$lambda$1);
   }

   public override fun synchronizeDecoded(entries: Collection<Entry<ResourceLocation, MutableSet<String>>>) {
      val `$this$associate$iv`: java.lang.Iterable = entries;
      val var14: SpeciesFeatureAssignments = SpeciesFeatureAssignments.INSTANCE;
      val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associate$iv`, 10)), 16)
      );

      for (Object element$iv$iv : $this$associate$iv) {
         val var15: Pair = new Pair((`element$iv$iv` as java.util.Map.Entry).getKey(), (`element$iv$iv` as java.util.Map.Entry).getValue());
         `destination$iv$iv`.put(var15.getFirst(), var15.getSecond());
      }

      var14.loadOnClient(`destination$iv$iv`);
   }

   @JvmStatic
   fun `decodeEntry$lambda$0`(`$buffer`: FriendlyByteBuf, it: FriendlyByteBuf): java.lang.String {
      return `$buffer`.m_130277_();
   }

   @JvmStatic
   fun `encodeEntry$lambda$1`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, value: java.lang.String) {
      `$buffer`.m_130070_(value);
   }

   @SourceDebugExtension(["SMAP\nSpeciesFeatureAssignmentSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignmentSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesFeatureAssignmentSyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,48:1\n1#2:49\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpeciesFeatureAssignmentSyncPacket {
         val var2: SpeciesFeatureAssignmentSyncPacket = new SpeciesFeatureAssignmentSyncPacket(MapsKt.emptyMap());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
