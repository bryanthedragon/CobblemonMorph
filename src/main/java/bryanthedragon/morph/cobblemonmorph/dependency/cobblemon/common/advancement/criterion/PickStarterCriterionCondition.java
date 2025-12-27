package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.advancements.critereon.ContextAwarePredicate
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer

public class PickStarterCriterionCondition(id: ResourceLocation, predicate: ContextAwarePredicate) : SimpleCriterionCondition(id, predicate) {
   public final var properties: PokemonProperties = new PokemonProperties()

   public override fun toJson(json: JsonObject) {
      json.addProperty("properties", this.properties.getOriginalString());
   }

   public override fun fromJson(json: JsonObject) {
      val var10001: PokemonProperties.Companion = PokemonProperties.Companion;
      val var10002: JsonElement = json.get("properties");
      var var2: java.lang.String = if (var10002 != null) var10002.getAsString() else null;
      if (var2 == null) {
         var2 = "";
      }

      this.properties = PokemonProperties.Companion.parse$default(var10001, var2, null, null, 6, null);
   }

   public open fun matches(player: ServerPlayer, context: Pokemon): Boolean {
      return this.properties.matches(context);
   }
}
