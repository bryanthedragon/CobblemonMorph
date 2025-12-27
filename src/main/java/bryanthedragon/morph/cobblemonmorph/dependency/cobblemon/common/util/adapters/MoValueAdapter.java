package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.bedrockk.molang.runtime.value.MoValue
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object MoValueAdapter : JsonSerializer<MoValue>, JsonDeserializer<MoValue> {
   public open fun serialize(src: MoValue, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      var var10000: JsonElement = MoValue.writeToJson(src);
      if (var10000 == null) {
         var10000 = (new JsonObject()) as JsonElement;
      }

      return var10000;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): MoValue {
      val var10000: MoValue = MoValue.of(json);
      return var10000;
   }
}
