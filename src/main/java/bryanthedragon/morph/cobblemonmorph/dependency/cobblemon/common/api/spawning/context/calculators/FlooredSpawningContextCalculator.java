package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.FlooredSpawningContext;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface FlooredSpawningContextCalculator<T extends FlooredSpawningContext> : AreaSpawningContextCalculator<T> {
   public val baseCondition: (BlockState) -> Boolean
   public val surroundingCondition: (BlockState) -> Boolean

   public override fun fits(input: AreaSpawningInput): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <T extends FlooredSpawningContext> fits(`$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput): Boolean {
         val floorState: BlockState = WorldSlice.getBlockState$default(input.getSlice(), input.getPosition(), null, 2, null);
         val var10000: WorldSlice = input.getSlice();
         val var10001: BlockPos = input.getPosition().m_7494_();
         return `$this`.getBaseCondition().invoke(floorState) as java.lang.Boolean
            && `$this`.getSurroundingCondition().invoke(WorldSlice.getBlockState$default(var10000, var10001, null, 2, null)) as java.lang.Boolean;
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getDepth(
         `$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int
      ): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getDepth(`$this`, input, condition, maximum);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getHeight(
         `$this`: FlooredSpawningContextCalculator<T>,
         input: AreaSpawningInput,
         condition: (BlockState?) -> java.lang.Boolean,
         maximum: Int,
         offsetX: Int,
         offsetY: Int,
         offsetZ: Int
      ): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getHeight(`$this`, input, condition, maximum, offsetX, offsetY, offsetZ);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getHorizontalSpace(
         `$this`: FlooredSpawningContextCalculator<T>,
         input: AreaSpawningInput,
         condition: (BlockState?) -> java.lang.Boolean,
         maximum: Int,
         offsetX: Int,
         offsetY: Int,
         offsetZ: Int
      ): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getHorizontalSpace(`$this`, input, condition, maximum, offsetX, offsetY, offsetZ);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getLight(`$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput, elseLight: Int): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getLight(`$this`, input, elseLight);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getSkyLight(`$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput, elseLight: Int): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getSkyLight(`$this`, input, elseLight);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getCanSeeSky(`$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput): Boolean {
         return AreaSpawningContextCalculator.DefaultImpls.getCanSeeSky(`$this`, input);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getSkySpaceAbove(`$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput): Int {
         return AreaSpawningContextCalculator.DefaultImpls.getSkySpaceAbove(`$this`, input);
      }

      @JvmStatic
      fun <T extends FlooredSpawningContext> getNearbyBlocks(
         `$this`: FlooredSpawningContextCalculator<T>, input: AreaSpawningInput, horizontalRadius: Int, verticalRadius: Int
      ): MutableList<BlockState> {
         return AreaSpawningContextCalculator.DefaultImpls.getNearbyBlocks(`$this`, input, horizontalRadius, verticalRadius);
      }
   }
}
