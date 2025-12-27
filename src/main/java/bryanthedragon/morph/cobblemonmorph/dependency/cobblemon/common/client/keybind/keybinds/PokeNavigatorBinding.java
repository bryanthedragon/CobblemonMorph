package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonPartyLockedKeyBinding
import com.mojang.blaze3d.platform.InputConstants.Type

public object PokeNavigatorBinding : CobblemonPartyLockedKeyBinding("key.cobblemon.pokenavigator", Type.KEYSYM, 78, "key.cobblemon.categories.cobblemon") {
   public override fun onPress() {
      try {
         Summary.Companion.open(CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots(), true, CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
      } catch (var2: Exception) {
         Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the PokeNav keybind", var2);
      }
   }
}
