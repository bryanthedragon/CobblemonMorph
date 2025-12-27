package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class ParalysisStatus : PersistentStatus(
      MiscUtilsKt.cobblemonResource("paralysis"), "par", "cobblemon.status.paralysis.apply", "cobblemon.status.paralysis.cure", new IntRange(180, 300)
   )
