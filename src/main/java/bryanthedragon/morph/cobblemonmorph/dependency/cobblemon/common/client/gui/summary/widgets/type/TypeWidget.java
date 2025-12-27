package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.type

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public abstract class TypeWidget : SoundlessWidget {
   open fun TypeWidget(pX: Int, pY: Int, pWidth: Int, pHeight: Int, pMessage: Component) {
      super(pX, pY, pWidth, pHeight, pMessage);
   }

   public fun renderType(type: ElementalType, pMatrixStack: PoseStack, pX: Int = this.m_252754_(), pY: Int = this.m_252907_()) {
      val var5: ResourceLocation = typeResource;
      val var6: Double = pX + 0.5;
      val var8: Int = this.f_93618_;
      val var9: Int = this.f_93619_;
      val var10: Double = (float)this.f_93618_ * type.getTextureXMultiplier() + 0.1;
      val var12: Int = this.f_93618_ * 18;
      GuiUtilsKt.blitk$default(pMatrixStack, var5, var6, pY, var9, var8, var10, null, var12, null, null, null, null, null, null, false, 0.0F, 130688, null);
   }

   public fun renderType(mainType: ElementalType, secondaryType: ElementalType, pMatrixStack: PoseStack) {
      renderType$default(this, secondaryType, pMatrixStack, this.m_252754_() + 16, 0, 8, null);
      renderType$default(this, mainType, pMatrixStack, 0, 0, 12, null);
   }

   public companion object {
      private const val OFFSET: Double
      public final val typeResource: ResourceLocation
   }
}
