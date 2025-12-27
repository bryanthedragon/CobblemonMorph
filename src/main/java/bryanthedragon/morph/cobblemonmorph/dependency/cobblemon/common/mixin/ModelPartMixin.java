package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements Bone {
   @Shadow
   @Final
   public Map<String, ModelPart> f_104213_;

   @Shadow
   public abstract void m_104306_(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha);

   @Shadow
   public abstract void m_104299_(PoseStack matrixStack);

   @NotNull
   @Override
   public Map<String, Bone> getChildren() {
      return this.f_104213_;
   }

   @Override
   public void render(RenderContext context, PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
      this.m_104306_(stack, buffer, packedLight, packedOverlay, r, g, b, a);
   }

   @Override
   public void transform(PoseStack matrixStack) {
      this.m_104299_(matrixStack);
   }
}
