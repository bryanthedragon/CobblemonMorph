package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.SpeciesFeatureAssignmentSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nSpeciesFeatureAssignments.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureAssignments.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignments\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,64:1\n1855#2:65\n1855#2:66\n1856#2:74\n1856#2:75\n361#3,7:67\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureAssignments.kt\ncom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureAssignments\n*L\n50#1:65\n51#1:66\n51#1:74\n50#1:75\n52#1:67,7\n*E\n"])
public object SpeciesFeatureAssignments : JsonDataRegistry<SpeciesFeatureAssignment> {
   private final val assignments: MutableMap<ResourceLocation, MutableSet<String>> = (new LinkedHashMap()) as java.util.Map
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("species_feature_assignments")
   public open val observable: SimpleObservable<SpeciesFeatureAssignments> = new SimpleObservable()
   public open val resourcePath: String = "species_feature_assignments"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<SpeciesFeatureAssignment> = TypeToken.get(SpeciesFeatureAssignment.class)

   public override fun sync(player: ServerPlayer) {
      CobblemonNetwork.INSTANCE.sendPacket(player, new SpeciesFeatureAssignmentSyncPacket(assignments));
   }

   public override fun reload(data: Map<ResourceLocation, SpeciesFeatureAssignment>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val it: SpeciesFeatureAssignment = `element$iv` as SpeciesFeatureAssignment;

         val `$this$forEach$ivx`: java.lang.Iterable;
         for (Object element$ivx : $this$forEach$ivx) {
            val pokemon: java.lang.String = `element$ivx` as java.lang.String;
            val `$this$getOrPut$iv`: java.util.Map = assignments;
            val `key$iv`: Any = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(pokemon, null, 1, null);
            val `value$iv`: Any = `$this$getOrPut$iv`.get(`key$iv`);
            val var10000: Any;
            if (`value$iv` == null) {
               val var20: Any = new LinkedHashSet();
               `$this$getOrPut$iv`.put(`key$iv`, var20);
               var10000 = var20;
            } else {
               var10000 = `value$iv`;
            }

            (var10000 as java.util.Set).addAll(it.getFeatures());
         }
      }

      this.getObservable().emit(this);
   }

   public fun loadOnClient(data: Map<ResourceLocation, MutableSet<String>>) {
      assignments.clear();
      assignments.putAll(data);
   }

   public fun getFeatures(species: Species): Set<String> {
      var var10000: java.util.Set = assignments.get(species.getResourceIdentifier());
      if (var10000 == null) {
         var10000 = SetsKt.emptySet();
      }

      return var10000;
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var10000: Gson = new GsonBuilder().setPrettyPrinting().create();
      gson = var10000;
   }
}
