package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class CaughtPokemonCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) : CountableCriterionCondition(id, predicate) {
   public final var type: String = "any"

   public override fun toJson(json: JsonObject) {
      super.toJson(json);
      json.addProperty("type", this.type);
   }

   public override fun fromJson(json: JsonObject) {
      super.fromJson(json);
      val var10001: JsonElement = json.get("type");
      var var2: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var2 == null) {
         var2 = "any";
      }

      this.type = var2;
   }

   public open fun matches(player: ServerPlayer, context: CountablePokemonTypeContext): Boolean {
      return super.matches(player, context) && (context.getType() == this.type || this.type == "any");
   }
}
