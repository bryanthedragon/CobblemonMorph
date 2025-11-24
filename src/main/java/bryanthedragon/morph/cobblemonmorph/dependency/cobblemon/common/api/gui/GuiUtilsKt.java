/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.BufferBuilder$RenderedBuffer
 *  com.mojang.blaze3d.vertex.BufferUploader
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u00b3\u0001\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0001\u001a\u00020\u00002\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\u00042\b\b\u0002\u0010\t\u001a\u00020\u00042\b\b\u0002\u0010\n\u001a\u00020\u00042\b\b\u0002\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u00042\b\b\u0002\u0010\u000e\u001a\u00020\u00042\b\b\u0002\u0010\u000f\u001a\u00020\u00042\b\b\u0002\u0010\u0010\u001a\u00020\u00042\b\b\u0002\u0010\u0011\u001a\u00020\u00042\b\b\u0002\u0010\u0013\u001a\u00020\u00122\b\b\u0002\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001aK\u0010!\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001e2\b\b\u0002\u0010 \u001a\u00020\u0012\u00a2\u0006\u0004\b!\u0010\"\u001aY\u0010-\u001a\u00020\u00162\u0006\u0010$\u001a\u00020#2\f\u0010'\u001a\b\u0012\u0004\u0012\u00020&0%2\u0006\u0010\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0015\u001a\u00020\u00142\b\b\u0002\u0010(\u001a\u00020\u00122\u0010\b\u0002\u0010+\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010)2\u0006\u0010,\u001a\u00020\u0014\u00a2\u0006\u0004\b-\u0010.\u001a]\u00107\u001a\u00020\u00162\u0006\u00100\u001a\u00020/2\u0006\u0010\u0005\u001a\u00020\u00142\u0006\u0010\u0006\u001a\u00020\u00142\u0006\u00101\u001a\u00020\u00142\u0006\u00102\u001a\u00020\u00142\u0006\u0010\r\u001a\u00020\u00142\u0006\u00103\u001a\u00020\u00142\u0006\u00104\u001a\u00020\u00142\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\u0014\u00a2\u0006\u0004\b7\u00108\u001aK\u00109\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020&2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001e2\b\b\u0002\u0010 \u001a\u00020\u00122\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b9\u0010:\u001aI\u0010=\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001d\u001a\u00020;2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010<\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u001e2\b\b\u0002\u0010 \u001a\u00020\u0012\u00a2\u0006\u0004\b=\u0010>\u001am\u0010=\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00192\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001d\u001a\u00020?2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010<\u001a\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u001e2\b\b\u0002\u0010 \u001a\u00020\u00122\n\b\u0002\u0010@\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010A\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b=\u0010B\u00a8\u0006C"}, d2={"Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "Lnet/minecraft/resources/ResourceLocation;", "texture", "", "x", "y", "height", "width", "uOffset", "vOffset", "textureWidth", "textureHeight", "blitOffset", "red", "green", "blue", "alpha", "", "blend", "", "scale", "", "blitk", "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/ResourceLocation;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;ZF)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "font", "Lnet/minecraft/network/chat/Component;", "text", "", "colour", "shadow", "drawCenteredText", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/Component;Ljava/lang/Number;Ljava/lang/Number;IZ)V", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "", "aspects", "reversed", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "state", "partialTicks", "drawPortraitPokemon", "(Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;Lcom/mojang/blaze3d/vertex/PoseStack;FZLcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;F)V", "Lorg/joml/Matrix4f;", "matrix", "endX", "endY", "minU", "maxU", "minV", "maxV", "drawRectangle", "(Lorg/joml/Matrix4f;FFFFFFFFF)V", "drawString", "(Lnet/minecraft/client/gui/GuiGraphics;Ljava/lang/String;Ljava/lang/Number;Ljava/lang/Number;IZLnet/minecraft/resources/ResourceLocation;)V", "Lnet/minecraft/util/FormattedCharSequence;", "centered", "drawText", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/util/FormattedCharSequence;Ljava/lang/Number;Ljava/lang/Number;ZIZ)V", "Lnet/minecraft/network/chat/MutableComponent;", "pMouseX", "pMouseY", "(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/MutableComponent;Ljava/lang/Number;Ljava/lang/Number;ZIZLjava/lang/Number;Ljava/lang/Number;)Z", "common"})
@SourceDebugExtension(value={"SMAP\nGuiUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GuiUtils.kt\ncom/cobblemon/mod/common/api/gui/GuiUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,246:1\n1#2:247\n*E\n"})
public final class GuiUtilsKt {
    public static final void blitk(@NotNull PoseStack matrixStack, @Nullable ResourceLocation texture, @NotNull Number x, @NotNull Number y, @NotNull Number height, @NotNull Number width, @NotNull Number uOffset, @NotNull Number vOffset, @NotNull Number textureWidth, @NotNull Number textureHeight, @NotNull Number blitOffset, @NotNull Number red, @NotNull Number green, @NotNull Number blue, @NotNull Number alpha, boolean blend, float scale) {
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Intrinsics.checkNotNullParameter((Object)height, (String)"height");
        Intrinsics.checkNotNullParameter((Object)width, (String)"width");
        Intrinsics.checkNotNullParameter((Object)uOffset, (String)"uOffset");
        Intrinsics.checkNotNullParameter((Object)vOffset, (String)"vOffset");
        Intrinsics.checkNotNullParameter((Object)textureWidth, (String)"textureWidth");
        Intrinsics.checkNotNullParameter((Object)textureHeight, (String)"textureHeight");
        Intrinsics.checkNotNullParameter((Object)blitOffset, (String)"blitOffset");
        Intrinsics.checkNotNullParameter((Object)red, (String)"red");
        Intrinsics.checkNotNullParameter((Object)green, (String)"green");
        Intrinsics.checkNotNullParameter((Object)blue, (String)"blue");
        Intrinsics.checkNotNullParameter((Object)alpha, (String)"alpha");
        RenderSystem.setShader(GuiUtilsKt::blitk$lambda$0);
        ResourceLocation resourceLocation = texture;
        if (resourceLocation != null) {
            ResourceLocation $this$blitk_u24lambda_u241 = resourceLocation;
            boolean bl = false;
            RenderSystem.setShaderTexture((int)0, (ResourceLocation)$this$blitk_u24lambda_u241);
        }
        if (blend) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.blendFunc((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }
        RenderSystem.setShaderColor((float)red.floatValue(), (float)green.floatValue(), (float)blue.floatValue(), (float)alpha.floatValue());
        matrixStack.m_85836_();
        matrixStack.m_85841_(scale, scale, 1.0f);
        Matrix4f matrix4f = matrixStack.m_85850_().m_252922_();
        Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrixStack.peek().positionMatrix");
        GuiUtilsKt.drawRectangle(matrix4f, x.floatValue(), y.floatValue(), x.floatValue() + width.floatValue(), y.floatValue() + height.floatValue(), blitOffset.floatValue(), uOffset.floatValue() / textureWidth.floatValue(), (uOffset.floatValue() + width.floatValue()) / textureWidth.floatValue(), vOffset.floatValue() / textureHeight.floatValue(), (vOffset.floatValue() + height.floatValue()) / textureHeight.floatValue());
        matrixStack.m_85849_();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static /* synthetic */ void blitk$default(PoseStack poseStack, ResourceLocation resourceLocation, Number number, Number number2, Number number3, Number number4, Number number5, Number number6, Number number7, Number number8, Number number9, Number number10, Number number11, Number number12, Number number13, boolean bl, float f, int n, Object object) {
        if ((n & 2) != 0) {
            resourceLocation = null;
        }
        if ((n & 0x10) != 0) {
            number3 = 0;
        }
        if ((n & 0x20) != 0) {
            number4 = 0;
        }
        if ((n & 0x40) != 0) {
            number5 = 0;
        }
        if ((n & 0x80) != 0) {
            number6 = 0;
        }
        if ((n & 0x100) != 0) {
            number7 = number4;
        }
        if ((n & 0x200) != 0) {
            number8 = number3;
        }
        if ((n & 0x400) != 0) {
            number9 = 0;
        }
        if ((n & 0x800) != 0) {
            number10 = 1;
        }
        if ((n & 0x1000) != 0) {
            number11 = 1;
        }
        if ((n & 0x2000) != 0) {
            number12 = 1;
        }
        if ((n & 0x4000) != 0) {
            number13 = Float.valueOf(1.0f);
        }
        if ((n & 0x8000) != 0) {
            bl = true;
        }
        if ((n & 0x10000) != 0) {
            f = 1.0f;
        }
        GuiUtilsKt.blitk(poseStack, resourceLocation, number, number2, number3, number4, number5, number6, number7, number8, number9, number10, number11, number12, number13, bl, f);
    }

    public static final void drawRectangle(@NotNull Matrix4f matrix, float x, float y, float endX, float endY, float blitOffset, float minU, float maxU, float minV, float maxV) {
        Intrinsics.checkNotNullParameter((Object)matrix, (String)"matrix");
        BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        bufferbuilder.m_252986_(matrix, x, endY, blitOffset).m_7421_(minU, maxV).m_5752_();
        bufferbuilder.m_252986_(matrix, endX, endY, blitOffset).m_7421_(maxU, maxV).m_5752_();
        bufferbuilder.m_252986_(matrix, endX, y, blitOffset).m_7421_(maxU, minV).m_5752_();
        bufferbuilder.m_252986_(matrix, x, y, blitOffset).m_7421_(minU, minV).m_5752_();
        BufferUploader.m_231202_((BufferBuilder.RenderedBuffer)bufferbuilder.m_231175_());
    }

    public static final void drawCenteredText(@NotNull GuiGraphics context, @Nullable ResourceLocation font, @NotNull Component text, @NotNull Number x, @NotNull Number y, int colour, boolean shadow) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        MutableComponent it = (MutableComponent)text;
        boolean bl = false;
        ResourceLocation resourceLocation = font;
        MutableComponent comp = resourceLocation != null ? TextKt.font(it, resourceLocation) : it;
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        context.m_280614_(textRenderer, (Component)comp, x.intValue() - textRenderer.m_92852_((FormattedText)comp) / 2, y.intValue(), colour, shadow);
    }

    public static /* synthetic */ void drawCenteredText$default(GuiGraphics guiGraphics, ResourceLocation resourceLocation, Component component, Number number, Number number2, int n, boolean bl, int n2, Object object) {
        if ((n2 & 2) != 0) {
            resourceLocation = null;
        }
        if ((n2 & 0x40) != 0) {
            bl = true;
        }
        GuiUtilsKt.drawCenteredText(guiGraphics, resourceLocation, component, number, number2, n, bl);
    }

    public static final boolean drawText(@NotNull GuiGraphics context, @Nullable ResourceLocation font, @NotNull MutableComponent text, @NotNull Number x, @NotNull Number y, boolean centered, int colour, boolean shadow, @Nullable Number pMouseX, @Nullable Number pMouseY) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        MutableComponent comp = font == null ? text : text.m_6270_(text.m_7383_().m_131150_(font));
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        Number x2 = x;
        int width = textRenderer.m_92852_((FormattedText)comp);
        if (centered) {
            x2 = x2.doubleValue() - (double)(width / 2);
        }
        context.m_280614_(textRenderer, (Component)comp, x2.intValue(), y.intValue(), colour, shadow);
        boolean isHovered = false;
        if (pMouseY != null && pMouseX != null && pMouseX.intValue() >= x2.intValue() && pMouseX.intValue() <= x2.intValue() + width && pMouseY.intValue() >= y.intValue() && pMouseY.intValue() <= y.intValue() + textRenderer.f_92710_) {
            isHovered = true;
        }
        return isHovered;
    }

