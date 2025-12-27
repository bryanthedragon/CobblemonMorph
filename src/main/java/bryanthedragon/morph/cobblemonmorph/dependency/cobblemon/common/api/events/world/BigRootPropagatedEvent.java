package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.world

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.state.BlockState

public class BigRootPropagatedEvent(world: ServerLevel, pos: BlockPos, newRootPosition: BlockPos, resultingSpread: BlockState) : Cancelable {
   public final var newRootPosition: BlockPos
   public final val pos: BlockPos
   public final var resultingSpread: BlockState
   public final val world: ServerLevel

   init {
      this.world = world;
      this.pos = pos;
      this.newRootPosition = newRootPosition;
      this.resultingSpread = resultingSpread;
   }
}
