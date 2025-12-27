package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.nonpersistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.VolatileStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class AttractStatus : VolatileStatus(
      MiscUtilsKt.cobblemonResource("attract"), "attract", "cobblemon.battle.attract_start", "cobblemon.battle.attract_snapped"
   )
