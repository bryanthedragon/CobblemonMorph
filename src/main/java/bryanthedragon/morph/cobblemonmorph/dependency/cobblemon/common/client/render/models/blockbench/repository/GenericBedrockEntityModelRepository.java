package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.generic.JsonGenericPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.jvm.functions.Function1
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

public object GenericBedrockEntityModelRepository : VaryingModelRepository<GenericBedrockEntity, PoseableEntityModel<GenericBedrockEntity>> {
   public open val animationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/animations")
   public open val fallback: ResourceLocation = MiscUtilsKt.cobblemonResource("substitute")
   public open val isForLivingEntityRenderer: Boolean
   public open val modelDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/models")
   public open val poserDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/posers")
   public open val title: String = "Generic"
   public open val type: String = "generic"
   public open val variationDirectories: List<String> = CollectionsKt.listOf("bedrock/${INSTANCE.getType()}/variations")

   public override fun registerInBuiltPosers() {
   }

   public override fun loadJsonPoser(json: String): (Bone) -> PoseableEntityModel<GenericBedrockEntity> {
      return (new Function1<Bone, JsonGenericPoseableModel>(json) {
         {
            super(1);
            this.$json = `$json`;
         }

         public final JsonGenericPoseableModel invoke(@NotNull Bone it) {
            JsonGenericPoseableModel.JsonGenericPoseableModelAdapter.INSTANCE.setModelPart(it);
            val var10000: Any = JsonGenericPoseableModel.Companion.getGson().fromJson(this.$json, JsonGenericPoseableModel.class);
            return var10000 as JsonGenericPoseableModel;
         }
      }) as (Bone?) -> PoseableEntityModel<GenericBedrockEntity>;
   }
}