    public static /* synthetic */ boolean drawText$default(GuiGraphics guiGraphics, ResourceLocation resourceLocation, MutableComponent mutableComponent, Number number, Number number2, boolean bl, int n, boolean bl2, Number number3, Number number4, int n2, Object object) {
        if ((n2 & 2) != 0) {
            resourceLocation = null;
        }
        if ((n2 & 0x20) != 0) {
            bl = false;
        }
        if ((n2 & 0x80) != 0) {
            bl2 = true;
        }
        if ((n2 & 0x100) != 0) {
            number3 = null;
        }
        if ((n2 & 0x200) != 0) {
            number4 = null;
        }
        return GuiUtilsKt.drawText(guiGraphics, resourceLocation, mutableComponent, number, number2, bl, n, bl2, number3, number4);
    }

    public static final void drawText(@NotNull GuiGraphics context, @NotNull FormattedCharSequence text, @NotNull Number x, @NotNull Number y, boolean centered, int colour, boolean shadow) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        Number tweakedX = x;
        if (centered) {
            int width = textRenderer.m_92724_(text);
            tweakedX = tweakedX.doubleValue() - (double)(width / 2);
        }
        context.m_280649_(textRenderer, text, tweakedX.intValue(), y.intValue(), colour, shadow);
    }

    public static /* synthetic */ void drawText$default(GuiGraphics guiGraphics, FormattedCharSequence formattedCharSequence, Number number, Number number2, boolean bl, int n, boolean bl2, int n2, Object object) {
        if ((n2 & 0x10) != 0) {
            bl = false;
        }
        if ((n2 & 0x40) != 0) {
            bl2 = true;
        }
        GuiUtilsKt.drawText(guiGraphics, formattedCharSequence, number, number2, bl, n, bl2);
    }

    public static final void drawString(@NotNull GuiGraphics context, @NotNull String text, @NotNull Number x, @NotNull Number y, int colour, boolean shadow, @Nullable ResourceLocation font) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)x, (String)"x");
        Intrinsics.checkNotNullParameter((Object)y, (String)"y");
        MutableComponent it = mutableComponent = Component.m_237113_((String)text);
        boolean bl = false;
        ResourceLocation resourceLocation = font;
        if (resourceLocation != null) {
            ResourceLocation $this$drawString_u24lambda_u244_u24lambda_u243 = resourceLocation;
            boolean bl2 = false;
            it.m_178405_(it.m_7383_().m_131150_($this$drawString_u24lambda_u244_u24lambda_u243));
        }
        MutableComponent comp = mutableComponent;
        Font textRenderer = Minecraft.m_91087_().f_91062_;
        context.m_280614_(textRenderer, (Component)comp, x.intValue(), y.intValue(), colour, shadow);
    }

    public static /* synthetic */ void drawString$default(GuiGraphics guiGraphics, String string, Number number, Number number2, int n, boolean bl, ResourceLocation resourceLocation, int n2, Object object) {
        if ((n2 & 0x20) != 0) {
            bl = true;
        }
        if ((n2 & 0x40) != 0) {
            resourceLocation = null;
        }
        GuiUtilsKt.drawString(guiGraphics, string, number, number2, n, bl, resourceLocation);
    }

    public static final void drawPortraitPokemon(@NotNull Species species, @NotNull Set<String> aspects, @NotNull PoseStack matrixStack, float scale, boolean reversed, @Nullable PoseableEntityState<PokemonEntity> state, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)matrixStack, (String)"matrixStack");
        PokemonPoseableModel model = (PokemonPoseableModel)PokemonModelRepository.INSTANCE.getPoser(species.getResourceIdentifier(), aspects);
        PoseableEntityState<PokemonEntity> poseableEntityState = state;
        ResourceLocation texture = PokemonModelRepository.INSTANCE.getTexture(species.getResourceIdentifier(), aspects, poseableEntityState != null ? poseableEntityState.getAnimationSeconds() : 0.0f);
        RenderContext context = new RenderContext();
        ResourceLocation it = PokemonModelRepository.INSTANCE.getTextureNoSubstitute(species.getResourceIdentifier(), aspects, 0.0f);
        boolean bl = false;
        context.put(RenderContext.Companion.getTEXTURE(), it);
        context.put(RenderContext.Companion.getSCALE(), Float.valueOf(species.getForm(aspects).getBaseScale()));
        context.put(RenderContext.Companion.getSPECIES(), species.getResourceIdentifier());
        context.put(RenderContext.Companion.getASPECTS(), aspects);
        RenderType renderType = model.m_103119_(texture);
        RenderSystem.applyModelViewMatrix();
        Quaternionf quaternion1 = Axis.f_252436_.m_252977_(-32.0f * (reversed ? -1.0f : 1.0f));
        Quaternionf quaternion2 = Axis.f_252529_.m_252977_(5.0f);
        if (state == null) {
            PoseType[] poseTypeArray = new PoseType[]{PoseType.PORTRAIT, PoseType.PROFILE};
            PoseableEntityModel.setupAnimStateless$default((PoseableEntityModel)model, SetsKt.setOf((Object[])poseTypeArray), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 62, null);
        } else {
            String originalPose = state.getCurrentPose();
            Pose pose = model.getPose(PoseType.PORTRAIT);
            if (pose != null) {
                Pose it2 = pose;
                boolean bl2 = false;
                state.setPose(it2.getPoseName());
            }
            state.setTimeEnteredPose(0.0f);
            state.updatePartialTicks(partialTicks);
            model.setupAnimStateful(null, state, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
            String string = originalPose;
            if (string != null) {
                String it3 = string;
                boolean bl3 = false;
                state.setPose(it3);
            }
        }
        matrixStack.m_85836_();
        matrixStack.m_85837_(0.0, 30.0, 0.0);
        matrixStack.m_85841_(scale, scale, -scale);
        matrixStack.m_85837_(0.0, -1.5555555555555556, 0.0);
        matrixStack.m_85837_(model.getPortraitTranslation().f_82479_ * (double)(reversed ? -1.0f : 1.0f), model.getPortraitTranslation().f_82480_, model.getPortraitTranslation().f_82481_ - (double)4);
        matrixStack.m_85841_(model.getPortraitScale(), model.getPortraitScale(), 1.0f / model.getPortraitScale());
        matrixStack.m_252781_(quaternion1);
        matrixStack.m_252781_(quaternion2);
        Vector3f light1 = new Vector3f(0.2f, 1.0f, -1.0f);
        Vector3f light2 = new Vector3f(0.1f, 0.0f, 8.0f);
        RenderSystem.setShaderLights((Vector3f)light1, (Vector3f)light2);
        quaternion1.conjugate();
        MultiBufferSource.BufferSource immediate = Minecraft.m_91087_().m_91269_().m_110104_();
        VertexConsumer buffer = immediate.m_6299_(renderType);
        int packedLight = LightTexture.m_109885_((int)11, (int)7);
        Intrinsics.checkNotNullExpressionValue((Object)immediate, (String)"immediate");
        model.withLayerContext((MultiBufferSource)immediate, state, PokemonModelRepository.INSTANCE.getLayers(species.getResourceIdentifier(), aspects), (Function0<Unit>)((Function0)new Function0<Unit>(model, context, matrixStack, buffer, packedLight, immediate){
            final /* synthetic */ PokemonPoseableModel $model;
            final /* synthetic */ RenderContext $context;
            final /* synthetic */ PoseStack $matrixStack;
            final /* synthetic */ VertexConsumer $buffer;
            final /* synthetic */ int $packedLight;
            final /* synthetic */ MultiBufferSource.BufferSource $immediate;
            {
                this.$model = $model;
                this.$context = $context;
                this.$matrixStack = $matrixStack;
                this.$buffer = $buffer;
                this.$packedLight = $packedLight;
                this.$immediate = $immediate;
                super(0);
            }

            public final void invoke() {
                VertexConsumer vertexConsumer = this.$buffer;
                Intrinsics.checkNotNullExpressionValue((Object)vertexConsumer, (String)"buffer");
                this.$model.render(this.$context, this.$matrixStack, vertexConsumer, this.$packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
                this.$immediate.m_109911_();
            }
        }));
        matrixStack.m_85849_();
        model.setDefault();
        Lighting.m_84931_();
    }

    public static /* synthetic */ void drawPortraitPokemon$default(Species species, Set set2, PoseStack poseStack, float f, boolean bl, PoseableEntityState poseableEntityState, float f2, int n, Object object) {
        if ((n & 8) != 0) {
            f = 13.0f;
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        if ((n & 0x20) != 0) {
            poseableEntityState = null;
        }
        GuiUtilsKt.drawPortraitPokemon(species, set2, poseStack, f, bl, poseableEntityState, f2);
    }

    private static final ShaderInstance blitk$lambda$0() {
        return GameRenderer.m_172817_();
    }
}

