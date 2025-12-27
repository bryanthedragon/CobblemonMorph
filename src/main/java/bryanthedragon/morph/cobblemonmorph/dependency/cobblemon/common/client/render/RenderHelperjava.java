package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.PoseStack.Pose
import com.mojang.blaze3d.vertex.VertexFormat.Mode
import com.mojang.math.Axis
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.MultiBufferSource.BufferSource
import net.minecraft.client.renderer.entity.ItemRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.texture.TextureAtlas
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.model.BakedModel
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FormattedCharSequence
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import org.joml.Matrix3f
import org.joml.Matrix4f

public fun renderImage(texture: ResourceLocation, x: Double, y: Double, height: Double, width: Double) {
   val textureManager: TextureManager = Minecraft.m_91087_().m_91097_();
   val buffer: BufferBuilder = Tesselator.m_85913_().m_85915_();
   buffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85817_);
   textureManager.m_174784_(texture);
   buffer.m_5483_(x, y + height, 0.0).m_7421_(0.0F, 1.0F).m_5752_();
   buffer.m_5483_(x + width, y + height, 0.0).m_7421_(1.0F, 1.0F).m_5752_();
   buffer.m_5483_(x + width, y, 0.0).m_7421_(1.0F, 0.0F).m_5752_();
   buffer.m_5483_(x, y, 0.0).m_7421_(0.0F, 0.0F).m_5752_();
   Tesselator.m_85913_().m_85914_();
}

public fun renderScaledGuiItemIcon(
   itemStack: ItemStack,
   x: Double,
   y: Double,
   scale: Double = 1.0,
   zTranslation: Float = 100.0F,
   matrixStack: PoseStack? = null
) {
   val itemRenderer: ItemRenderer = Minecraft.m_91087_().m_91291_();
   val textureManager: TextureManager = Minecraft.m_91087_().m_91097_();
   val model: BakedModel = itemRenderer.m_174264_(itemStack, null, null, 0);
   textureManager.m_118506_(TextureAtlas.f_118259_).m_117960_(false, false);
   RenderSystem.setShaderTexture(0, TextureAtlas.f_118259_);
   RenderSystem.enableBlend();
   RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
   RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   var var10000: PoseStack = matrixStack;
   if (matrixStack == null) {
      var10000 = RenderSystem.getModelViewStack();
   }

   var10000.m_85836_();
   var10000.m_85837_(x, y, (double)(zTranslation + (float)0));
   var10000.m_85837_(8.0 * scale, 8.0 * scale, 0.0);
   var10000.m_85841_(1.0F, -1.0F, 1.0F);
   var10000.m_85841_(16.0F * (float)scale, 16.0F * (float)scale, 16.0F * (float)scale);
   RenderSystem.applyModelViewMatrix();
   var10000 = matrixStack;
   if (matrixStack == null) {
      var10000 = new PoseStack();
   }

   val immediate: BufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
   val bl: Boolean = !model.m_7547_();
   if (bl) {
      Lighting.m_84930_();
   }

   itemRenderer.m_115143_(itemStack, ItemDisplayContext.GUI, false, var10000, immediate as MultiBufferSource, 15728880, OverlayTexture.f_118083_, model);
   immediate.m_109911_();
   RenderSystem.enableDepthTest();
   if (bl) {
      Lighting.m_84931_();
   }

   var10000.m_85849_();
   RenderSystem.applyModelViewMatrix();
}

@JvmSynthetic
fun `renderScaledGuiItemIcon$default`(var0: ItemStack, var1: Double, var3: Double, var5: Double, var7: Float, var8: PoseStack, var9: Int, var10: Any) {
   if ((var9 and 8) != 0) {
      var5 = 1.0;
   }

   if ((var9 and 16) != 0) {
      var7 = 100.0F;
   }

   if ((var9 and 32) != 0) {
      var8 = null;
   }

   renderScaledGuiItemIcon(var0, var1, var3, var5, var7, var8);
}

public fun getDepletableRedGreen(ratio: Float, yellowRatio: Float = 0.5F, redRatio: Float = 0.2F): Pair<Float, Float> {
   return TuplesKt.to(
      ((if (ratio > redRatio) (float)-2 * ratio - (float)-2 else 1.0) as java.lang.Number).floatValue(),
      ((if (ratio > yellowRatio) 1.0 else (if (ratio > redRatio) ratio * (float)1 / yellowRatio else 0.0)) as java.lang.Number).floatValue()
   );
}

@JvmSynthetic
fun `getDepletableRedGreen$default`(var0: Float, var1: Float, var2: Float, var3: Int, var4: Any): Pair {
   if ((var3 and 2) != 0) {
      var1 = 0.5F;
   }

   if ((var3 and 4) != 0) {
      var2 = 0.2F;
   }

   return getDepletableRedGreen(var0, var1, var2);
}

