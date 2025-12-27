package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.GildedChestBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.blockentity.BlockEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BlockEntityModelRepository
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import kotlin.jvm.functions.Function0
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.Property

public class GildedChestBlockRenderer(context: Context) : BlockEntityRenderer<GildedChestBlockEntity> {
   public open fun render(entity: GildedChestBlockEntity, tickDelta: Float, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, overlay: Int) {
      val aspects: java.util.Set = SetsKt.emptySet();
      val state: GildedState = entity.getPoseableState();
      state.updatePartialTicks(tickDelta);
      val poserId: ResourceLocation = entity.getType().getPoserId();
      val model: BlockEntityModel = BlockEntityModelRepository.INSTANCE.getPoser(poserId, aspects);
      val vertexConsumer: VertexConsumer = vertexConsumers.m_6299_(
         model.m_103119_(BlockEntityModelRepository.INSTANCE.getTexture(poserId, aspects, state.getAnimationSeconds()))
      );
      model.setBufferProvider(vertexConsumers);
      state.setCurrentModel(model);
      matrices.m_85836_();
      matrices.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      matrices.m_85837_(-0.5, 0.0, 0.5);
      matrices.m_252781_(Axis.f_252436_.m_252977_((entity.m_58900_().m_61143_(BlockStateProperties.f_61374_ as Property) as Direction).m_122435_()));
      matrices.m_252781_(Axis.f_252436_.m_252977_(180.0F));
      model.setupAnimStateful(null, state, 0.0F, 0.0F, state.getAnimationSeconds() * (float)20, 0.0F, 0.0F);
      model.m_7695_(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
      model.withLayerContext(
         vertexConsumers,
         state,
         BlockEntityModelRepository.INSTANCE.getLayers(poserId, aspects),
         (new Function0<Unit>(model, matrices, vertexConsumer, light) {
            {
               super(0);
               this.$model = `$model`;
               this.$matrices = `$matrices`;
               this.$vertexConsumer = `$vertexConsumer`;
               this.$light = `$light`;
            }

            public final void invoke() {
               val var10000: BlockEntityModel = this.$model;
               val var10001: PoseStack = this.$matrices;
               val var10002: VertexConsumer = this.$vertexConsumer;
               var10000.m_7695_(var10001, var10002, this.$light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            }
         }) as () -> Unit
      );
      model.setDefault();
      matrices.m_85849_();
   }
}
