package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.settings

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon

public object ServerSettings {
   public final var displayEntityLevelLabel: Boolean = Cobblemon.INSTANCE.getConfig().getDisplayEntityLevelLabel()
   public final var preventCompletePartyDeposit: Boolean = Cobblemon.INSTANCE.getConfig().getPreventCompletePartyDeposit()
}
