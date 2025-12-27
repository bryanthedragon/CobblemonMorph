package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.TagKey

public class TagKeyAdapter<T>(key: ResourceKey<Registry<Any>>) : JsonDeserializer<TagKey<T>>, JsonSerializer<TagKey<T>> {
   private final val key: ResourceKey<Registry<Any>>

   init {
      this.key = key;
   }

   public open fun deserialize(element: JsonElement, type: Type, ctx: JsonDeserializationContext): TagKey<Any> {
      val var10000: TagKey = TagKey.m_203882_(this.key, new ResourceLocation(element.getAsString()));
      return var10000;
   }

   public open fun serialize(tagKey: TagKey<Any>, type: Type, ctx: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(tagKey.f_203868_().toString())) as JsonElement;
   }
}
