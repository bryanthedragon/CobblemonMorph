package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.awt.Color
import java.lang.reflect.Type

public object LiteralHexColorAdapter : JsonDeserializer<Color>, JsonSerializer<Color> {
   public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Color {
      val var10002: java.lang.String = element.getAsString();
      return new Color(Integer.parseInt(StringsKt.removePrefix(var10002, "#"), CharsKt.checkRadix(16)));
   }

   public open fun serialize(color: Color, type: Type, context: JsonSerializationContext): JsonPrimitive {
      val var10002: java.lang.String = Integer.toString(color.getRGB(), CharsKt.checkRadix(16));
      return new JsonPrimitive("#$var10002");
   }
}
