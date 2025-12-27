package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public open class PokemonPropertiesAdapter(saveLong: Boolean) : JsonSerializer<PokemonProperties>, JsonDeserializer<PokemonProperties> {
   public final val saveLong: Boolean

   init {
      this.saveLong = saveLong;
   }

   public open fun serialize(props: PokemonProperties, type: Type, ctx: JsonSerializationContext): JsonElement {
      return if (this.saveLong) props.saveToJSON() as JsonElement else (new JsonPrimitive(props.getOriginalString())) as JsonElement;
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): PokemonProperties {
      var var4: PokemonProperties;
      if (json.isJsonPrimitive()) {
         val var10000: PokemonProperties.Companion = PokemonProperties.Companion;
         val var10001: java.lang.String = json.getAsString();
         var4 = PokemonProperties.Companion.parse$default(var10000, var10001, null, null, 6, null);
      } else {
         var4 = new PokemonProperties();
         val var6: JsonObject = json.getAsJsonObject();
         var4 = var4.loadFromJSON(var6);
      }

      return var4;
   }
}
