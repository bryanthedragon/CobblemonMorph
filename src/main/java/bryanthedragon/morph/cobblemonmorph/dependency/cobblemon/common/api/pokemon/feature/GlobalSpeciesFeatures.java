package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.aspect.AspectProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.GlobalSpeciesFeatureSyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpeciesFeatureProviderAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.Vec3dAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.ArrayList;
import java.util.LinkedHashMap
import kotlin.collections.Map.Entry
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.phys.Vec3
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nGlobalSpeciesFeatures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GlobalSpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/GlobalSpeciesFeatures\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,108:1\n1855#2,2:109\n1603#2,9:111\n1855#2:120\n1856#2:122\n1612#2:123\n1549#2:124\n1620#2,3:125\n1#3:121\n1#3:128\n*S KotlinDebug\n*F\n+ 1 GlobalSpeciesFeatures.kt\ncom/cobblemon/mod/common/api/pokemon/feature/GlobalSpeciesFeatures\n*L\n61#1:109,2\n69#1:111,9\n69#1:120\n69#1:122\n69#1:123\n72#1:124\n72#1:125,3\n69#1:121\n*E\n"])
public object GlobalSpeciesFeatures : JsonDataRegistry<SpeciesFeatureProvider<?>> {
   private final val codeFeatures: MutableMap<String, SpeciesFeatureProvider<*>> = (new LinkedHashMap()) as java.util.Map
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("global_species_features")
   public open val observable: SimpleObservable<SpeciesFeatures> = new SimpleObservable()
   private final val resourceFeatures: MutableMap<String, SpeciesFeatureProvider<*>> = (new LinkedHashMap()) as java.util.Map
   public open val resourcePath: String = "global_species_features"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<SpeciesFeatureProvider<*>>

   public override fun sync(player: ServerPlayer) {
      CobblemonNetwork.INSTANCE.sendPacket(player, new GlobalSpeciesFeatureSyncPacket(MapsKt.plus(codeFeatures, resourceFeatures)));
   }

   public override fun reload(data: Map<ResourceLocation, SpeciesFeatureProvider<*>>) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.unregister(`element$iv` as java.lang.String);
      }

      data.forEach(GlobalSpeciesFeatures::reload$lambda$0);
   }

   public fun getCodeFeature(name: String): SpeciesFeatureProvider<*>? {
      return codeFeatures.get(name);
   }

   public fun getResourceFeature(name: String): SpeciesFeatureProvider<*>? {
      return resourceFeatures.get(name);
   }

   public fun getFeature(name: String): SpeciesFeatureProvider<out SpeciesFeature>? {
      var var10000: SpeciesFeatureProvider = this.getCodeFeature(name);
      if (var10000 == null) {
         var10000 = this.getResourceFeature(name);
      }

      return var10000;
   }

   public fun getFeatures(): List<SpeciesFeatureProvider<out SpeciesFeature>> {
      val `$this$mapNotNull$iv`: java.lang.Iterable = SetsKt.plus(resourceFeatures.keySet(), codeFeatures.keySet());
      val `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv$iv : $this$mapNotNull$iv) {
         val var10000: SpeciesFeatureProvider = this.getFeature(`element$iv$iv$iv` as java.lang.String);
         if (var10000 != null) {
            `destination$iv$iv`.add(var10000);
         }
      }

      return `destination$iv$iv` as MutableList<SpeciesFeatureProvider<? extends SpeciesFeature>>;
   }

   public fun loadOnClient(entries: Collection<Entry<String, SpeciesFeatureProvider<*>>>) {
      val `$this$map$iv`: java.lang.Iterable = entries;
      val var12: java.util.Map = codeFeatures;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add(new Pair((`item$iv$iv` as java.util.Map.Entry).getKey(), (`item$iv$iv` as java.util.Map.Entry).getValue()));
      }

      MapsKt.putAll(var12, `destination$iv$iv` as java.util.List);
   }

   private fun register(name: String, provider: SpeciesFeatureProvider<*>, isCoded: Boolean) {
      val mapping: java.util.Map = if (isCoded) codeFeatures else resourceFeatures;
      if (provider is AspectProvider) {
         AspectProvider.Companion.register(provider as AspectProvider);
      }

      if (provider is CustomPokemonPropertyType) {
         CustomPokemonProperty.Companion.register(provider as CustomPokemonPropertyType);
      }

      mapping.put(name, provider);
   }

   public fun register(name: String, provider: SpeciesFeatureProvider<*>) {
      this.register(name, provider, true);
   }

   public fun <T : SpeciesFeature> register(name: String, providerLambda: () -> Any) {
      this.register(name, new SpeciesFeatureProvider<T>(providerLambda) {
         {
            this.$providerLambda = `$providerLambda`;
         }

         @NotNull
         @Override
         public T invoke(@NotNull Pokemon pokemon) {
            return (T)(this.$providerLambda.invoke() as SpeciesFeature);
         }

         @NotNull
         @Override
         public T invoke(@NotNull CompoundTag nbt) {
            val var2: Any = this.$providerLambda.invoke();
            (var2 as SpeciesFeature).loadFromNBT(nbt);
            return (T)(var2 as SpeciesFeature);
         }

         @NotNull
         @Override
         public T invoke(@NotNull JsonObject json) {
            val var2: Any = this.$providerLambda.invoke();
            (var2 as SpeciesFeature).loadFromJSON(json);
            return (T)(var2 as SpeciesFeature);
         }
      });
   }

   private fun registerFromAssets(identifier: ResourceLocation, provider: SpeciesFeatureProvider<*>) {
      val var10001: java.lang.String = identifier.m_135815_();
      this.register(var10001, provider, false);
   }

   public fun unregister(name: String) {
      var var8: Boolean = true;
      var var10000: SpeciesFeatureProvider = this.getResourceFeature(name);
      if (var10000 != null) {
         var8 = false;
         var10000 = var10000;
      } else {
         var10000 = this.getCodeFeature(name);
         if (var10000 == null) {
            return;
         }
      }

      if (var10000 is AspectProvider) {
         AspectProvider.Companion.unregister(var10000 as AspectProvider);
      }

      if (var10000 is CustomPokemonPropertyType) {
         CustomPokemonProperty.Companion.unregister(var10000 as CustomPokemonPropertyType<?>);
      }

      (if (var8) codeFeatures else resourceFeatures).remove(name);
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun `reload$lambda$0`(`$tmp0`: Function2, p0: Any, p1: Any) {
      `$tmp0`.invoke(p0, p1);
   }

   @JvmStatic
   fun {
      val var10000: Gson = new GsonBuilder()
         .setPrettyPrinting()
         .registerTypeAdapter(SpeciesFeatureProvider::class.java, SpeciesFeatureProviderAdapter.INSTANCE)
         .registerTypeAdapter(Vec3::class.java, Vec3dAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .create();
      gson = var10000;
      val var0: TypeToken = TypeToken.get(SpeciesFeatureProvider.class);
      typeToken = var0;
   }
}
