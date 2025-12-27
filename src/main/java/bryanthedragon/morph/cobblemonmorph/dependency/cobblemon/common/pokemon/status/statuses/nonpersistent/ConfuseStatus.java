package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class ConfuseStatus : VolatileStatus(
      MiscUtilsKt.cobblemonResource("confused"), "confusion", "cobblemon.battle.confusion_start", "cobblemon.battle.confusion_snapped"
   )
