package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.state.BlockState

public interface Mulchable {
   public abstract fun canHaveMulchApplied(world: ServerLevel, pos: BlockPos, state: BlockState, variant: MulchVariant): Boolean {
   }

   public abstract fun applyMulch(world: ServerLevel, random: RandomSource, pos: BlockPos, state: BlockState, variant: MulchVariant) {
   }
}
