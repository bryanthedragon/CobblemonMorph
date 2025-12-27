package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext

public object AllSpawningContextSelector : SpawningContextSelector {
   public override fun selects(ctx: SpawningContext): Boolean {
      return true;
   }
}
