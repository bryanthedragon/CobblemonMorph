package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import com.mojang.blaze3d.platform.InputConstants.Type

public abstract class CobblemonCustomKeyBinding : CobblemonKeyBinding {
   public final var run: Runnable?

   open fun CobblemonCustomKeyBinding(name: java.lang.String, type: Type, key: Int, category: java.lang.String) {
      super(name, type, key, category);
   }

   public override fun onPress() {
      if (this.run != null) {
         this.run.run();
      }
   }
}
