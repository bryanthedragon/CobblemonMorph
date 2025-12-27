@file:SourceDebugExtension(["SMAP\nGuiUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GuiUtils.kt\ncom/cobblemon/mod/common/api/gui/GuiUtilsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,246:1\n1#2:247\n*E\n"])

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.RenderContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species
import com.mojang.blaze3d.platform.Lighting
import com.mojang.blaze3d.platform.GlStateManager.DestFactor
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.blaze3d.vertex.VertexFormat.Mode
import com.mojang.math.Axis
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.MultiBufferSource.BufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.FormattedText
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.FormattedCharSequence
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f

public fun blitk(
   matrixStack: PoseStack,
   texture: ResourceLocation? = null,
   x: Number,
   y: Number,
   height: Number = 0 as java.lang.Number,
   width: Number = 0 as java.lang.Number,
   uOffset: Number = 0 as java.lang.Number,
   vOffset: Number = 0 as java.lang.Number,
   textureWidth: Number = width,
   textureHeight: Number = height,
   blitOffset: Number = 0 as java.lang.Number,
   red: Number = 1 as java.lang.Number,
   green: Number = 1 as java.lang.Number,
   blue: Number = 1 as java.lang.Number,
   alpha: Number = 1.0F as java.lang.Number,
   blend: Boolean = true,
   scale: Float = 1.0F
) {
   RenderSystem.setShader(GuiUtilsKt::blitk$lambda$0);
   if (texture != null) {
      RenderSystem.setShaderTexture(0, texture);
   }

   if (blend) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
   }

   RenderSystem.setShaderColor(red.floatValue(), green.floatValue(), blue.floatValue(), alpha.floatValue());
   matrixStack.m_85836_();
   matrixStack.m_85841_(scale, scale, 1.0F);
   val var10000: Matrix4f = matrixStack.m_85850_().m_252922_();
   drawRectangle(
      var10000,
      x.floatValue(),
      y.floatValue(),
      x.floatValue() + width.floatValue(),
      y.floatValue() + height.floatValue(),
      blitOffset.floatValue(),
      uOffset.floatValue() / textureWidth.floatValue(),
      (uOffset.floatValue() + width.floatValue()) / textureWidth.floatValue(),
      vOffset.floatValue() / textureHeight.floatValue(),
      (vOffset.floatValue() + height.floatValue()) / textureHeight.floatValue()
   );
   matrixStack.m_85849_();
   RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
}

@JvmSynthetic
fun `blitk$default`(
   var0: PoseStack,
   var1: ResourceLocation,
   var2: java.lang.Number,
   var3: java.lang.Number,
   var4: java.lang.Number,
   var5: java.lang.Number,
   var6: java.lang.Number,
   var7: java.lang.Number,
   var8: java.lang.Number,
   var9: java.lang.Number,
   var10: java.lang.Number,
   var11: java.lang.Number,
   var12: java.lang.Number,
   var13: java.lang.Number,
   var14: java.lang.Number,
   var15: Boolean,
   var16: Float,
   var17: Int,
   var18: Any
) {
   if ((var17 and 2) != 0) {
      var1 = null;
   }

   if ((var17 and 16) != 0) {
      var4 = 0;
   }

   if ((var17 and 32) != 0) {
      var5 = 0;
   }

   if ((var17 and 64) != 0) {
      var6 = 0;
   }

   if ((var17 and 128) != 0) {
      var7 = 0;
   }

   if ((var17 and 256) != 0) {
      var8 = var5;
   }

   if ((var17 and 512) != 0) {
      var9 = var4;
   }

   if ((var17 and 1024) != 0) {
      var10 = 0;
   }

   if ((var17 and 2048) != 0) {
      var11 = 1;
   }

   if ((var17 and 4096) != 0) {
      var12 = 1;
   }

   if ((var17 and 8192) != 0) {
      var13 = 1;
   }

   if ((var17 and 16384) != 0) {
      var14 = 1.0F;
   }

   if ((var17 and '耀') != 0) {
      var15 = true;
   }

   if ((var17 and 65536) != 0) {
      var16 = 1.0F;
   }

   blitk(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16);
}

public fun drawRectangle(matrix: Matrix4f, x: Float, y: Float, endX: Float, endY: Float, blitOffset: Float, minU: Float, maxU: Float, minV: Float, maxV: Float) {
   val bufferbuilder: BufferBuilder = Tesselator.m_85913_().m_85915_();
   bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85817_);
   bufferbuilder.m_252986_(matrix, x, endY, blitOffset).m_7421_(minU, maxV).m_5752_();
   bufferbuilder.m_252986_(matrix, endX, endY, blitOffset).m_7421_(maxU, maxV).m_5752_();
   bufferbuilder.m_252986_(matrix, endX, y, blitOffset).m_7421_(maxU, minV).m_5752_();
   bufferbuilder.m_252986_(matrix, x, y, blitOffset).m_7421_(minU, minV).m_5752_();
   BufferUploader.m_231202_(bufferbuilder.m_231175_());
}

