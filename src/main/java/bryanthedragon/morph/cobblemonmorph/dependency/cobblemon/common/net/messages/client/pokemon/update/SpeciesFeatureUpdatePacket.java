package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

public class SpeciesFeatureUpdatePacket(pokemon: () -> Pokemon, species: ResourceLocation, speciesFeature: SynchronizedSpeciesFeature) : SingleUpdatePacket(
      pokemon, speciesFeature
   ) {
   public open val id: ResourceLocation
   public final val species: ResourceLocation

   init {
      this.species = species;
      this.id = ID;
   }

   public override fun encodeValue(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.species);
      buffer.m_130070_(this.getValue().getName());
      this.getValue().encode(buffer);
   }

   public open fun set(pokemon: Pokemon, value: SynchronizedSpeciesFeature) {
      pokemon.getFeatures().removeIf(SpeciesFeatureUpdatePacket::set$lambda$0);
      pokemon.getFeatures().add(value);
   }

   @JvmStatic
   fun `set$lambda$0`(`$tmp0`: Function1, p0: Any): Boolean {
      return `$tmp0`.invoke(p0) as java.lang.Boolean;
   }

   @SourceDebugExtension(["SMAP\nSpeciesFeatureUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,53:1\n800#2,11:54\n1#3:65\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket$Companion\n*L\n35#1:54,11\n*E\n"])
   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpeciesFeatureUpdatePacket {
         val pokemon: Function0 = PokemonUpdatePacket.Companion.decodePokemon(buffer);
         val speciesIdentifier: ResourceLocation = buffer.m_130281_();
         val var10000: PokemonSpecies = PokemonSpecies.INSTANCE;
         val var19: Species = var10000.getByIdentifier(speciesIdentifier);
         if (var19 == null) {
            throw new IllegalStateException("Pokémon unable to be found during species feature update packet: $speciesIdentifier");
         } else {
            val speciesFeatureName: java.lang.String = buffer.m_130277_();
            val feature: java.lang.Iterable = SpeciesFeatures.INSTANCE.getFeaturesFor(var19);
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filterIsInstance$iv) {
               if (var13 is SynchronizedSpeciesFeatureProvider) {
                  `destination$iv$iv`.add(var13);
               }
            }

            val var15: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

            while (true) {
               if (var15.hasNext()) {
                  val var16: SynchronizedSpeciesFeatureProvider = var15.next() as SynchronizedSpeciesFeatureProvider;
                  val var17: SynchronizedSpeciesFeature = var16.invoke(buffer, speciesFeatureName);
                  if (var17 == null) {
                     continue;
                  }

                  var20 = var17;
                  break;
               }

               var20 = null;
               break;
            }

            val var21: SynchronizedSpeciesFeature = if (var20 is SynchronizedSpeciesFeature) var20 else null;
            if ((if (var20 is SynchronizedSpeciesFeature) var20 else null) == null) {
               throw new IllegalArgumentException("Couldn't find a feature provider to deserialize this feature. Something's wrong.");
            } else {
               return new SpeciesFeatureUpdatePacket(pokemon, speciesIdentifier, var21);
            }
         }
      }
   }
}
