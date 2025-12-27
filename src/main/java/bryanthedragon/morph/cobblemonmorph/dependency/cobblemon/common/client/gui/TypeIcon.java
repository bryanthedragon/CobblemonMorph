package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

public class TypeIcon(x: Number,
   y: Number,
   type: ElementalType,
   secondaryType: ElementalType? = null,
   centeredX: Boolean = false,
   small: Boolean = false,
   secondaryOffset: Float = 15.0F,
   doubleCenteredOffset: Float = 7.5F,
   opacity: Float = 1.0F
) {
   public final val centeredX: Boolean
   public final val doubleCenteredOffset: Float
   public final val opacity: Float
   public final val secondaryOffset: Float
   public final val secondaryType: ElementalType?
   public final val small: Boolean
   public final val type: ElementalType
   public final val x: Number
   public final val y: Number

   init {
      this.x = x;
      this.y = y;
      this.type = type;
      this.secondaryType = secondaryType;
      this.centeredX = centeredX;
      this.small = small;
      this.secondaryOffset = secondaryOffset;
      this.doubleCenteredOffset = doubleCenteredOffset;
      this.opacity = opacity;
   }

   public fun render(context: GuiGraphics) {
      val diameter: Int = if (this.small) 18 else 36;
      val offsetX: Float = if (this.centeredX)
         (if (this.small) 18 else 36) / 2 * 0.5F + (if (this.secondaryType != null) this.doubleCenteredOffset else 0.0F)
         else
         0.0F;
      if (this.secondaryType != null) {
         val var10000: PoseStack = context.m_280168_();
         val var10001: ResourceLocation = if (this.small) smallTypesResource else typesResource;
         val var10002: java.lang.Number = (this.x.floatValue() + this.secondaryOffset - offsetX) / 0.5F;
         val var10003: java.lang.Number = this.y.floatValue() / 0.5F;
         val var10004: java.lang.Number = diameter;
         val var10005: java.lang.Number = diameter;
         val var10006: Float = diameter;
         val var10007: ElementalType = this.secondaryType;
         GuiUtilsKt.blitk$default(
            var10000,
            var10001,
            var10002,
            var10003,
            var10004,
            var10005,
            (double)(var10006 * (float)var10007.getTextureXMultiplier()) + 0.1,
            null,
            diameter * 18,
            null,
            null,
            null,
            null,
            null,
            this.opacity,
            false,
            0.5F,
            48768,
            null
         );
      }

      val var4: PoseStack = context.m_280168_();
      GuiUtilsKt.blitk$default(
         var4,
         if (this.small) smallTypesResource else typesResource,
         (this.x.floatValue() - offsetX) / 0.5F,
         this.y.floatValue() / 0.5F,
         diameter,
         diameter,
         (double)((float)diameter * (float)this.type.getTextureXMultiplier()) + 0.1,
         null,
         diameter * 18,
         null,
         null,
         null,
         null,
         null,
         this.opacity,
         false,
         0.5F,
         48768,
         null
      );
   }

   public companion object {
      private const val SCALE: Float
      private const val TYPE_ICON_DIAMETER: Int
      private final val smallTypesResource: ResourceLocation
      private final val typesResource: ResourceLocation
   }
}
