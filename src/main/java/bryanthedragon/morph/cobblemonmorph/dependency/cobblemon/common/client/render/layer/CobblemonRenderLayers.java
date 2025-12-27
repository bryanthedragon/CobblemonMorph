package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat.Mode
import java.util.function.BiFunction
import java.util.function.Function
import net.minecraft.Util
import net.minecraft.client.render.RenderLayer.MultiPhase
import net.minecraft.client.renderer.RenderStateShard
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderStateShard.EmptyTextureStateShard
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard
import net.minecraft.client.renderer.RenderType.CompositeState
import net.minecraft.resources.ResourceLocation

public object CobblemonRenderLayers {
   public final val BERRY_LAYER: MultiPhase
   public final val ENTITY_CUTOUT: Function<ResourceLocation, RenderType>
   public final val ENTITY_TRANSLUCENT: BiFunction<ResourceLocation, Boolean, RenderType>

   @JvmStatic
   fun `ENTITY_TRANSLUCENT$lambda$1`(texture: ResourceLocation, affectsOutline: Boolean): RenderType {
      val var10000: CompositeState = CompositeState.m_110628_()
         .m_173292_(RenderStateShard.f_173066_)
         .m_173290_((new TextureStateShard(texture, false, false)) as EmptyTextureStateShard)
         .m_110685_(RenderStateShard.f_110139_)
         .m_110661_(RenderStateShard.f_110110_)
         .m_110671_(RenderStateShard.f_110152_)
         .m_110677_(RenderStateShard.f_110154_)
         .m_110691_(affectsOutline);
      return RenderType.m_173215_("entity_translucent", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, true, var10000) as RenderType;
   }

   @JvmStatic
   fun `ENTITY_CUTOUT$lambda$2`(texture: ResourceLocation): RenderType {
      return RenderType.m_173215_(
         "entity_cutout",
         DefaultVertexFormat.f_85812_,
         Mode.QUADS,
         256,
         true,
         false,
         CompositeState.m_110628_()
            .m_173292_(RenderStateShard.f_173113_)
            .m_173290_((new TextureStateShard(texture, false, false)) as EmptyTextureStateShard)
            .m_110685_(RenderStateShard.f_110134_)
            .m_110671_(RenderStateShard.f_110152_)
            .m_110677_(RenderStateShard.f_110154_)
            .m_110691_(true)
      ) as RenderType;
   }

   @JvmStatic
   fun {
      val `$this$BERRY_LAYER_u24lambda_u240`: CobblemonRenderLayers = INSTANCE;
      val var10000: BiFunction = Util.m_143821_(CobblemonRenderLayers::ENTITY_TRANSLUCENT$lambda$1);
      ENTITY_TRANSLUCENT = var10000;
      val var3: Function = Util.m_143827_(CobblemonRenderLayers::ENTITY_CUTOUT$lambda$2);
      ENTITY_CUTOUT = var3;
   }
}
