package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel

public open class FixedAreaSpawner(name: String,
   spawns: SpawnPool,
   manager: SpawnerManager,
   world: ServerLevel,
   position: BlockPos,
   horizontalRadius: Int,
   verticalRadius: Int,
   ticksBetweenSpawns: Float = 20.0F
) : AreaSpawner(name, spawns, manager) {
   public final val horizontalRadius: Int
   public final val position: BlockPos
   public open var ticksBetweenSpawns: Float
   public final val verticalRadius: Int
   public final val world: ServerLevel

   init {
      this.world = world;
      this.position = position;
      this.horizontalRadius = horizontalRadius;
      this.verticalRadius = verticalRadius;
      this.ticksBetweenSpawns = ticksBetweenSpawns;
   }

   public override fun getArea(cause: SpawnCause): SpawningArea? {
      val basePos: BlockPos = this.position.m_7918_(-this.horizontalRadius, -this.verticalRadius, -this.horizontalRadius);
      return new SpawningArea(
         cause,
         this.world,
         basePos.m_123341_(),
         basePos.m_123342_(),
         basePos.m_123343_(),
         this.horizontalRadius * 2 + 1,
         this.verticalRadius * 2 + 1,
         this.horizontalRadius * 2 + 1
      );
   }
}
