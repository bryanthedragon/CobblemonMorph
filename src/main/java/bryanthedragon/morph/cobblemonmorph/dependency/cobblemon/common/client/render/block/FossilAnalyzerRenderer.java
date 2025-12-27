package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.core.Direction
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.Vec3

@SourceDebugExtension(["SMAP\nFossilAnalyzerRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilAnalyzerRenderer.kt\ncom/cobblemon/mod/common/client/render/block/FossilAnalyzerRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n1864#2,3:69\n*S KotlinDebug\n*F\n+ 1 FossilAnalyzerRenderer.kt\ncom/cobblemon/mod/common/client/render/block/FossilAnalyzerRenderer\n*L\n47#1:69,3\n*E\n"])
public class FossilAnalyzerRenderer(ctx: Context) : BlockEntityRenderer<FossilAnalyzerBlockEntity> {
   public open fun render(
      entity: FossilAnalyzerBlockEntity,
      tickDelta: Float,
      matrices: PoseStack,
      vertexConsumers: MultiBufferSource?,
      light: Int,
      overlay: Int
   ) {
      val var10000: BlockState;
      if (entity.m_58904_() != null) {
         var10000 = entity.m_58900_();
      } else {
         val var21: Any = CobblemonBlocks.FOSSIL_ANALYZER
            .m_49966_()
            .m_61124_(HorizontalDirectionalBlock.f_54117_ as Property, Direction.SOUTH as java.lang.Comparable);
         var10000 = var21 as BlockState;
      }

      if (entity.getMultiblockStructure() != null) {
         val direction: Direction = var10000.m_61143_(HorizontalDirectionalBlock.f_54117_ as Property) as Direction;
         val yRot: Float = direction.m_122435_() + (if (direction != Direction.WEST && direction != Direction.EAST) 0.0F else 180.0F);
         val var22: MultiblockStructure = entity.getMultiblockStructure();
         val `$this$forEachIndexed$iv`: java.lang.Iterable = (var22 as FossilMultiblockStructure).getFossilInventory();
         var `index$iv`: Int = 0;

         for (Object item$iv : $this$forEachIndexed$iv) {
            val var16: Int = `index$iv`++;
            if (var16 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            val fossilStack: ItemStack = `item$iv` as ItemStack;
            matrices.m_85836_();
            var var24: Vec3;
            switch (direction == null ? -1 : FossilAnalyzerRenderer.WhenMappings.$EnumSwitchMapping$0[direction.ordinal()]) {
               case 1:
                  var24 = new Vec3(0.0, 0.0, 0.05);
                  break;
               case 2:
                  var24 = new Vec3(0.0, 0.0, -0.05);
                  break;
               case 3:
                  var24 = new Vec3(-0.05, 0.0, 0.0);
                  break;
               case 4:
                  var24 = new Vec3(0.05, 0.0, 0.0);
                  break;
               default:
                  var24 = Vec3.f_82478_;
            }

            matrices.m_85837_(0.5 + var24.f_82479_, 0.4 + (double)var16 * 0.1 + var24.f_82480_, 0.5 + var24.f_82481_);
            matrices.m_252781_(Axis.f_252436_.m_252977_(yRot));
            matrices.m_252781_(Axis.f_252403_.m_252977_(180.0F));
            matrices.m_252781_(Axis.f_252529_.m_252977_(90.0F));
            matrices.m_85841_(0.7F, 0.7F, 0.7F);
            Minecraft.m_91087_().m_91291_().m_269128_(fossilStack, ItemDisplayContext.NONE, light, overlay, matrices, vertexConsumers, entity.m_58904_(), 0);
            matrices.m_85849_();
         }
      }
   }
}
