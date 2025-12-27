package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component

import com.bedrockk.molang.Expression
import com.bedrockk.molang.ast.BooleanExpression
import com.bedrockk.molang.runtime.MoLangRuntime
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
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

public class FilterRuleComponent : SpawnRuleComponent {
   public final val allow: Expression = (new BooleanExpression(true)) as Expression
   public final val contextSelector: SpawningContextSelector = AllSpawningContextSelector.INSTANCE as SpawningContextSelector
   public final val runtime: MoLangRuntime = MoLangFunctions.INSTANCE.setup(new MoLangRuntime())
   public final val spawnSelector: SpawnDetailSelector = AllSpawnDetailSelector.INSTANCE as SpawnDetailSelector

   public override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      val var10000: Boolean;
      if (this.spawnSelector.selects(detail) && this.contextSelector.selects(ctx)) {
         this.runtime.getEnvironment().setSimpleVariable("spawn", detail.getStruct());
         this.runtime.getEnvironment().setSimpleVariable("context", ctx.getOrSetupStruct());
         var10000 = MoLangExtensionsKt.resolveBoolean(this.runtime, this.allow);
      } else {
         var10000 = true;
      }

      return var10000;
   }

   override fun isExpired(): Boolean {
      return SpawnRuleComponent.DefaultImpls.isExpired(this);
   }

   override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      return SpawnRuleComponent.DefaultImpls.affectWeight(this, detail, ctx, weight);
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
