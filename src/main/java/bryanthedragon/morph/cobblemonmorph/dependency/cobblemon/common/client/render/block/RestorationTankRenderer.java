package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.FossilModelRepository
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property

@SourceDebugExtension(["SMAP\nRestorationTankRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RestorationTankRenderer.kt\ncom/cobblemon/mod/common/client/render/block/RestorationTankRenderer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,202:1\n1855#2,2:203\n1855#2,2:205\n1855#2,2:207\n*S KotlinDebug\n*F\n+ 1 RestorationTankRenderer.kt\ncom/cobblemon/mod/common/client/render/block/RestorationTankRenderer\n*L\n58#1:203,2\n76#1:205,2\n123#1:207,2\n*E\n"])
public class RestorationTankRenderer(ctx: Context) : BlockEntityRenderer<RestorationTankBlockEntity> {
   public open fun render(
      entity: RestorationTankBlockEntity,
      tickDelta: Float,
      matrices: PoseStack,
      vertexConsumers: MultiBufferSource,
      light: Int,
      overlay: Int
   ) {
      if (entity.getMultiblockStructure() != null) {
         val var10000: MultiblockStructure = entity.getMultiblockStructure();
         val struct: FossilMultiblockStructure = var10000 as FossilMultiblockStructure;
         val connectionDir: Direction = (var10000 as FossilMultiblockStructure).getTankConnectorDirection();
         switch (connectionDir == null ? -1 : RestorationTankRenderer.WhenMappings.$EnumSwitchMapping$0[connectionDir.ordinal()]) {
            case 1:
               matrices.m_272245_(Axis.f_252436_.m_252977_(0.0F), 0.5F, 0.0F, 0.5F);
               break;
            case 2:
               matrices.m_272245_(Axis.f_252436_.m_252977_(270.0F), 0.5F, 0.0F, 0.5F);
               break;
            case 3:
               matrices.m_272245_(Axis.f_252436_.m_252977_(180.0F), 0.5F, 0.0F, 0.5F);
               break;
            case 4:
               matrices.m_272245_(Axis.f_252436_.m_252977_(90.0F), 0.5F, 0.0F, 0.5F);
            default:
         }

         val cutoutBuffer: VertexConsumer = vertexConsumers.m_6299_(RenderType.m_110463_());
         if (connectionDir != null) {
            matrices.m_85836_();
            val var25: BakedModel = CONNECTOR_MODEL;
            val var10003: Level = entity.m_58904_();

            val fillLevel: java.lang.Iterable;
            for (Object element$iv : fillLevel) {
               cutoutBuffer.m_85987_(matrices.m_85850_(), `$this$forEach$iv` as BakedQuad, 0.75F, 0.75F, 0.75F, light, OverlayTexture.f_118083_);
            }

            matrices.m_85849_();
         }

         val var19: Int = struct.getFillLevel();
         if (var19 != 0 || struct.getHasCreatedPokemon()) {
            if (struct.isRunning() or struct.getHasCreatedPokemon()) {
               this.renderFetus(entity, tickDelta, matrices, vertexConsumers, light, overlay);
            }

            matrices.m_85836_();
            val var20: VertexConsumer = vertexConsumers.m_6299_(RenderType.m_110466_());
            val var21: BakedModel = if (struct.isRunning())
               FLUID_MODELS.get(8)
               else
               (if (struct.getHasCreatedPokemon()) FLUID_MODELS.get(7) else FLUID_MODELS.get(RangesKt.coerceAtMost(var19, FLUID_MODELS.size() - 1) - 1));
            val var28: Level = entity.m_58904_();

            val var22: java.lang.Iterable;
            for (Object element$iv : var22) {
               val quad: BakedQuad = `element$iv` as BakedQuad;
               if (var20 != null) {
                  var20.m_85987_(matrices.m_85850_(), quad, 0.75F, 0.75F, 0.75F, light, OverlayTexture.f_118083_);
               }
            }

            matrices.m_85849_();
         }
      }
   }

