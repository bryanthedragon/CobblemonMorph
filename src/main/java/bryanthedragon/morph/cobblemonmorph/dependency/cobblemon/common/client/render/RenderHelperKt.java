/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.client.renderer.texture.TextureAtlas
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix3f
 *  org.joml.Matrix4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0005\u001am\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001ai\u0010 \u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u00172\b\b\u0002\u0010\u0018\u001a\u00020\u00062\b\b\u0002\u0010\u0019\u001a\u00020\u00062\b\b\u0002\u0010\u001a\u001a\u00020\u00172\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u001d2\b\b\u0002\u0010\u001f\u001a\u00020\u001d\u00a2\u0006\u0004\b \u0010!\u001a\u008d\u0001\u0010 \u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00132\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\"2\u0006\u0010\u0016\u001a\u00020$2\u0006\u0010\f\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\u00172\b\b\u0002\u0010%\u001a\u00020\u00062\b\b\u0002\u0010\u001a\u001a\u00020\u00172\b\b\u0002\u0010&\u001a\u00020\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u001b2\b\b\u0002\u0010\u001e\u001a\u00020\u001d2\b\b\u0002\u0010\u001f\u001a\u00020\u001d2\n\b\u0002\u0010'\u001a\u0004\u0018\u00010\u001b2\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u001b\u00a2\u0006\u0004\b \u0010)\u001a5\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060-2\u0006\u0010*\u001a\u00020\u00062\b\b\u0002\u0010+\u001a\u00020\u00062\b\b\u0002\u0010,\u001a\u00020\u0006\u00a2\u0006\u0004\b.\u0010/\u001a\u0081\u0001\u0010<\u001a\u00020\u00102\u0006\u00101\u001a\u0002002\u0006\u0010\u0005\u001a\u0002022\b\b\u0002\u00103\u001a\u00020\"2\u0006\u00104\u001a\u00020\u00062\u0006\u00106\u001a\u0002052\b\b\u0002\u00107\u001a\u00020\u00062\u0006\u00108\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u00109\u001a\u00020\u00062\u0006\u0010:\u001a\u00020\u00062\u0006\u0010;\u001a\u00020\u0006\u00a2\u0006\u0004\b<\u0010=\u001a5\u0010A\u001a\u00020\u00102\u0006\u0010>\u001a\u00020\"2\u0006\u0010\f\u001a\u00020?2\u0006\u0010\u000b\u001a\u00020?2\u0006\u00108\u001a\u00020?2\u0006\u0010@\u001a\u00020?\u00a2\u0006\u0004\bA\u0010B\u001a\u008d\u0001\u0010N\u001a\u00020\u00102\u0006\u00101\u001a\u0002002\u0006\u0010C\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00062\u0006\u0010F\u001a\u00020\u00062\u0006\u0010G\u001a\u00020\u00062\u0006\u0010H\u001a\u00020\u00062\u0006\u0010I\u001a\u00020\u00062\u0006\u0010J\u001a\u00020\u00062\u0006\u0010K\u001a\u00020\u00062\u0006\u0010L\u001a\u00020\u00062\u0006\u0010M\u001a\u00020\u0006\u00a2\u0006\u0004\bN\u0010O\u001au\u0010T\u001a\u00020\u00102\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010D\u001a\u00020\u00062\u0006\u0010E\u001a\u00020\u00062\u0006\u0010P\u001a\u00020\u00062\u0006\u0010Q\u001a\u00020\u00062\u0006\u0010R\u001a\u00020\u00062\u0006\u0010S\u001a\u00020\u0006\u00a2\u0006\u0004\bT\u0010U\u001aE\u0010Y\u001a\u00020\u00102\u0006\u0010W\u001a\u00020V2\u0006\u0010\f\u001a\u00020?2\u0006\u0010\u000b\u001a\u00020?2\b\b\u0002\u0010%\u001a\u00020?2\b\b\u0002\u0010X\u001a\u00020\u00062\n\b\u0002\u00101\u001a\u0004\u0018\u000100\u00a2\u0006\u0004\bY\u0010Z\u00a8\u0006["}, d2={"Lorg/joml/Matrix4f;", "matrixPos", "Lorg/joml/Matrix3f;", "matrixNormal", "Lcom/mojang/blaze3d/vertex/VertexConsumer;", "buffer", "", "red", "green", "blue", "alpha", "y", "x", "z", "texU", "texV", "", "addVertex", "(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFF)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "Lnet/minecraft/util/FormattedCharSequence;", "text", "", "scaleX", "scaleY", "opacity", "", "colour", "", "centered", "shadow", "drawScaledText", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/util/FormattedCharSequence;Ljava/lang/Number;Ljava/lang/Number;FFLjava/lang/Number;IZZ)V", "Lnet/minecraft/resources/ResourceLocation;", "font", "Lnet/minecraft/network/chat/MutableComponent;", "scale", "maxCharacterWidth", "pMouseX", "pMouseY", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/Number;Ljava/lang/Number;FLjava/lang/Number;IIZZLjava/lang/Integer;Ljava/lang/Integer;)V", "ratio", "yellowRatio", "redRatio", "Lkotlin/Pair;", "getDepletableRedGreen", "(FFF)Lkotlin/Pair;", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "Lnet/minecraft/client/renderer/MultiBufferSource;", "textureLocation", "partialTicks", "", "totalLevelTime", "yOffset", "height", "beamRadius", "glowRadius", "glowAlpha", "renderBeaconBeam", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/resources/ResourceLocation;FJFFFFFFFFF)V", "texture", "", "width", "renderImage", "(Lnet/minecraft/resources/ResourceLocation;DDDD)V", "vertexBuffer", "yMin", "yMax", "p_112164_", "p_112165_", "p_112166_", "p_112167_", "p_112168_", "p_112169_", "p_112170_", "p_112171_", "renderPart", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFFFFFF)V", "x1", "z1", "x2", "z2", "renderQuad", "(Lorg/joml/Matrix4f;Lorg/joml/Matrix3f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFF)V", "Lnet/minecraft/world/item/ItemStack;", "itemStack", "zTranslation", "renderScaledGuiItemIcon", "(Lnet/minecraft/world/item/ItemStack;DDDFLcom/mojang/blaze3d/vertex/PoseStack;)V", "common"})
