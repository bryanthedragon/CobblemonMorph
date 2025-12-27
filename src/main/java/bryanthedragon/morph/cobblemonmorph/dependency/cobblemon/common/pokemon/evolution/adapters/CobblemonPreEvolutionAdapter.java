package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.CobblemonLazyPreEvolution
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object CobblemonPreEvolutionAdapter : JsonDeserializer<PreEvolution>, JsonSerializer<PreEvolution> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): PreEvolution {
      val var10002: java.lang.String = json.getAsString();
      return new CobblemonLazyPreEvolution(var10002);
   }

   public open fun serialize(src: PreEvolution, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
      return if (src.getForm() == src.getSpecies().getStandardForm())
         (new JsonPrimitive(src.getSpecies().getResourceIdentifier().toString())) as JsonElement
         else
         (new JsonPrimitive("${src.getSpecies().getResourceIdentifier()} form=${src.getForm().getName()}")) as JsonElement;
   }
}
