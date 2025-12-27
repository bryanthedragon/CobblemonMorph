package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.TexturedModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.HashMap
import java.util.Map.Entry
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager

@SourceDebugExtension(["SMAP\nMiscModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MiscModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/MiscModelRepository\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,50:1\n215#2,2:51\n*S KotlinDebug\n*F\n+ 1 MiscModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/MiscModelRepository\n*L\n41#1:51,2\n*E\n"])
public object MiscModelRepository : JsonDataRegistry<TexturedModel> {
   public open val gson: Gson
   public open val id: ResourceLocation = MiscUtilsKt.cobblemonResource("misc_models")
   private final val models: HashMap<ResourceLocation, ModelPart> = new HashMap()
   public open val observable: SimpleObservable<MiscModelRepository> = new SimpleObservable()
   public open val resourcePath: String = "bedrock/misc"
   public open val type: PackType = PackType.CLIENT_RESOURCES
   public open val typeToken: TypeToken<TexturedModel>

   public override fun sync(player: ServerPlayer) {
   }

   public override fun reload(data: Map<ResourceLocation, TexturedModel>) {
      for (Entry element$iv : data.entrySet()) {
         val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
         val model: TexturedModel = `element$iv`.getValue() as TexturedModel;
         val var10: java.util.Map = models;
         val var10000: ModelPart = model.create(false).m_171564_();
         var10.put(identifier, var10000);
      }

      this.getObservable().emit(this);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded {} misc models", models.size());
   }

   public fun modelOf(identifier: ResourceLocation): ModelPart? {
      return models.get(identifier);
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
