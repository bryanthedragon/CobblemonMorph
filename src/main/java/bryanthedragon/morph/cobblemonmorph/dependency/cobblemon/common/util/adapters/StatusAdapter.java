package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.resources.ResourceLocation

public object StatusAdapter : JsonDeserializer<Status>, JsonSerializer<Status> {
   public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Status {
      val var10000: java.lang.String = element.getAsString();
      val id: ResourceLocation = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null);
      val status: Status = Statuses.INSTANCE.getStatus(id);
      if (status == null) {
         throw new IllegalArgumentException("There is no status with the ID $id");
      } else {
         return status;
      }
   }

   public open fun serialize(status: Status, type: Type, context: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(status.getName().toString())) as JsonElement;
   }
}
