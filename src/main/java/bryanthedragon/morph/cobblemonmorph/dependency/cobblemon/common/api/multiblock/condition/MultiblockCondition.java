package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.shapes.VoxelShape

public interface MultiblockCondition {
   public abstract fun test(world: ServerLevel, box: VoxelShape): Boolean {
   }
}
