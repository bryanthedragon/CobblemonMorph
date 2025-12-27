package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import java.util.LinkedHashMap
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity

public interface SpawnRuleComponent : SpawningInfluence {
   public companion object {
      public final val types: MutableMap<String, Class<out SpawnRuleComponent>> = (new LinkedHashMap()) as java.util.Map
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun isExpired(`$this`: SpawnRuleComponent): Boolean {
         return SpawningInfluence.DefaultImpls.isExpired(`$this`);
      }

      @JvmStatic
      fun affectSpawnable(`$this`: SpawnRuleComponent, detail: SpawnDetail, ctx: SpawningContext): Boolean {
         return SpawningInfluence.DefaultImpls.affectSpawnable(`$this`, detail, ctx);
      }

      @JvmStatic
      fun affectWeight(`$this`: SpawnRuleComponent, detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
         return SpawningInfluence.DefaultImpls.affectWeight(`$this`, detail, ctx, weight);
      }

      @JvmStatic
      fun affectAction(`$this`: SpawnRuleComponent, action: SpawnAction<?>) {
         SpawningInfluence.DefaultImpls.affectAction(`$this`, action);
      }

      @JvmStatic
      fun affectSpawn(`$this`: SpawnRuleComponent, entity: Entity) {
         SpawningInfluence.DefaultImpls.affectSpawn(`$this`, entity);
      }

      @JvmStatic
      fun affectBucketWeight(`$this`: SpawnRuleComponent, bucket: SpawnBucket, weight: Float): Float {
         return SpawningInfluence.DefaultImpls.affectBucketWeight(`$this`, bucket, weight);
      }

      @JvmStatic
      fun isAllowedPosition(`$this`: SpawnRuleComponent, world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<?, ?>): Boolean {
         return SpawningInfluence.DefaultImpls.isAllowedPosition(`$this`, world, pos, contextCalculator);
      }
   }
}
