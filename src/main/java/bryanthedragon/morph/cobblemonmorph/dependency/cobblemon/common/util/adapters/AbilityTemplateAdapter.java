package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityTemplate
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object AbilityTemplateAdapter : JsonSerializer<AbilityTemplate>, JsonDeserializer<AbilityTemplate> {
   public open fun serialize(src: AbilityTemplate, type: Type, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(src.getName());
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): AbilityTemplate {
      val var10000: Abilities = Abilities.INSTANCE;
      val var10001: java.lang.String = json.getAsString();
      return var10000.getOrException(var10001);
   }
}
