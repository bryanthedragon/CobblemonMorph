package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding
import com.mojang.blaze3d.platform.InputConstants.Type

public object HidePartyBinding : CobblemonKeyBinding("key.cobblemon.hideparty", Type.KEYSYM, 79, "key.cobblemon.categories.cobblemon") {
   public final var shouldHide: Boolean

   public override fun onPress() {
      shouldHide = !shouldHide;
   }
}
