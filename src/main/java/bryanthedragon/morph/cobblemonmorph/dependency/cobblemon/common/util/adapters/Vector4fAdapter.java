package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import org.joml.Vector4f

public object Vector4fAdapter : JsonDeserializer<Vector4f>, JsonSerializer<Vector4f> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Vector4f {
      return new Vector4f(
         (json as JsonArray).get(0).getAsFloat(),
         (json as JsonArray).get(1).getAsFloat(),
         (json as JsonArray).get(2).getAsFloat(),
         (json as JsonArray).get(3).getAsFloat()
      );
   }

   public open fun serialize(src: Vector4f, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
      val var4: JsonArray = new JsonArray();
      var4.add(src.x);
      var4.add(src.y);
      var4.add(src.z);
      var4.add(src.w);
      return var4 as JsonElement;
   }
}
