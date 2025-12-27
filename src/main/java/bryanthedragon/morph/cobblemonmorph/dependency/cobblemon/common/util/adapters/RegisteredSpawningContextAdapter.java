package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.RegisteredSpawningContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object RegisteredSpawningContextAdapter : JsonSerializer<RegisteredSpawningContext<?>>, JsonDeserializer<RegisteredSpawningContext<?>> {
   public open fun serialize(rctx: RegisteredSpawningContext<*>, type: Type, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(rctx.getName());
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): RegisteredSpawningContext<*> {
      val var10000: SpawningContext.Companion = SpawningContext.Companion;
      val var10001: java.lang.String = json.getAsString();
      val var4: RegisteredSpawningContext = var10000.getByName(var10001);
      if (var4 == null) {
         throw new IllegalArgumentException("No such spawning context: ${json.getAsString()}");
      } else {
         return var4;
      }
   }
}
