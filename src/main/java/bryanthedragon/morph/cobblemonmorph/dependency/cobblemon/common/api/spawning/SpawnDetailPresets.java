package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PossibleHeldItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BiomeLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BlockLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.EitherIdentifierOrTagAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FluidLikeConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IntRangesAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PossibleHeldItemAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.RegisteredSpawningContextAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnBucketAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawnDetailPresetAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.SpawningConditionAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mojang.datafixers.util.Either
import java.lang.reflect.Type
import java.util.LinkedHashMap
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.levelgen.structure.Structure
import net.minecraft.world.level.material.Fluid

public object SpawnDetailPresets : JsonDataRegistry<SpawnDetailPreset> {
   public final val GSON: Gson
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource(INSTANCE.getResourcePath())
   public open val observable: SimpleObservable<SpawnDetailPresets> = new SimpleObservable()
   public final val presetTypes: MutableMap<String, Class<out SpawnDetailPreset>> = (new LinkedHashMap()) as java.util.Map
   public final var presets: MutableMap<ResourceLocation, SpawnDetailPreset> = (new LinkedHashMap()) as java.util.Map
   public open val resourcePath: String = "spawn_detail_presets"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<SpawnDetailPreset> = TypeToken.get(SpawnDetailPreset.class)

   public fun <T : SpawnDetailPreset> registerPresetType(name: String, detailClass: Class<Any>) {
      presetTypes.put(name, detailClass);
   }

   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(data: Map<ResourceLocation, SpawnDetailPreset>) {
      presets = MapsKt.toMutableMap(data);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded ${presets.size()} spawn detail presets.");
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var7: GsonBuilder = new GsonBuilder()
         .setPrettyPrinting()
         .setLenient()
         .disableHtmlEscaping()
         .registerTypeAdapter(SpawnBucket::class.java, SpawnBucketAdapter.INSTANCE)
         .registerTypeAdapter(RegisteredSpawningContext::class.java, RegisteredSpawningContextAdapter.INSTANCE)
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Biome.class}).getType(), BiomeLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Block.class}).getType(), BlockLikeConditionAdapter.INSTANCE
         )
         .registerTypeAdapter(
            TypeToken.getParameterized(RegistryLikeCondition::class.java, new Type[]{Fluid.class}).getType(), FluidLikeConditionAdapter.INSTANCE
         );
      val var12: Type = TypeToken.getParameterized(
            Either::class.java, new Type[]{ResourceLocation.class, TypeToken.getParameterized(TagKey::class.java, new Type[]{Structure.class}).getType()}
         )
         .getType();
      val var13: ResourceKey = Registries.f_256944_;
      GSON = var7.registerTypeAdapter(var12, new EitherIdentifierOrTagAdapter(var13))
         .registerTypeAdapter(SpawnDetailPreset::class.java, SpawnDetailPresetAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(SpawningCondition::class.java, SpawningConditionAdapter.INSTANCE)
         .registerTypeAdapter(TimeRange::class.java, new IntRangesAdapter<>(TimeRange.Companion.getTimeRanges(), <unrepresentable>.INSTANCE))
         .registerTypeAdapter(MoonPhaseRange::class.java, new IntRangesAdapter<>(MoonPhaseRange.Companion.getMoonPhaseRanges(), <unrepresentable>.INSTANCE))
         .registerTypeAdapter(PokemonProperties::class.java, PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter())
         .registerTypeAdapter(PossibleHeldItem::class.java, PossibleHeldItemAdapter.INSTANCE)
         .create();
      val var8: Gson = GSON;
      gson = var8;
   }
}
