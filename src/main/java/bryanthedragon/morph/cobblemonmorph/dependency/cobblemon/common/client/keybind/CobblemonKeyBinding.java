package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import com.mojang.blaze3d.platform.InputConstants.Type
import net.minecraft.client.KeyMapping

public abstract class CobblemonKeyBinding : KeyMapping {
   open fun CobblemonKeyBinding(name: java.lang.String, type: Type, key: Int, category: java.lang.String) {
      super(name, type, key, category);
   }

   public abstract fun onPress() {
   }

   public open fun onTick() {
      if (this.m_90859_()) {
         this.onPress();
      }
   }
}
