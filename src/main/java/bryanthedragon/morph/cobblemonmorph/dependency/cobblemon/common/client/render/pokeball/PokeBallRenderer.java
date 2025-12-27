package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.EmptyPokeBallClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

public class PokeBallRenderer(context: Context) : EntityRenderer(context) {
   public open fun getTexture(pEntity: EmptyPokeBallEntity): ResourceLocation {
      val var10000: PokeBallModelRepository = PokeBallModelRepository.INSTANCE;
      val var10001: ResourceLocation = pEntity.getPokeBall().getName();
      val var10002: java.util.Set = pEntity.getAspects();
      val var10003: EntitySideDelegate = pEntity.getDelegate();
      return var10000.getTexture(var10001, var10002, (var10003 as EmptyPokeBallClientDelegate).getAnimationSeconds());
   }

   public open fun render(entity: EmptyPokeBallEntity, yaw: Float, partialTicks: Float, poseStack: PoseStack, buffer: MultiBufferSource, packedLight: Int) {
      val model: PokeBallModel = PokeBallModelRepository.INSTANCE.getPoser(entity.getPokeBall().getName(), entity.getAspects());
      poseStack.m_85836_();
      poseStack.m_252781_(Axis.f_252436_.m_252977_(yaw));
      poseStack.m_85841_(0.7F, -0.7F, -0.7F);
      val vertexConsumer: VertexConsumer = ItemRenderer.m_115222_(buffer, model.m_103119_(this.getTexture(entity)), false, false);
      val var10000: EntitySideDelegate = entity.getDelegate();
      val state: EmptyPokeBallClientDelegate = var10000 as EmptyPokeBallClientDelegate;
      (var10000 as EmptyPokeBallClientDelegate).updatePartialTicks(partialTicks);
      model.setLayerContext(buffer, state, PokemonModelRepository.INSTANCE.getLayers(entity.getPokeBall().getName(), entity.getAspects()));
      model.m_6973_(entity as Entity, 0.0F, 0.0F, (float)entity.f_19797_ + partialTicks, 0.0F, 0.0F);
      model.m_7695_(poseStack, vertexConsumer, packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      model.setGreen(1.0F);
      model.setBlue(1.0F);
      model.setRed(1.0F);
      model.resetLayerContext();
      poseStack.m_85849_();
      super.m_7392_(entity as Entity, yaw, partialTicks, poseStack, buffer, packedLight);
   }
}
