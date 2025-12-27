package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.BerryRegistrySyncPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters.CobblemonStatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.BoxCollectionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CobblemonBerrySpawnConditionAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.CobblemonGrowthFactorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.FloatNumberRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.IdentifierAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.LiteralHexColorAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.MulchVariantAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.StatusAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.TagKeyAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseIntRangeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.VerboseVec3dAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.awt.Color
import java.lang.reflect.Type
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.advancements.critereon.MinMaxBounds.Doubles
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.tags.TagKey
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nBerries.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berries.kt\ncom/cobblemon/mod/common/api/berry/Berries\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,107:1\n215#2,2:108\n*S KotlinDebug\n*F\n+ 1 Berries.kt\ncom/cobblemon/mod/common/api/berry/Berries\n*L\n71#1:108,2\n*E\n"])
public object Berries : JsonDataRegistry<Berry> {
   private final val berries: HashMap<ResourceLocation, Berry> = new HashMap()
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("berries")
   public open val observable: SimpleObservable<Berries> = new SimpleObservable()
   public open val resourcePath: String = "berries"
   public open val type: PackType = PackType.SERVER_DATA
   public open val typeToken: TypeToken<Berry>

   public override fun reload(data: Map<ResourceLocation, Berry>) {
      berries.clear();

      for (Entry element$iv : data.entrySet()) {
         val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
         val berry: Berry = `element$iv`.getValue() as Berry;

         try {
            berry.setIdentifier$common(identifier);
            berry.validate$common();
            berries.put(identifier, berry);
         } catch (var11: Exception) {
            Cobblemon.INSTANCE.getLOGGER().error("Skipped loading the {} berry", identifier, var11);
         }
      }

      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} berries", berries.size());
      this.getObservable().emit(this);
   }

   public override fun sync(player: ServerPlayer) {
      new BerryRegistrySyncPacket(this.all()).sendToPlayer(player);
   }

   public fun all(): List<Berry> {
      val var10000: java.util.Collection = berries.values();
      return CollectionsKt.toList(var10000);
   }

   public fun getByIdentifier(identifier: ResourceLocation): Berry? {
      return berries.get(identifier);
   }

   public fun getByName(name: String): Berry? {
      return this.getByIdentifier(MiscUtilsKt.cobblemonResource(name));
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var2: GsonBuilder = new GsonBuilder()
         .disableHtmlEscaping()
         .setPrettyPrinting()
         .registerTypeAdapter(MulchVariant::class.java, MulchVariantAdapter.INSTANCE)
         .registerTypeAdapter(Doubles::class.java, FloatNumberRangeAdapter.INSTANCE)
         .registerTypeAdapter(Status::class.java, StatusAdapter.INSTANCE)
         .registerTypeAdapter(TypeToken.getParameterized(java.util.Collection::class.java, new Type[]{AABB.class}).getType(), BoxCollectionAdapter.INSTANCE)
         .registerTypeAdapter(AABB::class.java, BoxAdapter.INSTANCE)
         .registerTypeAdapter(Vec3::class.java, VerboseVec3dAdapter.INSTANCE)
         .registerTypeAdapter(ResourceLocation::class.java, IdentifierAdapter.INSTANCE)
         .registerTypeAdapter(GrowthFactor::class.java, CobblemonGrowthFactorAdapter.INSTANCE)
         .registerTypeAdapter(IntRange::class.java, VerboseIntRangeAdapter.INSTANCE)
         .registerTypeAdapter(Color::class.java, LiteralHexColorAdapter.INSTANCE)
         .registerTypeAdapter(Stat::class.java, CobblemonStatTypeAdapter.INSTANCE);
      val var5: Type = TypeToken.getParameterized(TagKey::class.java, new Type[]{Biome.class}).getType();
      val var10004: ResourceKey = Registries.f_256952_;
      gson = var2.registerTypeAdapter(var5, new TagKeyAdapter(var10004))
         .registerTypeAdapter(BerrySpawnCondition::class.java, CobblemonBerrySpawnConditionAdapter.INSTANCE)
         .create();
      val var3: TypeToken = TypeToken.get(Berry.class);
      typeToken = var3;
   }
}
