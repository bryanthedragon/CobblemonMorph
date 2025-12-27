package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueAutoContinueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueNoInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOptionSetInput
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTextInput
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import java.lang.reflect.Type

public object DialogueInputAdapter : JsonDeserializer<DialogueInput> {
   public open fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): DialogueInput {
      if (!json.isJsonPrimitive() && !json.isJsonArray()) {
         val obj: JsonObject = json.getAsJsonObject();
         val typeId: java.lang.String = obj.get("type").getAsString();
         if (typeId != null) {
            var var7: DialogueInput;
            switch (typeId.hashCode()) {
               case -1010136971:
                  if (!typeId.equals("option")) {
                     throw new JsonParseException("Unknown dialogue input type $typeId");
                  }

                  val var9: Any = context.deserialize(obj as JsonElement, DialogueOptionSetInput::class.java);
                  var7 = var9 as DialogueInput;
                  break;
               case 3556653:
                  if (!typeId.equals("text")) {
                     throw new JsonParseException("Unknown dialogue input type $typeId");
                  }

                  val var8: Any = context.deserialize(obj as JsonElement, DialogueTextInput::class.java);
                  var7 = var8 as DialogueInput;
                  break;
               case 898090757:
                  if (typeId.equals("auto-continue")) {
                     val var10000: Any = context.deserialize(obj as JsonElement, DialogueAutoContinueInput::class.java);
                     var7 = var10000 as DialogueInput;
                     break;
                  }

                  throw new JsonParseException("Unknown dialogue input type $typeId");
               default:
                  throw new JsonParseException("Unknown dialogue input type $typeId");
            }

            return var7;
         } else {
            throw new JsonParseException("Unknown dialogue input type $typeId");
         }
      } else {
         val var10002: Any = context.deserialize(json, DialogueAction::class.java);
         return new DialogueNoInput(var10002 as DialogueAction);
      }
   }
}
