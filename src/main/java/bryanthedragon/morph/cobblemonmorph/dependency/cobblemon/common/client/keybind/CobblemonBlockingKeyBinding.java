package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import com.mojang.blaze3d.platform.InputConstants.Type
import net.minecraft.client.Minecraft

public abstract class CobblemonBlockingKeyBinding : CobblemonKeyBinding {
   public final var timeDown: Float
   public final var wasDown: Boolean

   open fun CobblemonBlockingKeyBinding(name: java.lang.String, type: Type, key: Int, category: java.lang.String) {
      super(name, type, key, category);
   }

   public open fun onRelease() {
   }

   public override fun onTick() {
      if (this.m_90857_() && !this.wasDown) {
         this.wasDown = true;
         this.timeDown = 0.0F;
         this.onPress();
      } else if (!this.m_90857_() && this.wasDown) {
         this.onRelease();
         this.wasDown = false;
      } else if (!this.m_90857_()) {
         this.wasDown = false;
      } else if (this.wasDown) {
         this.timeDown = this.timeDown + Minecraft.m_91087_().m_91296_();
      }
   }
}
