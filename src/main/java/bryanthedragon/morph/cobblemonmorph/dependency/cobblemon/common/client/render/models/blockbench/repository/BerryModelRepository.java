package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas.CobblemonAtlases
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nBerryModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BerryModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,78:1\n215#2,2:79\n1855#3,2:81\n*S KotlinDebug\n*F\n+ 1 BerryModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BerryModelRepository\n*L\n44#1:79,2\n53#1:81,2\n*E\n"])
public object BerryModelRepository : JsonDataRegistry<TexturedModel> {
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("berry_models")
   public open val observable: SimpleObservable<BerryModelRepository> = new SimpleObservable()
   private final val processedModels: HashMap<ResourceLocation, ModelPart> = new HashMap()
   private final val rawModels: HashMap<ResourceLocation, TexturedModel> = new HashMap()
   public open val resourcePath: String = "bedrock/berries"
   public open val type: PackType = PackType.CLIENT_RESOURCES
   public open val typeToken: TypeToken<TexturedModel>

   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(data: Map<ResourceLocation, TexturedModel>) {
      for (Entry element$iv : data.entrySet()) {
         rawModels.put(`element$iv`.getKey() as ResourceLocation, `element$iv`.getValue() as TexturedModel);
      }

      this.getObservable().emit(this);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} berry models", rawModels.size());
   }

   public fun patchModels() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         var it: Berry;
         var flowerModel: TexturedModel;
         var flowerTex: TextureAtlasSprite;
         var var13: java.util.Map;
         var var14: ResourceLocation;
         var var10000: ModelPart;
         label26: {
            it = `element$iv` as Berry;
            val fruitModel: TexturedModel = rawModels.get((`element$iv` as Berry).getFruitModelIdentifier());
            flowerModel = rawModels.get((`element$iv` as Berry).getFlowerModelIdentifier());
            val fruitTex: TextureAtlasSprite = CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().m_118901_(it.getFruitTexture());
            flowerTex = CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().m_118901_(it.getFlowerTexture());
            var13 = processedModels;
            var14 = it.getFruitModelIdentifier();
            if (fruitModel != null) {
               val var15: LayerDefinition = fruitModel.createWithUvOverride(
                  false,
                  fruitTex.m_174743_(),
                  fruitTex.m_174744_(),
                  CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276067_,
                  CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276070_
               );
               if (var15 != null) {
                  var10000 = var15.m_171564_();
                  break label26;
               }
            }

            var10000 = null;
         }

         label21: {
            var13.put(var14, var10000);
            var13 = processedModels;
            var14 = it.getFlowerModelIdentifier();
            if (flowerModel != null) {
               val var19: LayerDefinition = flowerModel.createWithUvOverride(
                  false,
                  flowerTex.m_174743_(),
                  flowerTex.m_174744_(),
                  CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276067_,
                  CobblemonAtlases.INSTANCE.getBERRY_SPRITE_ATLAS().f_118884_.f_276070_
               );
               if (var19 != null) {
                  var10000 = var19.m_171564_();
                  break label21;
               }
            }

            var10000 = null;
         }

         var13.put(var14, var10000);
      }
   }

   public fun modelOf(identifier: ResourceLocation): ModelPart? {
      return processedModels.get(identifier);
   }

   override fun reload(manager: ResourceManager) {
      JsonDataRegistry.DefaultImpls.reload(this, manager);
   }

   @JvmStatic
   fun {
      val var10000: Gson = TexturedModel.Companion.getGSON();
      gson = var10000;
      val var0: TypeToken = TypeToken.get(TexturedModel.class);
      typeToken = var0;
   }
}
