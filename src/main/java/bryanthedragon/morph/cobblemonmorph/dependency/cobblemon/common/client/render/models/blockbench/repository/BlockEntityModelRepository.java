package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatelessAnimation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity.BlockEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.AnimationReferenceFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.JsonPokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nBlockEntityModelRepository.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BlockEntityModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BlockEntityModelRepository\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,63:1\n19#2:64\n*S KotlinDebug\n*F\n+ 1 BlockEntityModelRepository.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/BlockEntityModelRepository\n*L\n37#1:64\n*E\n"])
public object BlockEntityModelRepository : VaryingModelRepository<Entity, BlockEntityModel> {
   public open val animationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/animations")
   public open val fallback: ResourceLocation = MiscUtilsKt.cobblemonResource("substitute")
   private final val gson: Gson = new GsonBuilder().create()
   public open val isForLivingEntityRenderer: Boolean
   public open val modelDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/models")
   public open val poserDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/posers")
   public open val title: String = "Block Entity"
   public open val type: String = "block_entities"
   public open val variationDirectories: List<String> =
      CollectionsKt.listOf(new java.lang.String[]{"bedrock/${INSTANCE.getType()}/variations", "bedrock/${INSTANCE.getType()}"})

   public override fun loadJsonPoser(json: String): (Bone) -> BlockEntityModel {
      val var10000: Gson = gson;
      val jsonObject: JsonObject = var10000.fromJson(json, JsonObject.class) as JsonObject;
      val var6: JsonArray = jsonObject.getAsJsonArray("animations");
      val var8: JsonElement = jsonObject.get("maxScale");
      val var7: Float = if (var8 != null) var8.getAsFloat() else 1.0F;
      val var9: JsonElement = jsonObject.get("yTranslation");
      val yTranslation: Float = if (var9 != null) var9.getAsFloat() else 0.0F;
      return (new Function1<Bone, BlockEntityModel>(var7, yTranslation, var6) {
         {
            super(1);
            this.$maxScale = `$maxScale`;
            this.$yTranslation = `$yTranslation`;
            this.$animations = `$animations`;
         }

         @NotNull
         public final BlockEntityModel invoke(@NotNull Bone bone) {
            val model: BlockEntityModel = new BlockEntityModel(bone);
            model.setMaxScale(this.$maxScale);
            model.setYTranslation(this.$yTranslation);
            val var10001: JsonArray = this.$animations;
            val `$this$toTypedArray$iv`: java.lang.Iterable = var10001 as java.lang.Iterable;
            val `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv$iv : $this$toTypedArray$iv) {
               val animString: java.lang.String = (`element$iv$iv$iv` as JsonElement).getAsString();
               val anim: java.lang.String = StringsKt.substringBefore$default(animString, "(", null, 2, null);
               var var23: StatelessAnimation;
               if (JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().containsKey(anim)) {
                  var23 = JsonPokemonPoseableModel.Companion.getANIMATION_FACTORIES().get(anim);
                  var23 = (var23 as AnimationReferenceFactory).stateless(model, animString);
               } else {
                  var23 = null;
               }

               if (var23 != null) {
                  `destination$iv$iv`.add(var23);
               }
            }

            model.setIdleAnimations((`destination$iv$iv` as java.util.List).toArray(new StatelessAnimation[0]));
            return model;
         }
      }) as (Bone?) -> BlockEntityModel;
   }

   public override fun registerInBuiltPosers() {
   }
}
