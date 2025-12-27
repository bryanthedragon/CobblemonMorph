package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class SleepStatus : PersistentStatus(
      MiscUtilsKt.cobblemonResource("sleep"), "slp", "cobblemon.status.sleep.apply", "cobblemon.status.sleep.cure", new IntRange(180, 300)
   )