public fun drawScaledText(
   context: GuiGraphics,
   font: ResourceLocation? = null,
   text: MutableComponent,
   x: Number,
   y: Number,
   scale: Float = 1.0F,
   opacity: Number = 1.0F as java.lang.Number,
   maxCharacterWidth: Int = Integer.MAX_VALUE,
   colour: Int = 16777215 + ((int)(opacity.floatValue() * (float)255) shl 24),
   centered: Boolean = false,
   shadow: Boolean = false,
   pMouseX: Int? = null,
   pMouseY: Int? = null
) {
   if (!(opacity.floatValue() < 0.05F)) {
      val textWidth: Int = Minecraft.m_91087_().f_91062_.m_92852_((if (font != null) TextKt.font(text, font) else text) as FormattedText);
      val extraScale: Float = if (textWidth < maxCharacterWidth) 1.0F else (float)maxCharacterWidth / textWidth;
      val fontHeight: Int = if (font == null) 5 else 6;
      val matrices: PoseStack = context.m_280168_();
      matrices.m_85836_();
      matrices.m_85841_(scale * extraScale, scale * extraScale, 1.0F);
      val isHovered: Boolean = GuiUtilsKt.drawText(
         context,
         font,
         text,
         x.floatValue() / (scale * extraScale),
         y.floatValue() / (scale * extraScale) + ((float)1 - extraScale) * (float)fontHeight * scale,
         centered,
         colour,
         shadow,
         if (pMouseX != null) (float)pMouseX.intValue() / (scale * extraScale) else null,
         if (pMouseY != null) (float)pMouseY.intValue() / (scale * extraScale) + ((float)1 - extraScale) * (float)fontHeight * scale else null
      );
      matrices.m_85849_();
      if (isHovered) {
         val var10001: Font = Minecraft.m_91087_().f_91062_;
         val var10002: Style = text.m_7383_();
         val var10003: Int = pMouseX;
         context.m_280304_(var10001, var10002, var10003, pMouseY);
      }
   }
}

