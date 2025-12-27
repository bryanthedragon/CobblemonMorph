package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.keyframes.ActionEffectKeyframe
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

public object ActionEffectKeyframeAdapter : JsonDeserializer<ActionEffectKeyframe> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): ActionEffectKeyframe {
      val var9: ActionEffectKeyframe;
      if (json.isJsonPrimitive()) {
         val var10000: Class = ActionEffectKeyframe.Companion.getTypes().get(json.getAsString());
         if (var10000 == null) {
            throw new IllegalArgumentException("Unrecognized action effect keyframe type: ${json.getAsJsonPrimitive()}");
         }

         val var8: Any = var10000.getConstructor().newInstance();
         var9 = var8 as ActionEffectKeyframe;
      } else {
         val var7: java.lang.String = (json as JsonObject).get("type").getAsString();
         val var10: Class = ActionEffectKeyframe.Companion.getTypes().get(var7);
         if (var10 == null) {
            throw new IllegalArgumentException("Unrecognized action effect keyframe type: $var7");
         }

         val var4: Any = ctx.deserialize(json, var10);
         var9 = var4 as ActionEffectKeyframe;
      }

      return var9;
   }
}
