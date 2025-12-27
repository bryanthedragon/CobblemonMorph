package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.generic

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation

public class GenericBedrockRenderer(context: Context) : EntityRenderer(context) {
   public open fun getTexture(entity: GenericBedrockEntity): ResourceLocation {
      val var10000: GenericBedrockEntityModelRepository = GenericBedrockEntityModelRepository.INSTANCE;
      val var10001: ResourceLocation = entity.getCategory();
      val var10002: java.util.Set = entity.getAspects();
      val var10003: EntitySideDelegate = entity.getDelegate();
      return var10000.getTexture(var10001, var10002, (var10003 as GenericBedrockClientDelegate).getAnimationSeconds());
   }

   public open fun render(entity: GenericBedrockEntity, yaw: Float, partialTicks: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int) {
      if (!entity.m_20145_()) {
         val model: PoseableEntityModel = GenericBedrockEntityModelRepository.INSTANCE.getPoser(entity.getCategory(), entity.getAspects());
         poseStack.m_85836_();
         poseStack.m_85841_(1.0F, -1.0F, 1.0F);
         poseStack.m_85841_(entity.getScale(), entity.getScale(), entity.getScale());
         poseStack.m_252781_(Axis.f_252436_.m_252977_(yaw));
         val vertexConsumer: VertexConsumer = buffer.m_6299_(model.m_103119_(this.getTexture(entity)));
         val var10000: EntitySideDelegate = entity.getDelegate();
         val state: GenericBedrockClientDelegate = var10000 as GenericBedrockClientDelegate;
         (var10000 as GenericBedrockClientDelegate).updatePartialTicks(partialTicks);
         model.setLayerContext(buffer, state, PokemonModelRepository.INSTANCE.getLayers(entity.getCategory(), entity.getAspects()));
         model.m_6973_(entity, 0.0F, 0.0F, (float)entity.f_19797_ + partialTicks, 0.0F, 0.0F);
         model.m_7695_(poseStack, vertexConsumer, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         model.setGreen(1.0F);
         model.setBlue(1.0F);
         model.setRed(1.0F);
         model.resetLayerContext();
         poseStack.m_85849_();
         super.m_7392_(entity, yaw, partialTicks, poseStack, buffer, packedLight);
      }
   }
}
