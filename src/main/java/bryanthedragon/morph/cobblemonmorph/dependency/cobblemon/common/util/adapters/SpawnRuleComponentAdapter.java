package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

public object SpawnRuleComponentAdapter : JsonDeserializer<SpawnRuleComponent> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnRuleComponent {
      val typex: java.lang.String = (json as JsonObject).get("type").getAsString();
      var var10000: Class = SpawnRuleComponent.Companion.getTypes().get(typex);
      if (var10000 == null) {
         throw new IllegalArgumentException("Unknown spawn rule component type: $typex");
      } else {
         var10000 = (Class)ctx.deserialize(json, var10000);
         return var10000 as SpawnRuleComponent;
      }
   }
}
