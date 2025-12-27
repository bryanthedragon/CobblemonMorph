package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;

import net.minecraft.server.level.ServerLevel;

public class SpawningContextInput(cause: SpawnCause, world: ServerLevel) {
   public final val cause: SpawnCause
   public final val world: ServerLevel

   init {
      this.cause = cause;
      this.world = world;
   }
}
