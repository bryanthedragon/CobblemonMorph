package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.Vec3

public class EntityTraceResult<T extends Entity>(location: Vec3, entities: Iterable<Any>) {
   public final val entities: Iterable<Any>
   public final val location: Vec3

   init {
      this.location = location;
      this.entities = entities;
   }
}
