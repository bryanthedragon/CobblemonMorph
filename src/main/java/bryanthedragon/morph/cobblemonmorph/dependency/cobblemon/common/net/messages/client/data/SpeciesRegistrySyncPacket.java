package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import java.util.LinkedHashMap
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nSpeciesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesRegistrySyncPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,52:1\n1194#2,2:53\n1222#2,4:55\n*S KotlinDebug\n*F\n+ 1 SpeciesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesRegistrySyncPacket\n*L\n45#1:53,2\n45#1:55,4\n*E\n"])
public class SpeciesRegistrySyncPacket(species: Collection<Species>) : DataRegistrySyncPacket(species) {
   public open val id: ResourceLocation

   init {
      this.id = ID;
   }

   public open fun encodeEntry(buffer: FriendlyByteBuf, entry: Species) {
      try {
         buffer.m_130085_(entry.getResourceIdentifier());
         entry.encode(buffer);
      } catch (var4: Exception) {
         Cobblemon.INSTANCE.getLOGGER().error("Caught exception encoding the species {}", entry.getResourceIdentifier(), var4);
      }
   }

   public open fun decodeEntry(buffer: FriendlyByteBuf): Species? {
      val identifier: ResourceLocation = buffer.m_130281_();
      val species: Species = new Species();
      species.setResourceIdentifier(identifier);

      var var4: Species;
      try {
         species.decode(buffer);
         var4 = species;
      } catch (var6: Exception) {
         Cobblemon.INSTANCE.getLOGGER().error("Caught exception decoding the species {}", identifier, var6);
         var4 = null;
      }

      return var4;
   }

   public override fun synchronizeDecoded(entries: Collection<Species>) {
      val `$this$associateBy$iv`: java.lang.Iterable = entries;
      val var12: PokemonSpecies = PokemonSpecies.INSTANCE;
      val `destination$iv$iv`: java.util.Map = new LinkedHashMap(
         RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(`$this$associateBy$iv`, 10)), 16)
      );

      for (Object element$iv$iv : $this$associateBy$iv) {
         `destination$iv$iv`.put((`element$iv$iv` as Species).getResourceIdentifier(), `element$iv$iv`);
      }

      var12.reload(`destination$iv$iv`);
   }

   @SourceDebugExtension(["SMAP\nSpeciesRegistrySyncPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesRegistrySyncPacket.kt\ncom/cobblemon/mod/common/net/messages/client/data/SpeciesRegistrySyncPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,52:1\n1#2:53\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpeciesRegistrySyncPacket {
         val var2: SpeciesRegistrySyncPacket = new SpeciesRegistrySyncPacket(CollectionsKt.emptyList());
         var2.decodeBuffer$common(buffer);
         return var2;
      }
   }
}
