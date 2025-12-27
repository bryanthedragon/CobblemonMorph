package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BucketPrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.ContextPrecalculation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider

public object CobblemonSpawnPools {
   public final lateinit var WORLD_SPAWN_POOL: SpawnPool

   public fun load() {
      this.setWORLD_SPAWN_POOL(
         CobblemonDataProvider.INSTANCE.register(new SpawnPool("world").addPrecalculators(ContextPrecalculation.INSTANCE, BucketPrecalculation.INSTANCE))
      );
   }
}
