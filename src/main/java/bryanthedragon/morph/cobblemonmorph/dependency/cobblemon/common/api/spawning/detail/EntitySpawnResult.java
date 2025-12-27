package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail

import net.minecraft.world.entity.Entity

public class EntitySpawnResult(entities: List<Entity>) {
   public final val entities: List<Entity>

   init {
      this.entities = entities;
   }
}
