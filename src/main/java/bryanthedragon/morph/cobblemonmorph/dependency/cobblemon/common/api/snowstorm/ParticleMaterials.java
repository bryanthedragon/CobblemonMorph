/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMaterials;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader.CobblemonShaders;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0004\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParticleMaterials;", "", "Lnet/minecraft/client/particle/ParticleRenderType;", "ADD", "Lnet/minecraft/client/particle/ParticleRenderType;", "getADD", "()Lnet/minecraft/client/particle/ParticleRenderType;", "ALPHA", "getALPHA", "BLEND", "getBLEND", "OPAQUE", "getOPAQUE", "<init>", "()V", "common"})
public final class ParticleMaterials {
    @NotNull
    public static final ParticleMaterials INSTANCE = new ParticleMaterials();
    @NotNull
    private static final ParticleRenderType ALPHA = new ParticleRenderType(){

        public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
            Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
            Intrinsics.checkNotNullParameter((Object)textureManager, (String)"textureManager");
            RenderSystem.disableBlend();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.setShader(ALPHA.1::begin$lambda$0);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.f_118260_);
            builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        public void m_6294_(@NotNull Tesselator tessellator) {
            Intrinsics.checkNotNullParameter((Object)tessellator, (String)"tessellator");
            tessellator.m_85914_();
        }

        @NotNull
        public String toString() {
            return "ALPHA";
        }

        private static final ShaderInstance begin$lambda$0() {
            return CobblemonShaders.INSTANCE.getPARTICLE_CUTOUT();
        }
    };
    @NotNull
    private static final ParticleRenderType ADD = new ParticleRenderType(){

        public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
            Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
            Intrinsics.checkNotNullParameter((Object)textureManager, (String)"textureManager");
            RenderSystem.enableBlend();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.setShader(ADD.1::begin$lambda$0);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.f_118260_);
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        public void m_6294_(@NotNull Tesselator tessellator) {
            Intrinsics.checkNotNullParameter((Object)tessellator, (String)"tessellator");
            tessellator.m_85914_();
        }

        @NotNull
        public String toString() {
            return "ADD";
        }

        private static final ShaderInstance begin$lambda$0() {
            return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
        }
    };
    @NotNull
    private static final ParticleRenderType BLEND = new ParticleRenderType(){

        public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
            Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
            Intrinsics.checkNotNullParameter((Object)textureManager, (String)"textureManager");
            RenderSystem.enableBlend();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.setShader(BLEND.1::begin$lambda$0);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.f_118260_);
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.DST_COLOR, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        public void m_6294_(@NotNull Tesselator tessellator) {
            Intrinsics.checkNotNullParameter((Object)tessellator, (String)"tessellator");
            tessellator.m_85914_();
        }

        @NotNull
        public String toString() {
            return "BLEND";
        }

        private static final ShaderInstance begin$lambda$0() {
            return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
        }
    };
    @NotNull
    private static final ParticleRenderType OPAQUE = new ParticleRenderType(){

        public void m_6505_(@NotNull BufferBuilder builder, @NotNull TextureManager textureManager) {
            Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
            Intrinsics.checkNotNullParameter((Object)textureManager, (String)"textureManager");
            RenderSystem.enableBlend();
            RenderSystem.depthMask((boolean)true);
            RenderSystem.setShader(OPAQUE.1::begin$lambda$0);
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.f_118260_);
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
            builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85813_);
        }

        public void m_6294_(@NotNull Tesselator tessellator) {
            Intrinsics.checkNotNullParameter((Object)tessellator, (String)"tessellator");
            tessellator.m_85914_();
        }

        @NotNull
        public String toString() {
            return "OPAQUE";
        }

        private static final ShaderInstance begin$lambda$0() {
            return CobblemonShaders.INSTANCE.getPARTICLE_BLEND();
        }
    };

    private ParticleMaterials() {
    }

    @NotNull
    public final ParticleRenderType getALPHA() {
        return ALPHA;
    }

    @NotNull
    public final ParticleRenderType getADD() {
        return ADD;
    }

    @NotNull
    public final ParticleRenderType getBLEND() {
        return BLEND;
    }

    @NotNull
    public final ParticleRenderType getOPAQUE() {
        return OPAQUE;
    }
}

