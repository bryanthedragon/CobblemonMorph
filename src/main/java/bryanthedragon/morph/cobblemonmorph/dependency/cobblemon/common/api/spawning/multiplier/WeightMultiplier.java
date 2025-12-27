package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity

@SourceDebugExtension(["SMAP\nWeightMultiplier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WeightMultiplier.kt\ncom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,38:1\n1747#2,3:39\n2624#2,3:42\n*S KotlinDebug\n*F\n+ 1 WeightMultiplier.kt\ncom/cobblemon/mod/common/api/spawning/multiplier/WeightMultiplier\n*L\n34#1:39,3\n35#1:42,3\n*E\n"])
public class WeightMultiplier : SpawningInfluence {
   public final var anticonditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List
   public final var conditions: MutableList<SpawningCondition<*>> = (new ArrayList()) as java.util.List
   public final var multiplier: Float = 1.0F

   public override fun affectWeight(detail: SpawnDetail, ctx: SpawningContext, weight: Float): Float {
      var var18: Boolean;
      label64: {
         label63: {
            label73: {
               if (!this.conditions.isEmpty()) {
                  val `$this$none$iv`: java.lang.Iterable = this.conditions;
                  if (this.conditions is java.util.Collection && this.conditions.isEmpty()) {
                     var18 = false;
                  } else {
                     val var7: java.util.Iterator = `$this$none$iv`.iterator();

                     while (true) {
                        if (!var7.hasNext()) {
                           var18 = false;
                           break;
                        }

                        if ((var7.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                           var18 = true;
                           break;
                        }
                     }
                  }

                  if (!var18) {
                     break label73;
                  }
               }

               if (this.anticonditions.isEmpty()) {
                  break label63;
               }

               val var11: java.lang.Iterable = this.anticonditions;
               if (this.anticonditions is java.util.Collection && this.anticonditions.isEmpty()) {
                  var18 = true;
               } else {
                  val var13: java.util.Iterator = var11.iterator();

                  while (true) {
                     if (!var13.hasNext()) {
                        var18 = true;
                        break;
                     }

                     if ((var13.next() as SpawningCondition).isSatisfiedBy(ctx)) {
                        var18 = false;
                        break;
                     }
                  }
               }

               if (var18) {
                  break label63;
               }
            }

            var18 = false;
            break label64;
         }

         var18 = true;
      }

      return if (var18) this.multiplier * weight else weight;
   }

   override fun isExpired(): Boolean {
      return SpawningInfluence.DefaultImpls.isExpired(this);
   }

   override fun affectSpawnable(detail: SpawnDetail, ctx: SpawningContext): Boolean {
      return SpawningInfluence.DefaultImpls.affectSpawnable(this, detail, ctx);
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
