package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public interface PreEmptsExplosion {
   public abstract fun whenExploded(world: Level, state: BlockState, pos: BlockPos) {
   }
}
