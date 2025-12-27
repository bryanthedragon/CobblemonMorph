package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthPoint
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.CobblemonRenderLayers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.ModelPartExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BerryModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Axis
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexBuffer
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.BufferBuilder.RenderedBuffer
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4fc

public class BerryBlockRenderer(context: Context) : BlockEntityRenderer<BerryBlockEntity> {
   private final val context: Context

   init {
      this.context = context;
   }

   public open fun isInRenderDistance(blockEntity: BerryBlockEntity, pos: Vec3): Boolean {
      return super.m_142756_(blockEntity, pos) && Minecraft.m_91087_().f_91060_.f_172938_.m_113029_(AABB.m_165882_(pos, 2.0, 4.0, 2.0));
   }

   public open fun render(entity: BerryBlockEntity, tickDelta: Float, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, overlay: Int) {
      val var10002: BlockPos = entity.m_58899_();
      if (this.isInRenderDistance(entity, BlockPosExtensionsKt.toVec3d(var10002))) {
         val age: Int = entity.m_58900_().m_61143_(BerryBlock.Companion.getAGE() as Property) as Int;
         if (age > 3) {
            if (entity.getRenderState() == null) {
               entity.setRenderState(new BerryBlockEntityRenderState());
            }

            var var10000: BerryBlockEntity.RenderState = entity.getRenderState();
            val renderState: BerryBlockEntityRenderState = var10000 as BerryBlockEntityRenderState;
            if ((var10000 as BerryBlockEntityRenderState).getNeedsRebuild() || (var10000 as BerryBlockEntityRenderState).getVboLightLevel() != light) {
               this.renderToBuffer(entity, light, overlay, renderState.getVbo());
               renderState.setVboLightLevel(light);
               var10000 = entity.getRenderState();
               (var10000 as BerryBlockEntityRenderState).setNeedsRebuild(false);
            }

            matrices.m_85836_();
            CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110185_();
            renderState.getVbo().m_85921_();
            renderState.getVbo()
               .m_253207_(
                  matrices.m_85850_().m_252922_().mul(RenderSystem.getModelViewMatrix() as Matrix4fc),
                  RenderSystem.getProjectionMatrix(),
                  GameRenderer.m_172646_()
               );
            VertexBuffer.m_85931_();
            CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110188_();
            matrices.m_85849_();
         }
      }
   }

   public fun renderToBuffer(entity: BerryBlockEntity, light: Int, overlay: Int, buffer: VertexBuffer) {
      val age: Int = entity.m_58900_().m_61143_(BerryBlock.Companion.getAGE() as Property) as Int;
      if (age > 3) {
         val isFlower: Boolean = age == 4;
         val var14: BufferBuilder = Tesselator.m_85913_().m_85915_();
         var14.m_166779_(CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_173186_(), CobblemonRenderLayers.INSTANCE.getBERRY_LAYER().m_110508_());

         for (Pair var10 : entity.berryAndGrowthPoint$common()) {
            val berry: Berry = var10.component1() as Berry;
            val growthPoint: GrowthPoint = var10.component2() as GrowthPoint;
            val var10000: ModelPart = if (isFlower)
               BerryModelRepository.INSTANCE.modelOf(berry.getFlowerModelIdentifier())
               else
               BerryModelRepository.INSTANCE.modelOf(berry.getFruitModelIdentifier());
            if (var10000 != null) {
               var10000.m_171327_(
                  (float)Math.toRadians(180.0 - growthPoint.getRotation().f_82479_),
                  (float)Math.toRadians(180.0 + growthPoint.getRotation().f_82480_),
                  (float)Math.toRadians(growthPoint.getRotation().f_82481_)
               );
               ModelPartExtensionsKt.setPosition(var10000, Axis.X_AXIS.ordinal(), (float)growthPoint.getPosition().f_82479_);
               ModelPartExtensionsKt.setPosition(var10000, Axis.Y_AXIS.ordinal(), (float)growthPoint.getPosition().f_82480_);
               ModelPartExtensionsKt.setPosition(var10000, Axis.Z_AXIS.ordinal(), (float)growthPoint.getPosition().f_82481_);
               var10000.m_104301_(new PoseStack(), var14 as VertexConsumer, light, overlay);
            }
         }

         val var15: RenderedBuffer = var14.m_231175_();
         buffer.m_85921_();
         buffer.m_231221_(var15);
         VertexBuffer.m_85931_();
      }
   }
}
