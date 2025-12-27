package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public interface ShearableBlock {
   public abstract fun attemptShear(world: Level, state: BlockState, pos: BlockPos, successCallback: () -> Unit = ...): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls
}
