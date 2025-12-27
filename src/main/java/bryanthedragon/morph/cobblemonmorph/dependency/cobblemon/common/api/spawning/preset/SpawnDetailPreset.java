package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset
;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnBucket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.multiplier.WeightMultiplier;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MergeMode;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public abstract class SpawnDetailPreset {
   public final var anticondition: JsonObject?
   public final var bucket: SpawnBucket?
   public final var condition: JsonObject?
   public final var context: RegisteredSpawningContext<*>?
   public final var mergeMode: MergeMode = MergeMode.INSERT
   public final var percentage: Float?
   public final var spawnDetailType: String?
   public final var weight: Float?
   public final var weightMultipliers: MutableList<WeightMultiplier>?

   public open fun apply(spawnDetail: SpawnDetail) {
      if (this.bucket != null) {
         spawnDetail.setBucket(this.bucket);
      }

      if (this.context != null) {
         spawnDetail.setContext(this.context);
      }

      if (this.weight != null) {
         spawnDetail.setWeight(this.weight.floatValue());
      }

      if (this.percentage != null) {
         spawnDetail.setPercentage(this.percentage.floatValue());
      }

      this.mergeMode.merge(spawnDetail.getWeightMultipliers(), this.weightMultipliers);
      var var10000: SpawnDetailPreset = this;
      val var10001: java.util.List = spawnDetail.getConditions();
      val var10002: SpawningCondition;
      if (this.condition != null) {
         val var6: SpawningCondition = this.resolveCondition(spawnDetail, this.condition);
         var10000 = this;
         var10002 = var6;
      } else {
         var10002 = null;
      }

      var10000.applyToConditionList(var10001, var10002);
      if (this.anticondition != null) {
         val var11: JsonObject = this.anticondition;
         spawnDetail.getAnticonditions().add(this.resolveCondition(spawnDetail, var11));
      }
   }

   public fun applyToConditionList(conditions: MutableList<SpawningCondition<*>>, resolvedCondition: SpawningCondition<*>?) {
      if (resolvedCondition != null) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            (`element$iv` as SpawningCondition).copyFrom(resolvedCondition, this.mergeMode);
         }

         if (conditions.isEmpty()) {
            conditions.add(resolvedCondition);
         }
      }
   }

   public fun resolveCondition(spawnDetail: SpawnDetail, conditionJson: JsonObject): SpawningCondition<*> {
      SpawnLoader.INSTANCE.setDeserializingConditionClass(SpawningCondition.Companion.getByName(spawnDetail.getContext().getDefaultCondition()));
      val var10000: Any = SpawnLoader.INSTANCE.getGson().fromJson(conditionJson as JsonElement, SpawningCondition.class);
      return var10000 as SpawningCondition<?>;
   }
}
