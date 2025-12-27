package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import kotlin.jvm.functions.Function2

public object LearnsetAdapter : JsonDeserializer<Learnset> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): Learnset {
      val array: JsonArray = json.getAsJsonArray();
      val learnset: Learnset = new Learnset();

      for (JsonElement element : array) {
         var added: Boolean = false;

         for (Learnset.Interpreter interpreter : Learnset.Companion.getInterpreters()) {
            val var10000: Function2 = interpreter.getLoadMove();
            if (var10000.invoke(element, learnset) as java.lang.Boolean) {
               added = true;
               break;
            }
         }

         if (!added) {
            Cobblemon.INSTANCE.getLOGGER().error("Unable to load entry from learnset: $element");
         }
      }

      return learnset;
   }
}
