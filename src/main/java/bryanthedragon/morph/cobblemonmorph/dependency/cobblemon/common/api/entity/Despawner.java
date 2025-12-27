package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity

import net.minecraft.world.entity.Entity

public interface Despawner<T extends Entity> {
   public abstract fun beginTracking(entity: Any) {
   }

   public abstract fun shouldDespawn(entity: Any): Boolean {
   }
}
