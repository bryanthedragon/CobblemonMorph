package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.DisplayCaseBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokemonItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Axis
import kotlin.jvm.functions.Function0
import net.minecraft.client.Minecraft
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.Direction
import net.minecraft.world.item.BannerItem
import net.minecraft.world.item.BedItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.Property
import org.joml.Vector4f

public class DisplayCaseRenderer(ctx: Context) : BlockEntityRenderer<DisplayCaseBlockEntity> {
   public final val coinPouchStack: ItemStack by LazyKt.lazy(<unrepresentable>.INSTANCE)
      public final get() {
         return this.coinPouchStack$delegate.getValue() as ItemStack;
      }


   public open fun render(entity: DisplayCaseBlockEntity, tickDelta: Float, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, overlay: Int) {
      val stack: ItemStack = if (entity.getStack().m_150930_(CobblemonItems.RELIC_COIN_POUCH as Item)) this.getCoinPouchStack() else entity.getStack();
      val var10000: Level = entity.m_58904_();
      if (var10000 != null) {
         val posType: DisplayCaseRenderer.PositioningType = DisplayCaseRenderer.Companion.access$getPositioningType(Companion, stack, var10000);
         val blockState: BlockState = if (entity.m_58904_() != null)
            entity.m_58900_()
            else
            CobblemonBlocks.DISPLAY_CASE
               .m_49966_()
               .m_61124_(DisplayCaseBlock.Companion.getITEM_DIRECTION() as Property, Direction.NORTH as java.lang.Comparable) as BlockState;
         val yRot: Float = if (posType === DisplayCaseRenderer.PositioningType.ITEM_MODEL)
            (blockState.m_61143_(DisplayCaseBlock.Companion.getITEM_DIRECTION() as Property) as Direction).m_122424_().m_122435_()
            else
            (blockState.m_61143_(DisplayCaseBlock.Companion.getITEM_DIRECTION() as Property) as Direction).m_122435_();
         if (stack.m_41720_() is PokemonItem) {
            this.renderPokemon(matrices, vertexConsumers, light, stack, yRot);
         } else {
            matrices.m_85836_();
            matrices.m_252880_(0.5F, 0.4F, 0.5F);
            matrices.m_85841_(posType.getScaleX(), posType.getScaleY(), posType.getScaleZ());
            matrices.m_252880_(posType.getTransX(), posType.getTransY(), posType.getTransZ());
            matrices.m_252781_(Axis.f_252436_.m_252977_(-yRot));
            matrices.m_252781_(Axis.f_252436_.m_252977_(posType.getRotY()));
            Minecraft.m_91087_().m_91291_().m_269128_(stack, ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.m_58904_(), 0);
            matrices.m_85849_();
         }
      }
   }

   private fun renderPokemon(matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, stack: ItemStack, yRot: Float) {
      val model: Item = stack.m_41720_();
      val var10000: PokemonItem = model as? PokemonItem;
      if ((model as? PokemonItem) != null) {
         val var14: Pokemon = var10000.asPokemon(stack);
         if (var14 != null) {
            val var13: PokemonPoseableModel = PokemonModelRepository.INSTANCE.getPoser(var14.getSpecies().getResourceIdentifier(), var14.getAspects());
            val renderLayer: RenderType = var13.m_103119_(
               PokemonModelRepository.INSTANCE.getTexture(var14.getSpecies().getResourceIdentifier(), var14.getAspects(), 0.0F)
            );
            val tint: Vector4f = var10000.tint(stack);
            val var15: VertexConsumer = vertexConsumers.m_6299_(renderLayer);
            matrices.m_85836_();
            matrices.m_85841_(1.0F, -1.0F, -1.0F);
            matrices.m_252880_(0.5F, -0.69F, -0.5F);
            matrices.m_85841_(0.25F, 0.25F, 0.25F);
            matrices.m_252781_(Axis.f_252436_.m_252977_(yRot));
            PoseableEntityModel.setupAnimStateless$default(var13, PoseType.PROFILE, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 62, null);
            var13.withLayerContext(
               vertexConsumers,
               null,
               PokemonModelRepository.INSTANCE.getLayers(var14.getSpecies().getResourceIdentifier(), var14.getAspects()),
               (
                  new Function0<Unit>(var13, matrices, var15, light, tint) {
                     {
                        super(0);
                        this.$model = `$model`;
                        this.$matrices = `$matrices`;
                        this.$vertexConsumer = `$vertexConsumer`;
                        this.$light = `$light`;
                        this.$tint = `$tint`;
                     }

                     public final void invoke() {
                        this.$model
                           .m_7695_(
                              this.$matrices,
                              this.$vertexConsumer,
                              this.$light,
                              OverlayTexture.f_118083_,
                              this.$tint.x,
                              this.$tint.y,
                              this.$tint.z,
                              this.$tint.w
                           );
                     }
                  }
               ) as () -> Unit
            );
            matrices.m_85849_();
         }
      }
   }

