package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonPartyLockedKeyBinding
import com.mojang.blaze3d.platform.InputConstants.Type

public object SummaryBinding : CobblemonPartyLockedKeyBinding("key.cobblemon.summary", Type.KEYSYM, 77, "key.cobblemon.categories.cobblemon") {
   public override fun onPress() {
      if (CobblemonClient.INSTANCE.getStorage().getSelectedSlot() >= 0) {
         try {
            Summary.Companion
               .open(CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots(), true, CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         } catch (var2: Exception) {
            Cobblemon.INSTANCE.getLOGGER().debug("Failed to open the summary from the Summary keybind", var2);
         }
      }
   }
}
