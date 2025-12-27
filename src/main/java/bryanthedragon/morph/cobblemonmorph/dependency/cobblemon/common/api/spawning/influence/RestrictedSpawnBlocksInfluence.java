package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.Block

public open class RestrictedSpawnBlocksInfluence : SpawningInfluence {
   private final val restrictedBlocks: TagKey<Block> = BlockTags.f_13034_

   public override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      if (!(detail.getType() == "pokemon")) {
         return true;
      } else {
         val pokemonSpawnPos: BlockPos = ctx.getPosition();
         return !ctx.getWorld().m_8055_(pokemonSpawnPos.m_175288_(pokemonSpawnPos.m_123342_() + 1)).m_204336_(this.restrictedBlocks);
      }
   }

   override fun isExpired(): Boolean {
      return SpawningInfluence.DefaultImpls.isExpired(this);
   }

   override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      return SpawningInfluence.DefaultImpls.affectWeight(this, detail, ctx, weight);
   }

   override fun affectAction(action: SpawnAction<?>) {
      SpawningInfluence.DefaultImpls.affectAction(this, action);
   }

   override fun affectSpawn(entity: Entity) {
      SpawningInfluence.DefaultImpls.affectSpawn(this, entity);
   }

   override fun affectBucketWeight(bucket: SpawnBucket, weight: Float): Float {
      return SpawningInfluence.DefaultImpls.affectBucketWeight(this, bucket, weight);
   }

   override fun isAllowedPosition(world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<?, ?>): Boolean {
      return SpawningInfluence.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
   }
}
