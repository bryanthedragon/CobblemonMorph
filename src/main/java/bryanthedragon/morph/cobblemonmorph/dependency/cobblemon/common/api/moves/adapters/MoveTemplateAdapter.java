package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object MoveTemplateAdapter : JsonSerializer<MoveTemplate>, JsonDeserializer<MoveTemplate> {
   public open fun serialize(template: MoveTemplate, type: Type?, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(template.getName());
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): MoveTemplate {
      val var10000: Moves = Moves.INSTANCE;
      val var10001: java.lang.String = json.getAsString();
      var var4: MoveTemplate = var10000.getByName(var10001);
      if (var4 == null) {
         var4 = Moves.INSTANCE.getExceptional();
      }

      return var4;
   }
}
