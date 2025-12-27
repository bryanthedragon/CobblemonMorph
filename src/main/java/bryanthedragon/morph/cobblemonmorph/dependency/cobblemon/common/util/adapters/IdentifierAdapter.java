package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.resources.ResourceLocation

public object IdentifierAdapter : JsonSerializer<ResourceLocation>, JsonDeserializer<ResourceLocation> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): ResourceLocation {
      return new ResourceLocation(json.getAsString());
   }

   public open fun serialize(src: ResourceLocation, type: Type, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(src.toString());
   }
}
