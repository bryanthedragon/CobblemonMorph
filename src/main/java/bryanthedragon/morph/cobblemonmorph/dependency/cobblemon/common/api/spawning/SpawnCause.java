package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import net.minecraft.world.entity.Entity;

public class SpawnCause(spawner: Spawner, bucket: SpawnBucket, entity: Entity? = null) {
   public final val bucket: SpawnBucket
   public final val entity: Entity?
   public final val spawner: Spawner

   init {
      this.spawner = spawner;
      this.bucket = bucket;
      this.entity = entity;
   }
}
