package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation

public class RegistryElementAdapter<T>(registryProvider: () -> Registry<Any>) : JsonDeserializer<T>, JsonSerializer<T> {
   public final val registryProvider: () -> Registry<Any>

   init {
      this.registryProvider = registryProvider;
   }

   public open fun deserialize(jElement: JsonElement, type: Type, context: JsonDeserializationContext): Any {
      val identifier: ResourceLocation = context.deserialize(jElement, ResourceLocation::class.java) as ResourceLocation;
      val registry: Registry = this.registryProvider.invoke() as Registry;
      val var10000: Any = registry.m_7745_(identifier);
      if (var10000 == null) {
         throw new IllegalArgumentException("Cannot resolve element '$identifier' from ${registry.m_123023_().m_135782_()}");
      } else {
         return (T)var10000;
      }
   }

   public open fun serialize(element: Any, type: Type, context: JsonSerializationContext): JsonElement {
      val registry: Registry = this.registryProvider.invoke() as Registry;
      val var10000: ResourceLocation = registry.m_7981_(element);
      if (var10000 == null) {
         throw new IllegalArgumentException("Cannot resolve the identifier from the registry ${registry.m_123023_().m_135782_()} for $element");
      } else {
         return (new JsonPrimitive(var10000.toString())) as JsonElement;
      }
   }
}
