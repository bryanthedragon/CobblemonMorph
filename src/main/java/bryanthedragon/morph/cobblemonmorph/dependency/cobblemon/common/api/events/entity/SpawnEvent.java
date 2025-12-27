package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.entity

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import net.minecraft.world.entity.Entity

public class SpawnEvent<T extends Entity>(entity: Any, ctx: SpawningContext) : Cancelable {
   public final val ctx: SpawningContext
   public final val entity: Any

   init {
      this.entity = (T)entity;
      this.ctx = ctx;
   }
}