   private fun renderFetus(
      entity: RestorationTankBlockEntity,
      tickDelta: Float,
      matrices: PoseStack,
      vertexConsumers: MultiBufferSource,
      light: Int,
      overlay: Int
   ) {
      val timeRemaining: MultiblockStructure = entity.getMultiblockStructure();
      val var10000: FossilMultiblockStructure = timeRemaining as? FossilMultiblockStructure;
      if ((timeRemaining as? FossilMultiblockStructure) != null) {
         val var39: Fossil = var10000.getResultingFossil();
         if (var39 != null) {
            val fossil: Fossil = var39;
            val var37: Int = var10000.getTimeRemaining();
            val var40: Level = entity.m_58904_();
            val var41: BlockState = if (var40 != null) var40.m_8055_(entity.m_58899_()) else null;
            if (var41 != null) {
               if (var41.m_60734_() == CobblemonBlocks.RESTORATION_TANK) {
                  val tankDirection: Direction = var41.m_61143_(HorizontalDirectionalBlock.f_54117_ as Property) as Direction;
                  val var42: MultiblockStructure = entity.getMultiblockStructure();
                  val connectionDir: Direction = (var42 as FossilMultiblockStructure).getTankConnectorDirection();
                  val aspects: java.util.Set = SetsKt.emptySet();
                  val state: FossilState = var10000.getFossilState();
                  state.updatePartialTicks(tickDelta);
                  val completionPercentage: Float = RangesKt.coerceIn((float)1 - (float)var37 / 14400.0F, 0.0F, 1.0F);
                  val fossilFetusModel: FossilModel = FossilModelRepository.INSTANCE.getPoser(var39.getIdentifier(), aspects);
                  val embryo1Scale: Float = (EMBRYO_CURVE_1.invoke(completionPercentage) as java.lang.Number).floatValue();
                  val embryo2Scale: Float = (EMBRYO_CURVE_2.invoke(completionPercentage) as java.lang.Number).floatValue();
                  val embryo3Scale: Float = (EMBRYO_CURVE_3.invoke(completionPercentage) as java.lang.Number).floatValue();

                  val var38: java.lang.Iterable;
                  for (Object element$iv : var38) {
                     val identifier: ResourceLocation = (`element$iv` as Pair).component1() as ResourceLocation;
                     val scale: Float = ((`element$iv` as Pair).component2() as java.lang.Number).floatValue();
                     val model: FossilModel = FossilModelRepository.INSTANCE.getPoser(identifier, aspects);
                     val texture: ResourceLocation = FossilModelRepository.INSTANCE.getTexture(identifier, aspects, state.getAnimationSeconds());
                     if (scale > 0.0F) {
                        val vertexConsumer: VertexConsumer = vertexConsumers.m_6299_(model.m_103119_(texture));
                        val pose: Pose = CollectionsKt.first(model.getPoses().values()) as Pose;
                        state.setCurrentModel(model);
                        state.setPose(pose.getPoseName());
                        state.setTimeEnteredPose(0.0F);
                        val scalex: Float = if (var37 == 0) model.getMaxScale() else scale * model.getMaxScale();
                        matrices.m_85836_();
                        matrices.m_85837_(0.5, 1.0 + (double)fossilFetusModel.getYTranslation(), 0.5);
                        matrices.m_252781_(Axis.f_252403_.m_252977_(180.0F));
                        if (tankDirection.m_175364_(net.minecraft.core.Direction.Axis.Y) === connectionDir) {
                           matrices.m_252781_(Axis.f_252436_.m_252977_(-90.0F));
                        } else if (tankDirection === connectionDir) {
                           matrices.m_252781_(Axis.f_252436_.m_252977_(180.0F));
                        } else if (tankDirection.m_122424_() != connectionDir) {
                           matrices.m_252781_(Axis.f_252436_.m_252977_(90.0F));
                        }

                        matrices.m_85836_();
                        matrices.m_85841_(scalex, scalex, scalex);
                        matrices.m_85837_(0.0, (double)model.getYGrowthPoint(), 0.0);
                        model.setupAnimStateful(null, state, 0.0F, 0.0F, state.getAnimationSeconds() * (float)20, 0.0F, 0.0F);
                        model.m_7695_(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                        model.withLayerContext(
                           vertexConsumers,
                           state,
                           FossilModelRepository.INSTANCE.getLayers(fossil.getIdentifier(), aspects),
                           (new Function0<Unit>(model, matrices, vertexConsumer, light) {
                              {
                                 super(0);
                                 this.$model = `$model`;
                                 this.$matrices = `$matrices`;
                                 this.$vertexConsumer = `$vertexConsumer`;
                                 this.$light = `$light`;
                              }

                              public final void invoke() {
                                 val var10000: FossilModel = this.$model;
                                 val var10001: PoseStack = this.$matrices;
                                 val var10002: VertexConsumer = this.$vertexConsumer;
                                 var10000.m_7695_(var10001, var10002, this.$light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
                              }
                           }) as () -> Unit
                        );
                        model.setDefault();
                        matrices.m_85849_();
                        matrices.m_85849_();
                     }
                  }
               }
            }
         }
      }
   }

   public companion object {
      public final val CONNECTOR_MODEL: BakedModel
      public final val EMBRYO_CURVE_1: (Float) -> Float
      public final val EMBRYO_CURVE_2: (Float) -> Float
      public final val EMBRYO_CURVE_3: (Float) -> Float
      public final val EMBRYO_IDENTIFIERS: List<ResourceLocation>
      public final val FLUID_MODELS: List<BakedModel>
      public final val FOSSIL_CURVE: (Float) -> Float
   }
}
