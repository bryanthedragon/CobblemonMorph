package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public open class SubmergedSpawningContext(cause: SpawnCause, world: ServerLevel, position: BlockPos, light: Int, skyLight: Int, canSeeSky: Boolean, influences: MutableList<SpawningInfluence>, height: Int, depth: Int, nearbyBlocks: List<BlockState>, slice: WorldSlice) : AreaSpawningContext(cause, world, position, light, skyLight, canSeeSky, influences, height, nearbyBlocks, slice) {
   public final val depth: Int
   public final val fluid: FluidState

   init {
      this.depth = depth;
      this.fluid = WorldSlice.getBlockState$default(slice, position.m_123341_(), position.m_123342_(), position.m_123343_(), null, 8, null).m_60819_();
   }

   public override fun isSafeSpace(world: ServerLevel, pos: BlockPos, state: BlockState): Boolean {
      return state.m_60819_().m_76152_() == this.fluid.m_76152_();
   }
}
