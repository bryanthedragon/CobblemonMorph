package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;

public interface Bone {
   Map<String, Bone> getChildren();

   void render(RenderContext context, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float a);

   void transform(PoseStack matrixStack);
}
