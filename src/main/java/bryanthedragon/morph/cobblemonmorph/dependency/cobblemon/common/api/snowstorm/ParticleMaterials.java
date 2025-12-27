package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader.CobblemonShaders
import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat.Mode
import net.minecraft.client.particle.ParticleRenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.renderer.texture.TextureManager
import org.jetbrains.annotations.NotNull

public object ParticleMaterials {
   public final val ADD: ParticleRenderType = (new ParticleRenderType() {
      public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
         RenderSystem.enableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.setShader(<unrepresentable>::begin$lambda$0);
         RenderSystem.setShaderTexture(0, TextureAtlas.f_118260_);
         RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
         builder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(@NotNull Tesselator tessellator) {
         tessellator.m_85914_();
      }

      @NotNull
      @Override
      public java.lang.String toString() {
         return "ADD";
      }

      private static final ShaderInstance begin$lambda$0() {
         return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
      }
   }) as ParticleRenderType

   public final val ALPHA: ParticleRenderType = (new ParticleRenderType() {
      public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.setShader(<unrepresentable>::begin$lambda$0);
         RenderSystem.setShaderTexture(0, TextureAtlas.f_118260_);
         builder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(@NotNull Tesselator tessellator) {
         tessellator.m_85914_();
      }

      @NotNull
      @Override
      public java.lang.String toString() {
         return "ALPHA";
      }

      private static final ShaderInstance begin$lambda$0() {
         return CobblemonShaders.INSTANCE.getPARTICLE_CUTOUT();
      }
   }) as ParticleRenderType

   public final val BLEND: ParticleRenderType = (new ParticleRenderType() {
      public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
         RenderSystem.enableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.setShader(<unrepresentable>::begin$lambda$0);
         RenderSystem.setShaderTexture(0, TextureAtlas.f_118260_);
         RenderSystem.blendFunc(SourceFactor.DST_COLOR, DestFactor.ZERO);
         builder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(@NotNull Tesselator tessellator) {
         tessellator.m_85914_();
      }

      @NotNull
      @Override
      public java.lang.String toString() {
         return "BLEND";
      }

      private static final ShaderInstance begin$lambda$0() {
         return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
      }
   }) as ParticleRenderType

   public final val OPAQUE: ParticleRenderType = (new ParticleRenderType() {
      public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
         RenderSystem.enableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.setShader(<unrepresentable>::begin$lambda$0);
         RenderSystem.setShaderTexture(0, TextureAtlas.f_118260_);
         RenderSystem.blendFunc(SourceFactor.ONE, DestFactor.ONE);
         builder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      }

      public void m_6294_(@NotNull Tesselator tessellator) {
         tessellator.m_85914_();
      }

      @NotNull
      @Override
      public java.lang.String toString() {
         return "OPAQUE";
      }

      private static final ShaderInstance begin$lambda$0() {
         return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
      }
   }) as ParticleRenderType
}