   @JvmStatic
   fun {
      val var0: Array<Item> = new Item[7];
      var var10002: Item = Items.f_42678_;
      var0[0] = var10002;
      var10002 = Items.f_42679_;
      var0[1] = var10002;
      var10002 = Items.f_42681_;
      var0[2] = var10002;
      var10002 = Items.f_260451_;
      var0[3] = var10002;
      var10002 = Items.f_42680_;
      var0[4] = var10002;
      var10002 = Items.f_42683_;
      var0[5] = var10002;
      var10002 = Items.f_42682_;
      var0[6] = var10002;
      mobHeads = CollectionsKt.listOf(var0);
   }

   public companion object {
      private final val mobHeads: List<Item>

      private fun getPositioningType(stack: ItemStack, world: Level): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.DisplayCaseRenderer.PositioningType {
         return if (DisplayCaseRenderer.access$getMobHeads$cp().contains(stack.m_41720_()))
            DisplayCaseRenderer.PositioningType.MOB_HEAD
            else
            (
               if (stack.m_41720_() is BedItem)
                  DisplayCaseRenderer.PositioningType.BED
                  else
                  (
                     if (stack.m_41720_() is BannerItem)
                        DisplayCaseRenderer.PositioningType.BANNER
                        else
                        (
                           if (stack.m_41720_() is PokeBallItem)
                              DisplayCaseRenderer.PositioningType.POKE_BALL
                              else
                              (
                                 if (stack.m_41720_() == CobblemonItems.RELIC_COIN_POUCH)
                                    DisplayCaseRenderer.PositioningType.COIN_POUCH
                                    else
                                    (
                                       if (stack.m_41720_() == CobblemonItems.PASTURE)
                                          DisplayCaseRenderer.PositioningType.PASTURE
                                          else
                                          (
                                             if (stack.m_41720_() == CobblemonItems.POKEMON_MODEL)
                                                DisplayCaseRenderer.PositioningType.ITEM_MODEL
                                                else
                                                (
                                                   if (stack.m_41720_() == Items.f_42740_)
                                                      DisplayCaseRenderer.PositioningType.SHIELD
                                                      else
                                                      (
                                                         if (stack.m_41720_() == Items.f_271478_)
                                                            DisplayCaseRenderer.PositioningType.MOB_HEAD
                                                            else
                                                            (
                                                               if (Minecraft.m_91087_().m_91291_().m_174264_(stack, world, null, 0).m_7539_())
                                                                  DisplayCaseRenderer.PositioningType.BLOCK_MODEL
                                                                  else
                                                                  DisplayCaseRenderer.PositioningType.ITEM_MODEL
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            );
      }
   }

   private enum class PositioningType(scaleX: Float, scaleY: Float, scaleZ: Float, transX: Float, transY: Float, transZ: Float, rotY: Float = 0.0F) {
      POKE_BALL(1.0F, 1.0F, 1.0F, 0.0F, 0.04F, 0.0F, 0.0F, 64, null),
      BLOCK_MODEL(1.0F, 1.0F, 1.0F, 0.0F, -0.15F, 0.0F, 0.0F, 64, null),
      ITEM_MODEL(1.0F, 1.0F, 1.0F, 0.0F, 0.04F, 0.0F, 0.0F, 64, null),
      BED(1.0F, 1.0F, 1.0F, 0.0F, -0.02F, 0.0F, 0.0F, 64, null),
      BANNER(1.0F, 1.0F, 1.0F, 0.0F, -0.02F, 0.0F, 180.0F),
      MOB_HEAD(1.0F, 1.0F, 1.0F, 0.0F, -0.025F, 0.0F, 180.0F),
      SHIELD(1.0F, 1.0F, 1.0F, 0.0F, -0.045F, 0.0F, 180.0F),
      PASTURE(1.0F, 1.0F, 1.0F, 0.0F, 0.0375F, 0.0F, 0.0F, 64, null),
      COIN_POUCH(1.0F, 1.0F, 1.0F, 0.0F, 0.415F, 0.0F, 0.0F, 64, null)
      public final val rotY: Float
      public final val scaleX: Float
      public final val scaleY: Float
      public final val scaleZ: Float
      public final val transX: Float
      public final val transY: Float
      public final val transZ: Float

      init {
         this.scaleX = scaleX;
         this.scaleY = scaleY;
         this.scaleZ = scaleZ;
         this.transX = transX;
         this.transY = transY;
         this.transZ = transZ;
         this.rotY = rotY;
      }
   }
}