public fun drawCenteredText(context: GuiGraphics, font: ResourceLocation? = null, text: Component, x: Number, y: Number, colour: Int, shadow: Boolean = true) {
   val comp: MutableComponent = if (font != null) TextKt.font(text as MutableComponent, font) else text as MutableComponent;
   val textRenderer: Font = Minecraft.m_91087_().f_91062_;
   context.m_280614_(textRenderer, comp as Component, x.intValue() - textRenderer.m_92852_(comp as FormattedText) / 2, y.intValue(), colour, shadow);
}

@JvmSynthetic
fun `drawCenteredText$default`(
   var0: GuiGraphics, var1: ResourceLocation, var2: Component, var3: java.lang.Number, var4: java.lang.Number, var5: Int, var6: Boolean, var7: Int, var8: Any
) {
   if ((var7 and 2) != 0) {
      var1 = null;
   }

   if ((var7 and 64) != 0) {
      var6 = true;
   }

   drawCenteredText(var0, var1, var2, var3, var4, var5, var6);
}

public fun drawText(
   context: GuiGraphics,
   font: ResourceLocation? = null,
   text: MutableComponent,
   x: Number,
   y: Number,
   centered: Boolean = false,
   colour: Int,
   shadow: Boolean = true,
   pMouseX: Number? = null,
   pMouseY: Number? = null
): Boolean {
   val comp: MutableComponent = if (font == null) text else text.m_6270_(text.m_7383_().m_131150_(font));
   val textRenderer: Font = Minecraft.m_91087_().f_91062_;
   var xx: java.lang.Number = x;
   val width: Int = textRenderer.m_92852_(comp as FormattedText);
   if (centered) {
      xx = x.doubleValue() - (double)(width / 2);
   }

   context.m_280614_(textRenderer, comp as Component, xx.intValue(), y.intValue(), colour, shadow);
   var isHovered: Boolean = false;
   if (pMouseY != null
      && pMouseX != null
      && pMouseX.intValue() >= xx.intValue()
      && pMouseX.intValue() <= xx.intValue() + width
      && pMouseY.intValue() >= y.intValue()
      && pMouseY.intValue() <= y.intValue() + textRenderer.f_92710_) {
      isHovered = true;
   }

   return isHovered;
}

