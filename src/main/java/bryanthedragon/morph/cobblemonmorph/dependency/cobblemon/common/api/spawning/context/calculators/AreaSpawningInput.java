package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;

import net.minecraft.core.BlockPos;

public open class AreaSpawningInput(spawner: Spawner, position: BlockPos, slice: WorldSlice) : SpawningContextInput(slice.getCause(), slice.getWorld()) {
   public final var position: BlockPos
   public final val slice: WorldSlice
   public final val spawner: Spawner

   init {
      this.spawner = spawner;
      this.position = position;
      this.slice = slice;
   }
}
