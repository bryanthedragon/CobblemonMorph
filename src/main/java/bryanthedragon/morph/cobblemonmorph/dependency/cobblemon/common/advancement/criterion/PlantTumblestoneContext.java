package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock
import net.minecraft.core.BlockPos

public open class PlantTumblestoneContext(pos: BlockPos, tumbleStoneBlock: TumblestoneBlock) {
   public final var pos: BlockPos
   public final var tumbleStoneBlock: TumblestoneBlock

   init {
      this.pos = pos;
      this.tumbleStoneBlock = tumbleStoneBlock;
   }
}
