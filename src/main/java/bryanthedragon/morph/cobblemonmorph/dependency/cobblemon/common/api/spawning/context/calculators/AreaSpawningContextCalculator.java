package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;

import net.minecraft.world.level.block.state.BlockState;

public interface AreaSpawningContextCalculator<O extends AreaSpawningContext> : SpawningContextCalculator<AreaSpawningInput, O> {
   public abstract fun fits(input: AreaSpawningInput): Boolean {
   }

   public open fun getDepth(input: AreaSpawningInput, condition: (BlockState) -> Boolean, maximum: Int): Int {
   }

   public open fun getHeight(
      input: AreaSpawningInput,
      condition: (BlockState) -> Boolean,
      maximum: Int,
      offsetX: Int = ...,
      offsetY: Int = ...,
      offsetZ: Int = ...
   ): Int {
   }

   public open fun getHorizontalSpace(
      input: AreaSpawningInput,
      condition: (BlockState) -> Boolean,
      maximum: Int,
      offsetX: Int = ...,
      offsetY: Int = ...,
      offsetZ: Int = ...
   ): Int {
   }

   public open fun getLight(input: AreaSpawningInput, elseLight: Int = ...): Int {
   }

   public open fun getSkyLight(input: AreaSpawningInput, elseLight: Int = ...): Int {
   }

   public open fun getCanSeeSky(input: AreaSpawningInput): Boolean {
   }

   public open fun getSkySpaceAbove(input: AreaSpawningInput): Int {
   }

   public open fun getNearbyBlocks(input: AreaSpawningInput, horizontalRadius: Int = ..., verticalRadius: Int = ...): List<BlockState> {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun <O extends AreaSpawningContext> getDepth(
         `$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput, condition: (BlockState?) -> java.lang.Boolean, maximum: Int
      ): Int {
         return input.getSlice()
            .depthSpace(input.getPosition().m_123341_(), input.getPosition().m_123342_(), input.getPosition().m_123343_(), condition, maximum);
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getHeight(
         `$this`: AreaSpawningContextCalculator<O>,
         input: AreaSpawningInput,
         condition: (BlockState?) -> java.lang.Boolean,
         maximum: Int,
         offsetX: Int,
         offsetY: Int,
         offsetZ: Int
      ): Int {
         return input.getSlice()
            .heightSpace(
               input.getPosition().m_123341_() + offsetX,
               input.getPosition().m_123342_() + offsetY,
               input.getPosition().m_123343_() + offsetZ,
               condition,
               maximum
            );
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getHorizontalSpace(
         `$this`: AreaSpawningContextCalculator<O>,
         input: AreaSpawningInput,
         condition: (BlockState?) -> java.lang.Boolean,
         maximum: Int,
         offsetX: Int,
         offsetY: Int,
         offsetZ: Int
      ): Int {
         return input.getSlice()
            .horizontalSpace(
               input.getPosition().m_123341_() + offsetX,
               input.getPosition().m_123342_() + offsetY,
               input.getPosition().m_123343_() + offsetZ,
               condition,
               maximum
            );
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getLight(`$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput, elseLight: Int): Int {
         return input.getSlice().getLight(input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), elseLight);
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getSkyLight(`$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput, elseLight: Int): Int {
         return input.getSlice().getSkyLight(input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), elseLight);
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getCanSeeSky(`$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput): Boolean {
         return WorldSlice.canSeeSky$default(
            input.getSlice(), input.getPosition().m_123341_(), input.getPosition().m_123342_() + 1, input.getPosition().m_123343_(), false, 8, null
         );
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getSkySpaceAbove(`$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput): Int {
         return input.getSlice().skySpaceAbove(input.getPosition().m_123341_(), input.getPosition().m_123342_(), input.getPosition().m_123343_());
      }

      @JvmStatic
      fun <O extends AreaSpawningContext> getNearbyBlocks(
         `$this`: AreaSpawningContextCalculator<O>, input: AreaSpawningInput, horizontalRadius: Int, verticalRadius: Int
      ): MutableList<BlockState> {
         return input.getSlice().nearbyBlocks(input.getPosition(), horizontalRadius, verticalRadius);
      }
   }
}
