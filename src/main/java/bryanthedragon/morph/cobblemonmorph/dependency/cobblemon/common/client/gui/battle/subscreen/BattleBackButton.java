package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.blaze3d.vertex.PoseStack

public class BattleBackButton(x: Float, y: Float) {
   public final val x: Float
   public final val y: Float

   init {
      this.x = x;
      this.y = y;
   }

   public fun render(matrices: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
      GuiUtilsKt.blitk$default(
         matrices,
         MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_back.png"),
         this.x * (float)2,
         this.y * (float)2,
         34,
         58,
         null,
         if (this.isHovered((double)mouseX, (double)mouseY)) 34 else 0,
         null,
         68,
         null,
         null,
         null,
         null,
         null,
         false,
         0.5F,
         64832,
         null
      );
   }

   public fun isHovered(mouseX: Double, mouseY: Double): Boolean {
      return this.x <= (float)mouseX && (float)mouseX <= this.x + 29.0F && this.y <= (float)mouseY && (float)mouseY <= this.y + 17.0F;
   }

   public companion object {
      public const val HEIGHT: Int
      public const val SCALE: Float
      public const val WIDTH: Int
   }
}
