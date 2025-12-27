package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import java.util.LinkedHashMap

public interface SpawnDetailSelector {
   public abstract fun selects(spawnDetail: SpawnDetail): Boolean {
   }

   public companion object {
      public final val types: MutableMap<String, Class<out SpawnDetailSelector>> = (new LinkedHashMap()) as java.util.Map
   }
}
