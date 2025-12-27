package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class BurnStatus : PersistentStatus(
      MiscUtilsKt.cobblemonResource("burn"), "brn", "cobblemon.status.burn.apply", "cobblemon.status.burn.cure", new IntRange(180, 300)
   )