@JvmSynthetic
fun `drawScaledText$default`(
   var0: GuiGraphics,
   var1: ResourceLocation,
   var2: MutableComponent,
   var3: java.lang.Number,
   var4: java.lang.Number,
   var5: Float,
   var6: java.lang.Number,
   var7: Int,
   var8: Int,
   var9: Boolean,
   var10: Boolean,
   var11: Int,
   var12: Int,
   var13: Int,
   var14: Any
) {
   if ((var13 and 2) != 0) {
      var1 = null;
   }

   if ((var13 and 32) != 0) {
      var5 = 1.0F;
   }

   if ((var13 and 64) != 0) {
      var6 = 1.0F;
   }

   if ((var13 and 128) != 0) {
      var7 = Integer.MAX_VALUE;
   }

   if ((var13 and 256) != 0) {
      var8 = 16777215 + ((int)(var6.floatValue() * 255) shl 24);
   }

   if ((var13 and 512) != 0) {
      var9 = false;
   }

   if ((var13 and 1024) != 0) {
      var10 = false;
   }

   if ((var13 and 2048) != 0) {
      var11 = null;
   }

   if ((var13 and 4096) != 0) {
      var12 = null;
   }

   drawScaledText(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
}

public fun drawScaledText(
   context: GuiGraphics,
   text: FormattedCharSequence,
   x: Number,
   y: Number,
   scaleX: Float = 1.0F,
   scaleY: Float = 1.0F,
   opacity: Number = 1.0F as java.lang.Number,
   colour: Int = 16777215 + ((int)(opacity.floatValue() * (float)255) shl 24),
   centered: Boolean = false,
   shadow: Boolean = false
) {
   if (!(opacity.floatValue() < 0.05F)) {
      val matrixStack: PoseStack = context.m_280168_();
      matrixStack.m_85836_();
      matrixStack.m_85841_(scaleX, scaleY, 1.0F);
      GuiUtilsKt.drawText(context, text, x.floatValue() / scaleX, y.floatValue() / scaleY, centered, colour, shadow);
      matrixStack.m_85849_();
   }
}

@JvmSynthetic
fun `drawScaledText$default`(
   var0: GuiGraphics,
   var1: FormattedCharSequence,
   var2: java.lang.Number,
   var3: java.lang.Number,
   var4: Float,
   var5: Float,
   var6: java.lang.Number,
   var7: Int,
   var8: Boolean,
   var9: Boolean,
   var10: Int,
   var11: Any
) {
   if ((var10 and 16) != 0) {
      var4 = 1.0F;
   }

   if ((var10 and 32) != 0) {
      var5 = 1.0F;
   }

   if ((var10 and 64) != 0) {
      var6 = 1.0F;
   }

   if ((var10 and 128) != 0) {
      var7 = 16777215 + ((int)(var6.floatValue() * 255) shl 24);
   }

   if ((var10 and 256) != 0) {
      var8 = false;
   }

   if ((var10 and 512) != 0) {
      var9 = false;
   }

   drawScaledText(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
}

public fun renderBeaconBeam(
   matrixStack: PoseStack,
   buffer: MultiBufferSource,
   textureLocation: ResourceLocation = CobblemonResources.INSTANCE.getPHASE_BEAM(),
   partialTicks: Float,
   totalLevelTime: Long,
   yOffset: Float = 0.0F,
   height: Float,
   red: Float,
   green: Float,
   blue: Float,
   alpha: Float,
   beamRadius: Float,
   glowRadius: Float,
   glowAlpha: Float
) {
   val i: Float = yOffset + height;
   val beamRotation: Float = Math.floorMod(totalLevelTime, 40) + partialTicks;
   matrixStack.m_85836_();
   matrixStack.m_252781_(Axis.f_252436_.m_252977_(beamRotation * 2.25F - 45.0F));
   var f9: Float = -beamRadius;
   val f12: Float = -beamRadius;
   var var10001: VertexConsumer = buffer.m_6299_(RenderType.m_110460_(textureLocation, false));
   renderPart(matrixStack, var10001, red, green, blue, alpha, yOffset, i, 0.0F, beamRadius, beamRadius, 0.0F, f9, 0.0F, 0.0F, f12);
   matrixStack.m_85849_();
   val f6: Float = -glowRadius;
   val f7: Float = -glowRadius;
   val f8: Float = -glowRadius;
   f9 = -glowRadius;
   var10001 = buffer.m_6299_(RenderType.m_110460_(textureLocation, true));
   renderPart(matrixStack, var10001, red, green, blue, glowAlpha, yOffset, i, f6, f7, glowRadius, f8, f9, glowRadius, glowRadius, glowRadius);
}

@JvmSynthetic
fun `renderBeaconBeam$default`(
   var0: PoseStack,
   var1: MultiBufferSource,
   var2: ResourceLocation,
   var3: Float,
   var4: Long,
   var6: Float,
   var7: Float,
   var8: Float,
   var9: Float,
   var10: Float,
   var11: Float,
   var12: Float,
   var13: Float,
   var14: Float,
   var15: Int,
   var16: Any
) {
   if ((var15 and 4) != 0) {
      var2 = CobblemonResources.INSTANCE.getPHASE_BEAM();
   }

   if ((var15 and 32) != 0) {
      var6 = 0.0F;
   }

   renderBeaconBeam(var0, var1, var2, var3, var4, var6, var7, var8, var9, var10, var11, var12, var13, var14);
}

public fun renderPart(
   matrixStack: PoseStack,
   vertexBuffer: VertexConsumer,
   red: Float,
   green: Float,
   blue: Float,
   alpha: Float,
   yMin: Float,
   yMax: Float,
   p_112164_: Float,
   p_112165_: Float,
   p_112166_: Float,
   p_112167_: Float,
   p_112168_: Float,
   p_112169_: Float,
   p_112170_: Float,
   p_112171_: Float
) {
   val pose: Pose = matrixStack.m_85850_();
   val matrix4f: Matrix4f = pose.m_252922_();
   val matrix3f: Matrix3f = pose.m_252943_();
   renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112164_, p_112165_, p_112166_, p_112167_);
   renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112170_, p_112171_, p_112168_, p_112169_);
   renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112166_, p_112167_, p_112170_, p_112171_);
   renderQuad(matrix4f, matrix3f, vertexBuffer, red, green, blue, alpha, yMin, yMax, p_112168_, p_112169_, p_112164_, p_112165_);
}

public fun renderQuad(
   matrixPos: Matrix4f,
   matrixNormal: Matrix3f,
   buffer: VertexConsumer,
   red: Float,
   green: Float,
   blue: Float,
   alpha: Float,
   yMin: Float,
   yMax: Float,
   x1: Float,
   z1: Float,
   x2: Float,
   z2: Float
) {
   addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMax, x1, z1, 1.0F, 0.0F);
   addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMin, x1, z1, 1.0F, 1.0F);
   addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMin, x2, z2, 0.0F, 1.0F);
   addVertex(matrixPos, matrixNormal, buffer, red, green, blue, alpha, yMax, x2, z2, 0.0F, 0.0F);
}

public fun addVertex(
   matrixPos: Matrix4f,
   matrixNormal: Matrix3f,
   buffer: VertexConsumer,
   red: Float,
   green: Float,
   blue: Float,
   alpha: Float,
   y: Float,
   x: Float,
   z: Float,
   texU: Float,
   texV: Float
) {
   buffer.m_252986_(matrixPos, x, y, z)
      .m_85950_(red, green, blue, alpha)
      .m_7421_(texU, texV)
      .m_86008_(OverlayTexture.f_118083_)
      .m_85969_(15728880)
      .m_252939_(matrixNormal, 0.0F, 1.0F, 0.0F)
      .m_5752_();
}
