package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type
import net.minecraft.world.phys.AABB

public object BoxAdapter : JsonDeserializer<AABB> {
   public open fun deserialize(json: JsonElement, type: Type, ctx: JsonDeserializationContext): AABB {
      val var10002: JsonElement = (json as JsonObject).get("minX");
      val var4: Double = if (var10002 != null) var10002.getAsDouble() else -9999999.0;
      val var10003: JsonElement = (json as JsonObject).get("minY");
      val var5: Double = if (var10003 != null) var10003.getAsDouble() else 0.0;
      val var10004: JsonElement = (json as JsonObject).get("minZ");
      val var6: Double = if (var10004 != null) var10004.getAsDouble() else -9999999.0;
      val var10005: JsonElement = (json as JsonObject).get("maxX");
      val var7: Double = if (var10005 != null) var10005.getAsDouble() else 9999999.0;
      val var10006: JsonElement = (json as JsonObject).get("maxY");
      val var8: Double = if (var10006 != null) var10006.getAsDouble() else 9999999.0;
      val var10007: JsonElement = (json as JsonObject).get("maxZ");
      return new AABB(var4, var5, var6, var7, var8, if (var10007 != null) var10007.getAsDouble() else 9999999.0);
   }
}
