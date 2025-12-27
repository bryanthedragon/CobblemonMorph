package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import net.minecraft.world.phys.Vec3

public object VerboseVec3dAdapter : JsonDeserializer<Vec3>, JsonSerializer<Vec3> {
   private const val X: String = "x"
   private const val Y: String = "y"
   private const val Z: String = "z"

   public open fun deserialize(jElement: JsonElement, type: Type, context: JsonDeserializationContext): Vec3 {
      val json: JsonObject = jElement.getAsJsonObject();
      return new Vec3(json.get("x").getAsDouble(), json.get("y").getAsDouble(), json.get("z").getAsDouble());
   }

   public open fun serialize(vec: Vec3, type: Type, context: JsonSerializationContext): JsonObject {
      val var4: JsonObject = new JsonObject();
      var4.addProperty("x", vec.f_82479_);
      var4.addProperty("y", vec.f_82480_);
      var4.addProperty("z", vec.f_82481_);
      return var4;
   }
}
