package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

public class ClassMapAdapter<T, K>(mapping: MutableMap<Any, Class<out Any>>, keyFromElement: (JsonElement) -> Any) : JsonDeserializer<T> {
   public final val keyFromElement: (JsonElement) -> Any
   public final val mapping: MutableMap<Any, Class<out Any>>

   init {
      this.mapping = mapping;
      this.keyFromElement = keyFromElement;
   }

   public open fun deserialize(json: JsonElement, typeOfT: Type, ctx: JsonDeserializationContext): Any {
      val key: Any = this.keyFromElement.invoke(json);
      val var10000: Class = this.mapping.get(key);
      if (var10000 == null) {
         throw new IllegalStateException("Could not find class registered for key: $key");
      } else {
         return (T)ctx.deserialize(json, var10000);
      }
   }
}
