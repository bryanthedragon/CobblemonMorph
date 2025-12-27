package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public abstract class CountableCriterionCondition<T extends CountableContext> : SimpleCriterionCondition<T> {
   public final var count: Int

   open fun CountableCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) {
      super(id, predicate);
   }

   public override fun fromJson(json: JsonObject) {
      val var10001: JsonElement = json.get("count");
      this.count = if (var10001 != null) var10001.getAsInt() else 0;
   }

   public override fun toJson(json: JsonObject) {
      json.addProperty("count", this.count);
   }

   public open fun matches(player: ServerPlayer, context: Any): Boolean {
      return context.getTimes() >= this.count;
   }
}
