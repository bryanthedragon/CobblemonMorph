package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import org.joml.Vector3f

public object Vector3fAdapter : JsonDeserializer<Vector3f>, JsonSerializer<Vector3f> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Vector3f {
      return new Vector3f((json as JsonArray).get(0).getAsFloat(), (json as JsonArray).get(1).getAsFloat(), (json as JsonArray).get(2).getAsFloat());
   }

   public open fun serialize(src: Vector3f, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
      val var4: JsonArray = new JsonArray();
      var4.add(src.x);
      var4.add(src.y);
      var4.add(src.z);
      return var4 as JsonElement;
   }
}
