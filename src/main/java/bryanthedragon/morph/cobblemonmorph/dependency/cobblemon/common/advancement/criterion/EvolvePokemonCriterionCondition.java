package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class EvolvePokemonCriterionCondition(id: ResourceLocation, entity: ContextAwarePredicate) : CountableCriterionCondition(id, entity) {
   public final var evolution: String = "any"
   public final var species: String = "any"

   public override fun toJson(json: JsonObject) {
      super.toJson(json);
      json.addProperty("species", this.species);
      json.addProperty("evolution", this.evolution);
   }

   public override fun fromJson(json: JsonObject) {
      super.fromJson(json);
      var var10001: JsonElement = json.get("species");
      var var2: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var2 == null) {
         var2 = "any";
      }

      this.species = var2;
      var10001 = json.get("evolution");
      var var4: java.lang.String = if (var10001 != null) var10001.getAsString() else null;
      if (var4 == null) {
         var4 = "any";
      }

      this.evolution = var4;
   }

   public open fun matches(player: ServerPlayer, context: EvolvePokemonContext): Boolean {
      return context.getTimes() >= this.getCount()
         && (context.getSpecies() == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.species, null, 1, null) || this.species == "any")
         && (
            context.getEvolution() == ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(this.evolution, null, 1, null)
               || this.evolution == "any"
         );
   }
}
