package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import kotlin.collections.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class GlobalSpeciesFeatureSyncPacket(speciesFeatures: Map<String, SpeciesFeatureProvider<*>>) : SpeciesFeatureSyncPacket(speciesFeatures) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public override fun synchronizeDecoded(entries: Collection<Entry<String, SynchronizedSpeciesFeatureProvider<*>>>) {
      GlobalSpeciesFeatures.INSTANCE.loadOnClient(entries);
   }

   @SourceDebugExtension(["SMAP\nGlobalSpeciesFeatureSyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalSpeciesFeatureSyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/GlobalSpeciesFeatureSyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,30:1\n1#2:31\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): GlobalSpeciesFeatureSyncPacket {
         val var2: GlobalSpeciesFeatureSyncPacket = new GlobalSpeciesFeatureSyncPacket(MapsKt.emptyMap());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
