package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class PokemonInteractCriterion(id: ResourceLocation, entity: ContextAwarePredicate) : SimpleCriterionCondition(id, entity) {
   public final var item: String = "any"
   public final var type: String = "any"

   public override fun toJson(json: JsonObject) {
      json.addProperty("type", this.type);
      json.addProperty("item", this.item);
   }

   public override fun fromJson(json: JsonObject) {
      var var10001: JsonElement = json.get("type");
      var var2: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var2 == null) {
         var2 = "any";
      }

      this.type = var2;
      var10001 = json.get("item");
      var var4: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var4 == null) {
         var4 = "any";
      }

      this.item = var4;
   }

   public open fun matches(player: ServerPlayer, context: PokemonInteractContext): Boolean {
      return (context.getType() == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.type, null, 1, null) || this.type == "any")
         && (context.getItem() == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.item, null, 1, null) || this.item == "any");
   }
}
