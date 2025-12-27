package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.phys.shapes.VoxelShape

public class AndCondition(conditionOne: MultiblockCondition, conditionTwo: MultiblockCondition) : MultiblockCondition {
   public final val conditionOne: MultiblockCondition
   public final val conditionTwo: MultiblockCondition

   init {
      this.conditionOne = conditionOne;
      this.conditionTwo = conditionTwo;
   }

   public override fun test(world: ServerLevel, box: VoxelShape): Boolean {
      return this.conditionOne.test(world, box) and this.conditionTwo.test(world, box);
   }
}
