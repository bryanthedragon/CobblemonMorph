package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object DamageCategoryAdapter : JsonSerializer<DamageCategory>, JsonDeserializer<DamageCategory> {
   public open fun serialize(src: DamageCategory, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(src.getName())) as JsonElement;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DamageCategory {
      val var10000: DamageCategories = DamageCategories.INSTANCE;
      val var10001: java.lang.String = json.getAsString();
      return var10000.getOrException(var10001);
   }
}
