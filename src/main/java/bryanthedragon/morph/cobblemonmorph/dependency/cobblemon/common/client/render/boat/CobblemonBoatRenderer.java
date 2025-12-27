package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.boat

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Axis
import java.util.HashMap
import java.util.Locale
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.BoatModel
import net.minecraft.client.model.ChestBoatModel
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.render.entity.EntityRendererFactory.Context
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import org.joml.Quaternionf

@SourceDebugExtension(["SMAP\nCobblemonBoatRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBoatRenderer.kt\ncom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,90:1\n13579#2,2:91\n*S KotlinDebug\n*F\n+ 1 CobblemonBoatRenderer.kt\ncom/cobblemon/mod/common/client/render/boat/CobblemonBoatRenderer\n*L\n34#1:91,2\n*E\n"])
public class CobblemonBoatRenderer(ctx: Context, hasChest: Boolean) : EntityRenderer(ctx) {
   private final val boatModels: HashMap<CobblemonBoatType, Pair<ResourceLocation, BoatModel>>
   private final val hasChest: Boolean

   init {
      this.hasChest = hasChest;
      this.boatModels = new HashMap<>();
      this.f_114477_ = 0.8F;

      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         this.boatModels
            .put(
               (CobblemonBoatType)`element$iv`,
               TuplesKt.to(
                  CobblemonBoatRenderer.Companion.access$generateTextureIdentifier(Companion, (CobblemonBoatType)`element$iv`, this.hasChest),
                  CobblemonBoatRenderer.Companion.access$generateBoatModel(Companion, ctx, (CobblemonBoatType)`element$iv`, this.hasChest)
               )
            );
      }
   }

   public open fun getTexture(entity: CobblemonBoatEntity): ResourceLocation {
      val var10000: Any = this.boatModels.get(entity.getBoatType());
      return (var10000 as Pair).getFirst() as ResourceLocation;
   }

   public open fun render(entity: CobblemonBoatEntity, yaw: Float, tickDelta: Float, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int) {
      matrices.m_85836_();
      matrices.m_252880_(0.0F, 0.375F, 0.0F);
      matrices.m_252781_(Axis.f_252436_.m_252977_(180.0F - yaw));
      val h: Float = entity.m_38385_() - tickDelta;
      val j: Float = RangesKt.coerceAtLeast(entity.m_38384_() - tickDelta, 0.0F);
      if (h > 0.0F) {
         matrices.m_252781_(Axis.f_252529_.m_252977_(Mth.m_14031_(h) * h * j / 10.0F * (float)entity.m_38386_()));
      }

      if (!Mth.m_14033_(entity.m_38352_(tickDelta), 0.0F)) {
         matrices.m_252781_(new Quaternionf().setAngleAxis(entity.m_38352_(tickDelta) * (float) (Math.PI / 180.0), 1.0F, 0.0F, 1.0F));
      }

      val var10000: Any = this.boatModels.get(entity.getBoatType());
      val identifier: ResourceLocation = (var10000 as Pair).component1() as ResourceLocation;
      val entityModel: BoatModel = (var10000 as Pair).component2() as BoatModel;
      matrices.m_85841_(-1.0F, -1.0F, 1.0F);
      matrices.m_252781_(Axis.f_252436_.m_252977_(90.0F));
      entityModel.m_6973_(entity, tickDelta, 0.0F, -0.1F, 0.0F, 0.0F);
      entityModel.m_7695_(matrices, vertexConsumers.m_6299_(entityModel.m_103119_(identifier)), light, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      if (!entity.m_5842_()) {
         entityModel.m_102282_().m_104301_(matrices, vertexConsumers.m_6299_(RenderType.m_110478_()), light, OverlayTexture.f_118083_);
      }

      matrices.m_85849_();
      super.m_7392_(entity as Entity, yaw, tickDelta, matrices, vertexConsumers, light);
   }

   public companion object {
      private fun generateTextureIdentifier(type: CobblemonBoatType, hasChest: Boolean): ResourceLocation {
         val boatSubPath: java.lang.String = if (hasChest) "chest_boat" else "boat";
         val var10001: java.lang.String = type.name().toLowerCase(Locale.ROOT);
         return MiscUtilsKt.cobblemonResource("textures/entity/$boatSubPath/$var10001.png");
      }

      private fun generateBoatModel(ctx: Context, type: CobblemonBoatType, hasChest: Boolean): BoatModel {
         val modelPart: ModelPart = ctx.m_174023_(this.createBoatModelLayer$common(type, hasChest));
         return if (hasChest) (new ChestBoatModel(modelPart)) as BoatModel else new BoatModel(modelPart);
      }

      internal fun createBoatModelLayer(type: CobblemonBoatType, hasChest: Boolean): ModelLayerLocation {
         val boatSubPath: java.lang.String = if (hasChest) "chest_boat" else "boat";
         val var10001: java.lang.String = type.name().toLowerCase(Locale.ROOT);
         return new ModelLayerLocation(MiscUtilsKt.cobblemonResource("$boatSubPath/$var10001"), "main");
      }
   }
}
