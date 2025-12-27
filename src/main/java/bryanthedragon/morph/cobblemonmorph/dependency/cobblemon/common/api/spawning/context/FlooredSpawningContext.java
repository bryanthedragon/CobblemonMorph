package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public abstract class FlooredSpawningContext : AreaSpawningContext {
   public final val baseBlock: BlockState

   open fun FlooredSpawningContext(cause: SpawnCause, world: ServerLevel, position: BlockPos, light: Int, skyLight: Int, canSeeSky: Boolean, influences: MutableList<SpawningInfluence>, height: Int, nearbyBlocks: MutableList<BlockState>, slice: WorldSlice) {
      super(cause, world, position, light, skyLight, canSeeSky, influences, height, nearbyBlocks, slice);
      this.baseBlock = WorldSlice.getBlockState$default(slice, position.m_123341_(), position.m_123342_(), position.m_123343_(), null, 8, null);
   }
}
