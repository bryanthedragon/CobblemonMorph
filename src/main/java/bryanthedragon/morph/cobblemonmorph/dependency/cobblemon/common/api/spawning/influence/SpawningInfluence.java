package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity

public interface SpawningInfluence {
   public open fun isExpired(): Boolean {
   }

   public open fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
   }

   public open fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
   }

   public open fun affectAction(action: SpawnAction<*>) {
   }

   public open fun affectSpawn(entity: Entity) {
   }

   public open fun affectBucketWeight(bucket: SpawnBucket, weight: Float): Float {
   }

   public open fun isAllowedPosition(world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<*, *>): Boolean {
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun isExpired(`$this`: SpawningInfluence): Boolean {
         return false;
      }

      @JvmStatic
      fun affectSpawnable(`$this`: SpawningInfluence, detail: SpawnDetail, ctx: SpawningContext): Boolean {
         return true;
      }

      @JvmStatic
      fun affectWeight(`$this`: SpawningInfluence, detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
         return weight;
      }

      @JvmStatic
      fun affectAction(`$this`: SpawningInfluence, action: SpawnAction<?>) {
      }

      @JvmStatic
      fun affectSpawn(`$this`: SpawningInfluence, entity: Entity) {
      }

      @JvmStatic
      fun affectBucketWeight(`$this`: SpawningInfluence, bucket: SpawnBucket, weight: Float): Float {
         return weight;
      }

      @JvmStatic
      fun isAllowedPosition(`$this`: SpawningInfluence, world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<?, ?>): Boolean {
         return true;
      }
   }
}
