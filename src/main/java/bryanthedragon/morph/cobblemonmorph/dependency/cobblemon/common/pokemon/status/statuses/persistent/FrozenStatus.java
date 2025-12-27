package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt

public class FrozenStatus : PersistentStatus(
      MiscUtilsKt.cobblemonResource("frozen"), "frz", "cobblemon.status.frozen.apply", "cobblemon.status.frozen.cure", new IntRange(180, 300)
   )
