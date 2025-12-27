package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

public object ExperienceGroupAdapter : JsonSerializer<ExperienceGroup>, JsonDeserializer<ExperienceGroup> {
   public open fun serialize(experienceGroup: ExperienceGroup, type: Type, ctx: JsonSerializationContext): JsonPrimitive {
      return new JsonPrimitive(experienceGroup.getName());
   }

   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): ExperienceGroup {
      val var10000: ExperienceGroups = ExperienceGroups.INSTANCE;
      var var10001: java.lang.String = json.getAsString();
      var var4: ExperienceGroup = var10000.findByName(var10001);
      if (var4 == null) {
         val var5: ExperienceGroup.Companion = ExperienceGroup.Companion;
         var10001 = json.getAsString();
         var4 = var5.dummy(var10001);
      }

      return var4;
   }
}
