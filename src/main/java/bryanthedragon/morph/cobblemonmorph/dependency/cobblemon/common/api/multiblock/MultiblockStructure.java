package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

public interface MultiblockStructure {
   public val controllerBlockPos: BlockPos

   public abstract fun onUse(
      blockState: BlockState,
      world: Level,
      blockPos: BlockPos,
      player: Player,
      interactionHand: InteractionHand,
      blockHitResult: BlockHitResult
   ): InteractionResult {
   }

   public abstract fun onBreak(world: Level, pos: BlockPos, state: BlockState, player: Player?) {
   }

   public abstract fun tick(world: Level) {
   }

   public abstract fun syncToClient(world: Level) {
   }

   public abstract fun markDirty(world: Level) {
   }

   public abstract fun writeToNbt(): CompoundTag {
   }

   public open fun getComparatorOutput(state: BlockState, world: Level?, pos: BlockPos?): Int {
   }

   public abstract fun markRemoved(world: Level) {
   }

   public abstract fun onTriggerEvent(state: BlockState?, world: ServerLevel?, pos: BlockPos?, random: RandomSource?) {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getComparatorOutput(`$this`: MultiblockStructure, state: BlockState, world: Level?, pos: BlockPos?): Int {
         return 0;
      }
   }
}
