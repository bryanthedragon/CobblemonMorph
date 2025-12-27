package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation

public class MoveCategoryIcon(x: Number, y: Number, category: DamageCategory, opacity: Float = 1.0F) {
   public final val category: DamageCategory
   public final val opacity: Float
   public final val x: Number
   public final val y: Number

   init {
      this.x = x;
      this.y = y;
      this.category = category;
      this.opacity = opacity;
   }

   public fun render(context: GuiGraphics) {
      val var2: PoseStack = context.m_280168_();
      val var3: ResourceLocation = categoriesResource;
      val var4: Float = this.x.floatValue() / 0.5F;
      val var5: Float = this.y.floatValue() / 0.5F;
      val var6: Int = 16 * this.category.getTextureXMultiplier();
      val var7: Float = this.opacity;
      GuiUtilsKt.blitk$default(var2, var3, var4, var5, 16, 24, null, var6, null, 48, null, null, null, null, var7, false, 0.5F, 48448, null);
   }

   public companion object {
      private const val HEIGHT: Int
      private const val SCALE: Float
      private const val WIDTH: Int
      private final val categoriesResource: ResourceLocation
   }
}
