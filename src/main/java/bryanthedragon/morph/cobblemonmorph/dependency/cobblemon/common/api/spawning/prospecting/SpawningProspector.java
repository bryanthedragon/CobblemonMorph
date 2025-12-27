package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea

public interface SpawningProspector {
   public abstract fun prospect(spawner: Spawner, area: SpawningArea): WorldSlice {
   }
}
