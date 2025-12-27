package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object VerboseIntRangeAdapter : JsonDeserializer<IntRange>, JsonSerializer<IntRange> {
   private const val MAX: String = "max"
   private const val MIN: String = "min"

   public open fun deserialize(jElement: JsonElement, type: Type, context: JsonDeserializationContext): IntRange {
      val json: JsonObject = jElement.getAsJsonObject();
      return new IntRange(json.get("min").getAsInt(), json.get("max").getAsInt());
   }

   public open fun serialize(range: IntRange, type: Type, context: JsonSerializationContext): JsonObject {
      val var4: JsonObject = new JsonObject();
      var4.addProperty("min", range.getFirst());
      var4.addProperty("max", range.getLast());
      return var4;
   }
}
