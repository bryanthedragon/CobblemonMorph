package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.SpawnDetailPreset
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

public object SpawnDetailPresetAdapter : JsonDeserializer<SpawnDetailPreset> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): SpawnDetailPreset {
      val var10000: JsonElement = (json as JsonObject).get("type");
      var var6: java.lang.String = if (var10000 != null) var10000.getAsString() else null;
      if (var6 == null) {
         var6 = "basic";
      }

      val var7: Class = SpawnDetailPresets.INSTANCE.getPresetTypes().get(var6);
      if (var7 == null) {
         throw new IllegalStateException("Unrecognized preset type: $var6");
      } else {
         val var8: Any = ctx.deserialize(json, var7);
         return var8 as SpawnDetailPreset;
      }
   }
}
