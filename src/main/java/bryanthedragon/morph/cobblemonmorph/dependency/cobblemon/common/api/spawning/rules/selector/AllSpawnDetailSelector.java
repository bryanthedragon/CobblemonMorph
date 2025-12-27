package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail

public object AllSpawnDetailSelector : SpawnDetailSelector {
   public override fun selects(spawnDetail: SpawnDetail): Boolean {
      return true;
   }
}
