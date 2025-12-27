package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.world.entity.EntityDimensions

public object EntityDimensionsAdapter : JsonSerializer<EntityDimensions>, JsonDeserializer<EntityDimensions> {
   public const val HEIGHT: String = "height"
   public const val WIDTH: String = "width"

   public open fun serialize(dimensions: EntityDimensions, type: Type, ctx: JsonSerializationContext): JsonElement {
      val json: JsonObject = new JsonObject();
      json.addProperty("width", dimensions.f_20377_);
      json.addProperty("height", dimensions.f_20378_);
      return json as JsonElement;
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): EntityDimensions {
      return new EntityDimensions((json as JsonObject).get("width").getAsFloat(), (json as JsonObject).get("height").getAsFloat(), false);
   }
}
