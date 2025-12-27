package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.functions.Function1

public class StringIdentifiedObjectAdapter<T>(fromString: (String) -> Any) : JsonDeserializer<T> {
   public final val fromString: (String) -> Any

   init {
      this.fromString = fromString;
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Any {
      val var10000: Function1 = this.fromString;
      val var10001: java.lang.String = json.getAsString();
      return (T)var10000.invoke(var10001);
   }
}
