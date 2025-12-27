package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.generic

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.JsonPoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import com.google.gson.Gson
import com.google.gson.InstanceCreator
import java.lang.reflect.Type

public class JsonGenericPoseableModel(rootPart: Bone, isForLivingEntityRenderer: Boolean = false) : JsonPoseableEntityModel(rootPart) {
   public open val isForLivingEntityRenderer: Boolean
   public open val rootPart: Bone

   init {
      this.rootPart = rootPart;
      this.isForLivingEntityRenderer = isForLivingEntityRenderer;
   }

   public open fun getState(entity: GenericBedrockEntity): GenericBedrockClientDelegate {
      val var10000: EntitySideDelegate = entity.getDelegate();
      return var10000 as GenericBedrockClientDelegate;
   }

   public companion object {
      public final val gson: Gson
      public final var model: JsonGenericPoseableModel?
   }

   public object JsonGenericPoseableModelAdapter : InstanceCreator<JsonGenericPoseableModel> {
      public final var model: JsonGenericPoseableModel?
      public final var modelPart: Bone?

      public open fun createInstance(type: Type): JsonGenericPoseableModel {
         val var10002: Bone = modelPart;
         val var2: JsonGenericPoseableModel = new JsonGenericPoseableModel(var10002, false, 2, null);
         model = var2;
         val var10001: Bone = modelPart;
         var2.loadAllNamedChildren(var10001);
         return var2;
      }
   }
}
