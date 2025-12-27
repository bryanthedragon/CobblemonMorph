package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry

import net.minecraft.core.BlockPos
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.state.BlockState

public interface GrowthFactor {
   public abstract fun validateArguments() {
   }

   public abstract fun isValid(world: LevelReader, state: BlockState, pos: BlockPos): Boolean {
   }

   public abstract fun yield(): Int {
   }

   public abstract fun minYield(): Int {
   }

   public abstract fun maxYield(): Int {
   }
}
