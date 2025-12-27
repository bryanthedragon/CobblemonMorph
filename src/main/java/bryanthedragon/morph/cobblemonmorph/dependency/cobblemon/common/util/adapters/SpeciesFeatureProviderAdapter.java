package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

public object SpeciesFeatureProviderAdapter : JsonDeserializer<SpeciesFeatureProvider<?>> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpeciesFeatureProvider<*> {
      val typeName: java.lang.String = json.getAsJsonObject().get("type").getAsString();
      var var10000: Class = SpeciesFeatures.INSTANCE.getTypes().get(typeName);
      if (var10000 == null) {
         throw new IllegalArgumentException("No type registered in SpeciesFeatures for name: $typeName");
      } else {
         var10000 = (Class)ctx.deserialize(json, var10000);
         return var10000 as SpeciesFeatureProvider<?>;
      }
   }
}
