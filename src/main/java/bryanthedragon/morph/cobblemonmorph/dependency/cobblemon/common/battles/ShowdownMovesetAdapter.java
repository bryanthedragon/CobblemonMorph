package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

public object ShowdownMovesetAdapter : JsonDeserializer<ShowdownMoveset> {
   public final val gson: Gson = new GsonBuilder().addDeserializationExclusionStrategy(ShowdownMovesetAdapter.MovesetExclusionStrategy.INSTANCE).create()

   public open fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): ShowdownMoveset {
      val json: JsonObject = jsonElement.getAsJsonObject();
      val moveset: ShowdownMoveset = gson.fromJson(json as JsonElement, ShowdownMoveset.class) as ShowdownMoveset;
      val var10000: JsonElement = json.get("maxMoves");
      if (var10000 != null) {
         val var9: JsonObject = var10000.getAsJsonObject();
         if (var9 != null) {
            moveset.setMaxMoves(
               new Gson().fromJson(var9.get("maxMoves"), (new TypeToken<java.util.List<? extends InBattleGimmickMove>>() {}).getType()) as MutableList<InBattleGimmickMove>
            );
         }
      }

      moveset.setGimmickMapping();
      return moveset;
   }

   public object MovesetExclusionStrategy : ExclusionStrategy {
      public open fun shouldSkipField(field: FieldAttributes?): Boolean {
         return (if (field != null) field.getName() else null) == "maxMoves";
      }

      public open fun shouldSkipClass(p0: Class<*>?): Boolean {
         return false;
      }
   }
}
