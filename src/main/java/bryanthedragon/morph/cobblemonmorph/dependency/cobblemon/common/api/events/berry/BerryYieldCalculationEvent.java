package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

public class BerryYieldCalculationEvent(berry: Berry,
      world: Level,
      state: BlockState,
      pos: BlockPos,
      placer: LivingEntity?,
      yield: Int,
      passedGrowthFactors: Collection<GrowthFactor>
   ) :
   BerryEvent {
   public open val berry: Berry
   public final val passedGrowthFactors: Collection<GrowthFactor>
   public final val placer: LivingEntity?
   public final val pos: BlockPos
   public final val state: BlockState
   public final val world: Level

   public final var yield: Int
      public final set(value) {
         val max: Int = this.getBerry().maxYield();
         if (value > max) {
            throw new IllegalArgumentException("Cannot set the berry yield for ${this.getBerry().getIdentifier()} above $max");
         } else if (value < 0) {
            throw new IllegalArgumentException("A berry tree cannot yield a negative amount of berries");
         } else {
            this.yield = value;
         }
      }


   init {
      this.berry = berry;
      this.world = world;
      this.state = state;
      this.pos = pos;
      this.placer = placer;
      this.passedGrowthFactors = passedGrowthFactors;
      this.yield = yield;
   }
}
