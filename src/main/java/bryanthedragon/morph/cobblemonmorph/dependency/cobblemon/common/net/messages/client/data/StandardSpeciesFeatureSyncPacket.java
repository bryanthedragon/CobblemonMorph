package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import kotlin.collections.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class StandardSpeciesFeatureSyncPacket(providers: Map<String, SpeciesFeatureProvider<*>>) : SpeciesFeatureSyncPacket(providers) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun synchronizeDecoded(entries: Collection<Entry<String, SynchronizedSpeciesFeatureProvider<*>>>) {
      SpeciesFeatures.INSTANCE.loadOnClient(entries);
   }

   @SourceDebugExtension(["SMAP\nStandardSpeciesFeatureSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StandardSpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/StandardSpeciesFeatureSyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,31:1\n1#2:32\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): StandardSpeciesFeatureSyncPacket {
         val var2: StandardSpeciesFeatureSyncPacket = new StandardSpeciesFeatureSyncPacket(MapsKt.emptyMap());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
