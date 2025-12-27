package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.phys.Vec3

public class TraceResult(location: Vec3, blockPos: BlockPos, direction: Direction) {
   public final val blockPos: BlockPos
   public final val direction: Direction
   public final val location: Vec3

   init {
      this.location = location;
      this.blockPos = blockPos;
      this.direction = direction;
   }
}
