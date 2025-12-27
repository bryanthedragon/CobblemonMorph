package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding
import com.mojang.blaze3d.platform.InputConstants.Type

public object UpShiftPartyBinding : CobblemonKeyBinding("key.cobblemon.upshiftparty", Type.KEYSYM, 265, "key.cobblemon.categories.cobblemon") {
   public override fun onPress() {
      CobblemonClient.INSTANCE.getStorage().shiftSelected(false);
   }
}
