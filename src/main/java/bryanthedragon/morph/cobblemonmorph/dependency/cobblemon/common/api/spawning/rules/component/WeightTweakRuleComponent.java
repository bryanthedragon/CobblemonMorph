package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component

import com.bedrockk.molang.Expression
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.value.DoubleValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.AllSpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.AllSpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawnDetailSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.selector.SpawningContextSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity

public class WeightTweakRuleComponent : SpawnRuleComponent {
   public final val contextSelector: SpawningContextSelector = AllSpawningContextSelector.INSTANCE as SpawningContextSelector
   public final val runtime: MoLangRuntime
   public final val spawnSelector: SpawnDetailSelector = AllSpawnDetailSelector.INSTANCE as SpawnDetailSelector
   public final val weight: Expression

   public override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      val var10000: Float;
      if (this.spawnSelector.selects(detail) && this.contextSelector.selects(ctx)) {
         this.runtime.getEnvironment().setSimpleVariable("spawn", detail.getStruct());
         this.runtime.getEnvironment().setSimpleVariable("weight", new DoubleValue((double)weight));
         var10000 = MoLangExtensionsKt.resolveFloat(this.runtime, this.weight);
      } else {
         var10000 = weight;
      }

      return var10000;
   }

   override fun isExpired(): Boolean {
      return SpawnRuleComponent.DefaultImpls.isExpired(this);
   }

   override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      return SpawnRuleComponent.DefaultImpls.affectSpawnable(this, detail, ctx);
   }

   override fun affectAction(action: SpawnAction<?>) {
      SpawnRuleComponent.DefaultImpls.affectAction(this, action);
   }

   override fun affectSpawn(entity: Entity) {
      SpawnRuleComponent.DefaultImpls.affectSpawn(this, entity);
   }

   override fun affectBucketWeight(bucket: SpawnBucket, weight: Float): Float {
      return SpawnRuleComponent.DefaultImpls.affectBucketWeight(this, bucket, weight);
   }

   override fun isAllowedPosition(world: ServerLevel, pos: BlockPos, contextCalculator: SpawningContextCalculator<?, ?>): Boolean {
      return SpawnRuleComponent.DefaultImpls.isAllowedPosition(this, world, pos, contextCalculator);
   }
}