public final class RenderHelperKt {
    public static final void renderImage(@NotNull ResourceLocation texture, double x, double y, double height, double width) {
        Intrinsics.checkNotNullParameter((Object)texture, (String)"texture");
        TextureManager textureManager = Minecraft.m_91087_().m_91097_();
        BufferBuilder buffer = Tesselator.m_85913_().m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        textureManager.m_174784_(texture);
        buffer.m_5483_(x, y + height, 0.0).m_7421_(0.0f, 1.0f).m_5752_();
        buffer.m_5483_(x + width, y + height, 0.0).m_7421_(1.0f, 1.0f).m_5752_();
        buffer.m_5483_(x + width, y, 0.0).m_7421_(1.0f, 0.0f).m_5752_();
        buffer.m_5483_(x, y, 0.0).m_7421_(0.0f, 0.0f).m_5752_();
        Tesselator.m_85913_().m_85914_();
    }

    public static final void renderScaledGuiItemIcon(@NotNull ItemStack itemStack, double x, double y, double scale, float zTranslation, @Nullable PoseStack matrixStack) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)itemStack, (String)"itemStack");
        ItemRenderer itemRenderer = Minecraft.m_91087_().m_91291_();
        TextureManager textureManager = Minecraft.m_91087_().m_91097_();
        BakedModel model = itemRenderer.m_174264_(itemStack, null, null, 0);
        textureManager.m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
        RenderSystem.setShaderTexture((int)0, (ResourceLocation)TextureAtlas.f_118259_);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        PoseStack poseStack = matrixStack;
        if (poseStack == null) {
            poseStack = RenderSystem.getModelViewStack();
        }
        PoseStack modelViewStack = poseStack;
        modelViewStack.m_85836_();
        modelViewStack.m_85837_(x, y, (double)(zTranslation + 0.0f));
        modelViewStack.m_85837_(8.0 * scale, 8.0 * scale, 0.0);
        modelViewStack.m_85841_(1.0f, -1.0f, 1.0f);
        modelViewStack.m_85841_(16.0f * (float)scale, 16.0f * (float)scale, 16.0f * (float)scale);
        RenderSystem.applyModelViewMatrix();
        PoseStack poseStack2 = matrixStack;
        if (poseStack2 == null) {
            poseStack2 = new PoseStack();
        }
        PoseStack stack = poseStack2;
        MultiBufferSource.BufferSource immediate = Minecraft.m_91087_().m_91269_().m_110104_();
        boolean bl2 = bl = !model.m_7547_();
        if (bl) {
            Lighting.m_84930_();
        }
        itemRenderer.m_115143_(itemStack, ItemDisplayContext.GUI, false, stack, (MultiBufferSource)immediate, 0xF000F0, OverlayTexture.f_118083_, model);
        immediate.m_109911_();
        RenderSystem.enableDepthTest();
        if (bl) {
            Lighting.m_84931_();
        }
        modelViewStack.m_85849_();
        RenderSystem.applyModelViewMatrix();
    }

    public static /* synthetic */ void renderScaledGuiItemIcon$default(ItemStack itemStack, double d, double d2, double d3, float f, PoseStack poseStack, int n, Object object) {
        if ((n & 8) != 0) {
            d3 = 1.0;
        }
        if ((n & 0x10) != 0) {
            f = 100.0f;
        }
        if ((n & 0x20) != 0) {
            poseStack = null;
        }
        RenderHelperKt.renderScaledGuiItemIcon(itemStack, d, d2, d3, f, poseStack);
    }

    @NotNull
    public static final Pair<Float, Float> getDepletableRedGreen(float ratio, float yellowRatio, float redRatio) {
        Double r;
        int m = -2;
        Number number = r = ratio > redRatio ? (Number)Float.valueOf((float)m * ratio - (float)m) : (Number)1.0;
        Double g = ratio > yellowRatio ? (Number)1.0 : (Number)(ratio > redRatio ? (Number)Float.valueOf(ratio * 1.0f / yellowRatio) : (Number)0.0);
        return TuplesKt.to((Object)Float.valueOf(((Number)r).floatValue()), (Object)Float.valueOf(((Number)g).floatValue()));
    }

    public static /* synthetic */ Pair getDepletableRedGreen$default(float f, float f2, float f3, int n, Object object) {
        if ((n & 2) != 0) {
            f2 = 0.5f;
        }
        if ((n & 4) != 0) {
            f3 = 0.2f;
        }
        return RenderHelperKt.getDepletableRedGreen(f, f2, f3);
    }

    public static final void drawScaledText(@NotNull GuiGraphics context, @Nullable ResourceLocation font, @NotNull MutableComponent text, @NotNull Number x, @NotNull Number y, float scale, @NotNull Number opacity, int maxCharacterWidth, int colour, boolean centered, boolean shadow, @Nullable Integer pMouseX, @Nullable Integer pMouseY) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)opacity, (String)"opacity");
        if (opacity.floatValue() < 0.05f) {
            return;
        }
        ResourceLocation resourceLocation = font;
        int textWidth = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)(resourceLocation != null ? TextKt.font(text, resourceLocation) : text));
        float extraScale = textWidth < maxCharacterWidth ? 1.0f : (float)maxCharacterWidth / (float)textWidth;
        int fontHeight = font == null ? 5 : 6;
        PoseStack matrices = context.m_280168_();
        matrices.m_85836_();
        matrices.m_85841_(scale * extraScale, scale * extraScale, 1.0f);
        Integer n = pMouseX;
        Integer n2 = pMouseY;
        boolean isHovered = GuiUtilsKt.drawText(context, font, text, Float.valueOf(x.floatValue() / (scale * extraScale)), Float.valueOf(y.floatValue() / (scale * extraScale) + (1.0f - extraScale) * (float)fontHeight * scale), centered, colour, shadow, n != null ? Float.valueOf((float)n.intValue() / (scale * extraScale)) : null, n2 != null ? Float.valueOf((float)n2.intValue() / (scale * extraScale) + (1.0f - extraScale) * (float)fontHeight * scale) : null);
        matrices.m_85849_();
        if (isHovered) {
            Font font2 = Minecraft.m_91087_().f_91062_;
            Style style = text.m_7383_();
            Integer n3 = pMouseX;
            Intrinsics.checkNotNull((Object)n3);
            int n4 = n3;
            Integer n5 = pMouseY;
            Intrinsics.checkNotNull((Object)n5);
            context.m_280304_(font2, style, n4, n5.intValue());
        }
    }

    public static /* synthetic */ void drawScaledText$default(GuiGraphics guiGraphics, ResourceLocation resourceLocation, MutableComponent mutableComponent, Number number, Number number2, float f, Number number3, int n, int n2, boolean bl, boolean bl2, Integer n3, Integer n4, int n5, Object object) {
        if ((n5 & 2) != 0) {
            resourceLocation = null;
        }
        if ((n5 & 0x20) != 0) {
            f = 1.0f;
        }
        if ((n5 & 0x40) != 0) {
            number3 = Float.valueOf(1.0f);
        }
        if ((n5 & 0x80) != 0) {
            n = Integer.MAX_VALUE;
        }
        if ((n5 & 0x100) != 0) {
            n2 = 0xFFFFFF + ((int)(number3.floatValue() * (float)255) << 24);
        }
        if ((n5 & 0x200) != 0) {
            bl = false;
        }
        if ((n5 & 0x400) != 0) {
            bl2 = false;
        }
        if ((n5 & 0x800) != 0) {
            n3 = null;
        }
        if ((n5 & 0x1000) != 0) {
            n4 = null;
        }
        RenderHelperKt.drawScaledText(guiGraphics, resourceLocation, mutableComponent, number, number2, f, number3, n, n2, bl, bl2, n3, n4);
    }

    public static final void drawScaledText(@NotNull GuiGraphics context, @NotNull FormattedCharSequence text, @NotNull Number x, @NotNull Number y, float scaleX, float scaleY, @NotNull Number opacity, int colour, boolean centered, boolean shadow) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)opacity, (String)"opacity");
        if (opacity.floatValue() < 0.05f) {
            return;
        }
        PoseStack matrixStack = context.m_280168_();
        matrixStack.m_85836_();
        matrixStack.m_85841_(scaleX, scaleY, 1.0f);
        GuiUtilsKt.drawText(context, text, Float.valueOf(x.floatValue() / scaleX), Float.valueOf(y.floatValue() / scaleY), centered, colour, shadow);
        matrixStack.m_85849_();
    }

    public static /* synthetic */ void drawScaledText$default(GuiGraphics guiGraphics, FormattedCharSequence formattedCharSequence, Number number, Number number2, float f, float f2, Number number3, int n, boolean bl, boolean bl2, int n2, Object object) {
        if ((n2 & 0x10) != 0) {
            f = 1.0f;
        }
        if ((n2 & 0x20) != 0) {
            f2 = 1.0f;
        }
        if ((n2 & 0x40) != 0) {
            number3 = Float.valueOf(1.0f);
        }
        if ((n2 & 0x80) != 0) {
            n = 0xFFFFFF + ((int)(number3.floatValue() * (float)255) << 24);
        }
        if ((n2 & 0x100) != 0) {
            bl = false;
        }
        if ((n2 & 0x200) != 0) {
            bl2 = false;
        }
        RenderHelperKt.drawScaledText(guiGraphics, formattedCharSequence, number, number2, f, f2, number3, n, bl, bl2);
    }

    public static final void renderBeaconBeam(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, @NotNull ResourceLocation textureLocation, float partialTicks, long totalLevelTime, float yOffset, float height, float red, float green, float blue, float alpha, float beamRadius, float glowRadius, float glowAlpha) {
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter((Object)textureLocation, (String)"textureLocation");
        float i = yOffset + height;
        float beamRotation = (float)Math.floorMod((long)totalLevelTime, (int)40) + partialTicks;
        matrixStack.m_85836_();
        matrixStack.m_252781_(Axis.f_252436_.m_252977_(beamRotation * 2.25f - 45.0f));
        float f9 = -beamRadius;
        float f12 = -beamRadius;
        VertexConsumer vertexConsumer = buffer.m_6299_(RenderType.m_110460_((ResourceLocation)textureLocation, (boolean)false));
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"buffer.getBuffer(RenderL\u2026(textureLocation, false))");
        RenderHelperKt.renderPart(matrixStack, vertexConsumer, red, green, blue, alpha, yOffset, i, 0.0f, beamRadius, beamRadius, 0.0f, f9, 0.0f, 0.0f, f12);
        matrixStack.m_85849_();
        float f6 = -glowRadius;
        float f7 = -glowRadius;
        float f8 = -glowRadius;
        f9 = -glowRadius;
        VertexConsumer vertexConsumer2 = buffer.m_6299_(RenderType.m_110460_((ResourceLocation)textureLocation, (boolean)true));
        Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer2, (String)"buffer.getBuffer(RenderL\u2026m(textureLocation, true))");
        RenderHelperKt.renderPart(matrixStack, vertexConsumer2, red, green, blue, glowAlpha, yOffset, i, f6, f7, glowRadius, f8, f9, glowRadius, glowRadius, glowRadius);
    }

    public static /* synthetic */ void renderBeaconBeam$default(PoseStack poseStack, MultiBufferSource multiBufferSource, ResourceLocation resourceLocation, float f, long l, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, int n, Object object) {
        if ((n & 4) != 0) {
            resourceLocation = CobblemonResources.INSTANCE.getPHASE_BEAM();
        }
        if ((n & 0x20) != 0) {
            f2 = 0.0f;
        }
        RenderHelperKt.renderBeaconBeam(poseStack, multiBufferSource, resourceLocation, f, l, f2, f3, f4, f5, f6, f7, f8, f9, f10);
    }

    public static final void renderPart(@NotNull PoseStack matrixStack, @NotNull VertexConsumer vertexBuffer, float red, float green, float blue, float alpha, float yMin, float yMax, float p_112164_, float p_112165_, float p_112166_, float p_112167_, float p_112168_, float p_112169_, float p_112170_, float p_112171_) {
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)vertexBuffer, (String)"vertexBuffer");
        PoseStack.Pose pose = matrixStack.m_85850_();
        Matrix4f matrix4f = pose.m_252922_();
        Matrix3f matrix3f = pose.m_252943_();
        Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrix4f");
        Intrinsics.checkNotNullExpressionValue((Object)matrix3f, (String)"matrix3f");
        RenderHelperKt.renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112164_, p_112165_, p_112166_, p_112167_);
        RenderHelperKt.renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112170_, p_112171_, p_112168_, p_112169_);
        RenderHelperKt.renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112166_, p_112167_, p_112170_, p_112171_);
        RenderHelperKt.renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112168_, p_112169_, p_112164_, p_112165_);
    }

    public static final void renderQuad(@NotNull Matrix4f matrixPos, @NotNull Matrix3f matrixNormal, @NotNull VertexConsumer buffer, float red, float green, float blue, float alpha, float yMin, float yMax, float x1, float z1, float x2, float z2) {
        Intrinsics.checkNotNullParameter((Object)matrixPos, (String)"matrixPos");
        Intrinsics.checkNotNullParameter((Object)matrixNormal, (String)"matrixNormal");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        RenderHelperKt.addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMax, x1, z1, 1.0f, 0.0f);
        RenderHelperKt.addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMin, x1, z1, 1.0f, 1.0f);
        RenderHelperKt.addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMin, x2, z2, 0.0f, 1.0f);
        RenderHelperKt.addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMax, x2, z2, 0.0f, 0.0f);
    }

    public static final void addVertex(@NotNull Matrix4f matrixPos, @NotNull Matrix3f matrixNormal, @NotNull VertexConsumer buffer, float red, float green, float blue, float alpha, float y, float x, float z, float texU, float texV) {
        Intrinsics.checkNotNullParameter((Object)matrixPos, (String)"matrixPos");
        Intrinsics.checkNotNullParameter((Object)matrixNormal, (String)"matrixNormal");
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_252986_(matrixPos, x, y, z).m_85950_(red, green, blue, alpha).m_7421_(texU, texV).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_252939_(matrixNormal, 0.0f, 1.0f, 0.0f).m_5752_();
    }
}

