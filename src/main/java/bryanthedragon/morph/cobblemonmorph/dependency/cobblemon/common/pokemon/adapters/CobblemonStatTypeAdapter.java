package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type

public object CobblemonStatTypeAdapter : StatTypeAdapter {
   public open fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Stat {
      val var10000: java.lang.String = element.getAsString();
      return Cobblemon.INSTANCE
         .getStatProvider()
         .fromIdentifierOrThrow(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null));
   }

   public open fun serialize(stat: Stat, type: Type, context: JsonSerializationContext): JsonElement {
      return (new JsonPrimitive(stat.getIdentifier().toString())) as JsonElement;
   }
}