@JvmSynthetic
fun `drawText$default`(
   var0: GuiGraphics,
   var1: ResourceLocation,
   var2: MutableComponent,
   var3: java.lang.Number,
   var4: java.lang.Number,
   var5: Boolean,
   var6: Int,
   var7: Boolean,
   var8: java.lang.Number,
   var9: java.lang.Number,
   var10: Int,
   var11: Any
): Boolean {
   if ((var10 and 2) != 0) {
      var1 = null;
   }

   if ((var10 and 32) != 0) {
      var5 = false;
   }

   if ((var10 and 128) != 0) {
      var7 = true;
   }

   if ((var10 and 256) != 0) {
      var8 = null;
   }

   if ((var10 and 512) != 0) {
      var9 = null;
   }

   return drawText(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
}

public fun drawText(context: GuiGraphics, text: FormattedCharSequence, x: Number, y: Number, centered: Boolean = false, colour: Int, shadow: Boolean = true) {
   val textRenderer: Font = Minecraft.m_91087_().f_91062_;
   var tweakedX: java.lang.Number = x;
   if (centered) {
      tweakedX = x.doubleValue() - (double)(textRenderer.m_92724_(text) / 2);
   }

   context.m_280649_(textRenderer, text, tweakedX.intValue(), y.intValue(), colour, shadow);
}

@JvmSynthetic
fun `drawText$default`(
   var0: GuiGraphics,
   var1: FormattedCharSequence,
   var2: java.lang.Number,
   var3: java.lang.Number,
   var4: Boolean,
   var5: Int,
   var6: Boolean,
   var7: Int,
   var8: Any
) {
   if ((var7 and 16) != 0) {
      var4 = false;
   }

   if ((var7 and 64) != 0) {
      var6 = true;
   }

   drawText(var0, var1, var2, var3, var4, var5, var6);
}

public fun drawString(context: GuiGraphics, text: String, x: Number, y: Number, colour: Int, shadow: Boolean = true, font: ResourceLocation? = null) {
   val textRenderer: MutableComponent = Component.m_237113_(text);
   if (font != null) {
      textRenderer.m_178405_(textRenderer.m_7383_().m_131150_(font));
   }

   context.m_280614_(Minecraft.m_91087_().f_91062_, textRenderer as Component, x.intValue(), y.intValue(), colour, shadow);
}

@JvmSynthetic
fun `drawString$default`(
   var0: GuiGraphics,
   var1: java.lang.String,
   var2: java.lang.Number,
   var3: java.lang.Number,
   var4: Int,
   var5: Boolean,
   var6: ResourceLocation,
   var7: Int,
   var8: Any
) {
   if ((var7 and 32) != 0) {
      var5 = true;
   }

   if ((var7 and 64) != 0) {
      var6 = null;
   }

   drawString(var0, var1, var2, var3, var4, var5, var6);
}

public fun drawPortraitPokemon(
   species: Species,
   aspects: Set<String>,
   matrixStack: PoseStack,
   scale: Float = 13.0F,
   reversed: Boolean = false,
   state: PoseableEntityState<PokemonEntity>? = null,
   partialTicks: Float
) {
   val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE.getPoser(species.getResourceIdentifier(), aspects);
   val texture: ResourceLocation = PokemonModelRepository.INSTANCE
      .getTexture(species.getResourceIdentifier(), aspects, if (state != null) state.getAnimationSeconds() else 0.0F);
   val context: RenderContext = new RenderContext();
   context.put(RenderContext.Companion.getTEXTURE(), PokemonModelRepository.INSTANCE.getTextureNoSubstitute(species.getResourceIdentifier(), aspects, 0.0F));
   context.put(RenderContext.Companion.getSCALE(), species.getForm(aspects).getBaseScale());
   context.put(RenderContext.Companion.getSPECIES(), species.getResourceIdentifier());
   context.put(RenderContext.Companion.getASPECTS(), aspects);
   val renderType: RenderType = model.m_103119_(texture);
   RenderSystem.applyModelViewMatrix();
   val var18: Quaternionf = Axis.f_252436_.m_252977_(-32.0F * (if (reversed) -1.0F else 1.0F));
   val var19: Quaternionf = Axis.f_252529_.m_252977_(5.0F);
   if (state == null) {
      PoseableEntityModel.setupAnimStateless$default(
         model, SetsKt.setOf(new PoseType[]{PoseType.PORTRAIT, PoseType.PROFILE}), 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 62, null
      );
   } else {
      val var20: java.lang.String = state.getCurrentPose();
      val var25: Pose = model.getPose(PoseType.PORTRAIT);
      if (var25 != null) {
         state.setPose(var25.getPoseName());
      }

      state.setTimeEnteredPose(0.0F);
      state.updatePartialTicks(partialTicks);
      model.setupAnimStateful(null, state, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      if (var20 != null) {
         state.setPose(var20);
      }
   }

   matrixStack.m_85836_();
   matrixStack.m_85837_(0.0, 30.0, 0.0);
   matrixStack.m_85841_(scale, scale, -scale);
   matrixStack.m_85837_(0.0, -1.5555555555555556, 0.0);
   matrixStack.m_85837_(
      model.getPortraitTranslation().f_82479_ * (double)(if (reversed) -1.0F else 1.0F),
      model.getPortraitTranslation().f_82480_,
      model.getPortraitTranslation().f_82481_ - (double)4
   );
   matrixStack.m_85841_(model.getPortraitScale(), model.getPortraitScale(), (float)1 / model.getPortraitScale());
   matrixStack.m_252781_(var18);
   matrixStack.m_252781_(var19);
   RenderSystem.setShaderLights(new Vector3f(0.2F, 1.0F, -1.0F), new Vector3f(0.1F, 0.0F, 8.0F));
   var18.conjugate();
   val immediate: BufferSource = Minecraft.m_91087_().m_91269_().m_110104_();
   val var23: VertexConsumer = immediate.m_6299_(renderType);
   val var24: Int = LightTexture.m_109885_(11, 7);
   model.withLayerContext(
      immediate as MultiBufferSource,
      state,
      PokemonModelRepository.INSTANCE.getLayers(species.getResourceIdentifier(), aspects),
      (new Function0<Unit>(model, context, matrixStack, var23, var24, immediate) {
         {
            super(0);
            this.$model = `$model`;
            this.$context = `$context`;
            this.$matrixStack = `$matrixStack`;
            this.$buffer = `$buffer`;
            this.$packedLight = `$packedLight`;
            this.$immediate = `$immediate`;
         }

         public final void invoke() {
            val var10000: PokemonPoseableModel = this.$model;
            val var10001: RenderContext = this.$context;
            val var10002: PoseStack = this.$matrixStack;
            val var10003: VertexConsumer = this.$buffer;
            var10000.render(var10001, var10002, var10003, this.$packedLight, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            this.$immediate.m_109911_();
         }
      }) as () -> Unit
   );
   matrixStack.m_85849_();
   model.setDefault();
   Lighting.m_84931_();
}

@JvmSynthetic
fun `drawPortraitPokemon$default`(
   var0: Species, var1: java.util.Set, var2: PoseStack, var3: Float, var4: Boolean, var5: PoseableEntityState, var6: Float, var7: Int, var8: Any
) {
   if ((var7 and 8) != 0) {
      var3 = 13.0F;
   }

   if ((var7 and 16) != 0) {
      var4 = false;
   }

   if ((var7 and 32) != 0) {
      var5 = null;
   }

   drawPortraitPokemon(var0, var1, var2, var3, var4, var5, var6);
}

fun `blitk$lambda$0`(): ShaderInstance {
   return GameRenderer.m_172817_();
}
