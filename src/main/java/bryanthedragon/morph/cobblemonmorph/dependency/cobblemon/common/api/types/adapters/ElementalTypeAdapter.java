package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object ElementalTypeAdapter : JsonSerializer<ElementalType>, JsonDeserializer<ElementalType> {
   public open fun serialize(src: ElementalType, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(src.getName())) as JsonElement;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): ElementalType {
      val var10000: ElementalTypes = ElementalTypes.INSTANCE;
      val var10001: java.lang.String = json.getAsString();
      return var10000.getOrException(var10001);
   }
}
