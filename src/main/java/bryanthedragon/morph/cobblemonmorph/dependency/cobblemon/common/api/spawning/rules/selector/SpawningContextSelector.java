package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import java.util.LinkedHashMap

public interface SpawningContextSelector {
   public abstract fun selects(ctx: SpawningContext): Boolean {
   }

   public companion object {
      public final val types: MutableMap<String, Class<out SpawningContextSelector>> = (new LinkedHashMap()) as java.util.Map
   }
}
